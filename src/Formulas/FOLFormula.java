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
	
	public FOLFormula(String formula) throws InvalidPropositionalLogicFormula
	{
		String validationResult=CheckSyntax.checkFirstOrderLogicSyntax(formula);
		if(!validationResult.equals("OK"))
		{
			throw new InvalidPropositionalLogicFormula(formula+" is not a valid first order logic formula\n"+validationResult);
		}
		this.formula=formula;
		this.syntaxTree=new FOLTree(formula);
	}
	
	public FOLFormula(FOLFormula formula)
	{
		this.syntaxTree=new FOLTree(formula.formula);
		this.formula=formula.toString();
	}
	

	public FOLFormula(FOLTreeNode node)
	{
		this.syntaxTree=new FOLTree(node);
		this.formula=this.syntaxTree.toString();

	}



	public void executeSubstitution(Substitution s)
	{
		this.syntaxTree.executeSubstitution(s);
		this.formula=this.syntaxTree.toString();
	}
	public void removeRedundantParathesis()
	{
		this.formula=this.syntaxTree.toString();
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
