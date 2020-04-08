package FormulaRemodelers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Operators.OperatorPrecedence;
import Operators.TypeTester;

public class PostfixNotation implements FormulaRemodeler {

	@Override
	public String remodel(String formula) {
		TypeTester operatorTester=new TypeTester();
		OperatorPrecedence precedence=new OperatorPrecedence(true);
		Stack<Character> operators = new Stack<Character>();
		Queue<Character> output = new LinkedList<Character>();
		int i = 0;
		while (i < formula.length()) {
			if (Character.isLetter(formula.charAt(i))) {
				output.add(formula.charAt(i));
			} else if (operatorTester.isOperator(formula.charAt(i))) {
				while (((!operators.empty() && precedence.getPrecedence(operators.peek()) < precedence.getPrecedence(formula.charAt(i)))
						|| (!operators.empty() && formula.charAt(i) == operators.peek() && formula.charAt(i) != '!'))
						&& operators.peek() != '(') {
					output.add(operators.pop());
				}
				operators.push(formula.charAt(i));

			}

			else if (formula.charAt(i) == '(') {
				operators.push(formula.charAt(i));

			}

			else if (formula.charAt(i) == ')') {

				while (operators.peek() != '(') {
					if (operators.empty()) {
						System.out.println("Exista o paranteza fara pereche!");
						System.exit(-1);
					}
					output.add(operators.pop());
				}
				operators.pop();
			}
			i++;
		}

		if (i == formula.length()) {
			while (!operators.empty()) {
				if (operators.peek() == ')' || operators.peek() == '(') {
					System.out.println("Exista o paranteza fara pereche!");
					System.exit(-1);
				}
				output.add(operators.pop());

			}

		}

		String postfix = new String();
		while(!output.isEmpty())
	    {
	        postfix+=output.peek();
	        output.remove();

	    }

		return postfix;

	}
	

}
