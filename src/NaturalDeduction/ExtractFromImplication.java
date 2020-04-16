package NaturalDeduction;

import PropositionalLogicFormula.Formula;

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
	public boolean appliedCorrectly(Object... objects) {
		if(objects.length!=3)
		{
			return false;
		}
		Sequence implicationRight = (Sequence) objects[0];
		Sequence implication = (Sequence) objects[1];
		Sequence implicationLeft = (Sequence) objects[2];
		if (!Sequence.hypothesisEqual(implication, implicationLeft)
				|| !Sequence.hypothesisEqual(implication, implicationRight)) {
			return false;
		}
		if (!implication.proven.syntaxTree.getRoot().getLabel().equals("->")) {
			return false;
		}
		if (!implication.proven.syntaxTree.getRoot().getLeftChild().equals(implicationLeft.proven.syntaxTree.getRoot())
				|| !implication.proven.syntaxTree.getRoot().getRightChild()
						.equals(implicationRight.proven.syntaxTree.getRoot())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "->e";
	}
	
	

}
