package NaturalDeduction;

import AbstractSyntaxTree.TreeNode;
import Formulas.Formula;

public class CreateNegationFromBottom implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Formula toNegate=(Formula)objects[1];
		if(s1.proven!=null)
		{
			return false;
		}
		if(!s1.hypothesis.contains(toNegate))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Formula toNegate=(Formula)objects[1];
		TreeNode negationRoot=new TreeNode("!",toNegate.syntaxTree.getRoot(),null);
		Formula negation=new Formula(negationRoot);
		Sequence result=new Sequence(s1.hypothesis,negation);
		result.hypothesis.remove(toNegate);
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
		if(result.proven==null)
		{
			return "Resulting sequence right side cannot be bottom";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return "Resulting sequence right side is not a negation";
		}
		Formula toNegate=new Formula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(!canApply(initial,toNegate))
		{
			return this.toString()+" cannot be applied for "+initial.toString()+" and "+toNegate.toString();
		}
		Sequence testHypothesis=new Sequence(initial.hypothesis,null);
		testHypothesis.hypothesis.remove(toNegate);
		if(!Sequence.hypothesisEqual(result, testHypothesis))
		{
			return "Initial sequence and resulting sequence do not have the same left side excluding the negated formula";
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "!i";
	}
	
	

}
