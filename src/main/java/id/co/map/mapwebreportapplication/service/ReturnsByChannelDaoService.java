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
import id.co.map.mapwebreportapplication.model.ReturnsByChannel;
import id.co.map.mapwebreportapplication.utility.MyDateConverter;

@Service
public class ReturnsByChannelDaoService {
	
	/* 
	 * Class to get Returns By Channel Report
	 * */
	
	private static final Logger logger = LogManager.getLogger(ReturnsByChannelDaoService.class);
	private MyDateConverter myDateConverter;
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public ReturnsByChannelDaoService() {
		myDateConverter = new MyDateConverter();
	}
	
	public List<ReturnsByChannel> findRetrunsByChannel(
			Integer startDate, 
			Integer endDate, 
			String channel, 
			String company,
			String warehouse) {
		List<ReturnsByChannel> returnByChannels = new ArrayList<ReturnsByChannel>();
		String queryReturnsByChannel = "EXEC Usp_GenReturnByChannel ?, ?, ?, ?, ?";
		Connection con = null;
		
		try {
			logger.info("Start Query Return StartDate = "+startDate +", EndDate = " +endDate);
			Class.forName(sqlServerConnection.getDriverClassName());
			
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement(queryReturnsByChannel);
			pstmt.setInt(1, startDate);
			pstmt.setInt(2, endDate);
			pstmt.setString(3, channel);
			pstmt.setString(4, company);
			pstmt.setString(5, warehouse);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					ReturnsByChannel r = new ReturnsByChannel();
					r.setChannel(rs.getString("Channel"));
					r.setCreateDate(myDateConverter.convertDate(rs.getInt("CreateDate")));
					r.seteCommOrderId(rs.getString("ECommOrderID"));
					r.setSalesReturnNumber(rs.getString("SalesReturnNumber"));
					r.setQty(rs.getInt("TotalQty"));
					r.setSalesPrice(rs.getLong("TotalSalesPrice"));
					r.setLocalAmount(rs.getLong("LocalAmount"));
					r.setWarehouse(rs.getString("Warehouse"));
					r.setOrderDate(myDateConverter.convertDate(rs.getInt("OrderDate")));
					r.setPaymentMode(rs.getString("PaymentMode"));
					returnByChannels.add(r);
				}while(rs.next());
			}else {
				logger.info("No Data For Query Return StartDate = "+startDate +", EndDate = " +endDate);
				throw new DataNotFoundException("No data between "+ myDateConverter.convertDate(startDate) + 
						" until " + myDateConverter.convertDate(endDate));
			}
		}catch(DataNotFoundException dn) {	
			throw new DataNotFoundException(dn.getMessage());
		}catch(Exception e) {
			logger.error("Error get data from SQL SERVER");
			e.printStackTrace();
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		
		
		logger.info("Finish Query Return StartDate = "+startDate +", EndDate = " +endDate);
		return returnByChannels;
	}
	
	
}
