package FormulaStringConverters;

import AbstractSyntaxTree.TreeNode;
import NormalForms.FND;
import PropositionalLogicFormula.Formula;

public class FNDStringConverter implements FormulaStringConverter {

	@Override
	public String convertToString(Formula formula) {
		FND fnd=new FND();
		if(!fnd.checkFormula(formula))
		{
			return formula.toString();
		}
		else
		{
			return convertRecursive(formula.syntaxTree.getRoot());
		}
	}
	
	
	public String convertRecursive(TreeNode node)
	{
		if(node.getLabel().equals("\\/"))
		{
			String right=convertRecursive(node.getRightChild());
			String left=convertRecursive(node.getLeftChild());
			if(node.getLeftChild().getLabel().equals("/\\"))
			{
				left="( "+left+" )";
			}
			if(node.getRightChild().getLabel().equals("/\\"))
			{
				right="( "+right+" )";
			}
			return left + "  \\/  " + right;
		}
		else if(node.getLabel().equals("/\\"))
		{
			return  convertRecursive(node.getLeftChild()) + " /\\  " + convertRecursive(node.getRightChild());
		}
		else
		{
			return node.toString();
		}
	}

}
