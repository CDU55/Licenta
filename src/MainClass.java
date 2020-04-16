import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import AbstractSyntaxTree.Explanation;
import AbstractSyntaxTree.TreeNode;
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

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Formula f=null;
		NormalForm fnc=new FNC();
		
		 try {
			f=new Formula("p /\\ !q \\/ !r /\\ p /\\ !r \\/ p /\\ r");
		} catch (InvalidPropositionalLogicFormula e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(f!=null)
		 {
			 System.out.println(Complement.getFromulaComplement(f).syntaxTree.toString());
		 }
		
		
		
		
		
		
	}

}
