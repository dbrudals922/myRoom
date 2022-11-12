package com.swh.db;

import static spark.Spark.get;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ConnectDB {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://183.99.87.90:3306/k_min?serverTimezone=UTC";

	static final String USERNAME = "root";
	static final String PASSWORD = "swhacademy!";

	public static void main(String[] arg){
		ConnectDB c = new ConnectDB();
		JSONObject j = c.getJson();
		get("/hello", (request, response) -> {
			return "Hello World!";	
		});
		get("/", "application/json", (request, response) -> j);
		System.out.println("성공");
	}

	public JSONObject getJson(){
		Connection connection = null;
		Statement statement = null;
		try{
			Class.forName(JDBC_DRIVER).newInstance();
			connection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			System.out.println("MariaDB 연결.");
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM jimunja;");

			JSONObject jsonObject = new JSONObject();
			JSONArray result = new JSONArray();
			while(rs.next()){

				JSONObject data = new JSONObject();
				data.put("jamo", rs.getString("jamo"));
				data.put("image", encodeBlobToBase64(rs.getBlob("image")));

				result.add(data);

			}
			jsonObject.put("result", result);

			rs.close();
			return jsonObject;
		}catch(SQLException se1){
			se1.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(statement!=null) statement.close();
				if(connection!=null) connection.close();
			}catch(SQLException se2){
			}
		}
		//		System.out.println("MariaDB 연결종료.");
		return null;
	}

	public static String encodeBlobToBase64(Blob data){
		String base64Str = "";
		byte[] blobToBytes = null;

		try {
			if(data != null && data.length() > 0){
				blobToBytes = data.getBytes(1l, (int) data.length());
				base64Str = ConvertBinaryStreamToString(Base64.getEncoder().encode(blobToBytes));

			}
		} catch (Exception e) {
			System.err.println("encodeBlobToBase64 error" + e);
		}

		return base64Str;
	}

	private static String ConvertBinaryStreamToString(byte[] encode) {
		return new String(encode);

	}
}