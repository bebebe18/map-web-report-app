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
import id.co.map.mapwebreportapplication.model.Company;
import id.co.map.mapwebreportapplication.model.Role;
import id.co.map.mapwebreportapplication.model.Store;

@Service
public class CommonService {

	private static final Logger logger = LogManager.getLogger(CommonService.class);
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public CommonService() {
		
	}
	
	public List<String> getChannels() {
		Connection con = null;
		
		try {
			List<String>channels = new ArrayList<String>();
			logger.info("Start get list channel");
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement("SELECT DISTINCT UPPER(OrderSourceName) Channel FROM O2O_info");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					channels.add(rs.getString("Channel"));
				}while(rs.next());
			}
			logger.info("Finish get list channel");
			return channels;
		}catch(Exception e) {
			logger.error("Error while get list of channels");
			e.printStackTrace();
			return null;
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
	}
	
	public List<Company> getCompanies() {
		
		/*
		List<Company>companies = new ArrayList<Company>();
		companies.add(new Company("0100", "PT. Mitra Adiperkasa Tbk"));
		companies.add(new Company("0110", "PT. Mitra Selaras Smpurna"));
		companies.add(new Company("0888", "PT. Map Aktif Adiperkasa"));
		companies.add(new Company("0170", "PT. SAMSONITE INDONESIA"));
		companies.add(new Company("0190", "PT. PUTRA AGUNG LESTARI"));
		companies.add(new Company("0300", "PT. Creasi Mode Indonesia"));
		return companies;
		*/
		
		Connection con = null;
		
		String queryCompany = "SELECT \r\n" + 
				"DISTINCT D.CompanyCode, LEFT(D.CompanyName, 30) CompanyName\r\n" + 
				"FROM O2O_Info O \r\n" + 
				"INNER JOIN Warehouse W ON O.Warehouse = W.Warehouse AND W.ResponsibleUser = 'ETP'\r\n" + 
				"INNER JOIN [192.168.1.5].[MAP_DW].dbo.DimCompany D ON W.Location = D.CompanyCode \r\n"+
				"INNER JOIN [192.168.1.40].[ETP_EAS_LIVE].dbo.UserCompany UC ON UC.CompanyId = D.CompanyCode \r\n"+
				"ORDER BY D.CompanyCode";
		
		try {
			List<Company>companies = new ArrayList<Company>();
			logger.info("Start get list companies");
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement(queryCompany);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					Company company = new Company();
					company.setCompanyCode(rs.getString("CompanyCode"));
					company.setCompanyName(rs.getString("CompanyName"));
					companies.add(company);
				}while(rs.next());
			}
			logger.info("Finish get list companies");
			return companies;
		}catch(Exception e) {
			logger.error("Error while get list of companies");
			e.printStackTrace();
			return null;
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		
	}
	
	public List<Role> getRoles() {
		
		List<Role>roles = new ArrayList<Role>();
		roles.add(new Role("1", "ROLE_ADMIN"));
		roles.add(new Role("2", "ROLE_OPERATOR"));
		return roles;
		
		/*
		Connection con = null;
		
		String queryCompany = "SELECT * from AppRole";
		
		try {
			List<Role>roles = new ArrayList<Role>();
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement(queryCompany);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					Role role = new Role();
					role.setRoleId(rs.getString("RoleId"));
					role.setRoleName(rs.getString("RoleName"));
					roles.add(role);
				}while(rs.next());
			}
			return roles;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		*/
	}
		
	public List<Store> getStores(String location) {
		Connection con = null;
		
		String queryCompany = "SELECT Warehouse, WarehouseDescription, Location \r\n" + 
				"FROM Warehouse\r\n" + 
				"WHERE ResponsibleUser = 'ETP'\r\n" + 
				"AND Location = ?";
		
		try {
			List<Store> stores = new ArrayList<Store>();
			logger.info("Start get list stores");
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement(queryCompany);
			pstmt.setString(1, location);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					Store store = new Store();
					store.setWarehouse(rs.getString("Warehouse"));
					store.setWarehouseDescription(rs.getString("WarehouseDescription"));
					stores.add(store);
				}while(rs.next());
			}
			logger.info("Finish get list stores");
			return stores;
		}catch(Exception e) {
			logger.error("Error while get list of stores");
			e.printStackTrace();
			return null;
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
	}
	
}
