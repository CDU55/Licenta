package NormalForms.SkolemNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import Formulas.FOLFormula;
import Operators.TypeTesterFirstOrderLogic;

public class FNCFOL {
	public boolean containsImplications(FOLTreeNode node)
	{
		if(!node.isVariable() && !node.isConnector())
		{
			return false;
		}
		else if(node.getLabel().equals("->") || node.getLabel().equals("<->") || node.getLabel().equals("<-") || TypeTesterFirstOrderLogic.isCuantifierWithTerm(node.getLabel()))
		{
			return true;
		}
		else if(node.getLabel().equals("!"))
		{
			return containsImplications(node.getLeftChild());
		}
		else
		{
			return containsImplications(node.getLeftChild()) || containsImplications(node.getRightChild());
		}
	}
	
	
	public boolean checkFormula(FOLFormula formula)
	{
		if(this.containsImplications(formula.syntaxTree.getRoot()))
		{
			return false;
		}
		else
		{
			return OperatorHierarchy(formula.syntaxTree.getRoot());
		}
	}
	
	
	
	public boolean OperatorHierarchy(FOLTreeNode node) {
		if(!node.isConnector() && !node.isVariable() )
		{
			return true;
		}
		else if(node.getLabel().equals("!"))
		{
			if(node.getLeftChild().getLabel().equals("/\\") || node.getLeftChild().getLabel().equals("\\/"))
			{
				return false;
			}
			else if(!node.getLeftChild().isConnector())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(node.getLabel().equals("\\/"))
		{
			if(node.getLeftChild().getLabel().equals("/\\") || node.getRightChild().getLabel().equals("/\\"))
			{
				return false;
			}
			else
			{
				return OperatorHierarchy(node.getLeftChild()) && OperatorHierarchy(node.getRightChild());
			}
		}
		else
		{
			return OperatorHierarchy(node.getLeftChild()) && OperatorHierarchy(node.getRightChild());
		}
	}
	
}
