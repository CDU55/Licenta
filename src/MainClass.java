import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.Explanation;
import FormulaRemodelers.PostfixNotation;
import Parsers.CheckSyntax;
import Parsers.ParseException;
import Parsers.PropositionalLogicParser;
import PropositionalLogicAnalysis.SatisfiabilityChecker;
import PropositionalLogicAnalysis.TautologyChecker;
import PropositionalLogicFormula.Formula;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Formula f=new Formula("!p<->(q->p) /\\ (x /\\ !x) ");
		Formula f2=new Formula("p -> !p");
		/*List<String> variables=new ArrayList<String>();
		f.syntaxTree.getAllVariables(f.syntaxTree.getRoot(), variables);
		List<String>subFormulas=new ArrayList<String>();
		f.syntaxTree.getAllSubformulas(f.syntaxTree.getRoot(), subFormulas);
		Explanation exp=f.syntaxTree.getSubfExplanation();
		for(String s:exp.messages)
		{
			System.out.println(s);
		}*/
		String formula="!(A(B))";
		System.out.println(CheckSyntax.checkPropositionalLogicSyntax(formula));
		
		
	}

}
