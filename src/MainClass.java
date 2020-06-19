
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidProof;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import FOLToJSON.JSONFormula;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import FirstOrderLogicSubstitutions.Unifier;
import Formulas.FOLFormula;
import Formulas.Formula;
import NaturalDeduction.NaturalDeductionFOL.CreateExistentialCuantifier;
import NaturalDeduction.NaturalDeductionFOL.DeductiveSystemFOL;
import NaturalDeduction.NaturalDeductionFOL.ProofReaderFOL;
import NaturalDeduction.NaturalDeductionFOL.RemoveUniversalCuantifier;
import NaturalDeduction.NaturalDeductionFOL.SequenceFOL;
import NormalForms.PrenexNormalForm.PrenexNormalForm;
import NormalForms.PrenexNormalForm.PrenexTransformationProof;
import NormalForms.PrenexNormalForm.PrenexTransformator;
import NormalForms.SkolemNormalForm.SkolemClausalTransformator;
import NormalForms.SkolemNormalForm.SkolemTransformator;
import Parsers.CheckSyntax;
import Resolution.ResolutionProofCheck;
import Resolution.ResolutionFirstOrderLogic.ResolutionFOL;
import Resolution.ResolutionFirstOrderLogic.ResolutionProofCheckFOL;
import Resolution.ResolutionFirstOrderLogic.ResolutionProofFOL;
import Util.PythonFunctionEvaluation;


public class MainClass {

	public static void main(String[] args) throws IOException, InvalidProof {		
		
			try {
				Formula f=new Formula("p /\\ ( q /\\ r)");
				String literalForm="((!)?([A-DF-UW-Z][a-zA-DF-UW-Z]*\\(([a-zA-Z,\\s\\(\\)]*?)\\)))";
				String clauseForm="\\s*\\{\\s*"+literalForm+"?"+"(\\s*,\\s*"+literalForm+"\\s*)*\\s*\\}";
				Matcher clauseMatcher = Pattern.compile(clauseForm).matcher("{ P(b) }");
				if(clauseMatcher.find())
				{
					System.out.println(clauseMatcher.group());
				}
				else
				{
					System.out.println("Nope");
				}
				
				System.out.println(f.syntaxTree.toString());
				System.out.println(ResolutionProofCheckFOL.checkProof("C:\\Users\\Claudiu\\Desktop\\Test.txt", true));
			} catch (InvalidPropositionalLogicFormula e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		
		
		
	}

}