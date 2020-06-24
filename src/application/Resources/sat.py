import json
import re
import sys


def load_formula(path):
    try:
        assignation_file = open(path, 'rb')
        assignation = json.load(assignation_file)
        assignation_file.close()
        return assignation
    except Exception as e:
        print("Error loading the formula :\n" + str(e))
        exit(-1)


def load_domains(domains_path):
    try:
        file_domains = open(domains_path).read()
        domains = dict()
        exec (file_domains, globals(), domains)
        return domains
    except Exception as e:
        print("Error loading domains :\n" + str(e))
        exit(-1)


def load_assignation(assignation_path):
    try:
        file_domains = open(assignation_path).read()
        assignation = dict()
        exec (file_domains, globals(), assignation)
        return assignation
    except Exception as e:
        print("Error loading assignations :\n" + str(e))
        exit(-1)


def parse_simple_function(function):
    declaration_and_implementation = re.split('=', function, 1)
    return 'def ' + declaration_and_implementation[0] + ':' + '\n\treturn ' + declaration_and_implementation[1]


def parse_complex_function(function):
    declaration_and_implementation = re.split('=', function, 1)
    pyhton_function = "def " + declaration_and_implementation[0] + ' :\n'
    function_cases = re.split(',', declaration_and_implementation[1])
    for case_and_return in function_cases:
        separated = re.split(':', case_and_return)
        case = separated[0]
        return_value = separated[1]
        pyhton_function += '\tif ' + case + ':\n' + '\t\treturn ' + return_value + '\n'
    return pyhton_function


def load_functions_and_predicates(file_path):
    try:
        functions_and_predicates = open(file_path).readlines()
        implementations = []
        boolean_return = '(True|False)'
        expression_term = '(' + '[a-zA-Z]' + '|' + '((-?[1-9][0-9]*)|0)' + '|' + '\'[a-zA-Z0-9]+\'' + ')'
        arithmetic_expression = '(' + expression_term + '(\s*(\+|\-|\*|\*\*|//|/)\s*' + expression_term + ')*?)'
        declaration = "^[a-zA-Z]+\(\w*(,\w+)*\)\s*=\s*"
        comparision_operator = '(==|!=|<=|>=|<|>)'
        comparision = '(' + arithmetic_expression + "\s*" + comparision_operator + "\s*" + arithmetic_expression + ')'
        simple_function = '^' + declaration + '(' + arithmetic_expression + ')' + '$'
        simple_predicate = '^' + declaration + '(' + comparision + ')' + '$'
        complex_function = '^' + declaration + '(' + comparision + ':' + arithmetic_expression + ')' + '(,' + comparision + ':' + arithmetic_expression + ')' + '*' + '$'
        complex_predicate = '^' + declaration + '(' + comparision + ':' + boolean_return + ')' + '(,' + comparision + ':' + boolean_return + ')' + '*' + '$'
        for func_pred in functions_and_predicates:
            func_pred = func_pred.strip()
            if re.match(simple_function, func_pred) or re.match(simple_predicate, func_pred):
                implementations.append(parse_simple_function(func_pred))
            elif re.match(complex_function, func_pred) or re.match(complex_predicate, func_pred):
                implementations.append(parse_complex_function(func_pred))
        return implementations
    except Exception as e:
        print("Error loading implementations :\n" + str(e))
        exit(-1)


def evaluate_formula(formula, assignation, domains):
    try:
        if formula['rawTerm'] == '!':
            return not evaluate_formula(formula['subTerms'][0], assignation, domains)
        elif formula['rawTerm'] == '\\/':
            return evaluate_formula(formula['subTerms'][0], assignation, domains) or evaluate_formula(
                formula['subTerms'][1], assignation, domains)
        elif formula['rawTerm'] == '/\\':
            return evaluate_formula(formula['subTerms'][0], assignation, domains) and evaluate_formula(
                formula['subTerms'][1], assignation, domains)
        elif formula['rawTerm'] == '->':
            return not evaluate_formula(formula['subTerms'][0], assignation, domains) or evaluate_formula(
                formula['subTerms'][1], assignation, domains)
        elif formula['rawTerm'] == '<->':
            return (not evaluate_formula(formula['subTerms'][0], assignation, domains) or evaluate_formula(
                formula['subTerms'][1], assignation, domains)) \
                   and (evaluate_formula(formula['subTerms'][0], assignation, domains) or not evaluate_formula(
                formula['subTerms'][1], assignation, domains))
        elif re.match('V\w+[.]', formula['rawTerm']):
            bound_variable = formula['rawTerm'][1:-1]
            new_assignation = dict(assignation)
            for value in domains[bound_variable]:
                new_assignation[bound_variable] = value
                if not evaluate_formula(formula['subTerms'][0], new_assignation, domains):
                    return False
            return True
        elif re.match('E\w+[.]', formula['rawTerm']):
            bound_variable = formula['rawTerm'][1:-1]
            new_assignation = dict(assignation)
            for value in domains[bound_variable]:
                new_assignation[bound_variable] = value
                if evaluate_formula(formula['subTerms'][0], new_assignation, domains):
                    return True
            return False
        else:
            predicate_and_arguments = re.split('\(', formula['rawTerm'], 1)
            predicate_and_arguments[1] = predicate_and_arguments[1][:-1]
            for variable in assignation:
                value = None
                if isinstance(assignation[variable], str):
                    value = '"' + assignation[variable] + '"'
                else:
                    value = assignation[variable]
                predicate_and_arguments[1] = predicate_and_arguments[1].replace(variable, str(value))
            result = eval(predicate_and_arguments[0] + '(' + predicate_and_arguments[1] + ')')
            if result is not True and result is not False:
                raise Exception(predicate_and_arguments[0] + " does not have a boolean return type")
            return result
    except Exception as e:
        print("Error evaluating the formula :\n" + str(e))
        exit(-1)


def start_evaluation(formula_path, implementations_path, assignation_path, domains_path):
    try:
        for function_or_predicate in load_functions_and_predicates(implementations_path):
            exec (function_or_predicate, globals())
        formula = load_formula(formula_path)
        domains = load_domains(domains_path)
        assignation = load_assignation(assignation_path)
        result = evaluate_formula(formula, assignation, domains)
        return result
    except Exception as e:
        print("Error evaluating the formula :\n" + str(e))
        exit(-1)


print(start_evaluation(sys.argv[1],
                       sys.argv[2],
                       sys.argv[3], sys.argv[4]))
