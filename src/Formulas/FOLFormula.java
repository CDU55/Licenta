package Formulas;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTree;
import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import FirstOrderLogicSubstitutions.Substitution;
import Parsers.CheckSyntax;

public class FOLFormula {
	
	private String formula;
	public FOLTree syntaxTree;
	private List<Substitution> substitutions;
	
	public FOLFormula(String formula) throws InvalidPropositionalLogicFormula
	{
		String validationResult=CheckSyntax.checkFirstOrderLogicSyntax(formula);
		if(!validationResult.equals("OK"))
		{
			throw new InvalidPropositionalLogicFormula(formula+" is not a valid first order logic formula\n"+validationResult);
		}
		this.formula=formula;
		this.syntaxTree=new FOLTree(formula);
		this.substitutions=new ArrayList<Substitution>();
	}
	
	public FOLFormula(FOLFormula formula)
	{
		this.syntaxTree=new FOLTree(formula.formula);
		this.formula=formula.toString();
		this.substitutions=new ArrayList<Substitution>(formula.substitutions);
	}
	
	public FOLFormula(FOLTreeNode node)
	{
		this.syntaxTree=new FOLTree(node);
		this.formula=this.syntaxTree.toString();
		this.substitutions=new ArrayList<Substitution>();

	}

	public void addSubstitution(Substitution substitution)
	{
		this.substitutions.add(substitution);
	}
	public void removeRedundantParathesis()
	{
		this.formula=this.syntaxTree.toString();
	}
	
	public static String getSubstituted(FOLFormula formula)
	{
		FOLFormula substituted=new FOLFormula(formula);
		for(Substitution s:substituted.substitutions)
		{
			substituted.syntaxTree.executeSubstitution(s);
		}
		formula.syntaxTree=new FOLTree(formula.toString());
		return substituted.syntaxTree.toString();
	}
	
	@Override
	public String toString() {
		return  formula ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FOLFormula other = (FOLFormula) obj;
		return this.syntaxTree.getRoot().equals(other.syntaxTree.getRoot());
		
	}

	
}
