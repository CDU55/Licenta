package Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import NaturalDeduction.DeductiveSystem;
import Resolution.Resolution;

public class WriteFile {
	
	public static void writeText(String text,String filePath) throws IOException
	{
		FileWriter writer=new FileWriter(filePath);
		writer.write(text);
		writer.close();
	}
	
	public static void writeLines(List<String> lines,String filePath) throws IOException
	{
		FileWriter writer=new FileWriter(filePath);
		for(String line:lines)
		{
			writer.write(line);
			writer.write("\r\n");
		}
		writer.close();
	}
	
	public static void writeResolutionProof(Resolution resolution,String filePath) throws IOException
	{
		FileWriter writer=new FileWriter(filePath);
		for(int index=0;index<resolution.getClausesNumber();index++)
		{
			writer.write(resolution.getClauseAndExplanation(index));
			writer.write("\r\n");
		}
		writer.close();

	}
	
	public static void writeNaturalDeductionProof(DeductiveSystem deduction,String filePath) throws IOException
	{
		FileWriter writer=new FileWriter(filePath);
		for(int index=0;index<deduction.sequences.size();index++)
		{
			writer.write(deduction.getSequenceAndExplanation(index));
			writer.write("\r\n");
		}
		writer.close();
	}

}
