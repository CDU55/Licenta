package NormalForms.SkolemNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Formulas.Formula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;

public class RemoveDoubleNegationFOL implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		FOLTreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("!"))
		{
			return false;
		}
		else if(!root.getLeftChild().getLabel().equals("!"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public FOLFormula apply(FOLFormula formula) {
		FOLTreeNode root=new FOLTreeNode(formula.syntaxTree.getRoot().getLeftChild().getLeftChild());
		FOLFormula result=new FOLFormula(root);
		return result;
	}

}
