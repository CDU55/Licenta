package Resolution;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import PropositionalLogicFormula.Formula;

public class ResolutionClause {
	
	public List<Literal> literals;
	
	public ResolutionClause()
	{
		this.literals=new ArrayList<Literal>();
	}
	public ResolutionClause(Formula clause) throws InvalidPropositionalLogicFormula, InvalidLiteral
	{
		this.literals=new ArrayList<Literal>();
		List<Formula> literals=Clause.getAllLiterals(clause);
		for(Formula literal:literals)
		{
			this.literals.add(new Literal(literal));
		}
	}
	
	public ResolutionClause(ResolutionClause clause)
	{
		this.literals=new ArrayList<Literal>();
		for(Literal literal:clause.literals)
		{
			this.literals.add(new Literal(literal));
		}
	}

	
	public static List<Literal> resolutionLiterals(ResolutionClause clause1,ResolutionClause clause2)
	{
		List<Literal> possibleResolutionLiterals=new ArrayList<Literal>();
		for(Literal literal:clause1.literals)
		{
			if(clause2.literals.contains(literal.getComplement()))
			{
				if(literal.negated==false)
				{
					possibleResolutionLiterals.add(new Literal(literal));
				}
				else
				{
					possibleResolutionLiterals.add(literal.getComplement());
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
		ResolutionClause other = (ResolutionClause) obj;
		if (literals == null) {
			if (other.literals != null)
				return false;
		} else {
			ResolutionClause temp=new ResolutionClause(other);
			for(Literal literal:this.literals)
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
