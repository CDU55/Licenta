package NormalForms;

import AbstractSyntaxTree.TreeNode;
import Formulas.Formula;

public class Complement {
	
	private static TreeNode getComplement(TreeNode node)
	{
		if(node.getLabel().matches("[a-zA-Z]"))
		{
			TreeNode result=new TreeNode("!",node,null);
			return result;
		}
		else if(node.getLabel().equals("!"))
		{
			TreeNode result=new TreeNode(node.getLeftChild());
			return result;
		}
		else if(node.getLabel().equals("/\\"))
		{
			TreeNode result=new TreeNode("\\/",getComplement(node.getLeftChild()),getComplement(node.getRightChild()));
			return result;
		}
		else
		{
			TreeNode result=new TreeNode("/\\",getComplement(node.getLeftChild()),getComplement(node.getRightChild()));
			return result;
		}
	}
	
	public static Formula getFromulaComplement(Formula f) 
	{
		Formula result=new Formula(f.syntaxTree.getRoot());
		result.replaceImplications();
		result=new Formula(getComplement(result.syntaxTree.getRoot()));
		return result;
	}

}
