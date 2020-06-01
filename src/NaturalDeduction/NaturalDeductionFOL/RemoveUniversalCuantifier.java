package NaturalDeduction.NaturalDeductionFOL;

import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.Substitution;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;

public class RemoveUniversalCuantifier implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL sequence=(SequenceFOL)objects[0];
		if(!sequence.proven.syntaxTree.getRoot().getLabel().matches("V[a-z][a-zA-DF-UW-Z]*\\."))
		{
			return false;
		}
		return true;
	}

	@Override
	public SequenceFOL Apply(Object... objects) throws InvalidSubstitution {
		SequenceFOL sequence=(SequenceFOL)objects[0];
		String cuantifier=sequence.proven.syntaxTree.getRoot().getLabel();
		String cuantifiedTerm=cuantifier.substring(1, cuantifier.length()-1);
		String substituteTo=(String)objects[1];
		FOLFormula newFormula=new FOLFormula(sequence.proven.syntaxTree.getRoot().getLeftChild());
		newFormula.executeSubstitution(new Substitution(cuantifiedTerm,substituteTo));
		SequenceFOL result=new SequenceFOL(sequence.hypothesis,newFormula);
		return result;

	}

	@Override
	public String appliedCorrectly(Object... objects) {
		if(objects.length!=2)
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
		SequenceFOL initial=(SequenceFOL)objects[1];
		SequenceFOL result=(SequenceFOL)objects[0];
		if(!SequenceFOL.hypothesisEqual(initial, result))
		{
			return initial.toString()+" and "+result.toString()+" do not have the same hypothesis";
		}
		if(!initial.proven.syntaxTree.getRoot().getLabel().matches("V[a-z][a-zA-DF-UW-Z]*\\."))
		{
			return "The initial sequence is not universally cuantified";
		}
		/*if(!initial.proven.syntaxTree.equals(result.proven.syntaxTree))
		{
			return "The resulting sequence is not valid";
		}*/
		String cuantifier=initial.proven.syntaxTree.getRoot().getLabel();
		String cuantifiedTerm=cuantifier.substring(1, cuantifier.length()-1);
		FOLFormula initialCuantifiedFormula=new FOLFormula(initial.proven.syntaxTree.getRoot().getLeftChild());
		SubstitutionsResult subs=SubstitutionsFinder.findSubstitutions(initialCuantifiedFormula, result.proven);
		if(!subs.validSubstitution)
		{
			return "Could not find a valid substitution for "+ initialCuantifiedFormula.toString() + " and "+result.proven.toString();
		}
		if(!subs.isSingleVariableSubstitution(cuantifiedTerm))
		{
			return "The substitution does not contain only " + cuantifiedTerm;
		}
		return "Ok";
	}
	
	@Override
	public String toString() {
		return "Ve";
	}

}
