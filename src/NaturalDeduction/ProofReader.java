package NaturalDeduction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;

public class ProofReader {

	private static String formula = "([a-zA-Z]|[(]|[)]|(!)|(/\\\\)|(\\\\/)|(->)|(<->)|(<-)|\\s)+";
	private static String rule = "((/\\\\i)|(/\\\\e1)|(/\\\\e2)|(->e)|(->i)|(\\\\/i1)"
			+ "|(\\\\/i2)|(\\\\/e)|(!e)|(!i)|(\\|e)|(IPOTEZA)|(EXTINDERE)|(!!e))";
	private static String sequenceForm = "\\s*\\{\\s*(" + formula + ")?(\\s*,\\s*" + formula + "\\s*)*\\s*\\}\\s*\\|-\\s*("
			+ formula + "|(\\|))";
	private static String explanationForm = "\\(\\s*" + rule + "\\s*(\\s*,\\s*[1-9]+\\s*)*\\s*\\)\\s*";
	private static String proofLineForm = sequenceForm + "[\\s\\t]*" + explanationForm;

	public static boolean isSequenceString(String sequence)
	{
		return sequence.matches(sequenceForm);
	}
	public static String parseLine(String line, ProofChecker p) throws InvalidRuleName {
		{
			if (!line.trim().matches(proofLineForm)) {
				return "Syntax Error";
			} else {
				Matcher explanationMatcher = Pattern.compile(explanationForm).matcher(line);
				if (explanationMatcher.find()) {
					String explanation = explanationMatcher.group().trim();
					Matcher sequenceMatcher = Pattern.compile(sequenceForm).matcher(line.replace(explanation, ""));
					if (sequenceMatcher.find()) {
						String sequence = sequenceMatcher.group().trim();
						try {
							p.Add(sequence, explanation);
						} catch (InvalidPropositionalLogicFormula e) {
							// TODO Auto-generated catch block
							return e.getMessage();
						}
						return "OK";
					} else {
						return "Error finding the sequence";

					}

				} else {
					return "Error finding the explanation";
				}

			}
		}

	}

	public static String checkProofString(String proof) throws InvalidRuleName {
		ProofChecker checker = new ProofChecker();
		String[] lines = proof.split("\n");
		for (String line : lines) {
			if (!parseLine(line, checker).equals("OK")) {
				return "Error parsing the proof";
			}
		}
		String checkResult = checker.checkProof();
		return checkResult;
	}

	public static String checkProofFromFile(String filePath) throws InvalidRuleName {
		ProofChecker checker = new ProofChecker();
		BufferedReader reader = null;
		String checkResult;
		try {
			 reader = new BufferedReader(new FileReader(filePath));
			String proofLine = reader.readLine();
			int lineIndex = 1;
			while (proofLine != null) {
				if (!parseLine(proofLine, checker).equals("OK")) {
					return "Error parsing the proof\nLine : " + lineIndex;
				}
				proofLine = reader.readLine();
				lineIndex++;
			}
			 checkResult = checker.checkProof();
			reader.close();
		} catch (FileNotFoundException e) {
			if(reader!=null)
			{
				try {
					reader.close();
				} catch (IOException e1) {
					return "Could not close the file";
				}
			}
			return "File cannot be found";
		} catch (IOException e) {
			if(reader!=null)
			{
				try {
					reader.close();
				} catch (IOException e1) {
					return "Could not close the file";
				}
			}
			return "Could not read from file";
		}
		return checkResult;

	}
}
