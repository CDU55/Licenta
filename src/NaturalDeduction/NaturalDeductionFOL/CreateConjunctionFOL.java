package NaturalDeduction.NaturalDeductionFOL;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;

public class CreateConjunctionFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		if(!SequenceFOL.hypothesisEqual(s1, s2))
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
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		SequenceFOL s2=(SequenceFOL)objects[1];
		FOLTreeNode cojunctionRoot=new FOLTreeNode("/\\",new FOLTreeNode(s1.proven.syntaxTree.getRoot()),new FOLTreeNode(s2.proven.syntaxTree.getRoot()));
		FOLFormula conjunction=new FOLFormula(cojunctionRoot);
		SequenceFOL result=new SequenceFOL(s1.hypothesis,conjunction);
		return result;
	}

	@Override
	public String appliedCorrectly(Object... objects) {
		if(objects.length!=3)
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
		SequenceFOL initial1=(SequenceFOL)objects[1];
		SequenceFOL initial2=(SequenceFOL)objects[2];
		
		if(!canApply(initial1,initial2))
		{
			return this.toString()+" cannot be aplied for"+ initial1.toString()+" and "+initial2.toString();
		}
		if(result.proven==null)
		{
			return "Result right side cannot be bottom";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("/\\"))
		{
			return "The result sequence right side is not a conjunction";
		}
		if(!SequenceFOL.hypothesisEqual(result, initial2) || !SequenceFOL.hypothesisEqual(result, initial2))
		{
			return "The resulting sequence does not have the same left side as the arguments";
		}
		FOLFormula leftChild=new FOLFormula(result.proven.syntaxTree.getRoot().getLeftChild());
		FOLFormula rightChild=new FOLFormula(result.proven.syntaxTree.getRoot().getRightChild());
		if(!initial1.proven.equals(leftChild) || !initial2.proven.equals(rightChild))
		{
			return "The resulting sequence is not a conjunction of the arguments";
		}

		return "OK";
	}

	@Override
	public String toString() {
		return "/\\i";
	}

	

}
