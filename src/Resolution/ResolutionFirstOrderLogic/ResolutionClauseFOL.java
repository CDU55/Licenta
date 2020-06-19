package Resolution.ResolutionFirstOrderLogic;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidClause;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import FirstOrderLogicSubstitutions.Unifier;
import Formulas.FOLFormula;

public class ResolutionClauseFOL {
	public List<LiteralFOL> literals;
	
	public ResolutionClauseFOL()
	{
		this.literals=new ArrayList<LiteralFOL>();
	}
	public ResolutionClauseFOL(FOLFormula clause) throws InvalidPropositionalLogicFormula, InvalidLiteral
	{
		this.literals=new ArrayList<LiteralFOL>();
		List<FOLFormula> literals=ClauseFOL.getAllLiterals(clause);
		for(FOLFormula literal:literals)
		{
			this.literals.add(new LiteralFOL(literal));
		}
	}
	
	public ResolutionClauseFOL(ResolutionClauseFOL clause)
	{
		this.literals=new ArrayList<LiteralFOL>();
		for(LiteralFOL literal:clause.literals)
		{
			this.literals.add(new LiteralFOL(literal));
		}
	}
	
	public ResolutionClauseFOL(String clause) throws InvalidLiteral, InvalidClause
	{
		if(!clause.matches("\\s*\\{(.+)?\\}\\s*"))
		{
			throw new InvalidClause(clause+" is not a valid clause");
		}
		else
		{
			this.literals=new ArrayList<LiteralFOL>();
			if(!clause.matches("\\{\\s*\\}"))
			{
				String[] literals=clause.trim().replaceAll(" ", "").replaceAll("\\{", "").replaceAll("\\}", "").split(",");
			for(String literal:literals)
			{
				this.literals.add(new LiteralFOL(literal));
			}
			}
		}
	}

	
	public static List<LiteralPair> resolutionLiterals(ResolutionClauseFOL clause1,ResolutionClauseFOL clause2) throws InvalidSubstitution
	{
		List<LiteralPair> possibleResolutionLiterals=new ArrayList<LiteralPair>();
		for(LiteralFOL literal:clause1.literals)
		{
			for(LiteralFOL literal2:clause2.literals)
			{
				if(literal.predicateSymbol.equals(literal2.predicateSymbol) && literal.negated!=literal2.negated)
				{
					SubstitutionsResult s=Unifier.findUnifier(new FOLTreeNode(literal.predicateWithArguments), new FOLTreeNode(literal2.predicateWithArguments));
					if(s.validSubstitution)
					{
						LiteralPair pair=new LiteralPair(literal,literal2);
						if(!possibleResolutionLiterals.contains(pair))
						{
							possibleResolutionLiterals.add(pair);
						}
					}
				}
			}
		}
		return possibleResolutionLiterals;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((literals == null) ? 0 : literals.hashCode());
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
		ResolutionClauseFOL other = (ResolutionClauseFOL) obj;
		if (literals == null) {
			if (other.literals != null)
				return false;
		} else {
			ResolutionClauseFOL temp=new ResolutionClauseFOL(other);
			for(LiteralFOL literal:this.literals)
			{
				if(!temp.literals.contains(literal))
				{
					return false;
				}
				while(temp.literals.contains(literal))
				{
					temp.literals.remove(literal);
				}
			}
			if(temp.literals.size()!=0)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		if(this.literals.size()==0)
		{
			return "{ }";
		}
		else
		{	
		String stringForm="{ "+literals.get(0).toString()+" ";
		for(int index=1;index<literals.size();index++)
		{
			stringForm+=", "+literals.get(index).toString()+" ";
		}
		stringForm+="}";
		return stringForm;
		}
	}
}
