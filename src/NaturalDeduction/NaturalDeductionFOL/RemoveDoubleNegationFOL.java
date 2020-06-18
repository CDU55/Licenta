package NaturalDeduction.NaturalDeductionFOL;

import Formulas.FOLFormula;

public class RemoveDoubleNegationFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if(objects.length!=1)
		{
			return false;
		}
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula proven=s1.proven;
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
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1=(SequenceFOL)objects[0];
		FOLFormula doubleNegationRemoved=new FOLFormula(s1.proven.syntaxTree.getRoot().getLeftChild().getLeftChild());
		SequenceFOL result=new SequenceFOL(s1.hypothesis,doubleNegationRemoved);
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
		SequenceFOL doubleNegation=(SequenceFOL)objects[1];
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
