package NormalForms.SkolemNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Formulas.Formula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;

public class ReplaceRightDisjunctionFOL implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		FOLTreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("\\/"))
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
		FOLTreeNode node1=root.getRightChild();
		FOLTreeNode node2=root.getLeftChild().getLeftChild();
		FOLTreeNode node3=root.getLeftChild().getRightChild();
		FOLTreeNode disjunction1=new FOLTreeNode("\\/",node2,node1);
		FOLTreeNode disjunction2=new FOLTreeNode("\\/",node3,node1);
		FOLTreeNode conjunction=new FOLTreeNode("/\\",disjunction1,disjunction2);
		FOLFormula result=new FOLFormula(conjunction);
		return result;

	}

}
