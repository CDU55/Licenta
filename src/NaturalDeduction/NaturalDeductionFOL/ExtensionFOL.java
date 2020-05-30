package NaturalDeduction.NaturalDeductionFOL;

import Formulas.FOLFormula;

public class ExtensionFOL implements InferenceRuleFOL {
	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula extension=(FOLFormula)objects[1];
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
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL initial=(SequenceFOL)objects[0];
		FOLFormula extensionFormula=(FOLFormula)objects[1];
		SequenceFOL result=new SequenceFOL(initial.hypothesis,initial.proven);
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
			if(!(objects[i] instanceof SequenceFOL))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial=(SequenceFOL)objects[1];
		if(result.hypothesis.size()-initial.hypothesis.size()!=1)
		{
			return "More than one formula was added";
		}
		int differentFormulas=0;
		for(FOLFormula formula:result.hypothesis)
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
