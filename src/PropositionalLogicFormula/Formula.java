package PropositionalLogicFormula;

import java.util.Map;

import AbstractSyntaxTree.PropositionalLogicTree;
import AbstractSyntaxTree.TreeNode;
import FormulaRemodelers.ConnectorReplacer;
import FormulaRemodelers.ConnectorReverter;
import FormulaRemodelers.FormulaRemodeler;
import FormulaRemodelers.PostfixNotation;
import PropositionalLogicAnalysis.FormulaAnalyser;

public class Formula {
	
	private String formula;
	public PropositionalLogicTree syntaxTree;
	
	public Formula(String formula)
	{
		this.formula=formula;
		this.remodelFormula(new ConnectorReplacer());
		String postfix=this.getRemodeledFormula(new PostfixNotation());
		this.syntaxTree=new PropositionalLogicTree(postfix);
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
	public String toString() {
		return this.getRemodeledFormula(new ConnectorReverter());
	}

	
}
