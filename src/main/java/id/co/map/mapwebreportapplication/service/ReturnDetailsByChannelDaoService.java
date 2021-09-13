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
import id.co.map.mapwebreportapplication.model.ReturnDetailsByChannel;
import id.co.map.mapwebreportapplication.utility.MyDateConverter;

@Service
public class ReturnDetailsByChannelDaoService {

	/*
	 * Class to get Return Details Report
	 * */
	
	private static final Logger logger = LogManager.getLogger(ReturnDetailsByChannelDaoService.class);
	
	private MyDateConverter myDateConverter;
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public ReturnDetailsByChannelDaoService() {
		myDateConverter = new MyDateConverter();
	}
	
	public List<ReturnDetailsByChannel> findReturnDetailsByChannel(
			Integer startDate, 
			Integer endDate, 
			String channel, 
			String company,
			String warehouse){
		List<ReturnDetailsByChannel> returnDetailsByChannels = new ArrayList<ReturnDetailsByChannel>();
		Connection con = null;
		
		try {
			logger.info("Start Query Return Details StartDate = "+startDate +", EndDate = " +endDate);
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement("EXEC Usp_GenReturnDetailByChannel ?, ?, ?, ?, ?");
			pstmt.setInt(1, startDate);
			pstmt.setInt(2, endDate);
			pstmt.setString(3, channel);
			pstmt.setString(4, company);
			pstmt.setString(5, warehouse);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					ReturnDetailsByChannel r = new ReturnDetailsByChannel();
					r.setWarehouse(rs.getString("Warehouse"));
					r.setChannel(rs.getString("Channel"));
					r.setCreateDate(rs.getString("CreateDate"));
					r.setSalesReturnNumber(rs.getString("SalesReturnNumber"));
					r.setReturnItemNumber(rs.getString("ReturnItemNumber"));
					r.setReturnItemName(rs.getString("ItemName"));
					r.setReturnQty(rs.getInt("ReturnQuantity"));
					r.setSalesPrice(rs.getLong("SalesPrice"));
					r.setDiscount(rs.getLong("Discount"));
					r.setLocalAmount(rs.getLong("LocalAmount"));
					r.setOrderDate(rs.getString("OrderDate"));
					r.seteCommOrderId(rs.getString("ECommOrderID"));
					returnDetailsByChannels.add(r);
				}while(rs.next());
			}else {
				logger.info("No Data For Query Return Details StartDate = "+startDate +", EndDate = " +endDate);
				throw new DataNotFoundException("No data between "+ myDateConverter.convertDate(startDate) + 
						" until " + myDateConverter.convertDate(endDate));
			}
		}catch(DataNotFoundException dn) {
			throw new DataNotFoundException(dn.getMessage());
		}catch(Exception e) {
			logger.info("No Data For Query Return StartDate = "+startDate +", EndDate = " +endDate);
			e.printStackTrace();
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		return returnDetailsByChannels;
	}
	
}
