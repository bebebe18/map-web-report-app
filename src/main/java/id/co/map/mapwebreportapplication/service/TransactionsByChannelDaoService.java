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
import id.co.map.mapwebreportapplication.model.TransactionsByChannel;
import id.co.map.mapwebreportapplication.utility.MyDateConverter;

@Service
public class TransactionsByChannelDaoService {

	private MyDateConverter myDateConverter;
	
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public TransactionsByChannelDaoService() {
		myDateConverter = new MyDateConverter();
	}
	
	public List<TransactionsByChannel> findTransactionsByChannel(
			Integer startDate, 
			Integer endDate, 
			String channel, 
			String company,
			String warehouse) {
		// TODO Auto-generated method stub
		List<TransactionsByChannel> transactions = new ArrayList<TransactionsByChannel>();
		String queryTransactionByChannel = "EXEC Usp_GenTransactionByChannel ?, ?, ?, ?, ?";
		Connection con = null;
		
		
		try{
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement(queryTransactionByChannel);
			pstmt.setInt(1, startDate);
			pstmt.setInt(2, endDate);
			pstmt.setString(3, channel);
			pstmt.setString(4, company);
			pstmt.setString(5, warehouse);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				do {
					TransactionsByChannel transaction = new TransactionsByChannel();
					transaction.setChannel(rs.getString("Channel"));
					transaction.setWarehouse(rs.getString("Warehouse"));
					transaction.seteCommOrderId(rs.getString("ECommOrderID"));
					transaction.setInvoiceNumber(rs.getString("InvoiceNumber"));
					transaction.setQty(rs.getInt("TotalQty"));
					transaction.setSalesPrice(rs.getLong("TotalSalesPrice"));
					transaction.setLocalAmount(rs.getLong("LocalAmount"));
					transaction.setCreateDate(myDateConverter.convertDate(rs.getInt("CreateDate")));
					transaction.setOrderDate(myDateConverter.convertDate(rs.getInt("OrderDate")));
					transaction.setPaymentMode(rs.getString("PaymentMode"));
					transactions.add(transaction);
				}while(rs.next());
			}else {
				throw new DataNotFoundException("No data between "+ myDateConverter.convertDate(startDate) + 
						" until " + myDateConverter.convertDate(endDate));
			}
			
		}catch(DataNotFoundException dn) {
			throw new DataNotFoundException(dn.getMessage());
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		
		return transactions;
	}

}
