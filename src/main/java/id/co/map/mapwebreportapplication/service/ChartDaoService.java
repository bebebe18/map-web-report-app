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
import id.co.map.mapwebreportapplication.model.chart.TransactionByMonth;

@Service
public class ChartDaoService {
	/*
	 * Provide Chart's data in Index Page*/
	
	private static final Logger logger = LogManager.getLogger(ChartDaoService.class);
		
	@Autowired
	private SqlServerConnection sqlServerConnection;
	
	public List<TransactionByMonth> findTrasactionByMonth(){
		Connection con = null;
		try {
			List<TransactionByMonth> transactions = new ArrayList<TransactionByMonth>();
			logger.info("Start generate data transation by month (chart data)");
			Class.forName(sqlServerConnection.getDriverClassName());
			con = DriverManager.getConnection(sqlServerConnection.getConnection_url(), 
					sqlServerConnection.getUsername(), 
					sqlServerConnection.getPassword());
			
			PreparedStatement pstmt = con.prepareStatement("EXEC Usp_GenO2OByMonth");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TransactionByMonth tbm = new TransactionByMonth();
				tbm.setPeriod(rs.getString("Period"));
				tbm.setChannel(rs.getString("Channel"));
				tbm.setTransactionType(rs.getString("TransactionType"));
				tbm.setNumberOfTransaction(rs.getInt("NumberOfTransactions"));
				tbm.setAmount(rs.getLong("Amount"));
				tbm.setBorderColor(rs.getString("BorderColor"));
				transactions.add(tbm);
			}
			
			logger.info("Finish generate data transation by month (chart data)");
			return transactions;
			
		}catch(Exception e) {
			logger.info("Error generate data transation by month (chart data)");
			e.printStackTrace();
			return null;
		}finally {
			try { if (con != null) {con.close();} }catch(SQLException ex) { ex.printStackTrace();}
		}
		
		
	}
	
	
}
