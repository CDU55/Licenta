package FirstOrderLogicSubstitutions;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTree;
import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidSubstitution;

public class Unifier {
	
	
	public static SubstitutionsResult findUnifier(FOLTreeNode formula1,FOLTreeNode formula2) throws InvalidSubstitution
	{
		if(formula1.getLabel().equals(formula2.getLabel()) && formula1.getArguments().size() == formula2.getArguments().size() && formula1.getArguments().size()==0)
		{
			return new SubstitutionsResult();
		}
		SubstitutionsResult result=new SubstitutionsResult();
		findUnifierRecursive(formula1,formula2,result);
		result.checkValid();
		return result;
	}
	public static Substitution findUnifierRecursive(FOLTreeNode formula1,FOLTreeNode formula2,SubstitutionsResult result) throws InvalidSubstitution
	{
		if(!result.validSubstitution)
		{
			return null;
		}
		if(formula1.isVariable() || formula2.isVariable() || (! formula1.isVariable() && !formula1.isConnector() && formula1.getArguments().size()==0)
			|| (! formula2.isVariable() && !formula2.isConnector() && formula2.getArguments().size()==0))
		{
			if(formula1.equals(formula2))
			{
				return null;
			}
			else if(formula1.isVariable())
			{
				List<String> variables=new ArrayList<String>();
				FOLTree.getAllVariables(formula2, variables);
				if(variables.contains(formula1.toString().trim()))
				{
					result.validSubstitution=false;
					return null;
				}
				else
				{
					return new Substitution(formula1.toString(),formula2.toString());
				}
			}
			else if(formula2.isVariable())
			{
				List<String> variables=new ArrayList<String>();
				FOLTree.getAllVariables(formula1, variables);
				if(variables.contains(formula2.toString().trim()))
				{
					result.validSubstitution=false;
				}
				else
				{
					return new Substitution(formula2.toString(),formula1.toString());
				}
			}
			else
			{
				result.validSubstitution=false;
				return null;
			}
		}
		if(!formula1.getLabel().equals(formula2.getLabel()))
		{
			result.validSubstitution=false;
			return null;
		}
		if(formula1.getArguments().size()!=formula2.getArguments().size())
		{
			result.validSubstitution=false;
			return null;

		}
		for(int i=0;i<formula1.getArguments().size();i++)
		{
			Substitution s=findUnifierRecursive(formula1.getArguments().get(i),formula2.getArguments().get(i),result);
			if(s!=null)
			{
				FOLTree.replaceVariable(formula1, s);
				FOLTree.replaceVariable(formula2, s);
				result.substitutions.add(s);
			}
		}
		return null;
		
	}

}
