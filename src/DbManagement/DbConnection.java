package DbManagement;
import java.sql.*;
public class DbConnection {
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException
	{
        Class.forName("org.sqlite.JDBC");
		Connection conn=null;
		String url="jdbc:sqlite:src\\application\\Resources\\LogicQuiz.db";
		conn=DriverManager.getConnection(url);
		return conn;
	}

}
