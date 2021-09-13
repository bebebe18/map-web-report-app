package id.co.map.mapwebreportapplication.model;

public class ReturnDetailsByChannel {

	private String warehouse;
	private String channel;
	private String createDate;
	private String salesReturnNumber;
	private String returnItemNumber;
	private String returnItemName;
	private Integer returnQty;
	private Long salesPrice;
	private Long discount;
	private Long localAmount;
	private String orderDate;
	private String eCommOrderId;
	
	public ReturnDetailsByChannel() {
		
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSalesReturnNumber() {
		return salesReturnNumber;
	}

	public void setSalesReturnNumber(String salesReturnNumber) {
		this.salesReturnNumber = salesReturnNumber;
	}

	public String getReturnItemNumber() {
		return returnItemNumber;
	}

	public void setReturnItemNumber(String returnItemNumber) {
		this.returnItemNumber = returnItemNumber;
	}

	public String getReturnItemName() {
		return returnItemName;
	}

	public void setReturnItemName(String returnItemName) {
		this.returnItemName = returnItemName;
	}

	public Integer getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	public Long getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Long salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getLocalAmount() {
		return localAmount;
	}
	
	public void setLocalAmount(Long localAmount) {
		this.localAmount = localAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String geteCommOrderId() {
		return eCommOrderId;
	}
	public void seteCommOrderId(String eCommOrderId) {
		this.eCommOrderId = eCommOrderId;
	}
	

}
