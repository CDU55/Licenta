package NormalForms;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class RemoveDoubleNegation implements NormalFormTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("!"))
		{
			return false;
		}
		else if(!root.getLeftChild().getLabel().equals("!"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public Formula apply(Formula formula) {
		TreeNode root=new TreeNode(formula.syntaxTree.getRoot().getLeftChild().getLeftChild());
		Formula result=new Formula(root);
		return result;
	}

}
