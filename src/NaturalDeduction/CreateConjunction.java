package NaturalDeduction;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class CreateConjunction implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Sequence s2=(Sequence)objects[1];
		if(!Sequence.hypothesisEqual(s1, s2))
		{
			return false;
		}
		if(s1.proven==null || s2.proven==null)
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Sequence s2=(Sequence)objects[1];
		TreeNode cojunctionRoot=new TreeNode("/\\",new TreeNode(s1.proven.syntaxTree.getRoot()),new TreeNode(s2.proven.syntaxTree.getRoot()));
		Formula conjunction=new Formula(cojunctionRoot);
		Sequence result=new Sequence(s1.hypothesis,conjunction);
		return result;
	}

	@Override
	public boolean appliedCorrectly(Object... objects) {
		Sequence result=(Sequence)objects[0];
		Sequence initial1=(Sequence)objects[1];
		Sequence initial2=(Sequence)objects[2];
		if(!canApply(initial1,initial2))
		{
			return false;
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("/\\"))
		{
			return false;
		}
		if(!Sequence.hypothesisEqual(result, initial2) || !Sequence.hypothesisEqual(result, initial2))
		{
			return false;
		}
		Formula leftChild=new Formula(result.proven.syntaxTree.getRoot().getLeftChild());
		Formula rightChild=new Formula(result.proven.syntaxTree.getRoot().getRightChild());
		if(!initial1.proven.equals(leftChild) || !initial2.proven.equals(rightChild))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "/\\i";
	}

	
}
