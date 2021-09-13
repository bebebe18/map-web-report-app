package id.co.map.mapwebreportapplication.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.map.mapwebreportapplication.model.chart.TransactionByMonth;
import id.co.map.mapwebreportapplication.model.chart.TransactionByMonthLineChart;
import id.co.map.mapwebreportapplication.service.ChartDaoService;

@RestController
public class ChartController {

	/*
	 * Class to provide Chart's data in Index Page
	 * */
	
	private static final Logger logger = LogManager.getLogger(ChartController.class);
	
	@Autowired
	private ChartDaoService service;
	

	@GetMapping("/api/chart/transactionByChannel")
	public HashMap<String, Object> getTransactionByChannel() {
		logger.info("Incoming request for transaction by channel");
		
		List<TransactionByMonth> data = service.findTrasactionByMonth();
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		//sum amount of sales YTD
		Long amountSalesYtd = data.stream()
				.filter(e -> e.getTransactionType().equals("Sales"))
				.mapToLong(e -> e.getAmount())
				.sum();
			
		//count number of sales YTD
		Long numberTransactionYtd = data.stream()
				.filter(e -> e.getTransactionType().equals("Sales"))
				.mapToLong(e -> e.getNumberOfTransaction())
				.sum();
		
		//sum amount of retrun YTD
		Long amountReturnYtd = data.stream()
				.filter(e -> e.getTransactionType().equals("Return"))
				.mapToLong(e -> e.getAmount())
				.sum();
				
		//count number of return YTD
		Long numberReturnYtd = data.stream()
				.filter(e -> e.getTransactionType().equals("Return"))
				.mapToLong(e -> e.getNumberOfTransaction())
				.sum();
		
		//Sales Amount YTD 
		Map<String, Long> salesYtd = data
								.stream()
								.filter(e -> e.getTransactionType().equals("Sales"))
								.collect(Collectors.groupingBy(TransactionByMonth::getChannel, 
										Collectors.summingLong(TransactionByMonth::getAmount)));

		//sort by channel
		Map<String, Long> salesYtdSorted = new TreeMap<String, Long>(salesYtd);

		List<String> salesChannelsYtd = new ArrayList<>(salesYtdSorted.keySet());
		List<Long> salesAmountYtd = new ArrayList<>(salesYtdSorted.values());
		//End Sales Amount YTD
		
		//Number of Sales Transactions  YTD 
		Map<String, Integer> transactionYtd = data
								.stream()
								.filter(e -> e.getTransactionType().equals("Sales"))
								.collect(Collectors.groupingBy(TransactionByMonth::getChannel, 
										Collectors.summingInt(TransactionByMonth::getNumberOfTransaction)));
		
		//sort by channel
		Map<String, Integer> transactionYtdSorted = new TreeMap<String, Integer>(transactionYtd);
		
		List<String> transactionsChannelsYtd = new ArrayList<>(transactionYtdSorted.keySet());
		List<Integer> numberOfTransactionsYtd = new ArrayList<>(transactionYtdSorted.values());
		//End Number of Sales Transactions  YTD 
		
		//Border Colors list
		List<String> borderColors = data
							.stream()
							.filter(distinctByKey(TransactionByMonth::getChannel))
							.map(TransactionByMonth::getBorderColor)
							.collect(Collectors.toList());
		
		
		//data set for line chart channels by month
		List<TransactionByMonthLineChart> dataset = genTransactionByMonthLineChart(data);
		
		//periods
		List<String> period = data.stream().map(c -> c.getPeriod()).distinct().collect(Collectors.toList());
		
		//max value of sales
		Long x = data.stream()
				.filter(m -> m.getTransactionType().equals("Sales"))
				.max(Comparator.comparing(TransactionByMonth::getAmount)).get().getAmount();
		
		Long highestAmount = (long) Math.ceil(x * 1.1/1000) * 1000;
		//end max value of sales

		json.put("amountSalesYtd", amountSalesYtd);			
		json.put("numberTransactionYtd", numberTransactionYtd);	
		json.put("amountReturnYtd", amountReturnYtd);	
		json.put("numberReturnYtd", numberReturnYtd);
		
		json.put("salesChannelsYtd", salesChannelsYtd);
		json.put("salesAmountYtd", salesAmountYtd);
		
		json.put("transactionsChannelsYtd", transactionsChannelsYtd);
		json.put("numberOfTransactionsYtd", numberOfTransactionsYtd);
		
		json.put("dataset", dataset);
		json.put("period", period);
		json.put("highestAmount", highestAmount);
		
		json.put("borderColors", borderColors);
		
		return json;
	}	
	
	
	//generate dataset for line chart channels by month
	private List<TransactionByMonthLineChart> genTransactionByMonthLineChart(List<TransactionByMonth> data){
		//set list of channels
		List<String> channels = data.stream().map(c -> c.getChannel()).distinct().collect(Collectors.toList());
		List<TransactionByMonthLineChart> transactions = new ArrayList<TransactionByMonthLineChart>();
		
		for(int i = 0; i < channels.size(); i ++) {
			TransactionByMonthLineChart transaction = new TransactionByMonthLineChart();
			ArrayList<Long> amounts = new ArrayList<Long>();
			transaction.setLabel(channels.get(i));
			transaction.setBorderColor("#00FFFF");
			
			for(TransactionByMonth x: data){
				if(x.getChannel().equals(channels.get(i)) && x.getTransactionType().equals("Sales")) {
					amounts.add(x.getAmount());
					transaction.setBorderColor(x.getBorderColor());
				}
			}
			transaction.setData(amounts);
			
			transactions.add(transaction);
		}
		return transactions;
	}
	
	//method to distinct
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor){
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
}
