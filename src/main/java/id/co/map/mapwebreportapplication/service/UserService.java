package id.co.map.mapwebreportapplication.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.map.mapwebreportapplication.configuration.SqlServerConnection;
import id.co.map.mapwebreportapplication.exception.DataNotFoundException;
import id.co.map.mapwebreportapplication.model.Company;
import id.co.map.mapwebreportapplication.model.User;
import id.co.map.mapwebreportapplication.model.UserPagination;
import id.co.map.mapwebreportapplication.model.enumerate.ClientResponseStatus;
import id.co.map.mapwebreportapplication.model.response.ClientUserResponse;

@Service
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public UserService() {
		
	}

	public ClientUserResponse add(User newUser) {
		
		ClientUserResponse response = new ClientUserResponse();
		
		String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        newUser.setDatatimeStamp(currentDateTime);
        
        try {
        	addUser(newUser);
        	
        	response.setResponseCode(ClientResponseStatus.USER_CREATED.getCode());
            response.setResponseMessage(ClientResponseStatus.USER_CREATED.getMessage());
            response.setUser(newUser);

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("============================== Error Add New User ==============================");
            logger.error(e.getMessage());
            e.printStackTrace();
            logger.error("============================== Error Add New User ==============================");

            response.setResponseCode(ClientResponseStatus.SQL_ERROR.getCode());
            response.setResponseMessage(ClientResponseStatus.SQL_ERROR.getMessage() + " " +e.getMessage());
        }
		
		return response;
	}
	
	public Map<String, Object> findUserPagination(String userName, String name, Integer roleId, Integer pageNumber,
			int size, Integer draw) {
		
		Map<String, Object> findResult = UserPagination(userName, name, roleId, pageNumber, size);
        Map<String, Object> map = new HashMap<>();
        map.put("draw", draw);
        map.put("recordsTotal", findResult.get("totalCount"));
        map.put("recordsFiltered", findResult.get("totalCount"));
        map.put("data", findResult.get("users"));

        return map;
	}
	
	public ClientUserResponse delete(String username) {
		
		ClientUserResponse response = new ClientUserResponse();
        try {
        	deleteUser(username);
            response.setResponseCode(ClientResponseStatus.USER_DELETED.getCode());
            response.setResponseMessage(ClientResponseStatus.USER_DELETED.getMessage());
        } catch (ClassNotFoundException | SQLException e) {
        	logger.error("============================== Error Delete User ==============================");
            logger.error(e.getMessage());
            e.printStackTrace();
            logger.error("============================== Error Delete User ==============================");

            response.setResponseCode(ClientResponseStatus.SQL_ERROR.getCode());
            response.setResponseMessage(ClientResponseStatus.SQL_ERROR.getMessage());
            response.setUser(null);
        }
        return response;
	}
	
	public void addUser(User newUser) throws ClassNotFoundException, SQLException {
		
		Connection con = null;
		
		logger.info("Start add User");
		Class.forName(sqlServerConnection.getDriverClassName());
		con = DriverManager.getConnection(sqlServerConnection.getLog_connection_url(), 
				sqlServerConnection.getLog_username(), 
				sqlServerConnection.getLog_password());
		con.setAutoCommit(false);
		
		PreparedStatement pstUser = null, pstUserRole = null, pstUserCompany = null;
		String qryUser = "INSERT INTO AppUser (Username, Name, Enabled, DataTimeStamp) VALUES(?, ?, ?, ?)";
        String qryUserRole= "INSERT INTO UserRole (Username, RoleId, DataTimeStamp) VALUES(?, ?, ?)";
        String qryUserComp= "INSERT INTO UserCompany (Username, CompanyId, DataTimeStamp) VALUES(?, ?, ?)";
        String username = newUser.getUserName();
        String datTime = newUser.getDatatimeStamp();
        
        logger.info("Extract Data New User");
        logger.info(newUser.getUserName());
        logger.info(newUser.getName());
        logger.info(newUser.getRole().getRoleId());
        logger.info(newUser.getCompanies().size());
        logger.info(newUser.getDatatimeStamp());
        logger.info("End Data New User");
        
        //insert into table AppUser
        pstUser = con.prepareStatement(qryUser);
        pstUser.setString(1, username);
        pstUser.setString(2, newUser.getName());
        pstUser.setString(3, "1");
        pstUser.setString(4, datTime);
        pstUser.execute();
		
        //insert into table UserRole
        pstUserRole = con.prepareStatement(qryUserRole);
        pstUserRole.setString(1, username);
        pstUserRole.setString(2, newUser.getRole().getRoleId());
        pstUserRole.setString(3, datTime);
        pstUserRole.execute();
        
        //insert into table UserCompany
        pstUserCompany = con.prepareStatement(qryUserComp);
        for (Company comp: newUser.getCompanies()) {
        	logger.info("Comp Code = "+comp.getCompanyCode());
        	
        	pstUserCompany.setString(1, username);
            pstUserCompany.setString(2, comp.getCompanyCode());
            pstUserCompany.setString(3, datTime);
            pstUserCompany.addBatch();
        }
        pstUserCompany.executeBatch();
        
        con.commit();

        if(pstUser != null) pstUser.close();
        if(pstUserRole != null) pstUserRole.close();
        if(pstUserCompany != null) pstUserCompany.close();
        if(con != null) con.close();
        
        logger.info("Finish add User");
	}
	
	public void deleteUser(String username) throws ClassNotFoundException, SQLException{
		
		Connection con = null;
		logger.info("Start delete User");
		
		Class.forName(sqlServerConnection.getDriverClassName());
		con = DriverManager.getConnection(sqlServerConnection.getLog_connection_url(), 
				sqlServerConnection.getLog_username(), 
				sqlServerConnection.getLog_password());
		con.setAutoCommit(false);
		
		PreparedStatement pstUser = null, pstUserRole = null, pstUserCompany = null;
        String qryUser = "DELETE FROM AppUser WHERE UserName = ?";
        String qryUserRole= "DELETE FROM UserRole WHERE UserName = ?";
        String qryUserCompany = "DELETE FROM UserCompany WHERE UserName = ?";
        
        //delete table appUser registration
        pstUser = con.prepareStatement(qryUser);
        pstUser.setString(1, username);
        pstUser.execute();

        //delete table UserRole registration
        pstUserRole = con.prepareStatement(qryUserRole);
        pstUserRole.setString(1, username);
        pstUserRole.execute();

        //delete table UserRole registration
        pstUserCompany = con.prepareStatement(qryUserCompany);
        pstUserCompany.setString(1, username);
        pstUserCompany.execute();

        con.commit();
        if(pstUser != null) pstUser.close();
        if(pstUserRole != null) pstUserRole.close();
        if(pstUserCompany != null) pstUserCompany.close();
        if(con != null) con.close();
        
        logger.info("Finish delete User");
		
	}

	public Map<String, Object> UserPagination(String username, String name, Integer roleId, Integer pageNumber, Integer size) {
        Connection con = null;
        logger.info("Start get User Pagination");
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        String temp_usn = "";
        String list_comp = "";

        Map<String, Object> map = new HashMap<>();
        List<UserPagination> users = new ArrayList<>();
        List<UserPagination> Listusers = new ArrayList<>();
        Integer totalCount = 0;

        try{
        	Class.forName(sqlServerConnection.getDriverClassName());
        	con = DriverManager.getConnection(sqlServerConnection.getLog_connection_url(), 
    				sqlServerConnection.getLog_username(), 
    				sqlServerConnection.getLog_password());
    		
            pst = con.prepareStatement("EXECUTE Usp_FindUserForPagination ?, ?, ?, ?, ?");
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setInt(3, roleId);
            pst.setInt(4, pageNumber);
            pst.setInt(5, size);
            rs = pst.executeQuery();
            
            if(rs.next()){
            	
            	totalCount = rs.getInt("TotalCount");
                Map<String, Object> UsrTemp = new HashMap<>();
                
                do{
                	if(temp_usn.equals(""))
                	{
                		temp_usn = rs.getString("UserName");
                        list_comp = rs.getString("CompanyId");
                        UsrTemp.put("uname", temp_usn);
                		UsrTemp.put("name", rs.getString("Name"));
                		UsrTemp.put("roleid", rs.getInt("RoleId"));
                		UsrTemp.put("rolename", rs.getString("RoleName"));
                		UsrTemp.put("ltcomp", list_comp);                		
                	}
                	else if(temp_usn.equals(rs.getString("UserName"))) 
                	{
                		if(list_comp.equals("")) {
                			list_comp = rs.getString("CompanyId");
                		}else {
                			list_comp += ","+ rs.getString("CompanyId");
                		}
                		
                		UsrTemp.put("uname", rs.getString("UserName"));
                		UsrTemp.put("name", rs.getString("Name"));
                		UsrTemp.put("roleid", rs.getInt("RoleId"));
                		UsrTemp.put("rolename", rs.getString("RoleName"));
                		UsrTemp.put("ltcomp", list_comp);
                		
                	}
                	else
                	{
                		temp_usn = rs.getString("UserName");
                        list_comp = rs.getString("CompanyId");
                        UsrTemp.put("uname", temp_usn);
                		UsrTemp.put("name", rs.getString("Name"));
                		UsrTemp.put("roleid", rs.getInt("RoleId"));
                		UsrTemp.put("rolename", rs.getString("RoleName"));
                		UsrTemp.put("ltcomp", list_comp);
                	}
                	
                	UserPagination user = new UserPagination();
                    user.setUserName((String) UsrTemp.get("uname"));
                    user.setName((String)     UsrTemp.get("name"));
                    user.setRoleId((Integer)  UsrTemp.get("roleid"));
                    user.setRoleName((String) UsrTemp.get("rolename"));
                    user.setListComp((String) UsrTemp.get("ltcomp"));
                    users.add(user);
                	
                }while (rs.next());
            }
            Listusers = cleanList(users);
            map.put("users", Listusers);
            map.put("totalCount", totalCount);

        }catch (ClassNotFoundException ce){
            logger.error("ClassNotFoundException : " + ce.getMessage(),ce);
        } catch (SQLException se){
            logger.error("SQLException : " + se.getMessage(),se);
        } finally {
            try { if(con != null) con.close(); }catch (SQLException e){}
            try { if(pst != null ) pst.close(); }catch (SQLException e){}
            try { if(rs != null) rs.close(); }catch (SQLException e){}
        }
        
        logger.info("Finish get User Pagination");
        return map;
    }

	private List<UserPagination> cleanList(
			List<UserPagination> users) {
		
		List<UserPagination> Listusr = new ArrayList<>();
		String Uname  = "";
		
		for (int i = 0; i < users.size(); i++) {
            if(i == 0)
            {
            	if(users.size() == 1) {
            		Listusr.add(users.get(i));
            	}else {
            		Uname = users.get(i).getUserName();
            	}
            }
            else 
            {
            	if(!Uname.equals(users.get(i).getUserName())) 
            	{
            		Listusr.add(users.get(i-1));
            		Uname = users.get(i).getUserName();
            		
            		if(i+1 == users.size()) {
            			Listusr.add(users.get(i));
            		}
	            }
            	else
            	{
            		if(i+1 == users.size()) {
            			Listusr.add(users.get(i));
            		}
            	}
            }
        }
		
		return Listusr;
	}

	public User findUserAccount(String userName) {
		User usr = new User();
		String queryUser = "SELECT * FROM AppUser WHERE UserName = ?";
		Connection con = null;
		
		try{
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getLog_connection_url(), 
					sqlServerConnection.getLog_username(), 
					sqlServerConnection.getLog_password());
			
			PreparedStatement pstmt = con.prepareStatement(queryUser);
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					usr.setUserName(rs.getString("UserName"));
					usr.setUserName(rs.getString("Name"));
				}while(rs.next());
			}else {
				throw new DataNotFoundException("No specific User on this System");
			}
			
		}catch(DataNotFoundException dn) {
			throw new DataNotFoundException(dn.getMessage());
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		
		return usr;
	}

}
