package NaturalDeduction.NaturalDeductionFOL;

import Formulas.FOLFormula;

public class ExtractFromImplicationFOL implements InferenceRuleFOL {

	@Override
	public boolean canApply(Object... objects) {
		if (objects.length != 2) {
			return false;
		}
		SequenceFOL s1 = (SequenceFOL) objects[0];
		SequenceFOL s2 = (SequenceFOL) objects[1];
		if (!SequenceFOL.hypothesisEqual(s1, s2)) {
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
	public SequenceFOL Apply(Object... objects) {
		SequenceFOL s1 = (SequenceFOL) objects[0];
		FOLFormula resultProven = new FOLFormula(s1.proven.syntaxTree.getRoot().getRightChild());
		SequenceFOL result = new SequenceFOL(s1.hypothesis, resultProven);
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
		SequenceFOL implicationRight = (SequenceFOL) objects[0];
		SequenceFOL implication = (SequenceFOL) objects[1];
		SequenceFOL implicationLeft = (SequenceFOL) objects[2];
		if (!SequenceFOL.hypothesisEqual(implication, implicationLeft)
				|| !SequenceFOL.hypothesisEqual(implication, implicationRight)) {
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
