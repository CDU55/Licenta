
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import FOLToJSON.JSONFormula;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import FirstOrderLogicSubstitutions.Unifier;
import Formulas.FOLFormula;
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
import Util.PythonFunctionEvaluation;


public class MainClass {

	public static void main(String[] args) {		
		
			try {
				FOLFormula f=new FOLFormula("(Vx.P(x,z)) /\\ (Ey.!P(y,z))");
				List<String> t=SkolemClausalTransformator.transform(f);
				FOLFormula f2=SkolemClausalTransformator.lastTransformations;
				for(String s:t)
				{
					System.out.println(s);
				}
				System.out.println(f2.toString());
				System.out.println(SkolemTransformator.testSkolemNormalForm(f));
			} catch (InvalidPropositionalLogicFormula e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidSubstitution e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

}