package FormulaRemodelers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import Operators.CorrespondingConnector;
import Operators.OperatorPrecedence;
import Operators.TypeTesterFirstOrderLogic;
import Util.FunctionParsing;

public class PostfixNotationFOL {
	
	public static List<String> shuntingYard(String formula) {
		TypeTesterFirstOrderLogic operatorType=new TypeTesterFirstOrderLogic();
		OperatorPrecedence precedence=new OperatorPrecedence(false);
		Stack<String> operators = new Stack<String>();
		Queue<String> output = new LinkedList<String>();
		int i = 0;
		String current=new String();
		formula=formula.trim().replaceAll(" ","");
		formula=new ConnectorReplacer().remodel(formula);
		while (i < formula.length()) {
			if (Character.isLetter(formula.charAt(i)) && formula.charAt(i)!='E' && formula.charAt(i)!='V') {
				while(true)
				{
					current=current+formula.charAt(i);
					i++; 
					if(formula.charAt(i-1)==')' && FunctionParsing.parathesisDifference(current)==0)
						break;
					if(i==formula.length())
						break;
				}
				output.add(current);
				current="";
			}
			else if(formula.charAt(i)=='E' || formula.charAt(i)=='V')
			{
				while(formula.charAt(i)!='.')
				{
					current=current+formula.charAt(i);
					i++;
				}
				current=current+formula.charAt(i);
				operators.add(current);
				current="";
				i++;
			}
			else if (operatorType.isOperator(formula.charAt(i))) {
				Character operator=new Character(formula.charAt(i));
				while (!operators.empty() && precedence.getPrecedence(CorrespondingConnector.getRemodeledConnector(operators.peek())) 
						< precedence.getPrecedence(operator) && !operators.peek().equals("(") || (!operators.empty() && precedence.getPrecedence(CorrespondingConnector.getRemodeledConnector(operators.peek())) 
								== precedence.getPrecedence(operator) && operator!='!')) {
					output.add(operators.pop());
				}
				operators.push(CorrespondingConnector.getOriginalConnector(operator));
				i++;

			}

			else if (formula.charAt(i) == '(') {
				operators.push(new Character(formula.charAt(i)).toString());
				i++;
			}

			else if (formula.charAt(i) == ')') {
				while (!operators.peek().equals("(")) {
					if (operators.empty()) {
						System.out.println("Exista o paranteza fara pereche!");
						System.exit(-1);
					}
					output.add(operators.pop());
				}
				operators.pop();
				i++;
			}
		}
		if (i == formula.length()) {
			while (!operators.empty()) {
				if (operators.peek().equals(")") || operators.peek().equals("(")) {
					System.out.println("Exista o paranteza fara pereche!");
					System.exit(-1);
				}
				output.add(operators.pop());

			}

		}
		List<String> postfix = new ArrayList<String>();
		while(!output.isEmpty())
	    {
	        postfix.add(output.peek());
			output.remove();

	    }

		return postfix;
	}
	

}
