package NormalForms;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public abstract class NormalForm {
	
	public boolean containsImplications(TreeNode node)
	{
		if(node.getLabel().matches("[a-zA-Z]"))
		{
			return false;
		}
		else if(node.getLabel().equals("->") || node.getLabel().equals("<->") || node.getLabel().equals("<-"))
		{
			return true;
		}
		else if(node.getLabel().equals("!"))
		{
			return containsImplications(node.getLeftChild());
		}
		else
		{
			return containsImplications(node.getLeftChild()) || containsImplications(node.getRightChild());
		}
	}
	
	public abstract boolean OperatorHierarchy(TreeNode node);
	
	public boolean checkFormula(Formula formula)
	{
		if(this.containsImplications(formula.syntaxTree.getRoot()))
		{
			return false;
		}
		else
		{
			return OperatorHierarchy(formula.syntaxTree.getRoot());
		}
	}

}
