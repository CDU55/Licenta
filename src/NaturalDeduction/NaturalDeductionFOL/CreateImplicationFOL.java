package NaturalDeduction.NaturalDeductionFOL;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;

public class CreateImplicationFOL implements InferenceRuleFOL {

	public boolean canApply(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula toAdd=(FOLFormula)objects[1];
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
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula toAdd=(FOLFormula)objects[1];
		FOLTreeNode implicationRoot=new FOLTreeNode("->",toAdd.syntaxTree.getRoot(),s1.proven.syntaxTree.getRoot());
		FOLFormula provenResult=new FOLFormula(implicationRoot);
		SequenceFOL result=new SequenceFOL(s1.hypothesis,provenResult);
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
			if(!(objects[i] instanceof SequenceFOL))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		SequenceFOL result=(SequenceFOL)objects[0];
		SequenceFOL initial=(SequenceFOL)objects[1];
		if(result.proven==null || initial.proven==null)
		{
			return "Result right side and initial right side cannot be bottmon";
		}
		if(!result.proven.syntaxTree.getRoot().getLabel().equals("->"))
		{
			return "Result right side is not an implication";
		}
		FOLFormula removedFromHypothesis=new FOLFormula(result.proven.syntaxTree.getRoot().getLeftChild());
		if(result.hypothesis.contains(removedFromHypothesis) || !initial.hypothesis.contains(removedFromHypothesis))
		{
			return "Implication left side was not removed from initial sequence or did not exist in initial";
		}
		if(!initial.proven.syntaxTree.getRoot().equals(result.proven.syntaxTree.getRoot().getRightChild()))
		{
			return "Implicaiton right side is not equal to the initial sequence right side";
		}
		SequenceFOL testHypothesis=new SequenceFOL(initial.hypothesis,null);
		testHypothesis.hypothesis.remove(removedFromHypothesis);
		if(!SequenceFOL.hypothesisEqual(result, testHypothesis))
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
