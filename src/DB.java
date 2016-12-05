import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DB {
	private static String url = "jdbc:mysql://127.0.0.1:3306/data_mining?user=root&password=xiangwenni0";
	private static String driver = "com.mysql.jdbc.Driver";
	private static Connection conn = null;

	public static boolean check(String username, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url);
		String sql = "select * from users where username = '" + username
				+ "' and password = '" + password + "';";
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		if (!rset.next()) {
			stmt.close();
			rset.close();
			conn.close();
			return false;
		}

		else {
			stmt.close();
			rset.close();
			conn.close();
			return true;
		}

	}

	public static boolean isTested(String username)
			throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url);
		String sql = "select ";

		return true;
	}
	
	public static void modify(String username,String password) throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		conn = DriverManager.getConnection(url);
		String sql = "update users set password = '"+password+"' where username = '"+username+"' ;";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
		conn.close();
	}

	public static void record(String path,String username,String code,String time) throws IOException,ClassNotFoundException, SQLException{
		BufferedReader br=new BufferedReader(new FileReader(path));
		Class.forName(driver);
		conn = DriverManager.getConnection(url);
		String userAnswer=br.readLine();
		br.close();
		if(code.charAt(0)=='q'){
			String sql="insert into feedback value('"+username+"','"+code+"','"+time+"','"+userAnswer+"',-1);";
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		else if(code.charAt(0)=='t'){
			String sql="insert into feedback value('"+username+"','"+code+"','"+time+"','"+userAnswer+"',-1);";
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		else if(code.charAt(0)!='q'){
			String sql="select standardAnswer from answers where Qcode = '"+code+"' ;";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			rset.next();
			String standardAnswer=rset.getString(1);
			int count=0;
			for(int i=0;i<standardAnswer.length();i++){
				if(userAnswer.charAt(i)==standardAnswer.charAt(i)) count++;
			}
			sql="insert into feedback value('"+username+"','"+code+"','"+time+"','"+userAnswer+"',"+count+");";
			stmt.executeUpdate(sql);
			stmt.close();
			rset.close();
		}
		conn.close();
	}
}
