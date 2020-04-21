package NormalForms;


import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class RemoveDoubleImplication implements FNCTransformationRule {

	@Override
	public boolean canApply(Formula formula) {
		TreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("<->"))
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
		// TODO Auto-generated method stub
		TreeNode root=formula.syntaxTree.getRoot();
		TreeNode left=root.getLeftChild();
		TreeNode right=root.getRightChild();
		TreeNode implicationLeft=new TreeNode("->",left,right);
		TreeNode implicationRight=new TreeNode("->",right,left);
		TreeNode implications=new TreeNode("/\\",implicationLeft,implicationRight);
		Formula result=new Formula(implications);
		return result;
	}

}
