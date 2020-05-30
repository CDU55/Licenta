package AbstractSyntaxTree;

import java.util.ArrayList;
import java.util.List;

import FirstOrderLogicSubstitutions.Substitution;
import Operators.CorrespondingConnector;
import Operators.OperatorPrecedence;
import Operators.TypeTesterFirstOrderLogic;
import Parsers.CheckSyntax;
import Util.FunctionParsing;

public class FOLTreeNode {

	private String label;
	private boolean isConnector;
	private boolean isVariable;
	private FOLTreeNode leftChild;
	private FOLTreeNode rightChild;
	private List<FOLTreeNode> arguments;

	public FOLTreeNode(FOLTreeNode node) {
		this.label = new String(node.label);
		this.isConnector = node.isConnector;
		this.isVariable = node.isVariable;
		if (node.leftChild != null) {
			this.leftChild = new FOLTreeNode(node.leftChild);
		}
		if (node.rightChild != null) {
			this.rightChild = new FOLTreeNode(node.rightChild);
		}
		if (node.arguments != null) {
			this.arguments = new ArrayList<FOLTreeNode>();
			for (FOLTreeNode arg : node.arguments) {
				this.arguments.add(new FOLTreeNode(arg));
			}
		}
	}

	public FOLTreeNode(String label, FOLTreeNode leftChild, FOLTreeNode rightChild) {
		this.label = label;
		this.isConnector = true;
		this.isVariable = false;
		this.rightChild = rightChild;
		this.leftChild = leftChild;
	}

