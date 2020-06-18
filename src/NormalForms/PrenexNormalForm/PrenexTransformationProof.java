package NormalForms.PrenexNormalForm;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;

import Formulas.FOLFormula;


public class PrenexTransformationProof {
	private static final PrenexTransformator transformator=new PrenexTransformator();
	public static FOLFormula lastTransformation;

	public static List<String> transform(FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidSubstitution
	{
		List<String> transformation=new ArrayList<String>();
		if(PrenexNormalForm.testPrenexNormalForm(formula))
		{
				transformation.add(formula.toString()+" is already in Prenex Normal Form");

			return transformation;
		}
		
		FOLFormula transformed=new FOLFormula(formula.syntaxTree.getRoot());
		transformed.replaceImplications();
		if(!transformed.equals(formula))
		{
			transformation.add("Removed implications : "+formula.toString()+"   ==>   "+transformed.toString());
		}
		
		while(!PrenexNormalForm.testPrenexNormalForm(transformed))
		{
			List<String> subformulasString=transformed.syntaxTree.getSubformulas();
			List<FOLFormula> subformulas=new ArrayList<FOLFormula>();
			for(String subf:subformulasString)
			{
				subformulas.add(new FOLFormula(subf));
			}
			for(int ruleIndex=0;ruleIndex<transformator.rules.size();ruleIndex++)
			{
				NormalFormTransformationRuleFOL rule=transformator.rules.get(ruleIndex);
				for(FOLFormula subf:subformulas)
				{
					if(rule.canApply(subf))
					{
						FOLFormula transf=rule.apply(subf);
						transformation.add("Rule "+(ruleIndex+1)+" applied : "+subf.toString()+"   ==>   "+transf.toString());
						transformed.replaceSubFormula(subf, transf);
						transformation.add("Resulting formula : "+transformed.toString());
						subformulas.remove(subf);
						subformulas.add(transf);
						break;
					}
				}
			
			}
		}

		transformation.add("Transformed formula : "+transformed.toString());
		lastTransformation=transformed;
		return transformation;
	}
}
