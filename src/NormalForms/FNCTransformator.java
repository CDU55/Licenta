package NormalForms;

import java.util.ArrayList;
import java.util.List;

import PropositionalLogicFormula.Formula;

public class FNCTransformator {
	
	private final List<FNCTransformationRule> rules;
	public FNCTransformator()
	{
		this.rules=new ArrayList<FNCTransformationRule>();
		this.rules.add(new RemoveDoubleImplication());
		this.rules.add(new RemoveImplication());
		this.rules.add(new ReplaceLeftDisjunction());
		this.rules.add(new ReplaceRightDisjunction());
		this.rules.add(new DisjunctionAssociativity());
		this.rules.add(new ConjunctionAssociativity());
		this.rules.add(new DeMorganConjunction());
		this.rules.add(new DeMorganDisjunction());
		this.rules.add(new RemoveDoubleNegation());
	}
	
	public Formula apply(String index,Formula formula)
	{
		int indexInt=Integer.parseInt(index);
		if(indexInt<1 || indexInt>9)
		{
			//exceptie
			return null;
		}
		else
		{
			FNCTransformationRule rule=this.rules.get(indexInt);
			if(!rule.canApply(formula))
			{
				//exceptie
				return null;
			}
			else
			{
				return rule.apply(formula);
			}
		}
	}

}