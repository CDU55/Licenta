package NormalForms.SkolemNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Formulas.Formula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;

public class ConjunctionAssociativityFOL implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		FOLTreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("/\\"))
		{
			return false;
		}
		else if(!root.getRightChild().getLabel().equals("/\\"))
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
		FOLTreeNode root=formula.syntaxTree.getRoot();
		FOLTreeNode node1=new FOLTreeNode(root.getLeftChild());
		FOLTreeNode node2=new FOLTreeNode(root.getRightChild().getLeftChild());
		FOLTreeNode node3=new FOLTreeNode(root.getRightChild().getRightChild());
		FOLTreeNode leftConjunction=new FOLTreeNode("/\\",node1,node2);
		FOLTreeNode conjunction=new FOLTreeNode("/\\",leftConjunction,node3);
		FOLFormula result=new FOLFormula(conjunction);
		return result;

	}


}
