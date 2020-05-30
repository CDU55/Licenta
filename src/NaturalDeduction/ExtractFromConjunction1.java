package NaturalDeduction;

import Formulas.Formula;

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
		
		if(!Sequence.hypothesisEqual(result, initial))
		{
			return initial.toString() +"and" + result.toString()+" do not have the same hypothesis";
		}
		Formula expectedResult=new Formula(initial.proven.syntaxTree.getRoot().getLeftChild());
		if(!expectedResult.equals(result.proven))
		{
			return initial.proven.toString()+" does not contain "+expectedResult.toString();
		}
		return "Ok";
		
	}

	@Override
	public String toString() {
		return "/\\e1";
	}
	

}
