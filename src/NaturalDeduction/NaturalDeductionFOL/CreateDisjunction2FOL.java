package NaturalDeduction.NaturalDeductionFOL;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;

public class CreateDisjunction2FOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		if(s1.proven==null)
		{
			return false;
		}
		return true;
	}

	@Override
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula conjuctionLeft=(FOLFormula)objects[1];
		FOLTreeNode conjunctionRoot=new FOLTreeNode("\\/",conjuctionLeft.syntaxTree.getRoot(),s1.proven.syntaxTree.getRoot());
		FOLFormula conjunction=new FOLFormula(conjunctionRoot);
		SequenceFOL result=new SequenceFOL(s1.hypothesis,conjunction);
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
			if(!(objects[i] instanceof SequenceFOL))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial=(SequenceFOL)objects[1];
		FOLFormula addedFormula=new FOLFormula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(!canApply(initial,addedFormula))
		{
			return this.toString()+" cannot be applied for "+initial.toString();
		}
		if(result.proven==null)
		{
			return "Result right side cannot be bottom";
		}
		if(!SequenceFOL.hypothesisEqual(initial, result))
		{
			return "Result and argument do not have the same left side";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("\\/"))
		{
			return "Result right side is not a disjunction";
		}
		if(!result.proven.syntaxTree.getRoot().getRightChild().equals(initial.proven.syntaxTree.getRoot()))
		{
			return "Result right side is not a disjunction that contains the initial sequence right side on the right";
		}
		return "Ok";

	}

	@Override
	public String toString() {
		return "\\/i2";
	}
	

}
