package id.co.map.mapwebreportapplication.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.map.mapwebreportapplication.configuration.SqlServerConnection;
import id.co.map.mapwebreportapplication.exception.DataNotFoundException;
import id.co.map.mapwebreportapplication.model.TransactionsByChannel;

@Service
public class RoleService {
	
private static final Logger logger = LogManager.getLogger(RoleService.class);
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public RoleService() {
		
	}
	
	public List<String> getRoleNames(String username){
		
		List<String> listRole = new ArrayList<String>();
		String queryRoleName = "SELECT AR.* FROM AppRole AR\n" +
                "INNER JOIN UserRole UR ON AR.RoleId = UR.RoleId\n" +
                "WHERE UR.UserName = ?";
		Connection con = null;
		
		try{
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement(queryRoleName);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					String roleName = "";
					roleName = rs.getString("RoleName");
					listRole.add(roleName);
				}while(rs.next());
			}else {
				throw new DataNotFoundException("No list Role on this user");
			}
			
		}catch(DataNotFoundException dn) {
			throw new DataNotFoundException(dn.getMessage());
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		
		return listRole;
		
	}

}
