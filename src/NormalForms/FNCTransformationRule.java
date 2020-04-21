package NormalForms;

import PropositionalLogicFormula.Formula;

public interface FNCTransformationRule {
	
	public boolean canApply(Formula formula);
	public Formula apply(Formula formula);

}
