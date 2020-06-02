package NormalForms;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;
import Operators.TypeTesterFirstOrderLogic;

public class PrenexNormalForm {
	
	public static boolean testPrenexNormalForm(FOLFormula formula)
	{
		return testPrenexNormalFormRecursive(formula.syntaxTree.getRoot());
	}
	private static boolean testPrenexNormalFormRecursive(FOLTreeNode currentNode)
	{
		if((!currentNode.isConnector() && !currentNode.isVariable()) || currentNode.isVariable())
		{
			return true;
		}
		else
		{
			if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLabel()))
			{
				return testPrenexNormalFormRecursive(currentNode.getLeftChild());
			}
			else if(currentNode.getLabel().equals("!"))
			{
				if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLeftChild().getLabel()))
				{
					return false;
				}
				else
				{
					return testPrenexNormalFormRecursive(currentNode.getLeftChild());
				}
			}
			else
			{
				boolean cuantifierLeft=TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLeftChild().getLabel());
				boolean cuantifierRight=TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getRightChild().getLabel());
				if(cuantifierRight || cuantifierLeft)
				{
					return false;
				}
				else
				{
					return testPrenexNormalFormRecursive(currentNode.getLeftChild()) && testPrenexNormalFormRecursive(currentNode.getRightChild());
				}
			}
		}
	}

}
