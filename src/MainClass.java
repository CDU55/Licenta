import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.Explanation;
import FormulaRemodelers.PostfixNotation;
import PropositionalLogicAnalysis.SatisfiabilityChecker;
import PropositionalLogicAnalysis.TautologyChecker;
import PropositionalLogicFormula.Formula;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Formula f=new Formula("!p<->(q->p) /\\ (x /\\ !x) ");
		Formula f2=new Formula("(A->(B <-> !B) -> !A)");
		/*List<String> variables=new ArrayList<String>();
		f.syntaxTree.getAllVariables(f.syntaxTree.getRoot(), variables);
		List<String>subFormulas=new ArrayList<String>();
		f.syntaxTree.getAllSubformulas(f.syntaxTree.getRoot(), subFormulas);
		Explanation exp=f.syntaxTree.getSubfExplanation();
		for(String s:exp.messages)
		{
			System.out.println(s);
		}*/
		System.out.println(f2.analyse(new TautologyChecker()));
		System.out.println(f2.toString());
		System.out.println(f2.syntaxTree.toString());

		
	}

}
