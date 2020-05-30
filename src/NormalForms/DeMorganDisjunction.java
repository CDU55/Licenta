package NormalForms;

import AbstractSyntaxTree.TreeNode;
import Formulas.Formula;

public class DeMorganDisjunction implements NormalFormTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("!"))
		{
			return false;
		}
		else if(!root.getLeftChild().getLabel().equals("\\/"))
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
		TreeNode node1=new TreeNode(root.getLeftChild().getLeftChild());
		TreeNode node2=new TreeNode(root.getLeftChild().getRightChild());
		TreeNode negationLeft=new TreeNode("!",node1,null);
		TreeNode negationRight=new TreeNode("!",node2,null);
		TreeNode conjunction=new TreeNode("/\\",negationLeft,negationRight);
		Formula result=new Formula(conjunction);
		return result;
	}

}
