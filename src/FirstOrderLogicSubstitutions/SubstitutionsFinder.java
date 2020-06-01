package FirstOrderLogicSubstitutions;

import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidSubstitution;
import Formulas.FOLFormula;

public class SubstitutionsFinder {

	public static boolean samePredicates(FOLFormula formula1, FOLFormula formula2) {
		return samePredicatesRecursive(formula1.syntaxTree.getRoot(), formula2.syntaxTree.getRoot());
	}

	private static boolean samePredicatesRecursive(FOLTreeNode node1, FOLTreeNode node2) {
		if (node1 == null && node2 == null) {
			return true;
		} else if (!node1.getLabel().equals(node2.getLabel()) || node1.isConnector() != node2.isConnector()
				|| node1.isVariable() != node2.isVariable()) {
			return false;
		} else if (node1.isConnector()) {
			if ((node1.getLeftChild() != null && node2.getLeftChild() == null)
					|| (node1.getLeftChild() == null && node2.getLeftChild() != null)) {
				return false;
			} else if ((node1.getRightChild() != null && node2.getRightChild() == null)
					|| (node1.getRightChild() == null && node2.getRightChild() != null)) {

				return false;
			}

			else {
				return samePredicatesRecursive(node1.getLeftChild(), node2.getLeftChild())
						&& samePredicatesRecursive(node1.getRightChild(), node2.getRightChild());
			}
		} else if (!node1.isVariable() && !node1.isVariable()) {
			if (node1.getArguments().size() != node2.getArguments().size()) {
				return false;
			} else {
				boolean argumentsSameSize=true;
				for(int i=0;i<node1.getArguments().size();i++)
				{
					FOLTreeNode argument1=node1.getArguments().get(i);
					FOLTreeNode argument2=node2.getArguments().get(i);
					if(!argument1.isVariable() && argument2.isVariable())
					{
						argumentsSameSize=false;
						break;
					}
					else if(!argument1.isVariable() && !argument2.isVariable())
					{
						if(!samePredicatesRecursive(node1.getArguments().get(i),node2.getArguments().get(i)))
					{
						argumentsSameSize=false;
					}
						
					}
				}
				return argumentsSameSize;
			}
		}
		return false;
	}

	private static void findSubstitutionRecursive(FOLTreeNode node1, FOLTreeNode node2,
			SubstitutionsResult substitutionsResult) {
		if (node1.isConnector() && node2.isConnector()) {
			if (node1.getLeftChild() != null && node2.getLeftChild() != null) {
				findSubstitutionRecursive(node1.getLeftChild(), node2.getLeftChild(), substitutionsResult);
			}
			if (node1.getRightChild() != null && node2.getRightChild() != null) {
				findSubstitutionRecursive(node1.getRightChild(), node2.getRightChild(), substitutionsResult);
			}
		}
		if (!node1.isConnector() && !node2.isConnector() && !node1.isVariable() && !node2.isVariable()) {
			if (!node1.getLabel().equals(node2.getLabel())
					|| node1.getArguments().size() != node2.getArguments().size()) {
				return;
			} else {
				for (int i = 0; i < node1.getArguments().size(); i++) {
					FOLTreeNode argument1=node1.getArguments().get(i);
					FOLTreeNode argument2=node2.getArguments().get(i);
					if(argument1.isVariable())
					{
						try {
							if(!argument2.isVariable() || !argument2.getLabel().equals(argument1.getLabel()))
							substitutionsResult.substitutions.add(new Substitution(argument1.toString(),argument2.toString()));
						} catch (InvalidSubstitution e) {
							return;
						}
					}
					else if(!argument1.isVariable() && !argument2.isVariable())
					{
						findSubstitutionRecursive(argument1, argument2, substitutionsResult);

					}
				}
			}
		}
	}

	public static SubstitutionsResult findSubstitutions(FOLFormula formula1,FOLFormula formula2)
	{
		SubstitutionsResult substitutions=new SubstitutionsResult();
		if(!samePredicates(formula1,formula2))
		{
			substitutions.validSubstitution=false;
		}
		else
		{
			findSubstitutionRecursive(formula1.syntaxTree.getRoot(),formula2.syntaxTree.getRoot(),substitutions);
			substitutions.removeDuplicates();
			substitutions.checkValid();
		}
		return substitutions;
	}
}
