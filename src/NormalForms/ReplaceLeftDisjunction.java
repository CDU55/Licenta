package NormalForms;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class ReplaceLeftDisjunction implements NormalFormTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("\\/"))
		{
			return false;
		}
		else if(!root.getRightChild().getLabel().equals("/\\"))
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
		TreeNode node1=root.getLeftChild();
		TreeNode node2=root.getRightChild().getLeftChild();
		TreeNode node3=root.getRightChild().getRightChild();
		TreeNode disjunction1=new TreeNode("\\/",node1,node2);
		TreeNode disjunction2=new TreeNode("\\/",node1,node3);
		TreeNode conjunction=new TreeNode("/\\",disjunction1,disjunction2);
		Formula result=new Formula(conjunction);
		return result;

	}

}
