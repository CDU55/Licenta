package NormalForms;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import FormulaStringConverters.FNCStringConverter;
import FormulaStringConverters.FNDStringConverter;
import PropositionalLogicFormula.Formula;

public class NormalFormTransformationProof {
	
	private static final NormalForm fnc=new FNC();
	private static final NormalForm fnd=new FND();
	public static boolean checkNormalForm(Formula formula,boolean checkFNC)
	{	
		if(checkFNC)
		{
			return fnc.checkFormula(formula);
		}
		else
		{
			return fnd.checkFormula(formula);
		}
	}
	
	public static List<String> transform(Formula formula,boolean transformIntoFNC) throws InvalidPropositionalLogicFormula
	{
		List<String> transformation=new ArrayList<String>();
		if(checkNormalForm(formula,transformIntoFNC))
		{
			if(transformIntoFNC)
			{
				transformation.add(formula.toString()+" is already in FNC");
			}
			else
			{
				transformation.add(formula.toString()+" is already in FND");

			}
			return transformation;
		}
		
		Formula transformed=new Formula(formula.syntaxTree.getRoot());
		transformed.replaceImplications();
		if(!transformed.equals(formula))
		{
			transformation.add("Removed implications : "+formula.toString()+"   ==>   "+transformed.toString());
		}
		NormalFormTransformator transformator=null;
		if(transformIntoFNC)
		{
			transformator=new FNCTransformator();
		}
		else
		{
			transformator=new FNDTransformator();
		}
		
		
		while(!checkNormalForm(transformed,transformIntoFNC))
		{
			List<String> subformulasString=transformed.syntaxTree.getSubformulas();
			List<Formula> subformulas=new ArrayList<Formula>();
			for(String subf:subformulasString)
			{
				subformulas.add(new Formula(subf));
			}
			for(int ruleIndex=1;ruleIndex<transformator.rules.size();ruleIndex++)
			{
				NormalFormTransformationRule rule=transformator.rules.get(ruleIndex);
				for(Formula subf:subformulas)
				{
					if(rule.canApply(subf))
					{
						Formula transf=rule.apply(subf);
						transformation.add("Rule "+(ruleIndex+1)+" applied : "+subf.toString()+"   ==>   "+transf.toString());
						transformed.replaceSubformula(subf, transf);
						transformation.add("Resulting formula : "+transformed.toString());
						subformulas.remove(subf);
						subformulas.add(transf);
						break;
					}
				}
			
			}
		}
		if(transformIntoFNC)
		{
			transformation.add("Transformed formula : "+transformed.convertToStringForm(new FNCStringConverter()));
		}
		else
		{
		transformation.add("Transformed formula : "+transformed.convertToStringForm(new FNDStringConverter()));
		}
		return transformation;

	}

}
