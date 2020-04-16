package NaturalDeduction;

import PropositionalLogicFormula.Formula;

public class ExtractFromConjunction2 implements InferenceRule {

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
		Formula resultProven=new Formula(s1.proven.syntaxTree.getRoot().getRightChild());
		Sequence result=new Sequence(s1.hypothesis,resultProven);
		return result;

	}

	@Override
	public boolean appliedCorrectly(Object... objects) {
		Sequence result=(Sequence)objects[0];
		Sequence initial=(Sequence)objects[1];
		if(!Sequence.hypothesisEqual(result, initial))
		{
			return false;
		}
		if(!initial.proven.syntaxTree.getRoot().getLabel().equals("/\\"))
		{
			return false;
		}
		Formula expectedResult=new Formula(initial.proven.syntaxTree.getRoot().getRightChild());
		if(!expectedResult.equals(result.proven))
		{
			return false;
		}
		return true;
		
	}

	@Override
	public String toString() {
		return "/\\e2";
	}
	
	

}
