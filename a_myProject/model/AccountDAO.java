package a_myProject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import a_myProject.dbutil.util;

public class AccountDAO {
	private Connection conn;
	private PreparedStatement pst; // ?지원
	private ResultSet rs;
	
	
	public int userLogin(String userID, String userPassword) {
		String sql = """
					select password from account 
					where id = ?
				""";
		conn = util.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, userID);
			rs = pst.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) return 1;
				else return 0;
			}
			return -1;
		}catch(SQLException e){
			e.printStackTrace();
			return -2;
		}
	}

}
