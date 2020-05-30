package Resolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.GoalReached;
import Exceptions.InvalidClause;
import Exceptions.InvalidInferenceRuleApplication;
import Exceptions.InvalidLiteral;
import Exceptions.InvalidProof;

public class ResolutionProofCheck {
	
	private static Resolution resolution;
	private static String literalForm="((!)?[a-zA-Z])";
	private static String clauseForm="\\s*\\{\\s*"+literalForm+"?"+"(\\s*,\\s*"+literalForm+"\\s*)*\\}";
	private static String numberForm="[1-9][0-9]*";
	private static String explanationForm="((\\s*\\(\\s*"+numberForm+"\\s*,\\s*"+numberForm+"\\s*,\\s*[a-zA-Z]\\s*\\))|(\\(\\s*premisa\\s*\\)))";
	private static String proofLineForm="\\s*"+clauseForm+"\\s*"+explanationForm;
	private static boolean foundPremises;
	public static boolean isClauseProof(String clause)
	{
		if(clause.matches(clauseForm))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isExplanationString(String explanation)
	{
		if(explanation.matches(explanationForm))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static ClauseAndExplanation parseLine(String line) throws InvalidLiteral, InvalidProof, InvalidClause
	{
		//if(!line.trim().matches(proofLineForm))
		//{
	//		throw new InvalidProof("Syntax Error");
	//	}
		//else
		//{
			Matcher explanationMatcher = Pattern.compile(explanationForm).matcher(line);
			if (explanationMatcher.find()) 
			{
				String explanation = explanationMatcher.group().trim();
				Matcher clauseMatcher = Pattern.compile(clauseForm).matcher(line.replace(explanation, ""));
				if (clauseMatcher.find()) 
				{
					String clause = clauseMatcher.group().trim();
					ClauseAndExplanation result=new ClauseAndExplanation(new ResolutionClause(clause),explanation);
					return result;
				}
				else
				{
					throw new InvalidProof("Couldn't find the clause");
				}
			}
			else
			{
				throw new InvalidProof("Couldn't find the explanation");
			}
		//}
	}
	
	public static String checkLine(ClauseAndExplanation line) throws InvalidProof, InvalidLiteral, InvalidInferenceRuleApplication, GoalReached
	{
		String explanation=line.explanation;
		String[] indexesAndLiteral=explanation.trim().replace(" ", "").replace("(", "").replace(")", "").split(",");
		if(indexesAndLiteral.length!=3)
		{
			throw new InvalidProof("Invalid arguments number on "+explanation);
		}
		else if(!indexesAndLiteral[0].matches(numberForm) || !indexesAndLiteral[1].matches(numberForm) || !indexesAndLiteral[2].matches("[a-zA-Z]"))
		{
			throw new InvalidProof("Invalid arguments on "+explanation);
		}
		else
		{
			int index1=Integer.parseInt(indexesAndLiteral[0]);
			int index2=Integer.parseInt(indexesAndLiteral[1]);
			Literal literal=new Literal(indexesAndLiteral[2]);
			if(index1<1 || index2<1 || index1>resolution.getClausesNumber()||index2>resolution.getClausesNumber())
			{
				throw new InvalidProof("Invalid clause index on "+explanation);
			}
			else
			{
				resolution.applyResolution(index1, index2, literal.toString());
				ResolutionClause result=resolution.getClause(resolution.getClausesNumber()-1);
				if(!result.equals(line.clause))
				{
					throw new InvalidProof("The resulting clause is not correct "+line.clause.toString());

				}
				else
				{
					return "OK";
				}
			}
		}
	}
	
	private static String checkProofString(String proof) throws InvalidProof 
	{
		foundPremises=false;
		resolution=null;
		List<ResolutionClause> premises=new ArrayList<ResolutionClause>();
		String[] lines=proof.split("\n");
		for(int lineIndex=0;lineIndex<lines.length;lineIndex++)
		{
			ClauseAndExplanation current=null;
			try {
				current = parseLine(lines[lineIndex]);
			} catch (InvalidLiteral | InvalidClause | InvalidProof e1) {
				throw new InvalidProof("Error on line "+(lineIndex+1)+"\n"+e1.getMessage());
			}
			if(current.explanation.matches("\\(\\s*premisa\\s*\\)"))
			{
				if(foundPremises==true)
				{
					throw new InvalidProof("Found premise after rule application on line "+(lineIndex+1));
				}
				else
				{
					premises.add(current.clause);
				}
			}
			else
			{
				foundPremises=true;
				if(resolution==null)
				{
					if(premises.size()!=0)
					{
						resolution=new Resolution(premises);
					}
					else
					{
						throw new InvalidProof("No premises");

					}
				}
				try {
					checkLine(current);
				} catch (Exception e) {
					throw new InvalidProof("Error on line "+(lineIndex+1)+"\n"+e.getMessage());
				}
				
			}
		}
		if(resolution==null)
		{
			throw new InvalidProof("Found only premises");
		}
		else if(!resolution.getNullClauseAchieved())
		{
			throw new InvalidProof("Null clause was not achieved");
		}
		
		return "Ok";
	}
	
	public static String checkProof(String pathOrProof,boolean isPath) throws IOException, InvalidProof
	{
		String proof=new String();
		if(isPath)
		{
			BufferedReader reader=new BufferedReader(new FileReader(pathOrProof));
			StringBuilder sb=new StringBuilder();
			String line=reader.readLine();
			while(line!=null)
			{
				sb.append(line+"\n");
				line=reader.readLine();
			}
			reader.close();
			proof=sb.toString();
		}
		else
		{
			proof=pathOrProof;
		}
		return checkProofString(proof);
	}

}
