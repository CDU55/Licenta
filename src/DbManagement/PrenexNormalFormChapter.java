package DbManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenexNormalFormChapter {
	private static String currentMaxLevel="SELECT MAX(LEVEL) FROM 'Prenex Normal Form';";
	private static String insertEntry="INSERT INTO 'Prenex Normal Form' VALUES(?,?);";
	private static String getEntry="SELECT FORMULA FROM 'Prenex Normal Form' WHERE Level=?;";
	public static int currentMaxLevel() throws ClassNotFoundException, SQLException
	{
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(currentMaxLevel);
		ResultSet rs=st.executeQuery();
		rs.next();
		int level=rs.getInt(1);
		rs.close();
		st.close();
		conn.close();
		return level;
		
	}
	public static void addEntry(String formula) throws ClassNotFoundException, SQLException
	{
		int level=currentMaxLevel()+1;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(insertEntry);
		st.setString(1, formula);
		st.setInt(2, level);
		st.executeUpdate();
		st.close();
		conn.close();
	}
	
	public static String getEntry(int level) throws ClassNotFoundException, SQLException
	{
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(getEntry);
		st.setInt(1, level);
		ResultSet rs=st.executeQuery();
		rs.next();
		String formula=rs.getString(1);
		rs.close();
		st.close();
		conn.close();
		return formula;
	}
}
