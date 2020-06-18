package NormalForms.SkolemNormalForm;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.Substitution;
import Formulas.FOLFormula;
import NormalForms.PrenexNormalForm.PrenexTransformationProof;
import Operators.TypeTesterFirstOrderLogic;

public class SkolemTransformator {

	public static FOLFormula lastTransformations;
	public static List<String> transform(FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidSubstitution
	{
		List<String> transformation=PrenexTransformationProof.transform(formula);
		FOLFormula newFormula=PrenexTransformationProof.lastTransformation;
		transformation.add("Existential closure : "+newFormula.toString() +" => "+newFormula.syntaxTree.existentialClosure().toString());
		newFormula=new FOLFormula(newFormula.syntaxTree.existentialClosure());
		newFormula=SkolemLemma(newFormula,transformation);
		lastTransformations=newFormula;
		transformation.add("Transformed formula : "+newFormula.toString());
		return transformation;
	}
	
	
	private static FOLFormula SkolemLemma(FOLFormula formula,List<String> transformation) throws InvalidSubstitution
	{
		List<String> existentialCuantifiers=new ArrayList<String>();
		List<String> universallyCuantifiedVariables=new ArrayList<String>();
		List<String> functions=formula.syntaxTree.getFunctionsAndPredicates();
		List<Substitution> substitutions=new ArrayList<Substitution>();
		replaceExistentialCuantifiers(formula.syntaxTree.getRoot(),existentialCuantifiers,universallyCuantifiedVariables,substitutions,functions);
		FOLFormula result=new FOLFormula(formula.syntaxTree.getRoot());
		for(String existentialCuantifier:existentialCuantifiers)
		{
			result.removeCuantifier(existentialCuantifier);
			transformation.add("Removed existential cuantifier : "+existentialCuantifier);
		}
		for(Substitution s: substitutions)
		{
			result.executeSubstitution(s);
			transformation.add("Replaced variable : "+s.Initial.toString()+" => "+s.Final.toString());
		}
		return result;
	}
	
	private static void replaceExistentialCuantifiers(FOLTreeNode currentNode,List<String> existentialCuantifiers,List<String> universallyCuantifiedVariables,List<Substitution> substitutions,List<String> functions) throws InvalidSubstitution
	{
		if(TypeTesterFirstOrderLogic.isCuantifierWithTerm(currentNode.getLabel()))
		{
			String cuantified=currentNode.getLabel().substring(1, currentNode.getLabel().length()-1);
			if(currentNode.getLabel().charAt(0)=='V')
			{
				if(!universallyCuantifiedVariables.contains(cuantified))
				{
					universallyCuantifiedVariables.add(cuantified);
				}
			}
			else
			{
				if(!existentialCuantifiers.contains(currentNode.getLabel()))
				{
					existentialCuantifiers.add(currentNode.getLabel());
				}
				substitutions.add(new Substitution(cuantified,getReplacementFunction(functions,universallyCuantifiedVariables)));
			}
			replaceExistentialCuantifiers(currentNode.getLeftChild(),existentialCuantifiers,universallyCuantifiedVariables,substitutions,functions);
		}
	}
	
	private static String getReplacementFunction(List<String> functions,List<String> variables)
	{
		String symbol=NewFunction.rename(1, 3, functions);
		functions.add(symbol);
		symbol+="(";
		for(int i=0;i<variables.size();i++)
		{
			if(i==0)
			{
				symbol+=variables.get(i);
			}
			else
			{
				symbol+=", ";
				symbol+=variables.get(i);
			}
		}
		symbol+=")";
		return symbol;
	}
	
	public static boolean testSkolemNormalForm(FOLFormula formula)
	{
		List<String> variables=formula.syntaxTree.getVariables();
		return testSkolemNormalFormRecursive(formula.syntaxTree.getRoot(),variables);
	}
	private static boolean testSkolemNormalFormRecursive(FOLTreeNode currentNode,List<String> variables)
	{
		if(currentNode.getLabel().charAt(0)=='V')
		{
			String cuantified=currentNode.getLabel().substring(1, currentNode.getLabel().length()-1);
			variables.remove(cuantified);
			return testSkolemNormalFormRecursive(currentNode.getLeftChild(),variables);
		}
		else
		{
			if(variables.isEmpty())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
}
