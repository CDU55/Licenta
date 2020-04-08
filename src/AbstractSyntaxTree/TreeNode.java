package AbstractSyntaxTree;

import Operators.CorrespondingConnector;
import Operators.OperatorPrecedence;
import Operators.TypeTester;

public class TreeNode {
	
	private String label;
	private TreeNode leftChild;
	private TreeNode rightChild;
	
	public TreeNode(String label, TreeNode leftChild, TreeNode rightChild) {
		this.label = label;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public TreeNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(TreeNode leftChild) {
		this.leftChild = leftChild;
	}
	public TreeNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(TreeNode rightChild) {
		this.rightChild = rightChild;
	}
	
	protected String convertToFormulaString(TreeNode currentNode,TypeTester operatorType,OperatorPrecedence precedence,boolean addParanthesis)
	{
		String label=currentNode.getLabel();
		if(label.matches("[A-Za-z]"))
		{
			return label;
		}
		else if(label.equals("!"))
		{
			if(addParanthesis && operatorType.isOperatorExcludeNegation(CorrespondingConnector.getRemodeledConnector(currentNode.getLeftChild().getLabel())))
			{
				
			return "( "+label+convertToFormulaString(currentNode.getLeftChild(),operatorType,precedence,true)+" )";
			
			}
			else if(operatorType.isOperatorExcludeNegation(CorrespondingConnector.getRemodeledConnector(currentNode.getLeftChild().getLabel())))
			{
				return label+convertToFormulaString(currentNode.getLeftChild(),operatorType,precedence,true);
			}
			else
			{
				return label+convertToFormulaString(currentNode.getLeftChild(),operatorType,precedence,false);

			}
		}
		else 
		{
			char remodeledOperator=CorrespondingConnector.getRemodeledConnector(label);
			boolean parathesisLeft=precedence.getPrecedence(remodeledOperator)<precedence.getPrecedence(CorrespondingConnector.getRemodeledConnector(currentNode.getLeftChild().getLabel()));
			boolean paranthesisRight=precedence.getPrecedence(remodeledOperator)<precedence.getPrecedence(CorrespondingConnector.getRemodeledConnector(currentNode.getRightChild().getLabel()));;
			if(addParanthesis)
			{
				return "( "+convertToFormulaString(currentNode.getLeftChild(),operatorType,precedence,parathesisLeft)+" "+label+" "+
						convertToFormulaString(currentNode.getRightChild(),operatorType,precedence,paranthesisRight) + " )";
			}
			else
			{
				return convertToFormulaString(currentNode.getLeftChild(),operatorType,precedence,parathesisLeft)+" "+label+" "+
						convertToFormulaString(currentNode.getRightChild(),operatorType,precedence,paranthesisRight);
			}
		}
		
	}
	@Override
	public String toString() {
		return convertToFormulaString(this,new TypeTester(),new OperatorPrecedence(true),false);
	}
	
	
	

}
