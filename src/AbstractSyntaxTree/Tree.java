package AbstractSyntaxTree;

import java.util.ArrayList;
import java.util.List;

import Operators.CorrespondingConnector;
import Operators.OperatorPrecedence;
import Operators.TypeTester;

public abstract class Tree {
	
	protected TreeNode root;
	
	
	public TreeNode getRoot() {
		return root;
	}

	public  int getHeight()
	{
	    return calculateHeight(this.root);
	}

	protected int calculateHeight(TreeNode currentNode)
	{
		if(currentNode==null)
	        return -1;

	    else return 1 + Math.max(calculateHeight(currentNode.getLeftChild()),calculateHeight(currentNode.getRightChild()));
	}
	
	public int getSize()
	{
		return calculateSize(this.root);
	}
	
	protected  int calculateSize(TreeNode currentNode)
	{
	    if(currentNode==null)
	        return 0;
	    else return 1+calculateSize(currentNode.getLeftChild())+calculateSize(currentNode.getRightChild());
	}
	
	protected abstract void buildTree(String formula);
	
	public void reaplceImplications(TreeNode currentNode)
	{
		if(currentNode.getLabel().equals("->"))
		{
			currentNode.setLabel("\\/");
			TreeNode leftChild=currentNode.getLeftChild();
			currentNode.setLeftChild(new TreeNode("!",leftChild,null));
		}
		else if(currentNode.getLabel().equals("<-"))
		{
			currentNode.setLabel("\\/");
			TreeNode rightChild=currentNode.getRightChild();
			currentNode.setRightChild(new TreeNode("!",rightChild,null));
		}
		else if(currentNode.getLabel().equals("<->"))
		{
			currentNode.setLabel("/\\");
			TreeNode leftChild=currentNode.getLeftChild();
			TreeNode rightChild=currentNode.getRightChild();
			currentNode.setLeftChild(new TreeNode("->",leftChild,rightChild));
			currentNode.setRightChild(new TreeNode("->",rightChild,leftChild));

		}
		if(currentNode.getLeftChild()!=null)
		{
			reaplceImplications(currentNode.getLeftChild());
		}
		if(currentNode.getRightChild()!=null)
		{
			reaplceImplications(currentNode.getRightChild());
		}
	}
	
	public void getAllVariables(TreeNode currentNode,List<String> variables)
	{
		String label=currentNode.getLabel();
		if(label.matches("[a-zA-Z]") && !variables.contains(label))
		{
			variables.add(label);
		}
		else
		{
			if(currentNode.getLeftChild()!=null)
			{
				getAllVariables(currentNode.getLeftChild(),variables);
			}
			if(currentNode.getRightChild()!=null)
			{
				getAllVariables(currentNode.getRightChild(),variables);
			}
		}
	}
	
	public List<String> getVariables()
	{
		List<String> variables=new ArrayList<String>();
		getAllVariables(this.root,variables);
		return variables;
	}
	public void getAllSubformulas(TreeNode currentNode,List<String> subformulas)
	{
		String formula=currentNode.toString();
		if(!subformulas.contains(formula))
		{
			subformulas.add(formula);
		}
		if(currentNode.getLeftChild()!=null)
		{
			getAllSubformulas(currentNode.getLeftChild(),subformulas);
		}
		if(currentNode.getRightChild()!=null)
		{
			getAllSubformulas(currentNode.getRightChild(),subformulas);
		}
		
	}
	
	public List<String> getSubformulas()
	{
		List<String> subformulas=new ArrayList<String>();
		getAllSubformulas(this.root,subformulas);
		return subformulas;
	}
	

}
