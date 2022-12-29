package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DButils {
	
   private static String dbHostName = TestDataReader.getProperty("dbhosturl");
     // we can separate them too ,, example int portNumber = 3306;
   private static String username =TestDataReader.getProperty("dbusername");
   private static String password = TestDataReader.getProperty("dbpassword");
   
   
   private Connection connection;
   private Statement statement;
   private ResultSet resultset;
   private ResultSetMetaData rsmd;
   
   //this function accepts a sql query and gets the record
    public List<String> selectArecord(String query){
	   List<String> list = new ArrayList<>();
	   try {
		connection = DriverManager.getConnection(dbHostName,username,password);
		statement = connection.createStatement();
		resultset = statement.executeQuery(query);
		rsmd = resultset.getMetaData();
		if (resultset.next() == true) {
			for (int i = 1; i <=rsmd.getColumnCount(); i++) {
				list.add(resultset.getString(i));
			}
		}
		
		connection.close();	
	} catch (SQLException e) {
		System.out.println("DB connection not established");
		e.printStackTrace();
	}  
	   return list;
   }
    
    
   //this function accepts an insert sql and inserts a record
   
    public void insertRecord(String insertQuery){
	    try {
		connection = DriverManager.getConnection(dbHostName,username,password);
		System.out.println("DB connection established");
		statement = connection.createStatement();
		statement.executeUpdate(insertQuery);
		connection.close();
		
	} catch (SQLException e) {
		System.out.println("DB connection is not established");
		e.printStackTrace();
	}  
   
   }
    
    public void updateRecord(String updateQuery){
	    try {
		connection = DriverManager.getConnection(dbHostName,username,password);
		System.out.println("DB connection established");
		statement = connection.createStatement();
		statement.executeUpdate(updateQuery);
		connection.close();
		
	} catch (SQLException e) {
		System.out.println("DB connection is not established");
		e.printStackTrace();
	}  
   
   }
    
    
    public void deleteRecord(String deleteQuery){
	    try {
		connection = DriverManager.getConnection(dbHostName,username,password);
		System.out.println("DB connection established");
		statement = connection.createStatement();
		statement.executeUpdate(deleteQuery);
		connection.close();
		
	} catch (SQLException e) {
		System.out.println("DB connection is not established");
		e.printStackTrace();
	}  
   
   }
    
    
    
   
   
//   
//   public static void main(String[] args) {
//	   
//	   String query = "Select id, name, email, phone From customers where name='John Smith'";
//	   try {
//		Connection connect = DriverManager.getConnection(dbHostName,username,password);
//		System.out.println("Connection is successful");
//		Statement statement = connect.createStatement();
//		ResultSet resultset = statement.executeQuery(query);
//     	ResultSetMetaData rsmd = resultset.getMetaData();
//		
//		resultset.next();
//		
//		System.out.println("First index is id: " + resultset.getString(1));
//		System.out.println("Column name for 3rd column is: " + rsmd.getColumnName(3));
//		
//		System.out.println("column count is :" + rsmd.getColumnCount());
//		
//		List<String> johnsmith = new ArrayList<>();
//		for (int i = 1; i<= rsmd.getColumnCount(); i++) {
//			johnsmith.add(resultset.getString(i));
//		}
//		for (String str: johnsmith) {
//			System.out.println(str);
//		}
//		connect.close();
//		
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	
//  }
}  

 
 

	



