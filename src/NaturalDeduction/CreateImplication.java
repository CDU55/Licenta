package NaturalDeduction;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class CreateImplication implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Formula toAdd=(Formula)objects[1];
		if(s1.proven==null)
		{
			return false;
		}
		if(!s1.hypothesis.contains(toAdd))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Formula toAdd=(Formula)objects[1];
		TreeNode implicationRoot=new TreeNode("->",toAdd.syntaxTree.getRoot(),s1.proven.syntaxTree.getRoot());
		Formula provenResult=new Formula(implicationRoot);
		Sequence result=new Sequence(s1.hypothesis,provenResult);
		result.hypothesis.remove(toAdd);
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
		if(result.proven==null || initial.proven==null)
		{
			return "Result right side and initial right side cannot be bottmon";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("->"))
		{
			return "Result right side is not an implication";
		}
		Formula removedFromHypothesis=new Formula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(result.hypothesis.contains(removedFromHypothesis) || !initial.hypothesis.contains(removedFromHypothesis))
		{
			return "Implication left side was not removed from initial sequence or did not exist in initial";
		}
		if(!initial.proven.syntaxTree.getRoot().equals(result.proven.syntaxTree.getRoot().getRightChild()))
		{
			return "Implicaiton right side is not equal to the initial sequence right side";
		}
		Sequence testHypothesis=new Sequence(initial.hypothesis,null);
		testHypothesis.hypothesis.remove(removedFromHypothesis);
		if(!Sequence.hypothesisEqual(result, testHypothesis))
		{
			return "Arguments do not have the same left side excluding the removed formula";
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "->i";
	}
	
	

}
