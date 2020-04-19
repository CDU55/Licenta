package PropositionalLogicAnalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PropositionalLogicFormula.Formula;

public class TableGenerator {
	
	private static List<List<Boolean>> tableRows;
	private static final List<Boolean> values=Arrays.asList(true,false);
	
	protected static void generateRows(Formula formula, List<String> variables, int currentIndex,Boolean[] currentAssignation) {
		for(Boolean value:values)
		{
			currentAssignation[currentIndex]=value;
			if(currentIndex==variables.size()-1)
			{
				Boolean evaluationResult=FormulaEvaluator.evaluate(formula.syntaxTree.getRoot(),currentAssignation,variables);
				List<Boolean> row=new ArrayList<Boolean>(Arrays.asList(currentAssignation));
				row.add(evaluationResult);
				tableRows.add(row);
			}
			else
			{
				generateRows(formula,variables,currentIndex+1,currentAssignation);
			}
		}
		
	}
	
	public static void generateTable(Formula formula,String path) throws IOException
	{
		tableRows=new ArrayList<List<Boolean>>();
		Formula testFormula=new Formula(formula.syntaxTree.getRoot());
		List<String> variables=testFormula.syntaxTree.getVariables();
		testFormula.syntaxTree.reaplceImplications(testFormula.syntaxTree.getRoot());
		Boolean[] currentAssignation=new Boolean[variables.size()];
		generateRows(testFormula,variables,0,currentAssignation);
		String html=new String();
		html+="<html>";
		html+="<style>\ntable, th, td {\r\n" + 
				"  allign: center;\r\n" + 
				"  border: 1px solid black;\r\n" + 
				"  border-collapse: collapse;\r\n" +
				"text-allign:center;"+
				"}</style>";
		html+="<head>\n<title>Table</title>\n</head>";
		html+="<body>";
		 html+="<table>\n<tr>";
		for(String variable:variables)
		{
			html+="<th>"+variable+"</th>\n";
		}
		html+="<th>"+formula.toString().replaceAll(" ", "&nbsp;")+"</th>\n";
		html+="</tr>";
		for(List<Boolean> row:tableRows)
		{
			html+="<tr>";
			for(Boolean value:row)
			{
				html+="<td>"+value.toString()+"</td>\n";
			}
			html+="</tr>";
		}
		html+="</table>";
		html+="<body>";
		html+="</html>";
		FileWriter writer=new FileWriter(path);
		writer.write(html);
		writer.close();
	}

}
