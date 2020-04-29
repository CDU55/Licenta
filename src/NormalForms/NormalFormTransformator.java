package NormalForms;

import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import PropositionalLogicFormula.Formula;

public abstract class NormalFormTransformator {
	
	protected List<NormalFormTransformationRule> rules;

	public abstract Formula apply(String index,Formula formula)throws InvalidPropositionalLogicFormula, InvalidRuleName;

}
