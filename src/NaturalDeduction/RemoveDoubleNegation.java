package NaturalDeduction;

import Formulas.Formula;

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
		Sequence doubleNegation=(Sequence)objects[1];
		if(!doubleNegation.proven.syntaxTree.getRoot().getLabel().equals("!"))
		{
			return doubleNegation.proven.toString() +" is not a  negation";
		}
		if(!doubleNegation.proven.syntaxTree.getRoot().getLeftChild().getLabel().equals("!"))
		{
			return doubleNegation.proven.toString() +" is not a double  negation";
		}
		if(!doubleNegation.proven.syntaxTree.getRoot().getLeftChild().getLeftChild().equals(result.proven.syntaxTree.getRoot()))
		{
			return "Result sequence right side does not match initial sequence right side with the double negation removed";
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "!!e";
	}
	
	

}
