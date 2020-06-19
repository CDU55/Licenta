package Resolution.ResolutionFirstOrderLogic;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import Formulas.FOLFormula;
import Formulas.Formula;
import NormalForms.SkolemNormalForm.FNCFOL;
import Parsers.CheckSyntax;

public class ClauseFOL {
	public static List<FOLFormula> getAllClauses(FOLFormula formula) throws InvalidPropositionalLogicFormula {
		FNCFOL fnc = new FNCFOL();
		if (!fnc.checkFormula(formula)) {
			throw new InvalidPropositionalLogicFormula(formula + " is not in FNC");
		} else {
			List<FOLFormula> clauses = new ArrayList<FOLFormula>();
			getAllClausesFromNode(formula.syntaxTree.getRoot(), clauses);
			return clauses;
		}
	}

	private static void getAllClausesFromNode(FOLTreeNode node, List<FOLFormula> clauses) {
		if (node.getLabel().equals("/\\")) {
			if (node.getLeftChild().getLabel().equals("/\\")) {
				getAllClausesFromNode(node.getLeftChild(), clauses);
			} else {
				FOLFormula clause = new FOLFormula(node.getLeftChild());
				if (!clauses.contains(clause)) {
					clauses.add(new FOLFormula(node.getLeftChild()));
				}
			}
			if (node.getRightChild().getLabel().equals("/\\")) {
				getAllClausesFromNode(node.getRightChild(), clauses);
			} else {
				FOLFormula clause = new FOLFormula(node.getRightChild());
				if (!clauses.contains(clause)) {
					clauses.add(new FOLFormula(node.getRightChild()));
				}
			}
		}
		else if(CheckSyntax.checkFunctionSyntaxBoolean(node.toString()) || node.getLabel().equals("\\/"))
		{
			FOLFormula clause = new FOLFormula(node);
			if (!clauses.contains(clause)) {
				clauses.add(new FOLFormula(node));
			}
		}
	}
	
	public static List<FOLFormula> getAllLiterals(FOLFormula formula) throws InvalidPropositionalLogicFormula
	{
		if(!isClause(formula))
		{
			throw new InvalidPropositionalLogicFormula(formula + " is not a clause");
		}
		else
		{
			List<FOLFormula> literals=new ArrayList<FOLFormula>();
			getAllLiteralsFromFormula(formula.syntaxTree.getRoot(),literals);
			return literals;
		}
	}

	private static void getAllLiteralsFromFormula(FOLTreeNode node,List<FOLFormula> literals)
	{
		if(CheckSyntax.checkFunctionSyntaxBoolean(node.toString())) {
			literals.add(new FOLFormula(node));
		}
		else if(node.getLabel().equals("!"))
		{
			if(CheckSyntax.checkFunctionSyntaxBoolean(node.getLeftChild().toString()))
			{
				literals.add(new FOLFormula(node));
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
	public static boolean isClause(FOLFormula formula) {
		return isClauseNode(formula.syntaxTree.getRoot());
	}

	private static boolean isClauseNode(FOLTreeNode node) {
		if (!node.getLabel().equals("\\/") && !node.getLabel().equals("!") && !CheckSyntax.checkFunctionSyntaxBoolean(node.toString())) {
			return false;
		} else if (node.getLabel().equals("!")) {
			if (CheckSyntax.checkFunctionSyntaxBoolean(node.getLeftChild().toString())) {
				return true;
			} else {
				return false;
			}
		} else if (CheckSyntax.checkFunctionSyntaxBoolean(node.toString())) {
			return true;
		} else {
			return isClauseNode(node.getLeftChild()) && isClauseNode(node.getRightChild());
		}
	}

	public static boolean isLiteral(Formula f) {
		if (CheckSyntax.checkFunctionSyntaxBoolean(f.syntaxTree.getRoot().toString()) || (f.syntaxTree.getRoot().getLabel().equals("!")
				&& CheckSyntax.checkFunctionSyntaxBoolean(f.syntaxTree.getRoot().getLeftChild().toString()))) {
			return true;
		} else {
			return false;
		}
	}
}
