package FirstOrderLogicSubstitutions;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidSubstitution;
import Parsers.CheckSyntax;

public class Substitution {
	
	public FOLTreeNode Initial;
	public FOLTreeNode Final;
	
	public Substitution(String initial,String Final) throws InvalidSubstitution
	{
		
		if(!initial.matches("[a-z]([a-zA-DF-UW-Z])*"))
		{
			throw new InvalidSubstitution(initial +" is a not a variable");
		}
		if(!CheckSyntax.checkFunctionSyntaxBoolean(Final))
		{
			throw new InvalidSubstitution(Final +" is a not a function");

		}
		this.Initial=new FOLTreeNode(initial);
		this.Final=new FOLTreeNode(Final);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Final == null) ? 0 : Final.hashCode());
		result = prime * result + ((Initial == null) ? 0 : Initial.hashCode());
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
		Substitution other = (Substitution) obj;
		if (Final == null) {
			if (other.Final != null)
				return false;
		} else if (!Final.equals(other.Final))
			return false;
		if (Initial == null) {
			if (other.Initial != null)
				return false;
		} else if (!Initial.equals(other.Initial))
			return false;
		return true;
	}

	
}
