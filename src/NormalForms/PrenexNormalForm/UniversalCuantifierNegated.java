package NormalForms.PrenexNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;

public class UniversalCuantifierNegated implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		if(!formula.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return false;
		}
		else
		{
			String negated=formula.syntaxTree.getRoot().getLeftChild().getLabel();
			if(negated.charAt(0)!='V')
			{
				return false;
			}
			return true;
		}
	}

	@Override
	public FOLFormula apply(FOLFormula formula) {
		String cuantifier=formula.syntaxTree.getRoot().getLeftChild().getLabel();
		String cuantified=cuantifier.substring(1,cuantifier.length()-1);
		FOLTreeNode negatedFormula= new FOLTreeNode("!",new FOLTreeNode(formula.syntaxTree.getRoot().getLeftChild().getLeftChild()),null);
		String newCuantifier="E"+cuantified+".";
		FOLTreeNode cuantifiedFormula=new FOLTreeNode(newCuantifier,negatedFormula,null);
		FOLFormula newFormula=new FOLFormula(cuantifiedFormula);
		return newFormula;
	}

}
