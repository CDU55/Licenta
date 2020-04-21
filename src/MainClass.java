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
			Formula f2=new Formula("p/\\q");
			Formula f=new Formula("p");
			Formula f3=new Formula("r");
			f2.replaceSubformula(f, f3);
			System.out.println(f2.toString());
			
			
			
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
