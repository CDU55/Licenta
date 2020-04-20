import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AbstractSyntaxTree.Explanation;
import AbstractSyntaxTree.TreeNode;
import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import FormulaRemodelers.PostfixNotation;
import NaturalDeduction.CreateImplication;
import NaturalDeduction.DeductiveSystem;
import NaturalDeduction.Extension;
import NaturalDeduction.ExtractFromConjunction1;
import NaturalDeduction.ExtractFromConjunction2;
import NaturalDeduction.Hypothesis;
import NaturalDeduction.ProofChecker;
import NaturalDeduction.ProofReader;
import NaturalDeduction.RemoveDoubleNegation;
import NaturalDeduction.RuleExplanation;
import NaturalDeduction.Sequence;
import NormalForms.Complement;
import NormalForms.FNC;
import NormalForms.NormalForm;
import Parsers.CheckSyntax;
import Parsers.ParseException;
import Parsers.PropositionalLogicParser;
import PropositionalLogicAnalysis.ContradictionChecker;
import PropositionalLogicAnalysis.SatisfiabilityChecker;
import PropositionalLogicAnalysis.TableGenerator;
import PropositionalLogicAnalysis.TautologyChecker;
import PropositionalLogicFormula.Formula;
import Resolution.Clause;
import Resolution.Resolution;
import Resolution.ResolutionClause;
import Resolution.ResolutionProof;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Formula f=new Formula("p->q");
			Formula f2=new Formula("q->r");
			Formula f3=new Formula("p");
			List<Formula>formulas=Arrays.asList(f,f2,f3);
			DeductiveSystem d=new DeductiveSystem(formulas,new Sequence("{(p->q),(q->r)}|-(p->r)"));
			System.out.println(d.toString());
			d.apply("->e",1,3);
			d.apply("->e",2,4);
			d.apply("->i",5,new Formula("p"));
			System.out.println();
			System.out.println(d.toString());
			
			
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
