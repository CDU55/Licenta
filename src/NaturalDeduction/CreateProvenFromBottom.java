package NaturalDeduction;

import PropositionalLogicFormula.Formula;

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
	public boolean appliedCorrectly(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence result=(Sequence)objects[0];
		Sequence initial=(Sequence)objects[1];
		if(initial.proven!=null)
		{
			return false;
		}
		if(result.proven==null)
		{
			return false;
		}
		if(!Sequence.hypothesisEqual(result, initial))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "|e";
	}

	
}
