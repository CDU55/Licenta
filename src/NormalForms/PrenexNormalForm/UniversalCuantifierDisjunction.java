package NormalForms.PrenexNormalForm;

import java.util.List;

import AbstractSyntaxTree.FOLTree;
import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.Substitution;
import Formulas.FOLFormula;

public class UniversalCuantifierDisjunction implements NormalFormTransformationRuleFOL {

	@Override
	public boolean canApply(FOLFormula formula) {
		if(!formula.syntaxTree.getRoot().getLabel().equals("\\/"))
		{
			return false;
		}
		String left=formula.syntaxTree.getRoot().getLeftChild().getLabel();
		String right=formula.syntaxTree.getRoot().getRightChild().getLabel();
	
		if(left.charAt(0)!='V' && right.charAt(0)!='V')
		{
			return false;
		}
		return true;
	}

	@Override
	public FOLFormula apply(FOLFormula formula) throws InvalidSubstitution {
		String cuantifier;
		String cuantified;
		FOLTreeNode left;
		FOLTreeNode right;
		List<String> freeVariables;
		String toCuantify;
		boolean leftCuantified=true;
		if(formula.syntaxTree.getRoot().getLeftChild().getLabel().charAt(0)=='V')
		{
			cuantifier=formula.syntaxTree.getRoot().getLeftChild().getLabel();
			left=new FOLTreeNode(formula.syntaxTree.getRoot().getLeftChild().getLeftChild());
			right=new FOLTreeNode(formula.syntaxTree.getRoot().getRightChild());
		}
		else
		{
			cuantifier=formula.syntaxTree.getRoot().getRightChild().getLabel();
			left=new FOLTreeNode(formula.syntaxTree.getRoot().getLeftChild());
			right=new FOLTreeNode(formula.syntaxTree.getRoot().getRightChild().getLeftChild());
			leftCuantified=false;
		}
		freeVariables=new FOLFormula(formula.syntaxTree.getRoot().getLeftChild()).syntaxTree.getFree();
		freeVariables.addAll(new FOLFormula(formula.syntaxTree.getRoot().getRightChild()).syntaxTree.getFree());
		cuantified=cuantifier.substring(1, cuantifier.length()-1);
		if(!freeVariables.contains(cuantified))
		{
			toCuantify=cuantified;
		}
		else
		{
			toCuantify=RenameVariable.rename(1,2, freeVariables);
		}
		if(!cuantified.equals(toCuantify))
		{
			if(leftCuantified)
		{
			FOLTree tree=new FOLTree(left);
			tree.executeSubstitution(new Substitution(cuantified,toCuantify));
			left=tree.getRoot();
		}
		else
		{
			FOLTree tree=new FOLTree(right);
			tree.executeSubstitution(new Substitution(cuantified,toCuantify));
			right=tree.getRoot();
		}
		}
		FOLTreeNode newCuantifiedFormula=new FOLTreeNode("\\/",new FOLTreeNode(left),new FOLTreeNode(right));
		FOLTreeNode result=new FOLTreeNode("V"+toCuantify+".",newCuantifiedFormula,null);
		FOLFormula newFormula=new FOLFormula(result);
		return newFormula;
		
	}

}
