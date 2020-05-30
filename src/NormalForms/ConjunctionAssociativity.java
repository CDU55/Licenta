package NormalForms;

import AbstractSyntaxTree.TreeNode;
import Formulas.Formula;

public class ConjunctionAssociativity implements NormalFormTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("/\\"))
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
		TreeNode node1=new TreeNode(root.getLeftChild());
		TreeNode node2=new TreeNode(root.getRightChild().getLeftChild());
		TreeNode node3=new TreeNode(root.getRightChild().getRightChild());
		TreeNode leftConjunction=new TreeNode("/\\",node1,node2);
		TreeNode conjunction=new TreeNode("/\\",leftConjunction,node3);
		Formula result=new Formula(conjunction);
		return result;

	}

}
