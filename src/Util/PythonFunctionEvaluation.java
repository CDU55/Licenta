package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import FOLToJSON.JSONFormula;

public class PythonFunctionEvaluation {
	public static String execPy(JSONFormula formula,String implementationsPath,String assignationPath,String domainsPath) throws IOException
	{
		
			formula.Convert();
			String jsonPath=new File("src/application/Resources/formula.json").getAbsolutePath();
			String pythonPath=new File("src/application/Resources/sat.py").getAbsolutePath();
			Process p=Runtime.getRuntime().exec("python "+pythonPath+" "+jsonPath+" "+implementationsPath+" "+assignationPath+" "+domainsPath);
			BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String output=new String();
			String line=new String();
			while((line=processOutputReader.readLine())!=null)
			{
				output+=line+'\n';
			}
			return output;
	}
}
