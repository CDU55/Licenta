package PropositionalLogicAnalysis;

import java.util.Arrays;
import java.util.List;
import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public abstract class FormulaAnalyser {
	
	protected boolean evaluationDone;
	protected boolean evaluationResult;
	protected final List<Boolean> values=Arrays.asList(true,false);
	
	protected boolean evaluate(TreeNode currentNode,Boolean[]assignation,List<String> variables)
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
	public boolean analyse(Formula formula)
	{
		evaluationDone=false;
		Formula testFormula=new Formula(formula.toString());
		List<String> variables=testFormula.syntaxTree.getVariables();
		testFormula.syntaxTree.reaplceImplications(testFormula.syntaxTree.getRoot());
		Boolean[] currentAssignation=new Boolean[variables.size()];
		analysisIteration(testFormula,variables,0,currentAssignation);
		return evaluationResult;
		
	}
	protected abstract void analysisIteration(Formula formula,List<String> variables,int currentIndex,Boolean[] currentAssignation);
	public abstract boolean analyseRandom(Formula formula,int maxIterations);

}
