package AbstractSyntaxTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Operators.CorrespondingConnector;
import Operators.TypeTester;

public class PropositionalLogicTree extends Tree {

	public PropositionalLogicTree(String formulaPostfix) {
		this.buildTree(formulaPostfix);
	}

	public PropositionalLogicTree(TreeNode root) {
		this.root = root;
	}

	@Override
	protected void buildTree(String formulaPostfix) {

		TypeTester operatorType = new TypeTester();
		Stack<TreeNode> treeStack = new Stack<TreeNode>();
		TreeNode current;
		TreeNode currentLeft;
		TreeNode currentRight;
		for (int i = 0; i < formulaPostfix.length(); i++) {
			if (Character.isLetter(formulaPostfix.charAt(i))) {
				current = new TreeNode(Character.toString(formulaPostfix.charAt(i)), null, null);
				treeStack.push(current);

			} else if (operatorType.isOperatorExcludeNegation(formulaPostfix.charAt(i))) {
				currentRight = treeStack.pop();
				currentLeft = treeStack.pop();
				current = new TreeNode(CorrespondingConnector.getOriginalConnector(formulaPostfix.charAt(i)),
						currentLeft, currentRight);
				treeStack.push(current);

			} else if (operatorType.isOperator(formulaPostfix.charAt(i))) {
				currentLeft = treeStack.pop();
				current = new TreeNode(Character.toString(formulaPostfix.charAt(i)), currentLeft, null);
				treeStack.push(current);
			}
		}

		this.root = treeStack.pop();
	}

	public Explanation getVarsExplanation() {
		Explanation exp = new Explanation();
		String initialMessage = "= " + Explanation.addVars(this.root.toString());
		exp.messages.add(initialMessage);
		exp.previousMessage = initialMessage;
		generateVarsExplanation(this.root, exp);
		return exp;

	}

	public Explanation getSubfExplanation() {
		Explanation exp = new Explanation();
		String initialMessage = "= " + Explanation.addSuform(this.root.toString());
		exp.messages.add(initialMessage);
		exp.previousMessage = initialMessage;
		generateSubfExplanation(this.root, exp);
		return exp;

	}

	private void generateVarsExplanation(TreeNode currentNode, Explanation explanation) {
		if (currentNode.getLabel().matches("[a-zA-Z]")
				&& explanation.previousMessage.contains(Explanation.addVars(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),
					Explanation.addBraces(currentNode.getLabel()));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
		} else if (currentNode.getLabel().equals("!")
				&& explanation.previousMessage.contains(Explanation.addVars(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),
					Explanation.addVars((currentNode.getLeftChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateVarsExplanation(currentNode.getLeftChild(), explanation);

		} else if (explanation.previousMessage.contains(Explanation.addVars(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addVars(currentNode.toString()),
					Explanation.addVars(currentNode.getLeftChild().toString()) + " U "
							+ Explanation.addVars((currentNode.getRightChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateVarsExplanation(currentNode.getLeftChild(), explanation);
			generateVarsExplanation(currentNode.getRightChild(), explanation);

		}
	}

	private void generateSubfExplanation(TreeNode currentNode, Explanation explanation) {
		if (currentNode.getLabel().matches("[a-zA-Z]")
				&& explanation.previousMessage.contains(Explanation.addSuform(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addSuform(currentNode.toString()),
					Explanation.addBraces(currentNode.getLabel()));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
		} else if (currentNode.getLabel().equals("!")
				&& explanation.previousMessage.contains(Explanation.addSuform(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addSuform(currentNode.toString()),
					Explanation.addBraces(currentNode.toString()) + " U "
							+ Explanation.addSuform((currentNode.getLeftChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateSubfExplanation(currentNode.getLeftChild(), explanation);

		} else if (explanation.previousMessage.contains(Explanation.addSuform(currentNode.toString()))) {
			String newMessage = explanation.previousMessage.replace(Explanation.addSuform(currentNode.toString()),
					Explanation.addBraces(currentNode.toString()) + " U "
							+ Explanation.addSuform(currentNode.getLeftChild().toString()) + " U "
							+ Explanation.addSuform((currentNode.getRightChild().toString())));
			explanation.messages.add(new String(newMessage));
			explanation.previousMessage = newMessage;
			generateSubfExplanation(currentNode.getLeftChild(), explanation);
			generateSubfExplanation(currentNode.getRightChild(), explanation);

		}
	}

	@Override
	public String toString() {
		return this.root.toString();
	}

}
