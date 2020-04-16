package NaturalDeduction;

import PropositionalLogicFormula.Formula;

public class RemoveDoubleNegation implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		Sequence s1=(Sequence)objects[0];
		Formula proven=s1.proven;
		if(s1.proven==null)
		{
			return false;
		}
		if(!proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return false;
		}
		if(!proven.syntaxTree.getRoot().getLeftChild().getLabel().equals("!"))
		{
			return false;
		}
		return true;
		
	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1=(Sequence)objects[0];
		Formula doubleNegationRemoved=new Formula(s1.proven.syntaxTree.getRoot().getLeftChild().getLeftChild());
		Sequence result=new Sequence(s1.hypothesis,doubleNegationRemoved);
		return result;

	}

	@Override
	public boolean appliedCorrectly(Object... objects) {
		if(objects.length!=2)
		{
			return false;
		}
		Sequence result=(Sequence)objects[0];
		Sequence doubleNegation=(Sequence)objects[1];
		if(!doubleNegation.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return false;
		}
		if(!doubleNegation.proven.syntaxTree.getRoot().getLeftChild().getLabel().equals("!"))
		{
			return false;
		}
		if(!doubleNegation.proven.syntaxTree.getRoot().getLeftChild().getLeftChild().equals(result.proven.syntaxTree.getRoot()))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "!!e";
	}
	
	

}
