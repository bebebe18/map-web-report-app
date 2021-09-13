package id.co.map.mapwebreportapplication.model;

public class ReturnsByChannel {
	
	private String createDate;
	private String channel;
	private String warehouse;
	private String eCommOrderId;
	private String salesReturnNumber;
	private Integer qty;
	private Long salesPrice;
	private Long localAmount;
	private String orderDate;
	private String paymentMode;
	
	public ReturnsByChannel() {
		
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String geteCommOrderId() {
		return eCommOrderId;
	}

	public void seteCommOrderId(String eCommOrderId) {
		this.eCommOrderId = eCommOrderId;
	}

	public String getSalesReturnNumber() {
		return salesReturnNumber;
	}

	public void setSalesReturnNumber(String salesReturnNumber) {
		this.salesReturnNumber = salesReturnNumber;
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

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
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
