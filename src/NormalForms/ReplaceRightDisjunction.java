package NormalForms;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class ReplaceRightDisjunction implements FNCTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("\\/"))
		{
			return false;
		}
		else if(!root.getLeftChild().getLabel().equals("/\\"))
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
		TreeNode node1=root.getRightChild();
		TreeNode node2=root.getLeftChild().getLeftChild();
		TreeNode node3=root.getLeftChild().getRightChild();
		TreeNode disjunction1=new TreeNode("\\/",node2,node1);
		TreeNode disjunction2=new TreeNode("\\/",node3,node1);
		TreeNode conjunction=new TreeNode("/\\",disjunction1,disjunction2);
		Formula result=new Formula(conjunction);
		return result;

	}

}
