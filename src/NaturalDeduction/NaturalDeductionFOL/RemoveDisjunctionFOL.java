package NaturalDeduction.NaturalDeductionFOL;

import Formulas.FOLFormula;
import NaturalDeduction.Sequence;

public class RemoveDisjunctionFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=3)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		SequenceFOL s3=(SequenceFOL)objects[2];
		if(s1.proven==null || s2.proven==null || s3.proven==null)
		{
			return false;
		}
		if(!s1.proven.syntaxTree.getRoot().getLabel().equals("\\/"))
		{
			return false;
		}
		if(!s2.proven.equals(s3.proven))
		{
			return false;
		}
		FOLFormula disjunctionLeft=new FOLFormula(s1.proven.syntaxTree.getRoot().getLeftChild());
		FOLFormula disjunctionRight=new FOLFormula(s1.proven.syntaxTree.getRoot().getRightChild());
		SequenceFOL testHypothesis1=new SequenceFOL(s2.hypothesis,null);
		testHypothesis1.hypothesis.remove(disjunctionLeft);
		if(!SequenceFOL.hypothesisEqual(s1, testHypothesis1))
		{
			return false;
		}
		SequenceFOL testHypothesis2=new SequenceFOL(s3.hypothesis,null);
		testHypothesis2.hypothesis.remove(disjunctionRight);
		if(!SequenceFOL.hypothesisEqual(s1, testHypothesis2))
		{
			return false;
		}
		return true;

	}

	@Override
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		SequenceFOL result=new SequenceFOL(s1.hypothesis,s2.proven);
		return result;
	}

	@Override
	public String appliedCorrectly(Object... objects) {
		if(objects.length!=4)
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
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial1=(SequenceFOL)objects[1];
		SequenceFOL initial2=(SequenceFOL)objects[2];
		SequenceFOL initial3=(SequenceFOL)objects[3];
		if(!canApply(initial1,initial2,initial3))
		{
			return this.toString() +" cannot be applied for the given arguments";
		}
		if(result.proven==null)
		{
			return "Resulting sequence right side cannot be bottom";
		}
		if(!SequenceFOL.hypothesisEqual(result, initial1))
		{
			return result.toString() +" and "+initial1.toString()+" do not have the same hypothesis";
		}
		if(!result.proven.equals(initial2.proven))
		{
			return result.proven.toString() +" and " +initial2.proven.toString()+" are not equal";
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "\\/e";
	}

}
