import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import Exceptions.InvalidProof;
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
import NormalForms.NormalFormTransformationProof;
import Parsers.CheckSyntax;
import PropositionalLogicAnalysis.ContradictionChecker;
import PropositionalLogicAnalysis.SatisfiabilityChecker;
import PropositionalLogicAnalysis.TableGenerator;
import PropositionalLogicAnalysis.TautologyChecker;
import PropositionalLogicFormula.Formula;
import Resolution.Clause;
import Resolution.Resolution;
import Resolution.ResolutionClause;
import Resolution.ResolutionProof;
import Resolution.ResolutionProofCheck;

public class MainClass {

	public static void main(String[] args) {		
		try {
			Formula f=new Formula("((!p->!q)<->(q->p))");
			List<String> demo=NormalFormTransformationProof.transform(f, false);
			for(String s:demo)
			{
				System.out.println(s);
			}
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

//(!p /\ q /\ q /\ !p) \/ (!q /\ q /\ !p) \/ (p /\ q /\ !p) \/ ( !p /\ q /\ p) \/ (!q /\ p) \/ (p /\ p) \/ ( !p /\ q /\ !q) \/ (!q /\ !q) \/ (p /\ !q )
