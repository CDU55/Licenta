package NaturalDeduction.NaturalDeductionFOL;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;

public class RemoveExistentialCuantifier implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		if(! s1.proven.syntaxTree.getRoot().getLabel().matches("E[a-z][a-zA-DF-UW-Z]*\\."))
		{
			return false;
		}
		String cuantifiedTerm=s1.proven.syntaxTree.getRoot().getLabel();
		cuantifiedTerm=cuantifiedTerm.substring(1, cuantifiedTerm.length()-1);
		FOLFormula cuantifiedFormula=new FOLFormula(s1.proven.syntaxTree.getRoot().getLeftChild());
		boolean found=false;
		String substituteVariable = null;
		for(FOLFormula formula:s2.hypothesis)
		{
			SubstitutionsResult result=SubstitutionsFinder.findSubstitutions(cuantifiedFormula, formula);
			if(result.isSingleVariableSubstitution(cuantifiedTerm))
			{
				found=true;
				substituteVariable=result.substitutions.get(0).Final.toString();
				break;
			}
		}
		if(!found)
		{
			return false;
		}
		List<String> variables=new ArrayList<String>();
		for(FOLFormula formula:s1.hypothesis)
		{
			variables.addAll(formula.syntaxTree.getVariables());
		}
		variables.addAll(s2.proven.syntaxTree.getVariables());
		if(variables.contains(substituteVariable))
		{
			return false;
		}
		return true;

	}

	@Override
	public SequenceFOL Apply(Object... objects) throws InvalidSubstitution {
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		SequenceFOL result=new SequenceFOL(s1.hypothesis,s2.proven);
		return result;
	}

	@Override
	public String appliedCorrectly(Object... objects) {
		if(objects.length!=3)
		{
			return "Invalid arguments number";
		}
		for(int i=0;i<objects.length;i++)
		{
			if(!(objects[i] instanceof SequenceFOL))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial1=(SequenceFOL)objects[1];
		SequenceFOL initial2=(SequenceFOL)objects[2];
		if(!canApply(initial1,initial2))
		{
			return "Rule "+this.toString()+" cannot be applied for "+initial1.toString()+" and "+initial2.toString();
		}
		if(!SequenceFOL.hypothesisEqual(result, initial1))
		{
			return "The resulting sequence and the first sequence do not have the same hypothesis";
		}
		if(!initial1.proven.syntaxTree.getRoot().getLabel().matches("E[a-z][a-zA-DF-UW-Z]*\\."))
		{
			return "The proven formula from the first sequence is not existentially cuantified";
		}
		if(!result.proven.equals(initial2.proven))
		{
			return "The proven formula from the result is not equal to the proven formula from the second sequence";
		}

		return "Ok";

	}
	
	@Override
	public String toString() {
		return "Ee";
	}

}
