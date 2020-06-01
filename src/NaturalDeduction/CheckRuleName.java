package NaturalDeduction;

import java.util.Arrays;
import java.util.List;

public class CheckRuleName {

	private final static List<String> rules = Arrays.asList("/\\i", "/\\e1", "/\\e2", "->e", "->i", "\\/i1", "\\/i2",
			"\\/e", "!e", "!i", "|e", "IPOTEZA", "EXTINDERE", "!!e");
	private final static List<String> rulesFOL = Arrays.asList("/\\i", "/\\e1", "/\\e2", "->e", "->i", "\\/i1", "\\/i2",
			"\\/e", "!e", "!i", "|e", "IPOTEZA", "EXTINDERE", "!!e","Ei","Ee","Vi","Ve");

	public static boolean check(String rule) {
		if (rules.contains(rule.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkFOL(String rule) {
		if (rulesFOL.contains(rule.trim())) {
			return true;
		} else {
			return false;
		}
	}
}
