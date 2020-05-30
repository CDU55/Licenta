package NaturalDeduction;

import Formulas.Formula;

public class CreateProvenFromBottom implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		if(s1.proven!=null)
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Formula toAdd=(Formula)objects[1];
		Sequence result=new Sequence(s1.hypothesis,toAdd);
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
			if(!(objects[i] instanceof Sequence))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		Sequence result=(Sequence)objects[0];
		Sequence initial=(Sequence)objects[1];
		if(initial.proven!=null)
		{
			return "Initial sequence right side must be bottom";
		}
		if(result.proven==null)
		{
			return "Resulting sequence right side cannot be bottom";
		}
		if(!Sequence.hypothesisEqual(result, initial))
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
