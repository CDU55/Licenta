
import AbstractSyntaxTree.FOLTreeNode;
import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidSubstitution;
import FirstOrderLogicSubstitutions.Substitution;
import FormulaRemodelers.PostfixNotationFOL;
import Formulas.FOLFormula;
import NaturalDeduction.FOLSequence;
import Parsers.CheckSyntax;


public class MainClass {

	public static void main(String[] args) {		
		
			try {
				FOLSequence s=new FOLSequence("{Vx.Ey.!!P(x,y,z),Q(asfsa)}|-H(z)");
				System.out.println(s.toString());
			} catch (InvalidPropositionalLogicFormula  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

}

//(!p /\ q /\ q /\ !p) \/ (!q /\ q /\ !p) \/ (p /\ q /\ !p) \/ ( !p /\ q /\ p) \/ (!q /\ p) \/ (p /\ p) \/ ( !p /\ q /\ !q) \/ (!q /\ !q) \/ (p /\ !q )
