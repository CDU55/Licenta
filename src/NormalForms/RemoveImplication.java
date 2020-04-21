package NormalForms;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class RemoveImplication implements FNCTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("->"))
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
		TreeNode root=formula.syntaxTree.getRoot();
		TreeNode left=root.getLeftChild();
		TreeNode right=root.getRightChild();
		TreeNode leftNegation=new TreeNode("!",left,null);
		TreeNode disjunction=new TreeNode("\\/",leftNegation,right);
		Formula result=new Formula(disjunction);
		return result;
	}

}
