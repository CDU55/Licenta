package Resolution;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import PropositionalLogicFormula.Formula;

public class Resolution {

	private List<ResolutionClause> clauses;
	private List<String> explanations;

	public Resolution(Formula formula) throws InvalidPropositionalLogicFormula, InvalidLiteral {
		this.clauses=new ArrayList<ResolutionClause>();
		this.explanations=new ArrayList<String>();
		List<Formula> clauses=Clause.getAllClauses(formula);
		for(Formula clause:clauses)
		{
			this.clauses.add(new ResolutionClause(clause));
			this.explanations.add("( premisa )");
		}
		
	}
	
	public Resolution(Resolution resolution)
	{
		this.clauses=new ArrayList<ResolutionClause>();
		this.explanations=new ArrayList<String>();
		for(int i=0;i<resolution.getClausesNumber();i++)
		{
			this.clauses.add(new ResolutionClause(resolution.clauses.get(i)));
			this.explanations.add(new String(resolution.explanations.get(i)));
		}
	}
	
	public ResolutionClause apply(ResolutionClause clause1,ResolutionClause clause2,Literal literal)
	{
		Literal negated=literal.getComplement();
		List<Literal> clause1Part=new ArrayList<Literal>(clause1.literals);
		List<Literal> clause2Part=new ArrayList<Literal>(clause2.literals);
		while(clause1Part.contains(literal))
		{
			clause1Part.remove(literal);
		}
		while(clause2Part.contains(negated))
		{
			clause2Part.remove(negated);
		}
		ResolutionClause result=new ResolutionClause();
		result.literals.addAll(clause1Part);
		result.literals.addAll(clause2Part);
		return result;
	}

	public boolean canApply(ResolutionClause clause1,ResolutionClause clause2,Literal literal) {
		
		if(literal.negated)
		{
			return false;
		}
		else if(!clause1.literals.contains(literal) || !clause2.literals.contains(literal.getComplement()))
		{
			return false;
		}
		return true;
		
	}
	
	
	public String applyResolution(Integer clause1Index,Integer clause2Index,String literalStr)
	{
		if(clause1Index<1 || clause1Index>this.clauses.size())
		{
			return clause1Index+" is not a valid index";
		}
		else if(clause2Index<1 || clause2Index>this.clauses.size())
		{
			return clause2Index+" is not a valid index";
		}
		else
		{
			ResolutionClause clause1=this.clauses.get(clause1Index-1);
			ResolutionClause clause2=this.clauses.get(clause2Index-1);
			Literal literal=null;
			try {
				 literal=new Literal(literalStr);
			} catch (InvalidLiteral e) {
				return e.getMessage();
			}
			if(!canApply(clause1,clause2,literal))
			{
				return "Cannot apply resolution with the given parameters";
			}
			else
			{
				ResolutionClause newClause=apply(clause1,clause2,literal);
				this.clauses.add(newClause);
				String explanation="( "+clause1Index+" , "+clause2Index+" , "+literalStr+" )";
				this.explanations.add(explanation);
				return "OK";
			}
		}
	}

	public void add(ResolutionClause clause,String explanation)
	{
		this.clauses.add(clause);
		this.explanations.add(explanation);
	}
	
	public ResolutionClause getClause(int index)
	{
		if(index<0 || index>=this.clauses.size())
		{
			return null;
		}
		else
		{
			return this.clauses.get(index);
		}
	}
	
	public boolean containsClause(ResolutionClause clause)
	{
		return this.clauses.contains(clause);
	}
	@Override
	public String toString() {
		String message=new String();
		for(int index=0;index<this.clauses.size();index++)
		{
			message+=this.clauses.get(index).toString()+"\t\t"+this.explanations.get(index)+"\n";
		}
		return message;
	}
	
	public int getClausesNumber()
	{
		return this.clauses.size();
	}
	
}
