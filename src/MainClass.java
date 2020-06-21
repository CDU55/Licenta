
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DbManagement.PrenexNormalFormChapter;
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

	public static void main(String[] args) throws IOException, InvalidProof, InvalidLiteral, InvalidSubstitution {		
		
			try {
				ResolutionFOL r =new ResolutionFOL(new FOLFormula("Vx.Va.(P(a,x) \\/ P(x,a))"));
				System.out.println(r.toString());
				r.positiveFactorization(1, "P");
				System.out.println(r.toString());
				System.out.println(Math.sin(75));
			} catch (InvalidPropositionalLogicFormula e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		
		
		
	}

}