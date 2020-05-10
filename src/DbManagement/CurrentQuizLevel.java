package DbManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrentQuizLevel {
	
	private static String resetLevelCommand="UPDATE 'CURRENT LEVEL' SET Level=1 WHERE Chapter=? AND PropLogic=? ;";
	private static String updateLevelCommand="UPDATE 'CURRENT LEVEL' SET Level=? WHERE Chapter=? AND PropLogic=? ;";
	private static String currentLevelCommand="SELECT Level FROM 'CURRENT LEVEL'WHERE Chapter=? AND PropLogic=? ;";
	public static void createEntries() throws ClassNotFoundException, SQLException
	{
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement("INSERT INTO 'CURRENT LEVEL' VALUES(?,?,?);");
		st.setString(1, "Natural Deduction");
		st.setInt(2, 1);
		st.setInt(3, 1);
		st.executeUpdate();
		st.setString(1, "Natural Deduction");
		st.setInt(2, 1);
		st.setInt(3, 0);
		st.executeUpdate();
		st.setString(1, "Resolution");
		st.setInt(2, 1);
		st.setInt(3, 1);
		st.executeUpdate();
		st.setString(1, "Resolution");
		st.setInt(2, 1);
		st.setInt(3, 0);
		st.executeUpdate();
		st.setString(1, "FNC");
		st.setInt(2, 1);
		st.setInt(3, 1);
		st.executeUpdate();
		st.setString(1, "FNC");
		st.setInt(2, 1);
		st.setInt(3, 0);
		st.executeUpdate();
		st.setString(1, "FND");
		st.setInt(2, 1);
		st.setInt(3, 1);
		st.executeUpdate();
		st.setString(1, "FND");
		st.setInt(2, 1);
		st.setInt(3, 0);
		st.executeUpdate();
		conn.close();
	}
	
	public static void resetLevel(String quizChapter,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement st=conn.prepareStatement(resetLevelCommand);
		st.setString(1, quizChapter);
		st.setInt(2, propLogicFilter);
		st.executeUpdate();
		st.close();
		conn.close();
	}
	
	public static void incrementLevel(String quizChapter,boolean propLogic) throws ClassNotFoundException, SQLException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement getCurrentLevel=conn.prepareStatement(currentLevelCommand);
		getCurrentLevel.setString(1, quizChapter);
		getCurrentLevel.setInt(2, propLogicFilter);
		ResultSet rs=getCurrentLevel.executeQuery();
		rs.next();
		int currentLevel=rs.getInt("Level");
		getCurrentLevel.close();
		PreparedStatement st=conn.prepareStatement(updateLevelCommand);
		st.setInt(1, currentLevel+1);
		st.setString(2,quizChapter);
		st.setInt(3, propLogicFilter);
		st.executeUpdate();
		st.close();
		conn.close();
	}

	public static int getCurrentLevel(String quizChapter,boolean propLogic) throws SQLException, ClassNotFoundException
	{
		int propLogicFilter=propLogic==true?1:0;
		Connection conn=DbConnection.getConnection();
		PreparedStatement getCurrentLevel=conn.prepareStatement(currentLevelCommand);
		getCurrentLevel.setString(1, quizChapter);
		getCurrentLevel.setInt(2, propLogicFilter);
		ResultSet rs=getCurrentLevel.executeQuery();
		rs.next();
		int currentLevel=rs.getInt("Level");
		rs.close();
		getCurrentLevel.close();
		conn.close();
		return currentLevel;
	}
}
