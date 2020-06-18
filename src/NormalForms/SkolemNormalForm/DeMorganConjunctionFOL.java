package NormalForms.SkolemNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Formulas.Formula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;

public class DeMorganConjunctionFOL implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		FOLTreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("!"))
		{
			return false;
		}
		else if(!root.getLeftChild().getLabel().equals("/\\"))
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
		FOLTreeNode node1=new FOLTreeNode(root.getLeftChild().getLeftChild());
		FOLTreeNode node2=new FOLTreeNode(root.getLeftChild().getRightChild());
		FOLTreeNode negationLeft=new FOLTreeNode("!",node1,null);
		FOLTreeNode negationRight=new FOLTreeNode("!",node2,null);
		FOLTreeNode disjunction=new FOLTreeNode("\\/",negationLeft,negationRight);
		FOLFormula result=new FOLFormula(disjunction);
		return result;
	}

}
