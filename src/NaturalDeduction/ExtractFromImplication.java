package NaturalDeduction;

import Formulas.Formula;

public class ExtractFromImplication implements InferenceRule {

	@Override
	public boolean canApply(Object... objects) {
		if (objects.length != 2) {
			return false;
		}
		Sequence s1 = (Sequence) objects[0];
		Sequence s2 = (Sequence) objects[1];
		if (!Sequence.hypothesisEqual(s1, s2)) {
			return false;
		}
		if(s1.proven==null || s2.proven==null)
		{
			return false;
		}
		if (!s1.proven.syntaxTree.getRoot().getLabel().equals("->")) {
			return false;
		}
		if (!s1.proven.syntaxTree.getRoot().getLeftChild().equals(s2.proven.syntaxTree.getRoot())) {
			return false;
		}
		return true;

	}

	@Override
	public Sequence Apply(Object... objects) {
		Sequence s1 = (Sequence) objects[0];
		Formula resultProven = new Formula(s1.proven.syntaxTree.getRoot().getRightChild());
		Sequence result = new Sequence(s1.hypothesis, resultProven);
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
			if(!(objects[i] instanceof Sequence))
			{
				return "Argument "+i+" type is not valid";
			}
		}
		Sequence implicationRight = (Sequence) objects[0];
		Sequence implication = (Sequence) objects[1];
		Sequence implicationLeft = (Sequence) objects[2];
		if (!Sequence.hypothesisEqual(implication, implicationLeft)
				|| !Sequence.hypothesisEqual(implication, implicationRight)) {
			return "Resulting sequence and arguments do not have the same hypothesis";
		}
		if (!implication.proven.syntaxTree.getRoot().getLabel().equals("->")) {
			return implication.toString()+" rights side is not and implication";
		}
		if (!implication.proven.syntaxTree.getRoot().getLeftChild().equals(implicationLeft.proven.syntaxTree.getRoot())
				|| !implication.proven.syntaxTree.getRoot().getRightChild()
						.equals(implicationRight.proven.syntaxTree.getRoot())) {
			return "The implication is not a composed of "+implicationLeft.proven.toString() + " and "+implicationRight.proven.toString();
		}
		return "Ok";
	}

	@Override
	public String toString() {
		return "->e";
	}
	
	

}
