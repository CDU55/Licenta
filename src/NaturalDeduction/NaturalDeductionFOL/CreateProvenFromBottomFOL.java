package NaturalDeduction.NaturalDeductionFOL;

import Formulas.FOLFormula;

public class CreateProvenFromBottomFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		if(s1.proven!=null)
		{
			return false;
		}
		return true;
	}

	@Override
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula toAdd=(FOLFormula)objects[1];
		SequenceFOL result=new SequenceFOL(s1.hypothesis,toAdd);
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
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial=(SequenceFOL)objects[1];
		if(initial.proven!=null)
		{
			return "Initial sequence right side must be bottom";
		}
		if(result.proven==null)
		{
			return "Resulting sequence right side cannot be bottom";
		}
		if(!SequenceFOL.hypothesisEqual(result, initial))
		{
			return "Resulting sequence and initial sequence do not have the same hypothesis";
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "|e";
	}

}
