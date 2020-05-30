package PropositionalLogicAnalysis;

import java.util.List;

import Formulas.Formula;

public class SatisfiabilityChecker extends FormulaAnalyser {

	public SatisfiabilityChecker()
	{
		this.evaluationResult=false;
	}
	@Override
	public boolean analyseRandom(Formula formula,int maxIterations) {
		int k=0;
		Boolean[] assignation;
		List<String> variables=formula.syntaxTree.getVariables();
		Boolean result;
		while(k<maxIterations)
		{
			assignation=RandomAssignationGenerator.generate(variables.size());
			result=FormulaEvaluator.evaluate(formula.syntaxTree.getRoot(),assignation,variables);
			if(result==true)
			{
				return true;
			}
			k++;
		}
		return false;

	}

	@Override
	protected void analysisIteration(Formula formula, List<String> variables, int currentIndex,Boolean[] currentAssignation) {
		if(this.evaluationDone)
		{
			return;
		}
		for(Boolean value:this.values)
		{
			currentAssignation[currentIndex]=value;
			if(currentIndex==variables.size()-1)
			{
				if(FormulaEvaluator.evaluate(formula.syntaxTree.getRoot(),currentAssignation,variables))
				{
					this.evaluationResult=true;
					this.evaluationDone=true;
				}
			}
			else
			{
				analysisIteration(formula,variables,currentIndex+1,currentAssignation);
			}
		}
		
	}

}
