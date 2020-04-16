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
	public boolean appliedCorrectly(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence result=(Sequence)objects[0];
		Sequence initial=(Sequence)objects[1];
		if(result.proven==null || initial.proven==null)
		{
			return false;
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("->"))
		{
			return false;
		}
		Formula removedFromHypothesis=new Formula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(result.hypothesis.contains(removedFromHypothesis) || !initial.hypothesis.contains(removedFromHypothesis))
		{
			return false;
		}
		if(!initial.proven.syntaxTree.getRoot().equals(result.proven.syntaxTree.getRoot().getRightChild()))
		{
			return false;
		}
		Sequence testHypothesis=new Sequence(initial.hypothesis,null);
		testHypothesis.hypothesis.remove(removedFromHypothesis);
		if(!Sequence.hypothesisEqual(result, testHypothesis))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "->i";
	}
	
	

}
