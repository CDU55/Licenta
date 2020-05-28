package DbManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NaturalDeductionChapter {
	
	private static String currentMaxLevel="SELECT MAX(LEVEL) FROM 'NaturalDeductionGoals' WHERE PropLogic=?;";
	private static String insertEntryGoal="INSERT INTO 'NaturalDeductionGoals' VALUES(?,?,?);";
	private static String insertEntryHypothesis="INSERT INTO 'NaturalDeductionHypothesis' VALUES(?,?,?);";
	private static String getEntryGoal="SELECT GOAL FROM 'NaturalDeductionGoals' WHERE  PropLogic=? AND Level=?;";
	private static String getEntryHypothesis="SELECT FORMULA FROM 'NaturalDeductionHypothesis' WHERE  PropLogic=? AND Level=?;";

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
	public static void addEntryGoal(String sequence,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int level=currentMaxLevel(propLogic)+1;
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(insertEntryGoal);
		st.setString(1, sequence);
		st.setInt(2, propLogicFilter);
		st.setInt(3, level);
		st.executeUpdate();
		st.close();
		conn.close();
	}
	
	public static void addEntryHypothesis(String hypothesis,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int level=currentMaxLevel(propLogic);
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(insertEntryHypothesis);
		st.setString(1, hypothesis);
		st.setInt(2, propLogicFilter);
		st.setInt(3, level);
		st.executeUpdate();
		st.close();
		conn.close();
	}
	
	public static String getEntryGoal(boolean propLogic,int level) throws ClassNotFoundException, SQLException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(getEntryGoal);
		st.setInt(1, propLogicFilter);
		st.setInt(2, level);
		ResultSet rs=st.executeQuery();
		rs.next();
		String goal=rs.getString(1);
		return goal;
	}
	
	public static List<String> getEntryHypothesis(boolean propLogic,int level) throws ClassNotFoundException, SQLException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(getEntryHypothesis);
		st.setInt(1, propLogicFilter);
		st.setInt(2, level);
		ResultSet rs=st.executeQuery();
		List<String> hypothesis=new ArrayList<String>();
		while(rs.next())
		{
			hypothesis.add(rs.getString(1));
		}
		return hypothesis;
	}
}
