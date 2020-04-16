package NaturalDeduction;

import java.util.Arrays;
import java.util.List;

public class CheckRuleName {

	private final static List<String> rules = Arrays.asList("/\\i", "/\\e1", "/\\e2", "->e", "->i", "\\/i1", "\\/i2",
			"\\/e", "!e", "!i", "|e", "IPOTEZA", "EXTINDERE", "!!e");

	public static boolean check(String rule) {
		if (rules.contains(rule.trim())) {
			return true;
		} else {
			return false;
		}
	}
}
