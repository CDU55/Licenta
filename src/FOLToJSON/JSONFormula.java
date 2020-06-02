package FOLToJSON;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import AbstractSyntaxTree.FOLTreeNode;

public class JSONFormula {
	
	public String value;
	public List<JSONFormula> subTerms;
	public String rawTerm;

	public JSONFormula(FOLTreeNode node)
	{
		this.value=node.getLabel();
		this.subTerms=new ArrayList<JSONFormula>();
		if(!node.isVariable() && !node.isConnector())
		{
			this.rawTerm=node.toString();
		}
		else
		{
			this.rawTerm=this.value;
		}
		if(node.isConnector())
		{
			if(node.getLeftChild()!=null)
			{
				this.subTerms.add(new JSONFormula(node.getLeftChild()));
			}
			if(node.getRightChild()!=null)
			{
				this.subTerms.add(new JSONFormula(node.getRightChild()));
			}
		}
		else
		{
			for(FOLTreeNode arg:node.getArguments())
			{
				this.subTerms.add(new JSONFormula(arg));
			}
		}
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<JSONFormula> getSubTerms() {
		return subTerms;
	}

	public void setSubTerms(List<JSONFormula> subTerms) {
		this.subTerms = subTerms;
	}

	public String getRawTerm() {
		return rawTerm;
	}

	public void setRawTerm(String rawTerm) {
		this.rawTerm = rawTerm;
	}

	public void Convert() throws IOException
	{
		JSONObject jo=new JSONObject(this);
			FileWriter file=new FileWriter("./src/application/Resources/formula.json");
			file.write(jo.toString());
			file.close();
	}
	public void print(String tabs) {
		System.out.println(tabs + value);
		for (JSONFormula t : this.subTerms)
			t.print(tabs + '\t');

	}


}
