package Resolution.ResolutionFirstOrderLogic;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import FirstOrderLogicSubstitutions.Unifier;
import Formulas.FOLFormula;
import NormalForms.SkolemNormalForm.SkolemClausalTransformator;

public class ResolutionFOL {
	private List<ClauseAndExplanationFOL> clausesAndExplanations;
	private int initialClausesNumber;
	private boolean goalReached;

	public ResolutionFOL(FOLFormula formula) throws InvalidPropositionalLogicFormula, InvalidLiteral, InvalidSubstitution {
		SkolemClausalTransformator.transform(formula);
		this.goalReached=false;
		this.clausesAndExplanations=new ArrayList<ClauseAndExplanationFOL>();
		List<FOLFormula> clauses=ClauseFOL.getAllClauses(SkolemClausalTransformator.lastFNCTransformation);
		for(FOLFormula clause:clauses)
		{
			this.clausesAndExplanations.add(new ClauseAndExplanationFOL(new ResolutionClauseFOL(clause),"( premisa )"));
		}
		this.initialClausesNumber=this.clausesAndExplanations.size();
		
	}
	
	public ResolutionFOL(List<ResolutionClauseFOL> clauses)
	{
		this.goalReached=false;
		this.clausesAndExplanations=new ArrayList<ClauseAndExplanationFOL>();
		for(ResolutionClauseFOL clause:clauses)
		{
			this.clausesAndExplanations.add(new ClauseAndExplanationFOL(new ResolutionClauseFOL(clause),"( premisa )"));

		}
	}
	
	
	public ResolutionFOL(ResolutionFOL resolution)
	{
		this.goalReached=resolution.goalReached;
		this.clausesAndExplanations=new ArrayList<ClauseAndExplanationFOL>();
		for(int i=0;i<resolution.getClausesNumber();i++)
		{
			this.clausesAndExplanations.add(new ClauseAndExplanationFOL(resolution.clausesAndExplanations.get(i)));
		}
		this.initialClausesNumber=this.clausesAndExplanations.size();
	}
	
	public ResolutionClauseFOL apply(ResolutionClauseFOL clause1,ResolutionClauseFOL clause2,LiteralPair pair) 
	{
		
		List<LiteralFOL> clause1Part=new ArrayList<LiteralFOL>(clause1.literals);
		List<LiteralFOL> clause2Part=new ArrayList<LiteralFOL>(clause2.literals);
		while(clause1Part.contains(pair.literal1))
		{
			clause1Part.remove(pair.literal1);
		}
		while(clause2Part.contains(pair.literal2))
		{
			clause2Part.remove(pair.literal2);
		}
		ResolutionClauseFOL result=new ResolutionClauseFOL();
		result.literals.addAll(clause1Part);
		result.literals.addAll(clause2Part);
		return result;
	}

	public LiteralPair canApply(ResolutionClauseFOL clause1,ResolutionClauseFOL clause2,String predicateSymbol) throws InvalidSubstitution {
		
		if(!predicateSymbol.matches("[A-DF-UW-Z][a-zA-DF-UW-Z]*"))
		{
			return null;
		}
		for(LiteralFOL literal : clause1.literals)
		{
			if(literal.predicateSymbol.equals(predicateSymbol.trim()) && !literal.negated)
			{
				for(LiteralFOL literal2:clause2.literals)
				{
					if(literal2.predicateSymbol.equals(predicateSymbol.trim()) && literal2.negated)
					{
						SubstitutionsResult s=Unifier.findUnifier(new FOLTreeNode(literal.predicateWithArguments), new FOLTreeNode(literal2.predicateWithArguments));
						if(s.validSubstitution)
						{
							return new LiteralPair(literal,literal2);
						}
					}

				}
			}
		}
		return null;
		
	}
	
	
	public String applyResolution(Integer clause1Index,Integer clause2Index,String literalStr) throws InvalidInferenceRuleApplication, GoalReached, InvalidSubstitution
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
			ResolutionClauseFOL clause1=this.clausesAndExplanations.get(clause1Index-1).clause;
			ResolutionClauseFOL clause2=this.clausesAndExplanations.get(clause2Index-1).clause;
			LiteralPair pair=canApply(clause1,clause2,literalStr);
			if(pair==null)
			{
				throw new InvalidInferenceRuleApplication("Cannot apply resolution with the given parameters");
			}
			else
			{
				ResolutionClauseFOL newClause=apply(clause1,clause2,pair);
				if(newClause.literals.size()==0)
				{
					this.goalReached=true;
				}
				String explanation="( "+clause1Index+" , "+clause2Index+" , "+literalStr+" )";
				this.clausesAndExplanations.add(new ClauseAndExplanationFOL(newClause,explanation));
				return "OK";
			}
		}
	}
	public void positiveFactorization(Integer clauseIndex,String literalStr) throws InvalidSubstitution, InvalidPropositionalLogicFormula
	{
		if(clauseIndex<1 || clauseIndex>this.clausesAndExplanations.size())
		{
			return;
		}
		ResolutionClauseFOL clause1=this.clausesAndExplanations.get(clauseIndex-1).clause;
		LiteralFOL literal1=null;
		LiteralFOL literal2=null;
		for(LiteralFOL literal:clause1.literals)
		{
			if(literal.predicateSymbol.equals(literalStr) && !literal.negated && literal1==null)
			{
				literal1=literal;
			}
			else if(literal.predicateSymbol.equals(literalStr) && !literal.negated && literal2==null)
			{
				literal2=literal;
				break;
			}
		}
		if(literal1!=null && literal2!=null)
		{
			SubstitutionsResult sub=Unifier.findUnifier(new FOLTreeNode(literal1.predicateWithArguments), new FOLTreeNode(literal2.predicateWithArguments));
			if(sub.validSubstitution)
			{
				clause1.literals.remove(literal1);
			}
			for(LiteralFOL literal : clause1.literals)
			{
				literal.applySubstitution(sub);
			}
		}
	}
	public void add(ResolutionClauseFOL clause,String explanation)
	{
		if(clause.literals.size()==0)
		{
			this.goalReached=true;
		}
		this.clausesAndExplanations.add(new ClauseAndExplanationFOL(clause,explanation));
	}
	
	public ResolutionClauseFOL getClause(int index)
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
	
	public String getClauseAndExplanation(int index)
	{
		if(index<0 || index>=this.clausesAndExplanations.size())
		{
			return null;
		}
		else
		{
			return (index+1)+"."+this.clausesAndExplanations.get(index).clause.toString()+"\t\t"+
		this.clausesAndExplanations.get(index).explanation;
		}
	}
	public boolean containsClause(ResolutionClauseFOL clause)
	{
		for(ClauseAndExplanationFOL element:this.clausesAndExplanations)
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
		for(int i=0;i<this.clausesAndExplanations.size();i++)
		{
			message+=(i+1)+"."+this.clausesAndExplanations.get(i).clause.toString()+"\t\t"+this.clausesAndExplanations.get(i).explanation+"\n";
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
