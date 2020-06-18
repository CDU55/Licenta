package NormalForms.PrenexNormalForm;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;


public class PrenexTransformator {
	
	List<NormalFormTransformationRuleFOL> rules;
	public PrenexTransformator()
	{
		this.rules=new ArrayList<NormalFormTransformationRuleFOL>();
		this.rules.add(new UniversalCuantifierConjunction());
		this.rules.add(new UniversalCuantifierDisjunction());
		this.rules.add(new ExistentialCuantifierConjunction());
		this.rules.add(new ExistentialCuantifierDisjunction());
		this.rules.add(new UniversalCuantifierNegated());
		this.rules.add(new ExistentialCuantifierNegated());

	}
	
	public FOLFormula apply(String index,FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidRuleName, InvalidSubstitution
	{
		int indexInt=Integer.parseInt(index);
		if(indexInt<1 || indexInt>6)
		{
			throw new InvalidRuleName("Invalid rule index");
		}
		else
		{
			indexInt-=1;
			NormalFormTransformationRuleFOL rule=this.rules.get(indexInt);
			if(!rule.canApply(formula))
			{
				throw new InvalidPropositionalLogicFormula("Rule "+(indexInt+1)+" cannot be applied on "+ formula.toString());
			}
			else
			{
				return rule.apply(formula);
			}
		}
	}
}
