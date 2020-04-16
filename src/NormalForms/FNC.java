package NormalForms;

import AbstractSyntaxTree.TreeNode;

public class FNC extends NormalForm {

	@Override
	public boolean OperatorHierarchy(TreeNode node) {
		if(node.getLabel().matches("[a-zA-Z]"))
		{
			return true;
		}
		else if(node.getLabel().equals("!"))
		{
			if(node.getLeftChild().getLabel().equals("/\\") || node.getLeftChild().getLabel().equals("\\/"))
			{
				return false;
			}
			else if(node.getLeftChild().getLabel().matches("[a-zA-Z]"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(node.getLabel().equals("\\/"))
		{
			if(node.getLeftChild().getLabel().equals("/\\") || node.getRightChild().getLabel().equals("/\\"))
			{
				return false;
			}
			else
			{
				return OperatorHierarchy(node.getLeftChild()) && OperatorHierarchy(node.getRightChild());
			}
		}
		else
		{
			return OperatorHierarchy(node.getLeftChild()) && OperatorHierarchy(node.getRightChild());
		}
	}
	

}
