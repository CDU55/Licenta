package NormalForms.SkolemNormalForm;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;

public class FNCTransformatorFOL {
	
	List<NormalFormTransformationRuleFOL> rules;
	public FNCTransformatorFOL()
	{
		this.rules=new ArrayList<NormalFormTransformationRuleFOL>();
		this.rules.add(new ReplaceLeftDisjunctionFOL());
		this.rules.add(new ReplaceRightDisjunctionFOL());
		this.rules.add(new DisjunctionAssociativityFOL());
		this.rules.add(new ConjunctionAssociativityFOL());
		this.rules.add(new DeMorganDisjunctionFOL());
		this.rules.add(new DeMorganConjunctionFOL());
		this.rules.add(new RemoveDoubleNegationFOL());
	}
	
	public FOLFormula apply(String index,FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidRuleName, InvalidSubstitution
	{
		int indexInt=Integer.parseInt(index);
		if(indexInt<1 || indexInt>7)
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
