package id.co.map.mapwebreportapplication.model;


public class TransactionDetailsByChannel {
	
	private String warehouse;
	private String channel;
	private String createDate;
	private String invoiceNumber;
	private String itemNumber;
	private String itemName;
	private Integer invoiceQuantity;
	private Long salesPrice;
	private Long discount;
	private Long localAmount;
	private String orderDate;
	private String eCommOrderId;
	
	public TransactionDetailsByChannel() {
		
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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getInvoiceQuantity() {
		return invoiceQuantity;
	}

	public void setInvoiceQuantity(Integer invoiceQuantity) {
		this.invoiceQuantity = invoiceQuantity;
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
