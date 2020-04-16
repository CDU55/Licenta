package NaturalDeduction;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

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
	public boolean appliedCorrectly(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence result=(Sequence)objects[0];
		Sequence initial=(Sequence)objects[1];
		if(result.proven==null)
		{
			return false;
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return false;
		}
		Formula toNegate=new Formula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(!canApply(initial,toNegate))
		{
			return false;
		}
		Sequence testHypothesis=new Sequence(initial.hypothesis,null);
		testHypothesis.hypothesis.remove(toNegate);
		if(!Sequence.hypothesisEqual(result, testHypothesis))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "!i";
	}
	
	

}
