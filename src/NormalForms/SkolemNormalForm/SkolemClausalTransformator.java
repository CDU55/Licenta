package NormalForms.SkolemNormalForm;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;
import Operators.TypeTesterFirstOrderLogic;

public class SkolemClausalTransformator {
	public static FOLFormula lastTransformations;
	public static FOLFormula lastFNCTransformation;
	public static FNCFOL fnc=new FNCFOL();
	public static List<String> transform(FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidSubstitution
	{
		List<String> transformation=SkolemTransformator.transform(formula);
		FOLFormula skolemForm=SkolemTransformator.lastTransformations;
		FOLTreeNode cuantifiersRemoved=removeCuantifiers(skolemForm);
		if(!skolemForm.syntaxTree.getRoot().equals(cuantifiersRemoved))
		{
			transformation.add("Cuantifiers Removed : " +skolemForm.toString()+" => "+cuantifiersRemoved.toString());
		}
		transformation.add("Converting to FNC...");
		List<String> transformationFNC=transformFNC(new FOLFormula(cuantifiersRemoved));
		transformation.addAll(transformationFNC);
		FOLTreeNode fncTransformed=lastFNCTransformation.syntaxTree.getRoot();
		FOLTreeNode node=skolemForm.syntaxTree.getRoot();
		if(!TypeTesterFirstOrderLogic.isCuantifierWithTerm(node.getLabel()))
		{
					node=fncTransformed;
		}
		else {
		while(TypeTesterFirstOrderLogic.isCuantifierWithTerm(node.getLabel()))
		{
			if(!TypeTesterFirstOrderLogic.isCuantifierWithTerm(node.getLeftChild().getLabel()))
			{
				node.setLeftChild(fncTransformed);
				break;
			}
			node=node.getLeftChild();
		}
		}
		transformation.add("Transformed Formula : "+node.toString());
		lastTransformations=new FOLFormula(node);
		return transformation;
	}
	public static boolean checkNormalForm(FOLFormula formula)
	{	
			return fnc.checkFormula(formula);
	}
	
	private static FOLTreeNode removeCuantifiers(FOLFormula formula)
	{
		FOLTreeNode node=new FOLTreeNode(formula.syntaxTree.getRoot());
		while(TypeTesterFirstOrderLogic.isCuantifierWithTerm(node.getLabel()))
		{
			node=node.getLeftChild();
		}
		return node;
	}
	public static List<String> transformFNC(FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidSubstitution
	{
		List<String> transformation=new ArrayList<String>();
		if(checkNormalForm(formula))
		{
				transformation.add(formula.toString()+" is already in FNC");
			lastFNCTransformation=formula;
				return transformation;
		}
		
		FOLFormula transformed=new FOLFormula(formula.syntaxTree.getRoot());
		transformed.replaceImplications();
		if(!transformed.equals(formula))
		{
			transformation.add("Removed implications : "+formula.toString()+"   ==>   "+transformed.toString());
		}
		FNCTransformatorFOL transformator=new FNCTransformatorFOL();
		
		while(!checkNormalForm(transformed))
		{
			List<String> subformulasString=transformed.syntaxTree.getSubformulas();
			List<FOLFormula> subformulas=new ArrayList<FOLFormula>();
			for(String subf:subformulasString)
			{
				subformulas.add(new FOLFormula(subf));
			}
			for(int ruleIndex=1;ruleIndex<transformator.rules.size();ruleIndex++)
			{
				boolean trans=false;
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
						trans=true;
						break;
					}
				}
				if(trans)
				{
					break;
				}
			
			}
		}
		transformation.add("Transformed formula : "+ transformed);
		lastFNCTransformation=transformed;
		return transformation;

	}
}
