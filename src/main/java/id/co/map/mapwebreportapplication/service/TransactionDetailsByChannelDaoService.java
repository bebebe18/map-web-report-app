package id.co.map.mapwebreportapplication.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.map.mapwebreportapplication.configuration.SqlServerConnection;
import id.co.map.mapwebreportapplication.exception.DataNotFoundException;
import id.co.map.mapwebreportapplication.model.TransactionDetailsByChannel;
import id.co.map.mapwebreportapplication.utility.MyDateConverter;

@Service
public class TransactionDetailsByChannelDaoService  {
	
	private MyDateConverter myDateConverter;
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public TransactionDetailsByChannelDaoService() {
		myDateConverter = new MyDateConverter();
	}
	
	public List<TransactionDetailsByChannel> getTransactionDetailsByChannel(
			int startDate, 
			int endDate, 
			String channel, 
			String company,
			String warehouse) {
		
		List<TransactionDetailsByChannel> details = new ArrayList<TransactionDetailsByChannel>();
		Connection con = null;
		
		try {
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement("EXEC Usp_GenTransactionDetailByChannel ?, ?, ?, ?, ?");
			pstmt.setInt(1, startDate);
			pstmt.setInt(2, endDate);
			pstmt.setString(3, channel);
			pstmt.setString(4, company);
			pstmt.setString(5, warehouse);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do{
					TransactionDetailsByChannel transDetail = new TransactionDetailsByChannel();
					transDetail.setWarehouse(rs.getString("Warehouse"));
					transDetail.setChannel(rs.getString("Channel"));
					transDetail.setInvoiceNumber(rs.getString("InvoiceNumber"));
					transDetail.setItemNumber(rs.getString("ItemNumber"));
					transDetail.setItemName(rs.getString("ItemName"));
					transDetail.setInvoiceQuantity(rs.getInt("InvoiceQuantity"));
					transDetail.setSalesPrice(rs.getLong("SalesPrice"));
					transDetail.setDiscount(rs.getLong("Discount"));
					transDetail.setLocalAmount(rs.getLong("LocalAmount"));
					transDetail.setCreateDate(rs.getString("CreateDate"));
					transDetail.setOrderDate(rs.getString("OrderDate"));
					transDetail.seteCommOrderId(rs.getString("ECommOrderID"));
					details.add(transDetail);
				}while(rs.next());
			}
			else {
				throw new DataNotFoundException("No data between "+ myDateConverter.convertDate(startDate) + 
						" until " + myDateConverter.convertDate(endDate));
			}
		}catch(DataNotFoundException dn) {
			throw new DataNotFoundException(dn.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		return details;
	}

}