	public FOLTreeNode(String function) {
		this.arguments = new ArrayList<FOLTreeNode>();
		if (CheckSyntax.checkFunctionSyntaxBoolean(function)) {
			this.label = FunctionParsing.extractFuncitonName(function);
			this.isConnector = false;
			this.isVariable = false;
			List<String> parameters = FunctionParsing.extractFunctionParameters(function);
			for (String parameter : parameters) {
				this.arguments.add(new FOLTreeNode(parameter));
			}
		} else {
			this.label = function;
			this.isConnector = false;
			this.isVariable = true;
		}

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isConnector() {
		return isConnector;
	}

	public void setConnector(boolean isConnector) {
		this.isConnector = isConnector;
	}

	public boolean isVariable() {
		return isVariable;
	}

	public void setVariable(boolean isVariable) {
		this.isVariable = isVariable;
	}

	public FOLTreeNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(FOLTreeNode leftChild) {
		this.leftChild = leftChild;
	}

	public FOLTreeNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(FOLTreeNode rightChild) {
		this.rightChild = rightChild;
	}

	public List<FOLTreeNode> getArguments() {
		return arguments;
	}

	public void setArguments(List<FOLTreeNode> arguments) {
		this.arguments = arguments;
	}

	private String convertFunctionToString(FOLTreeNode node) {
		String result = node.label + "(";
		for (int argumentIndex = 0; argumentIndex < node.arguments.size(); argumentIndex++) {
			if (argumentIndex != 0) {
				result += ",";
			}
			if (!node.arguments.get(argumentIndex).isVariable && !node.arguments.get(argumentIndex).isConnector) {
				result += convertFunctionToString(node.arguments.get(argumentIndex));
			} else {
				result += node.arguments.get(argumentIndex).label;
			}

		}
		result += ")";
		return result;
	}

	protected String convertToFormulaString(FOLTreeNode currentNode, TypeTesterFirstOrderLogic operatorType,
			OperatorPrecedence precedence, boolean addParanthesis) {
		String label = currentNode.label;
		if (!currentNode.isConnector && !currentNode.isVariable) {
			return convertFunctionToString(currentNode);
		}

		else if (currentNode.isVariable) {
			return currentNode.label;
		} else if (TypeTesterFirstOrderLogic.isCuantifierWithTerm(label) || label.equals("!")) {
			if (addParanthesis && precedence.getPrecedence(
					CorrespondingConnector.getRemodeledConnector(currentNode.leftChild.label)) > precedence
							.getPrecedence(CorrespondingConnector.getRemodeledConnector(label))) {

				return "( " + label + convertToFormulaString(currentNode.leftChild, operatorType, precedence, true)
						+ " )";

			} else if (precedence.getPrecedence(
					CorrespondingConnector.getRemodeledConnector(currentNode.leftChild.label)) > precedence
							.getPrecedence(CorrespondingConnector.getRemodeledConnector(label))) {
				return label + convertToFormulaString(currentNode.leftChild, operatorType, precedence, true);
			} else {
				return label + convertToFormulaString(currentNode.leftChild, operatorType, precedence, false);

			}
		} else {
			char remodeledOperator = CorrespondingConnector.getRemodeledConnector(label);
			boolean parathesisLeft = precedence.getPrecedence(remodeledOperator) < precedence
					.getPrecedence(CorrespondingConnector.getRemodeledConnector(currentNode.leftChild.label));
			boolean paranthesisRight = precedence.getPrecedence(remodeledOperator) <= precedence
					.getPrecedence(CorrespondingConnector.getRemodeledConnector(currentNode.rightChild.label));
			;
			if (addParanthesis) {
				return "( " + convertToFormulaString(currentNode.leftChild, operatorType, precedence, parathesisLeft)
						+ " " + label + " "
						+ convertToFormulaString(currentNode.rightChild, operatorType, precedence, paranthesisRight)
						+ " )";
			} else {
				return convertToFormulaString(currentNode.leftChild, operatorType, precedence, parathesisLeft) + " "
						+ label + " "
						+ convertToFormulaString(currentNode.rightChild, operatorType, precedence, paranthesisRight);
			}
		}

	}

	public boolean isSubTree(FOLTreeNode treeRoot)
	{
		if(treeRoot.equals(this))
		{
			return true;
		}
		else if(!treeRoot.isConnector && !treeRoot.isVariable)
		{
			return false;
		}
		else if(treeRoot.getLabel().equals("!") || TypeTesterFirstOrderLogic.isCuantifierWithTerm(treeRoot.getLabel()))
		{
			return isSubTree(treeRoot.getLeftChild());
		}
		else
		{
			return isSubTree(treeRoot.getLeftChild()) || isSubTree(treeRoot.getRightChild());
		}
	}
	public static boolean areEqual(FOLTreeNode node1, FOLTreeNode node2) {
		if (node1 == null && node2 == null) {
			return true;
		} else if (!node1.label.equals(node2.label) || node1.isConnector != node2.isConnector
				|| node1.isVariable != node2.isVariable) {
			return false;
		} else if (node1.isConnector) {
			if ((node1.leftChild != null && node2.leftChild == null)
					|| (node1.leftChild == null && node2.leftChild != null)) {
				return false;
			} else if ((node1.rightChild != null && node2.rightChild == null)
					|| (node1.rightChild == null && node2.rightChild != null)) {

				return false;
			}

			else {
				return areEqual(node1.leftChild, node2.leftChild) && areEqual(node1.rightChild, node2.rightChild);
			}
		} else if (!node1.isConnector && !node1.isVariable) {
			if (node1.arguments.size() != node2.arguments.size()) {
				return false;
			} else {
				for (int i = 0; i < node1.arguments.size(); i++) {
					if (!areEqual(node1.arguments.get(i), node2.arguments.get(i))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + (isConnector ? 1231 : 1237);
		result = prime * result + (isVariable ? 1231 : 1237);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((leftChild == null) ? 0 : leftChild.hashCode());
		result = prime * result + ((rightChild == null) ? 0 : rightChild.hashCode());
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
		FOLTreeNode other = (FOLTreeNode) obj;
		return areEqual(this, other);
	}

	@Override
	public String toString() {
		return convertToFormulaString(this, new TypeTesterFirstOrderLogic(), new OperatorPrecedence(false), false);
	}

}
