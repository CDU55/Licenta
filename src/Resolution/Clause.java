package Resolution;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.TreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import NormalForms.FNC;
import NormalForms.NormalForm;
import PropositionalLogicFormula.Formula;

public class Clause {

	public static List<Formula> getAllClauses(Formula formula) throws InvalidPropositionalLogicFormula {
		NormalForm fnc = new FNC();
		if (!fnc.checkFormula(formula)) {
			throw new InvalidPropositionalLogicFormula(formula + " is not in FNC");
		} else {
			List<Formula> clauses = new ArrayList<Formula>();
			getAllClausesFromNode(formula.syntaxTree.getRoot(), clauses);
			return clauses;
		}
	}

	private static void getAllClausesFromNode(TreeNode node, List<Formula> clauses) {
		if (node.getLabel().equals("/\\")) {
			if (node.getLeftChild().getLabel().equals("/\\")) {
				getAllClausesFromNode(node.getLeftChild(), clauses);
			} else {
				Formula clause = new Formula(node.getLeftChild());
				if (!clauses.contains(clause)) {
					clauses.add(new Formula(node.getLeftChild()));
				}
			}
			if (node.getRightChild().getLabel().equals("/\\")) {
				getAllClausesFromNode(node.getRightChild(), clauses);
			} else {
				Formula clause = new Formula(node.getRightChild());
				if (!clauses.contains(clause)) {
					clauses.add(new Formula(node.getRightChild()));
				}
			}
		}
		else if(node.getLabel().matches("[a-zA-Z]") || node.getLabel().equals("\\/"))
		{
			Formula clause = new Formula(node);
			if (!clauses.contains(clause)) {
				clauses.add(new Formula(node));
			}
		}
	}
	
	public static List<Formula> getAllLiterals(Formula formula) throws InvalidPropositionalLogicFormula
	{
		if(!isClause(formula))
		{
			throw new InvalidPropositionalLogicFormula(formula + " is not a clause");
		}
		else
		{
			List<Formula> literals=new ArrayList<Formula>();
			getAllLiteralsFromFormula(formula.syntaxTree.getRoot(),literals);
			return literals;
		}
	}

	private static void getAllLiteralsFromFormula(TreeNode node,List<Formula> literals)
	{
		if(node.getLabel().matches("[a-zA-Z]")) {
			literals.add(new Formula(node));
		}
		else if(node.getLabel().equals("!"))
		{
			if(node.getLeftChild().getLabel().matches("[a-zA-Z]"))
			{
				literals.add(new Formula(node));
			}
			else
			{
				getAllLiteralsFromFormula(node.getLeftChild(),literals);
			}
		}
		else
		{
			getAllLiteralsFromFormula(node.getLeftChild(),literals);
			getAllLiteralsFromFormula(node.getRightChild(),literals);
		}
	}
	public static boolean isClause(Formula formula) {
		return isClauseNode(formula.syntaxTree.getRoot());
	}

	private static boolean isClauseNode(TreeNode node) {
		if (!node.getLabel().equals("\\/") && !node.getLabel().equals("!") && !node.getLabel().matches("[a-zA-Z]")) {
			return false;
		} else if (node.getLabel().equals("!")) {
			if (node.getLeftChild().getLabel().matches("[a-zA-Z]")) {
				return true;
			} else {
				return false;
			}
		} else if (node.getLabel().matches("[a-zA-Z]")) {
			return true;
		} else {
			return isClauseNode(node.getLeftChild()) && isClauseNode(node.getRightChild());
		}
	}

	public static boolean isLiteral(Formula f) {
		if (f.syntaxTree.getRoot().getLabel().matches("[a-zA-Z]") || (f.syntaxTree.getRoot().getLabel().equals("!")
				&& f.syntaxTree.getRoot().getLeftChild().getLabel().matches("[a-zA-Z]"))) {
			return true;
		} else {
			return false;
		}
	}

}
