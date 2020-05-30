package NormalForms;

import Formulas.Formula;

public interface NormalFormTransformationRule {
	
	public boolean canApply(Formula formula);
	public Formula apply(Formula formula);

}
