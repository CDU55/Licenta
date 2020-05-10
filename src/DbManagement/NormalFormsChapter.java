package DbManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NormalFormsChapter {
	
	private static String currentMaxLevel="SELECT MAX(LEVEL) FROM 'NORMAL FORMS' WHERE toFNC=? AND PropLogic=?;";
	private static String insertEntry="INSERT INTO 'NORMAL FORMS' VALUES(?,?,?,?);";
	private static String getEntry="SELECT FORMULA FROM 'NORMAL FORMS' WHERE ToFNC=? AND PropLogic=? AND Level=?;";
	public static int currentMaxLevel(boolean toFNC,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int toFNCFilter=toFNC==true?1:0;
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(currentMaxLevel);
		st.setInt(1, toFNCFilter);
		st.setInt(2, propLogicFilter);
		ResultSet rs=st.executeQuery();
		rs.next();
		int level=rs.getInt(1);
		rs.close();
		st.close();
		conn.close();
		return level;
		
	}
	public static void addEntry(String formula,boolean toFNC,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int level=currentMaxLevel(toFNC,propLogic)+1;
		int toFNCFilter=toFNC==true?1:0;
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(insertEntry);
		st.setString(1, formula);
		st.setInt(2, toFNCFilter);
		st.setInt(3, propLogicFilter);
		st.setInt(4, level);
		st.executeUpdate();
		st.close();
		conn.close();
	}
	
	public static String getEntry(boolean toFNC,boolean propLogic,int level) throws ClassNotFoundException, SQLException
	{
		int toFNCFilter=toFNC==true?1:0;
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(getEntry);
		st.setInt(1, toFNCFilter);
		st.setInt(2, propLogicFilter);
		st.setInt(3, level);
		ResultSet rs=st.executeQuery();
		rs.next();
		String formula=rs.getString(1);
		rs.close();
		st.close();
		conn.close();
		return formula;
	}

}
