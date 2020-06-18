package NaturalDeduction.NaturalDeductionFOL;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.Substitution;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;

public class CreateUniversalCuantifier implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=3)
		{
			return false;
		}
		SequenceFOL sequence=(SequenceFOL)objects[0];
		String toReplace=(String)objects[1];
		if(!sequence.proven.syntaxTree.getVariables().contains(toReplace.trim()))
		{
			return false;
		}

		List<String> hypothesisVariables=new ArrayList<String>();
		for(FOLFormula formula:sequence.hypothesis)
		{
			hypothesisVariables.addAll(formula.syntaxTree.getVariables());
		}
		if(hypothesisVariables.contains(toReplace))
		{
			return false;
		}
		return true;
	}

	@Override
	public SequenceFOL Apply(Object... objects) throws InvalidSubstitution {
		SequenceFOL sequence=(SequenceFOL)objects[0];
		String toReaplce=(String)objects[1];
		String toCuantify=(String)objects[2];
		FOLFormula newFormula=new FOLFormula(sequence.proven);
		newFormula.executeSubstitution(new Substitution(toReaplce,toCuantify));
		FOLTreeNode newTreeNode=new FOLTreeNode("V"+toCuantify+".",newFormula.syntaxTree.getRoot(),null);
		FOLFormula cuantifiedFormula=new FOLFormula(newTreeNode);
		SequenceFOL result=new SequenceFOL(sequence.hypothesis,cuantifiedFormula);
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
		if(!result.proven.syntaxTree.getRoot().getLabel().matches("V[a-z][a-zA-DF-UW-Z]*\\."))
		{
			return "The resulting sequence is not existentially cuantified";
		}
		/*if(!result.proven.syntaxTree.equals(result.proven.syntaxTree))
		{
			return "The resulting sequence is not valid";
		}*/
		String cuantifier=result.proven.syntaxTree.getRoot().getLabel();
		String cuantifiedTerm=cuantifier.substring(1, cuantifier.length()-1);
		FOLFormula resultingCuantifiedFormula=new FOLFormula(result.proven.syntaxTree.getRoot().getLeftChild());
		SubstitutionsResult subs=SubstitutionsFinder.findSubstitutions(resultingCuantifiedFormula, initial.proven);
		if(!subs.validSubstitution)
		{
			return "Could not find a valid substitution for "+ resultingCuantifiedFormula.toString() + " and "+initial.proven.toString();
		}
		if(!subs.isSingleVariableSubstitution(cuantifiedTerm))
		{
			return "The substitution does not contain only " + cuantifiedTerm;
		}
		String substituteTerm=subs.substitutions.get(0).Final.toString();
		List<String> hypothesisVariables=new ArrayList<String>();
		for(FOLFormula formula:initial.hypothesis)
		{
			hypothesisVariables.addAll(formula.syntaxTree.getVariables());
		}
		if(hypothesisVariables.contains(substituteTerm))
		{
			return "The hypothesis from the initial sequence contains "+substituteTerm;
		}
		return "Ok";
	}

}
