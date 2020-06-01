
import java.io.IOException;
import java.util.Arrays;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;
import NaturalDeduction.NaturalDeductionFOL.CreateExistentialCuantifier;
import NaturalDeduction.NaturalDeductionFOL.DeductiveSystemFOL;
import NaturalDeduction.NaturalDeductionFOL.ProofReaderFOL;
import NaturalDeduction.NaturalDeductionFOL.RemoveUniversalCuantifier;
import NaturalDeduction.NaturalDeductionFOL.SequenceFOL;


public class MainClass {

	public static void main(String[] args) {		
		
			try {
				System.out.println(ProofReaderFOL.checkProofFromFile("C:\\Users\\Claudiu\\Desktop\\FOLProof3.txt"));
				SubstitutionsResult s=SubstitutionsFinder.findSubstitutions(new FOLFormula("P(x)->Q(x)"),new FOLFormula("P(a)->Q(a)"));
				System.out.println(s);

			} catch (InvalidRuleName | IOException | InvalidPropositionalLogicFormula  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

}

//(!p /\ q /\ q /\ !p) \/ (!q /\ q /\ !p) \/ (p /\ q /\ !p) \/ ( !p /\ q /\ p) \/ (!q /\ p) \/ (p /\ p) \/ ( !p /\ q /\ !q) \/ (!q /\ !q) \/ (p /\ !q )
