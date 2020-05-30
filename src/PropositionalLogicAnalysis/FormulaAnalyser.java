package PropositionalLogicAnalysis;

import java.util.Arrays;
import java.util.List;
import Formulas.Formula;

public abstract class FormulaAnalyser {
	
	protected boolean evaluationDone;
	protected boolean evaluationResult;
	protected final List<Boolean> values=Arrays.asList(true,false);
	
	public boolean analyse(Formula formula)
	{
		evaluationDone=false;
		Formula testFormula=new Formula(formula.syntaxTree.getRoot());
		List<String> variables=testFormula.syntaxTree.getVariables();
		testFormula.syntaxTree.reaplceImplications(testFormula.syntaxTree.getRoot());
		Boolean[] currentAssignation=new Boolean[variables.size()];
		analysisIteration(testFormula,variables,0,currentAssignation);
		return evaluationResult;
		
	}
	protected abstract void analysisIteration(Formula formula,List<String> variables,int currentIndex,Boolean[] currentAssignation);
	public abstract boolean analyseRandom(Formula formula,int maxIterations);

}
