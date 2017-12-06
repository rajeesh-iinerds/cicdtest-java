package com.amazonaws.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIUtil {
	
	/**
	 * Updates markup value based on the retailerId, distributorId and categoryId
	 * @param parameters
	 * @return
	 */
	
	static{
		try{			
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the list of distributors based on  the retailer id.
	 * @param parameters
	 * @return
	 */
	public static JSONObject getDistributorsList(JSONObject parameters){
		JSONArray distributorList=new JSONArray();
		JSONObject returnJSON=new JSONObject();
		JSONObject status=new JSONObject();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			conn=DBUtil.getNewConnection();
			stmt=conn.createStatement();
			String retailerId=parameters.get("retailerId").toString();
			String query="SELECT distributorName,distributor.distributorId FROM distributor INNER JOIN `retailerdistributermap` "
					+ "ON `distributor`.`DistributorID`=`retailerdistributermap`.`DistributorId` wHERE `retailerdistributermap`.`RetailerId`="+retailerId;
			rs=DBUtil.executeQuery(conn, stmt, query);
			while(rs.next()){
				JSONObject distributor=new JSONObject();
				distributor.put("distributorId", rs.getLong("distributorId"));
				distributor.put("distributorName", rs.getString("distributorName"));
				distributorList.put(distributor);
			}
			
			returnJSON.put("distributors",distributorList);
			status.put("code", 200);
			status.put("type", "OK");
			status.put("message", "Success");
			status.put("userMessage", "Success");
			returnJSON.put("status", status);
		}
		catch(Exception e){
			e.printStackTrace();
			try{				
				status.put("code", 400);
				status.put("message", "UnKnown error: "+e.getMessage());
				status.put("userMessage", "UnKnown error");
				returnJSON.put("status", status);
				return returnJSON;
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		finally{
			DBUtil.closeConnection(rs, stmt, conn);
		}
		return returnJSON;
	}
	
}
