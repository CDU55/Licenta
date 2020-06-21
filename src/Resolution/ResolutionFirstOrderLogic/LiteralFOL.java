package Resolution.ResolutionFirstOrderLogic;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import FirstOrderLogicSubstitutions.Substitution;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;
import Parsers.CheckSyntax;

public class LiteralFOL {
	public String predicateSymbol;
	public String predicateWithArguments;
	public boolean negated;
	
	public LiteralFOL(FOLFormula formula) throws InvalidLiteral
	{
		if(CheckSyntax.checkFunctionSyntaxBoolean(formula.syntaxTree.getRoot().toString()))
		{
			this.predicateSymbol=formula.syntaxTree.getRoot().getLabel();
			this.predicateWithArguments=formula.syntaxTree.getRoot().toString();
			this.negated=false;
		}
		else if(formula.syntaxTree.getRoot().getLabel().equals("!"))
		{
			String negatedNode=formula.syntaxTree.getRoot().getLeftChild().toString();
			if(CheckSyntax.checkFunctionSyntaxBoolean(negatedNode))
			{
				this.predicateSymbol=formula.syntaxTree.getRoot().getLeftChild().getLabel();
				this.predicateWithArguments=negatedNode;
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

	
	public LiteralFOL(LiteralFOL literal) {
		this.predicateSymbol=literal.predicateSymbol;
		this.predicateWithArguments=literal.predicateWithArguments;
		this.negated=literal.negated;
	}

	private LiteralFOL(String predicate,boolean negated)
	{
		this.predicateSymbol=new Character(predicate.trim().charAt(0)).toString();
		this.predicateWithArguments=predicate;
		this.negated=negated;
	}
	
	public LiteralFOL(String literal) throws InvalidLiteral
	{
		String literalTrim=literal.trim();
		if(CheckSyntax.checkFunctionSyntaxBoolean(literalTrim))
		{
			FOLTreeNode node=new FOLTreeNode(literalTrim);
			this.predicateSymbol=node.getLabel();
			this.predicateWithArguments=node.toString();
			this.negated=false;
		}
		else if(CheckSyntax.checkFunctionSyntaxBoolean(literalTrim.substring(1)) && literalTrim.charAt(0)=='!')
		{
			FOLTreeNode node=new FOLTreeNode(literalTrim.substring(1));
			this.predicateSymbol=node.getLabel();
			this.predicateWithArguments=node.toString();
			this.negated=true;
		}
		else
		{
			throw new InvalidLiteral(literal+"is not a literal");
		}
		
	}
	
	public void applySubstitution(SubstitutionsResult r) throws InvalidPropositionalLogicFormula
	{
		if(r.validSubstitution)
		{
			FOLFormula f=new FOLFormula(this.predicateWithArguments);
			for(Substitution sub:r.substitutions)
		{
			f.executeSubstitution(sub);
		}
			this.predicateWithArguments=f.toString();
		}
	}

	public LiteralFOL getComplement()
	{
		return new LiteralFOL(this.predicateWithArguments,!this.negated);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (negated ? 1231 : 1237);
		result = prime * result + ((predicateSymbol == null) ? 0 : predicateSymbol.hashCode());
		result = prime * result + ((predicateWithArguments == null) ? 0 : predicateWithArguments.hashCode());
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
		LiteralFOL other = (LiteralFOL) obj;
		if (negated != other.negated)
			return false;
		if (predicateSymbol == null) {
			if (other.predicateSymbol != null)
				return false;
		} else if (!predicateSymbol.equals(other.predicateSymbol))
			return false;
		if (predicateWithArguments == null) {
			if (other.predicateWithArguments != null)
				return false;
		} else if (!predicateWithArguments.equals(other.predicateWithArguments))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		if(this.negated)
		{
			return "!"+this.predicateWithArguments;
		}
		else
		{
			return this.predicateWithArguments;
		}
	}
	
}
