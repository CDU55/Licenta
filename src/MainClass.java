import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.Explanation;
import AbstractSyntaxTree.TreeNode;
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
import PropositionalLogicAnalysis.SatisfiabilityChecker;
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
			Formula f=new Formula("p /\\ (!p\\/q) /\\ (p\\/!q\\/r) /\\(!q \\/ r) /\\ !r ");
			Resolution r=new Resolution(f);
			r.applyResolution(1,2,"p");
			r.applyResolution(6, 4, "q");
			r.applyResolution(7, 5, "r");
			System.out.println(r.toString());
			//Resolution r2=ResolutionProof.findProof(f);
			//System.out.println(r2.toString());

		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLiteral e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
