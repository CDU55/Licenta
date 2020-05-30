package NaturalDeduction.NaturalDeductionFOL;

import Formulas.FOLFormula;

public class ExtractFromConjunction2FOL implements InferenceRuleFOL {
	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		if(s1.proven==null)
		{
			return false;
		}
		if(!s1.proven.syntaxTree.getRoot().getLabel().equals("/\\"))
		{
			return false;
		}
		return true;
	}

	@Override
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula resultProven=new FOLFormula(s1.proven.syntaxTree.getRoot().getRightChild());
		SequenceFOL result=new SequenceFOL(s1.hypothesis,resultProven);
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
		if(!SequenceFOL.hypothesisEqual(result, initial))
		{
			return this.toString()+" cannot be applied for "+initial.toString();
		}
		if(!initial.proven.syntaxTree.getRoot().getLabel().equals("/\\"))
		{
			return initial.toString() +"and" + result.toString()+" do not have the same hypothesis";
		}
		FOLFormula expectedResult=new FOLFormula(initial.proven.syntaxTree.getRoot().getRightChild());
		if(!expectedResult.equals(result.proven))
		{
			return initial.proven.toString()+" does not contain "+expectedResult.toString();
		}
		return "Ok";
		
	}

	@Override
	public String toString() {
		return "/\\e2";
	}
	
}
