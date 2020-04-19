package PropositionalLogicAnalysis;

import java.util.List;

import AbstractSyntaxTree.TreeNode;

public class FormulaEvaluator {
	
	public static boolean evaluate(TreeNode currentNode,Boolean[]assignation,List<String> variables)
	{
		if(currentNode.getLabel().matches("[a-zA-Z]"))
		{
			return assignation[variables.indexOf(currentNode.getLabel())];
		}
		else if(currentNode.getLabel().equals("!"))
		{
			return ! evaluate(currentNode.getLeftChild(),assignation,variables);
		}
		else if(currentNode.getLabel().equals("/\\"))
		{
			return evaluate(currentNode.getLeftChild(),assignation,variables) && evaluate(currentNode.getRightChild(),assignation,variables);
		}
		else 
		{
			return evaluate(currentNode.getLeftChild(),assignation,variables) || evaluate(currentNode.getRightChild(),assignation,variables);
		}
	}

}
