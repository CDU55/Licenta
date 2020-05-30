
import Exceptions.InvalidPropositionalLogicFormula;
import NaturalDeduction.NaturalDeductionFOL.SequenceFOL;


public class MainClass {

	public static void main(String[] args) {		
		
			try {
				SequenceFOL s=new SequenceFOL("{Vx.Ey.!!P(x,y,z),Q(asfsa)}|-H(z)");
				System.out.println(s.toString());
			} catch (InvalidPropositionalLogicFormula  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

}

//(!p /\ q /\ q /\ !p) \/ (!q /\ q /\ !p) \/ (p /\ q /\ !p) \/ ( !p /\ q /\ p) \/ (!q /\ p) \/ (p /\ p) \/ ( !p /\ q /\ !q) \/ (!q /\ !q) \/ (p /\ !q )
