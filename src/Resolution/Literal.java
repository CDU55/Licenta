package Resolution;

import Exceptions.InvalidLiteral;
import PropositionalLogicFormula.Formula;

public class Literal {
	
	public String variable;
	public boolean negated;
	
	public Literal(Formula formula) throws InvalidLiteral
	{
		if(formula.syntaxTree.getRoot().getLabel().matches("[a-z-AZ]"))
		{
			this.variable=formula.syntaxTree.getRoot().getLabel();
			this.negated=false;
		}
		else if(formula.syntaxTree.getRoot().getLabel().equals("!"))
		{
			String negatedNode=formula.syntaxTree.getRoot().getLeftChild().getLabel();
			if(negatedNode.matches("[a-zA-Z]"))
			{
				this.variable=negatedNode;
				this.negated=true;
			}
			else
			{
				throw new InvalidLiteral(formula.toString()+"is not a literal");
			}
		}
		else
		{
			throw new InvalidLiteral(formula.toString()+"is not a literal");
		}
	}

	
	public Literal(Literal literal) {
		this.variable = literal.variable;
		this.negated = literal.negated;
	}

	private Literal(String variable,boolean negated)
	{
		this.variable=variable;
		this.negated=negated;
	}
	
	public Literal(String literal) throws InvalidLiteral
	{
		String literalTrim=literal.trim();
		if(literalTrim.matches("[a-zA-Z]"))
		{
			this.variable=Character.toString(literalTrim.charAt(0));
			this.negated=false;
		}
		else if(literalTrim.matches("![a-zA-Z]"))
		{
			this.variable=Character.toString(literalTrim.charAt(1));
			this.negated=true;
		}
		else
		{
			throw new InvalidLiteral(literal+"is not a literal");
		}
		
	}

	public Literal getComplement()
	{
		return new Literal(this.variable,!this.negated);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (negated ? 1231 : 1237);
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
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
		Literal other = (Literal) obj;
		if (negated != other.negated)
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}


	@Override
	public String toString() {
		if(this.negated)
		{
			return "!"+this.variable;
		}
		else
		{
			return this.variable;
		}
	}
	
	

}
