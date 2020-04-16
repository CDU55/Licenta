package NaturalDeduction;

import PropositionalLogicFormula.Formula;

public class Hypothesis implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence s =(Sequence)objects[0];
		Formula proven=(Formula)objects[1];
		if(!s.hypothesis.contains(proven))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s =(Sequence)objects[0];
		Formula proven=(Formula)objects[1];
		Sequence result=new Sequence(s.hypothesis,proven);
		return result;
	}

	@Override
	public boolean appliedCorrectly(Object... objects) {
		 if(objects.length!=1)
		 {
			 return false;
		 }
		 Sequence s=(Sequence)objects[0];
		 if(s.proven==null)
		 {
			 return false;
		 }
		 if(!s.hypothesis.contains(s.proven))
		 {
			 return false;
		 }
		 return true;
	}

	@Override
	public String toString() {
		return "IPOTEZA";
	}

	
}
