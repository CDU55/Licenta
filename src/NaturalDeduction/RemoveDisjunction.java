package NaturalDeduction;

import PropositionalLogicFormula.Formula;

public class RemoveDisjunction implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=3)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Sequence s2=(Sequence)objects[1];
		Sequence s3=(Sequence)objects[2];
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
		Formula disjunctionLeft=new Formula(s1.proven.syntaxTree.getRoot().getLeftChild());
		Formula disjunctionRight=new Formula(s1.proven.syntaxTree.getRoot().getRightChild());
		Sequence testHypothesis1=new Sequence(s2.hypothesis,null);
		testHypothesis1.hypothesis.remove(disjunctionLeft);
		if(!Sequence.hypothesisEqual(s1, testHypothesis1))
		{
			return false;
		}
		Sequence testHypothesis2=new Sequence(s3.hypothesis,null);
		testHypothesis2.hypothesis.remove(disjunctionRight);
		if(!Sequence.hypothesisEqual(s1, testHypothesis2))
		{
			return false;
		}
		return true;

	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Sequence s2=(Sequence)objects[1];
		Sequence result=new Sequence(s1.hypothesis,s2.proven);
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
		Sequence result=(Sequence)objects[0];
		Sequence initial1=(Sequence)objects[1];
		Sequence initial2=(Sequence)objects[2];
		Sequence initial3=(Sequence)objects[3];
		if(!canApply(initial1,initial2,initial3))
		{
			return this.toString() +" cannot be applied for the given arguments";
		}
		if(result.proven==null)
		{
			return "Resulting sequence right side cannot be bottom";
		}
		if(!Sequence.hypothesisEqual(result, initial1))
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
