package com.swh.db;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJimunjaDB {
	
	private String[] jasoBytes = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ", "ㄲ", "ㄸ", "ㅃ", "ㅆ", "ㅉ", 
			"ㅏ", "ㅑ", "ㅓ", "ㅕ", "ㅗ", "ㅛ", "ㅜ", "ㅠ", "ㅡ", "ㅣ", "ㅐ", "ㅒ", "ㅔ", "ㅖ", "ㅢ", "ㅚ", "ㅟ"};

	public static void main(String[] args) throws SQLException {
		InsertJimunjaDB imageTest = new InsertJimunjaDB();
		imageTest.insertImage();
		System.out.println("성공");
	}

	public Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://192.168.0.41:3306/k_min?serverTimezone=UTC", "root", "swhacademy!");
		} catch (Exception e) {
			System.out.println("Error Occured While Getting the Connection: - " + e);
		}
		return connection;
	}

	public void insertImage() {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;
		for(int i = 1; i<37; i++){
			try{
				File image = new File("src/main/resources/munja/munja_"+ i +".jpg");
				inputStream = new FileInputStream(image);

				connection = getConnection();
				statement = connection.prepareStatement("insert into jimunja values(?, ?)");
				statement.setString(1, jasoBytes[i-1]);
				statement.setBinaryStream(2, (InputStream) inputStream, (int)(image.length()));
				System.out.println("change");

				statement.executeUpdate();
			} catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					connection.close();
					statement.close();

				} catch (SQLException e) {
					System.out.println("SQLException Finally: - " + e);
				}

			}
		}
	}
}