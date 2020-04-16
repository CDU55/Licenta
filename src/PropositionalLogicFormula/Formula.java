package PropositionalLogicFormula;

import AbstractSyntaxTree.PropositionalLogicTree;
import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import FormulaRemodelers.ConnectorReplacer;
import FormulaRemodelers.ConnectorReverter;
import FormulaRemodelers.FormulaRemodeler;
import FormulaRemodelers.PostfixNotation;
import Parsers.CheckSyntax;
import PropositionalLogicAnalysis.FormulaAnalyser;

public class Formula {
	
	private String formula;
	public PropositionalLogicTree syntaxTree;
	
	public Formula(String formula) throws InvalidPropositionalLogicFormula
	{
		String validationResult=CheckSyntax.checkPropositionalLogicSyntax(formula);
		if(!validationResult.equals("OK"))
		{
			throw new InvalidPropositionalLogicFormula(formula+" is not a valid propositional logic formula\n"+validationResult);
		}
		this.formula=formula;
		this.remodelFormula(new ConnectorReplacer());
		String postfix=this.getRemodeledFormula(new PostfixNotation());
		this.syntaxTree=new PropositionalLogicTree(postfix);
	}
	public Formula(TreeNode root)
	{
		this.syntaxTree=new PropositionalLogicTree(root);
		this.formula=this.syntaxTree.toString();
		this.remodelFormula(new ConnectorReplacer());
		
	}
	public String getRemodeledFormula(FormulaRemodeler remodeler)
	{
		return remodeler.remodel(formula);
	}
	
	public void remodelFormula(FormulaRemodeler remodeler)
	{
		this.formula=remodeler.remodel(formula);
	}

	public void replaceImplications()
	{
		this.syntaxTree.reaplceImplications(this.syntaxTree.getRoot());
		this.formula=syntaxTree.toString();
	}
	public boolean analyse(FormulaAnalyser analyser)
	{
		return analyser.analyse(this);
	}
	public boolean analyseRandom(FormulaAnalyser analyser,int maxIterations)
	{
		return analyser.analyseRandom(this, maxIterations);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Formula other = (Formula) obj;
		return this.syntaxTree.getRoot().equals(other.syntaxTree.getRoot());
		
	}

	@Override
	public String toString() {
		return this.getRemodeledFormula(new ConnectorReverter());
	}

	
}
