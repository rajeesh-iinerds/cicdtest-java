package com.amazonaws.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil {
	
	private static String endpoint="iihccdb.ccotz7ldulel.us-west-2.rds.amazonaws.com:3306";
	private static String endPointUserName="master";
	private static String endPointPassword="cctiia1234";
	private static String endPointDBName="gunbroker";

	
	public static Object getFirstValueForQuery(Connection conn,String tableName,String columnName,String criteria){
		Object returnValue=null;
//		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
//			conn=getNewConnection();
			stmt=conn.createStatement();
			criteria=criteria==null?"":" where "+criteria;
			rs=executeQuery(conn, stmt, "select "+columnName+" from "+tableName+criteria);
			if( rs!=null && rs.next()){				
				returnValue=rs.getObject(columnName);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeConnection(rs, stmt, null);
		}
		return returnValue;
	}
	
	public static Connection getNewConnection(){
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://"+endpoint+"/"+endPointDBName,endPointUserName,endPointPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
	public static ResultSet executeQuery(Connection conn, Statement stmt, String query){
		ResultSet rs=null;
		try{
			stmt.executeQuery(query);
			rs=stmt.getResultSet();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	public static boolean executeUpdate(String query){
		boolean result=false;
		Connection conn=null;
		Statement stmt=null;
		try{
			conn=getNewConnection();
			stmt=conn.createStatement();
			stmt.executeUpdate(query);
			result=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeConnection(null, stmt, conn);
		}
		return result;
	}
	
	public static void closeConnection(ResultSet rs, Statement stmt, Connection conn){
		try{
			try{
				if(rs!=null){					
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println("unable to close ResultSet");
			}
			try{
				if(stmt!=null){					
					stmt.close();
				}
			}
			catch(Exception e){
				System.out.println("unable to close Statement");
			}
			try{
				if(conn!=null){					
					conn.close();
				}
			}
			catch(Exception e){
				System.out.println("unable to close Connection");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
