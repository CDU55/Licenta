package Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

}
