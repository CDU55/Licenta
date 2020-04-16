package NaturalDeduction;

import PropositionalLogicFormula.Formula;

public class ExtractFromConjunction1 implements InferenceRule {

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
		if(!s1.proven.syntaxTree.getRoot().getLabel().equals("/\\"))
		{
			return false;
		}
		return true;
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Formula resultProven=new Formula(s1.proven.syntaxTree.getRoot().getLeftChild());
		Sequence result=new Sequence(s1.hypothesis,resultProven);
		return result;

	}

	@Override
	public boolean appliedCorrectly(Object... objects) {
		Sequence result=(Sequence)objects[0];
		Sequence initial=(Sequence)objects[1];
		if(!canApply(initial))
		{
			return false;
		}
		if(!Sequence.hypothesisEqual(result, initial))
		{
			return false;
		}
		Formula expectedResult=new Formula(initial.proven.syntaxTree.getRoot().getLeftChild());
		if(!expectedResult.equals(result.proven))
		{
			return false;
		}
		return true;
		
	}

	@Override
	public String toString() {
		return "/\\e1";
	}
	

}
