package DbManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResolutionChapter {
	private static String currentMaxLevel="SELECT MAX(LEVEL) FROM RESOLUTION WHERE PropLogic=?;";
	private static String insertEntry="INSERT INTO RESOLUTION VALUES(?,?,?);";
	private static String getEntry="SELECT FORMULA FROM 'RESOLUTION' WHERE PropLogic=? AND Level=?;";
	public static int currentMaxLevel(boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(currentMaxLevel);
		st.setInt(1, propLogicFilter);
		ResultSet rs=st.executeQuery();
		rs.next();
		int level=rs.getInt(1);
		rs.close();
		st.close();
		conn.close();
		return level;
		
	}
	public static void addEntry(String formula,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int level=currentMaxLevel(propLogic)+1;
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(insertEntry);
		st.setString(1, formula);
		st.setInt(2, propLogicFilter);
		st.setInt(3, level);
		st.executeUpdate();
		st.close();
		conn.close();
	}
	
	public static String getEntry(boolean propLogic,int level) throws ClassNotFoundException, SQLException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(getEntry);
		st.setInt(1, propLogicFilter);
		st.setInt(2, level);
		ResultSet rs=st.executeQuery();
		rs.next();
		String formula=rs.getString(1);
		return formula;
	}
}
