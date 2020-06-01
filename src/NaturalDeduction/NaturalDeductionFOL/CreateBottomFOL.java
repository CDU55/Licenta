package NaturalDeduction.NaturalDeductionFOL;

import NaturalDeduction.Sequence;

public class CreateBottomFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		if(s1.proven==null || s2.proven==null)
		{
			return false;
		}
		if(!s2.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return false;
		}
		if(!s1.proven.syntaxTree.getRoot().equals(s2.proven.syntaxTree.getRoot().getLeftChild()))
		{
			return false;
		}
		if(!SequenceFOL.hypothesisEqual(s1, s2))
		{
			return false;
		}
		return true;
	}

	@Override
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL result=new SequenceFOL(s1.hypothesis,null);
		return result;
	}

	@Override
	public String appliedCorrectly(Object... objects) {
		if(objects.length!=3)
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
		SequenceFOL s1=(SequenceFOL)objects[1];
		SequenceFOL s2=(SequenceFOL)objects[2];
		if(result.proven!=null)
		{
			return "The resulting sequence right side must be bottom";
		}
		if(!canApply(s1,s2))
		{
			return this.toString()+" cannot be aplied on "+s1.toString()+" and "+s2.toString();
		}
		if(!SequenceFOL.hypothesisEqual(result, s1) || !SequenceFOL.hypothesisEqual(result, s2))
		{
			return "The resulting sequence does not have the same right side as the arguments";
		}
		return "Ok";
		
	}

	@Override
	public String toString() {
		return "!e";
	}
	
	

}