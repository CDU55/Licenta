package NaturalDeduction.NaturalDeductionFOL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;

public class ProofReaderFOL {
	private static String formula = "(.+)";
	private static String rule = "((/\\\\i)|(/\\\\e1)|(/\\\\e2)|(->e)|(->i)|(\\\\/i1)"
			+ "|(\\\\/i2)|(\\\\/e)|(!e)|(!i)|(\\|e)|(IPOTEZA)|(EXTINDERE)|(!!e)|(Ei)|(Vi)|(Ee)|(Ve))";
	private static String sequenceForm = "\\s*\\{\\s*(" + formula + ")?(\\s*,\\s*" + formula + "\\s*)*\\s*\\}\\s*\\|-\\s*("
			+ formula + "|(\\|))";
	private static String explanationForm = "\\(\\s*" + rule + "\\s*(\\s*,\\s*([1-9][0-9]*)|([a-z][a-zA-DF-UW-Z]*)\\s*)*\\s*\\)\\s*";
	private static String proofLineForm = sequenceForm + "[\\s\\t]*" + explanationForm;

	public static boolean isSequenceString(String sequence)
	{
		return sequence.matches(sequenceForm);
	}
	public static String parseLine(String line, ProofCheckerFOL p) throws InvalidRuleName {
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
		ProofCheckerFOL checker = new ProofCheckerFOL();
		String[] lines = proof.split("\n");
		for (int  lineIndex=0;lineIndex<lines.length;lineIndex++) {
			String result=parseLine(lines[lineIndex], checker);
			if (!result.equals("OK")) {
				return "Error parsing the proof\n"+result+"\nLine : "+lineIndex;
			}
		}
		String checkResult = checker.checkProof();
		return checkResult;
	}

	public static String checkProofFromFile(String filePath) throws InvalidRuleName, IOException {
		ProofCheckerFOL checker = new ProofCheckerFOL();
		BufferedReader reader = null;
		String checkResult;
			 reader = new BufferedReader(new FileReader(filePath));
			String proofLine = reader.readLine();
			int lineIndex = 1;
			while (proofLine != null) {
				String result=parseLine(proofLine, checker);
				if (!result.equals("OK")) 
				{
					return "Error parsing the proof\nLine : " + lineIndex;
				}
				proofLine = reader.readLine();
				lineIndex++;
			}
			 checkResult = checker.checkProof();
			reader.close();
		return checkResult;
	}
}
