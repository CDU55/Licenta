package NaturalDeduction.NaturalDeductionFOL;

import java.util.ArrayList;
import java.util.List;

import Exceptions.InvalidPropositionalLogicFormula;
import Exceptions.InvalidRuleName;
import NaturalDeduction.CreateBottom;
import NaturalDeduction.CreateConjunction;
import NaturalDeduction.CreateDisjunction1;
import NaturalDeduction.CreateDisjunction2;
import NaturalDeduction.CreateImplication;
import NaturalDeduction.CreateNegationFromBottom;
import NaturalDeduction.CreateProvenFromBottom;
import NaturalDeduction.Extension;
import NaturalDeduction.ExtractFromConjunction1;
import NaturalDeduction.ExtractFromConjunction2;
import NaturalDeduction.ExtractFromImplication;
import NaturalDeduction.Hypothesis;
import NaturalDeduction.InferenceRule;
import NaturalDeduction.RemoveDisjunction;
import NaturalDeduction.RemoveDoubleNegation;
import NaturalDeduction.RuleExplanation;
import NaturalDeduction.Sequence;

public class ProofCheckerFOL {
	private List<InferenceRuleFOL> rules;
	private List<SequenceFOL> sequences;
	private List<RuleExplanation> explanations;

	public ProofCheckerFOL() {
		this.rules=new ArrayList<InferenceRuleFOL>();
		rules.add(new CreateConjunctionFOL());
		rules.add(new ExtractFromConjunction1FOL());
		rules.add(new ExtractFromConjunction2FOL());
		rules.add(new ExtractFromImplicationFOL());
		rules.add(new CreateImplicationFOL());
		rules.add(new CreateDisjunction1FOL());
		rules.add(new CreateDisjunction2FOL());
		rules.add(new RemoveDisjunctionFOL());
		rules.add(new CreateBottomFOL());
		rules.add(new CreateNegationFromBottomFOL());
		rules.add(new CreateProvenFromBottomFOL());
		rules.add(new HypothesisFOL());
		rules.add(new ExtensionFOL());
		rules.add(new RemoveDoubleNegationFOL());
		rules.add(new RemoveUniversalCuantifier());
		rules.add(new CreateUniversalCuantifier());
		rules.add(new RemoveExistentialCuantifier());
		rules.add(new CreateExistentialCuantifier());
		this.sequences = new ArrayList<SequenceFOL>();
		this.explanations = new ArrayList<RuleExplanation>();
	}

	public void Add(String sequence, String explanation) throws InvalidPropositionalLogicFormula, InvalidRuleName {
		this.sequences.add(new SequenceFOL(sequence));
		this.explanations.add(new RuleExplanation(explanation));
	}

	public String checkProof() {
		for (int i = 0; i < this.sequences.size(); i++) {
			String ruleName = this.explanations.get(i).ruleName;
			List<Object> args = new ArrayList<Object>();
			args.add(this.sequences.get(i));
			for (int j = 0; j < this.explanations.get(i).args.length; j++) {
				Object currentArgument = this.explanations.get(i).args[j];
				if (currentArgument instanceof Integer) {
					args.add(this.sequences.get((Integer) currentArgument - 1));
				}
			}
			for (InferenceRuleFOL rule : this.rules) {
				if (rule.toString().equals(ruleName)) {
					/*if (rule.toString().equals("IPOTEZA") && i != 0) {
						Boolean sameHypothesis = false;
						for (int j = 0; j < i; j++) {
							if (Sequence.hypothesisEqual(this.sequences.get(i), this.sequences.get(j))) {
								sameHypothesis = true;
							}
						}
						if (!sameHypothesis) {
							return "Error on the hypothesis rule on line " + String.valueOf(i + 1);
						}
					}*/
					String evaluationResult=rule.appliedCorrectly(args.toArray());
					if (!evaluationResult.equals("Ok")) {
						return "Error on line " + String.valueOf(i + 1)+"\n"+evaluationResult;
					}
				}
			}
		}
		return "OK";
	}
}
