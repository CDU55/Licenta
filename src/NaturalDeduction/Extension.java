package NaturalDeduction;

import PropositionalLogicFormula.Formula;

public class Extension implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Formula extension=(Formula)objects[1];
		if(s1.proven==null)
		{
			return false;
		}
		if(s1.hypothesis.contains(extension))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence initial=(Sequence)objects[0];
		Formula extensionFormula=(Formula)objects[1];
		Sequence result=new Sequence(initial.hypothesis,initial.proven);
		result.hypothesis.add(extensionFormula);
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
		if(result.hypothesis.size()-initial.hypothesis.size()!=1)
		{
			return "More than one formula was added";
		}
		int differentFormulas=0;
		for(Formula formula:result.hypothesis)
		{
			if(!initial.hypothesis.contains(formula))
			{
				differentFormulas++;
			}
		}
		if(differentFormulas!=1)
		{
			return "More than one formula was added";
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "EXTINDERE";
	}
	
	

}
