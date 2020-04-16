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

	public TreeNode(TreeNode toCopy) {
		this.label = toCopy.getLabel();
		if (toCopy.getLeftChild() != null) {
			this.leftChild = new TreeNode(toCopy.getLeftChild());
		}
		if (toCopy.getRightChild() != null) {
			this.rightChild = new TreeNode(toCopy.getRightChild());
		}
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

	protected String convertToFormulaString(TreeNode currentNode, TypeTester operatorType,
			OperatorPrecedence precedence, boolean addParanthesis) {
		String label = currentNode.getLabel();
		if (label.matches("[A-Za-z]")) {
			return label;
		} else if (label.equals("!")) {
			if (addParanthesis && operatorType.isOperatorExcludeNegation(
					CorrespondingConnector.getRemodeledConnector(currentNode.getLeftChild().getLabel()))) {

				return "( " + label + convertToFormulaString(currentNode.getLeftChild(), operatorType, precedence, true)
						+ " )";

			} else if (operatorType.isOperatorExcludeNegation(
					CorrespondingConnector.getRemodeledConnector(currentNode.getLeftChild().getLabel()))) {
				return label + convertToFormulaString(currentNode.getLeftChild(), operatorType, precedence, true);
			} else {
				return label + convertToFormulaString(currentNode.getLeftChild(), operatorType, precedence, false);

			}
		} else {
			char remodeledOperator = CorrespondingConnector.getRemodeledConnector(label);
			boolean parathesisLeft = precedence.getPrecedence(remodeledOperator) < precedence
					.getPrecedence(CorrespondingConnector.getRemodeledConnector(currentNode.getLeftChild().getLabel()));
			boolean paranthesisRight = precedence.getPrecedence(remodeledOperator) < precedence.getPrecedence(
					CorrespondingConnector.getRemodeledConnector(currentNode.getRightChild().getLabel()));
			;
			if (addParanthesis) {
				return "( "
						+ convertToFormulaString(currentNode.getLeftChild(), operatorType, precedence, parathesisLeft)
						+ " " + label + " " + convertToFormulaString(currentNode.getRightChild(), operatorType,
								precedence, paranthesisRight)
						+ " )";
			} else {
				return convertToFormulaString(currentNode.getLeftChild(), operatorType, precedence, parathesisLeft)
						+ " " + label + " " + convertToFormulaString(currentNode.getRightChild(), operatorType,
								precedence, paranthesisRight);
			}
		}

	}

	public static boolean isEqual(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null) {
			return true;
		} else if (!node1.getLabel().equals(node2.getLabel())) {
			return false;
		} else if ((node1.getLeftChild() != null && node2.getLeftChild() == null)
				|| (node1.getLeftChild() == null && node2.getLeftChild() != null)) {
			return false;
		} else if ((node1.getRightChild() != null && node2.getRightChild() == null)
				|| (node1.getRightChild() == null && node2.getRightChild() != null)) {
			return false;
		} else {
			return isEqual(node1.getLeftChild(), node2.getLeftChild())
					&& isEqual(node1.getRightChild(), node2.getRightChild());
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode other = (TreeNode) obj;
		return isEqual(this, other);
	}

	@Override
	public String toString() {
		return convertToFormulaString(this, new TypeTester(), new OperatorPrecedence(true), false);
	}

}
