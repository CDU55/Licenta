package Formulas;

import AbstractSyntaxTree.FOLTree;
import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import FirstOrderLogicSubstitutions.Substitution;
import FormulaRemodelers.ConnectorReplacer;
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
	public void replaceImplications()
	{
		this.syntaxTree.reaplceImplications(this.syntaxTree.getRoot());
		this.formula=syntaxTree.toString();
	}
	public void replaceSubFormula(FOLFormula toReplace,FOLFormula newFormula)
	{
		this.syntaxTree.replaceSubTree(toReplace.syntaxTree.getRoot(), newFormula.syntaxTree.getRoot());
		this.formula=this.syntaxTree.toString();
	}
	
	public void removeCuantifier(String cuantifierText)
	{
		this.syntaxTree.removeCuantifierFromTree(cuantifierText);
		this.formula=this.syntaxTree.toString();
	}
	
	public boolean isSubFormula(FOLFormula formula)
	{
		return this.syntaxTree.isSubTree(formula.syntaxTree);
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
