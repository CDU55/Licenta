package NaturalDeduction;

import AbstractSyntaxTree.TreeNode;
import PropositionalLogicFormula.Formula;

public class CreateDisjunction1 implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
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
		Formula conjuctionRight=(Formula)objects[1];
		TreeNode conjunctionRoot=new TreeNode("\\/",s1.proven.syntaxTree.getRoot(),conjuctionRight.syntaxTree.getRoot());
		Formula conjunction=new Formula(conjunctionRoot);
		Sequence result=new Sequence(s1.hypothesis,conjunction);
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
		if(!canApply(initial))
		{
			return this.toString()+" cannot be applied for "+initial.toString();
		}
		if(result.proven==null)
		{
			return "Result right side cannot be bottom";
		}
		if(!Sequence.hypothesisEqual(initial, result))
		{
			return "Result and argument do not have the same left side";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("\\/"))
		{
			return "Result right side is not a disjunction";
		}
		if(!result.proven.syntaxTree.getRoot().getLeftChild().equals(initial.proven.syntaxTree.getRoot()))
		{
			return "Result right side is not a disjunction that contains the initial sequence right side on the left";
		}
		return "Ok";

	}

	@Override
	public String toString() {
		return "\\/i1";
	}
	
	

}
