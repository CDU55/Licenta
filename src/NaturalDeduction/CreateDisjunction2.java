package NaturalDeduction;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class CreateDisjunction2 implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		if(s1.proven==null)
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Formula conjuctionLeft=(Formula)objects[1];
		TreeNode conjunctionRoot=new TreeNode("\\/",conjuctionLeft.syntaxTree.getRoot(),s1.proven.syntaxTree.getRoot());
		Formula conjunction=new Formula(conjunctionRoot);
		Sequence result=new Sequence(s1.hypothesis,conjunction);
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
		if(!canApply(initial))
		{
			return false;
		}
		if(result.proven==null)
		{
			return false;
		}
		if(!Sequence.hypothesisEqual(initial, result))
		{
			return false;
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("\\/"))
		{
			return false;
		}
		if(!result.proven.syntaxTree.getRoot().getRightChild().equals(initial.proven.syntaxTree.getRoot()))
		{
			return false;
		}
		return true;

	}

	@Override
	public String toString() {
		return "\\/i2";
	}
	
	

}
