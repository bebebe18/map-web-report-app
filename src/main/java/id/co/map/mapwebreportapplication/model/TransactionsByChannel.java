package id.co.map.mapwebreportapplication.model;

public class TransactionsByChannel {

	private String channel;
	private String warehouse;
	private String eCommOrderId;
	private String invoiceNumber;
	private Integer qty;
	private Long salesPrice;
	private Long localAmount;
	private String createDate;
	private String orderDate;
	private String paymentMode;
	
	public TransactionsByChannel() {
		
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String geteCommOrderId() {
		return eCommOrderId;
	}

	public void seteCommOrderId(String eCommOrderId) {
		this.eCommOrderId = eCommOrderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Long getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Long getLocalAmount() {
		return localAmount;
	}

	public void setLocalAmount(Long localAmount) {
		this.localAmount = localAmount;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
		
	public String getPaymentMode() {
		return paymentMode;
	}
	
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
}
