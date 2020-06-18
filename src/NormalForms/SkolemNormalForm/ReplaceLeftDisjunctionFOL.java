package NormalForms.SkolemNormalForm;

import AbstractSyntaxTree.FOLTreeNode;
import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;
import Formulas.Formula;
import NormalForms.PrenexNormalForm.NormalFormTransformationRuleFOL;

public class ReplaceLeftDisjunctionFOL implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		FOLTreeNode root=formula.syntaxTree.getRoot();
		if(!root.getLabel().equals("\\/"))
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
		FOLTreeNode node1=root.getLeftChild();
		FOLTreeNode node2=root.getRightChild().getLeftChild();
		FOLTreeNode node3=root.getRightChild().getRightChild();
		FOLTreeNode disjunction1=new FOLTreeNode("\\/",node1,node2);
		FOLTreeNode disjunction2=new FOLTreeNode("\\/",node1,node3);
		FOLTreeNode conjunction=new FOLTreeNode("/\\",disjunction1,disjunction2);
		FOLFormula result=new FOLFormula(conjunction);
		return result;

	}

}
