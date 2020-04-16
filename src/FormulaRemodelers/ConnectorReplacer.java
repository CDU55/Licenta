package FormulaRemodelers;

public class ConnectorReplacer implements FormulaRemodeler {

	@Override
	public String remodel(String formula) {
		
		String newFormula=new String(formula);
		newFormula=newFormula.replace("/\\", "&");
		newFormula=newFormula.replace("\\/", "|");
		newFormula=newFormula.replace("<->", "-");
		newFormula=newFormula.replace("->", ">");
		newFormula=newFormula.replace("<-", "<");
		return newFormula;
		
	}

}
