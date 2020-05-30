package NaturalDeduction.NaturalDeductionFOL;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;

public class CreateNegationFromBottomFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula toNegate=(FOLFormula)objects[1];
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
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula toNegate=(FOLFormula)objects[1];
		FOLTreeNode negationRoot=new FOLTreeNode("!",toNegate.syntaxTree.getRoot(),null);
		FOLFormula negation=new FOLFormula(negationRoot);
		SequenceFOL result=new SequenceFOL(s1.hypothesis,negation);
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
			if(!(objects[i] instanceof SequenceFOL))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial=(SequenceFOL)objects[1];
		if(result.proven==null)
		{
			return "Resulting sequence right side cannot be bottom";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return "Resulting sequence right side is not a negation";
		}
		FOLFormula toNegate=new FOLFormula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(!canApply(initial,toNegate))
		{
			return this.toString()+" cannot be applied for "+initial.toString()+" and "+toNegate.toString();
		}
		SequenceFOL testHypothesis=new SequenceFOL(initial.hypothesis,null);
		testHypothesis.hypothesis.remove(toNegate);
		if(!SequenceFOL.hypothesisEqual(result, testHypothesis))
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
