import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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
			Formula f=new Formula("p /\\ ( !p \\/ h ) /\\ (z \\/ !q \\/ r ) /\\ ( !q \\/ r ) /\\ !r /\\ r  ");
			TableGenerator.generateTable(f, "C:\\Users\\Claudiu\\Desktop\\test.html");
			System.out.println(f.analyse(new ContradictionChecker()));
			Resolution r=ResolutionProof.findProof(f);
			System.out.println(r.toString());
			r.applyResolution(1, 2, "p");
			
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GoalReached e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInferenceRuleApplication e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
