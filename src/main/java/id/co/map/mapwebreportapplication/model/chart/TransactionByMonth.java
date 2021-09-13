package id.co.map.mapwebreportapplication.model.chart;

public class TransactionByMonth {
	
	private String period;
	private String channel;
	private String transactionType;
	private Integer numberOfTransaction;
	private Long amount;
	private String borderColor;
	
	public TransactionByMonth() {
		
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getNumberOfTransaction() {
		return numberOfTransaction;
	}

	public void setNumberOfTransaction(Integer numberOfTransaction) {
		this.numberOfTransaction = numberOfTransaction;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
}
