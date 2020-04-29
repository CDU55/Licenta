package Resolution;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.TreeNode;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import PropositionalLogicFormula.Formula;

public class Resolution {

	private List<ClauseAndExplanation> clausesAndExplanations;
	private int initialClausesNumber;
	private boolean goalReached;

	public Resolution(Formula formula) throws InvalidPropositionalLogicFormula, InvalidLiteral {
		this.goalReached=false;
		this.clausesAndExplanations=new ArrayList<ClauseAndExplanation>();
		List<Formula> clauses=Clause.getAllClauses(formula);
		for(Formula clause:clauses)
		{
			this.clausesAndExplanations.add(new ClauseAndExplanation(new ResolutionClause(clause),"( premisa )"));
		}
		this.initialClausesNumber=this.clausesAndExplanations.size();
		
	}
	
	public Resolution(List<ResolutionClause> clauses)
	{
		this.goalReached=false;
		this.clausesAndExplanations=new ArrayList<ClauseAndExplanation>();
		for(ResolutionClause clause:clauses)
		{
			this.clausesAndExplanations.add(new ClauseAndExplanation(new ResolutionClause(clause),"( premisa )"));

		}
	}
	
	
	public Resolution(Resolution resolution)
	{
		this.goalReached=resolution.goalReached;
		this.clausesAndExplanations=new ArrayList<ClauseAndExplanation>();
		for(int i=0;i<resolution.getClausesNumber();i++)
		{
			this.clausesAndExplanations.add(new ClauseAndExplanation(resolution.clausesAndExplanations.get(i)));
		}
		this.initialClausesNumber=this.clausesAndExplanations.size();
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
	
	
	public String applyResolution(Integer clause1Index,Integer clause2Index,String literalStr) throws InvalidInferenceRuleApplication, GoalReached
	{
		if(this.goalReached)
		{
			throw new GoalReached("Null clause already achieved");

		}
		if(clause1Index<1 || clause1Index>this.clausesAndExplanations.size())
		{
			return clause1Index+" is not a valid index";
		}
		else if(clause2Index<1 || clause2Index>this.clausesAndExplanations.size())
		{
			return clause2Index+" is not a valid index";
		}
		else
		{
			ResolutionClause clause1=this.clausesAndExplanations.get(clause1Index-1).clause;
			ResolutionClause clause2=this.clausesAndExplanations.get(clause2Index-1).clause;
			Literal literal=null;
			try {
				 literal=new Literal(literalStr);
			} catch (InvalidLiteral e) {
				return e.getMessage();
			}
			if(!canApply(clause1,clause2,literal))
			{
				throw new InvalidInferenceRuleApplication("Cannot apply resolution with the given parameters");
			}
			else
			{
				ResolutionClause newClause=apply(clause1,clause2,literal);
				if(newClause.literals.size()==0)
				{
					this.goalReached=true;
				}
				String explanation="( "+clause1Index+" , "+clause2Index+" , "+literalStr+" )";
				this.clausesAndExplanations.add(new ClauseAndExplanation(newClause,explanation));
				return "OK";
			}
		}
	}

	public void add(ResolutionClause clause,String explanation)
	{
		if(clause.literals.size()==0)
		{
			this.goalReached=true;
		}
		this.clausesAndExplanations.add(new ClauseAndExplanation(clause,explanation));
	}
	
	public ResolutionClause getClause(int index)
	{
		if(index<0 || index>=this.clausesAndExplanations.size())
		{
			return null;
		}
		else
		{
			return this.clausesAndExplanations.get(index).clause;
		}
	}
	
	public boolean containsClause(ResolutionClause clause)
	{
		for(ClauseAndExplanation element:this.clausesAndExplanations)
		{
			if(element.clause.equals(clause))
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		String message=new String();
		for(ClauseAndExplanation line:this.clausesAndExplanations)
		{
			message+=line.clause.toString()+"\t\t"+line.explanation+"\n";
		}
		return message;
	}
	
	public int getClausesNumber()
	{
		return this.clausesAndExplanations.size();
	}
	
	public void remove()
	{
		if(this.clausesAndExplanations.size()>this.initialClausesNumber)
		{
			this.clausesAndExplanations.remove(this.clausesAndExplanations.size()-1);
		}
		this.goalReached=false;
	}
	
	public boolean getNullClauseAchieved()
	{
		return this.goalReached;
	}
	
}
