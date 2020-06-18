package NormalForms.PrenexNormalForm;

import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;

public interface NormalFormTransformationRuleFOL {
	public boolean canApply(FOLFormula formula);
	public FOLFormula apply(FOLFormula formula) throws InvalidSubstitution;
}
