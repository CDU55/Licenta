
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import Exceptions.GoalReached;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import Exceptions.InvalidSubstitution;
import FOLToJSON.JSONFormula;
import FirstOrderLogicSubstitutions.SubstitutionsFinder;
import FirstOrderLogicSubstitutions.SubstitutionsResult;
import Formulas.FOLFormula;
import NaturalDeduction.NaturalDeductionFOL.CreateExistentialCuantifier;
import NaturalDeduction.NaturalDeductionFOL.DeductiveSystemFOL;
import NaturalDeduction.NaturalDeductionFOL.ProofReaderFOL;
import NaturalDeduction.NaturalDeductionFOL.RemoveUniversalCuantifier;
import NaturalDeduction.NaturalDeductionFOL.SequenceFOL;
import NormalForms.PrenexNormalForm;
import Util.PythonFunctionEvaluation;


public class MainClass {

	public static void main(String[] args) {		
		
			try {
				/*System.out.println(ProofReaderFOL.checkProofFromFile("C:\\Users\\Claudiu\\Desktop\\FOLProof3.txt"));
				SubstitutionsResult s=SubstitutionsFinder.findSubstitutions(new FOLFormula("P(x)->Q(x)"),new FOLFormula("P(a)->Q(a)"));
				System.out.println(s);
				FOLFormula f=new FOLFormula("Vx.P(x,y,z,g) /\\(Vz.H(z))");
				JSONFormula fol=new JSONFormula(f.syntaxTree.getRoot());
				FOLFormula f2=new FOLFormula(f.syntaxTree.existentialClosure()); 
				FOLFormula f3=new FOLFormula(f.syntaxTree.UniversalClosure());
				System.out.println(f.syntaxTree.toString());
				System.out.println(f2.toString());
				System.out.println(f3.toString());
				fol.Convert();
				System.out.println(PrenexNormalForm.testPrenexNormalForm(f));
				String pythonPath=new File("src/application/Resources/sat.py").getAbsolutePath();
				System.out.println(pythonPath);*/
				FOLFormula test=new FOLFormula("Ex.Ey.P(x,y)");
				System.out.println(PythonFunctionEvaluation.execPy(new JSONFormula(test.syntaxTree.getRoot()), 
						"C:\\Users\\Claudiu\\Desktop\\PyEval\\implementations.txt", "C:\\Users\\Claudiu\\Desktop\\PyEval\\assignation.txt"
						, "C:\\Users\\Claudiu\\Desktop\\PyEval\\domains.txt"));
			} catch (IOException | InvalidPropositionalLogicFormula  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

}