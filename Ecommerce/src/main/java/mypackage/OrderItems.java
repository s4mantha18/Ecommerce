package mypackage;

public class OrderItems {
	int orderid;
    int productid;
    String name;
    Double price;
    int quantity;
    String size;
    int userid;
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "OrderItems [orderid=" + orderid + ", productid=" + productid + ", name=" + name + ", price=" + price
				+ ", quantity=" + quantity + ", size=" + size + ", userid=" + userid + "]";
	}
	public OrderItems(int orderid, int productid, String name, Double price, int quantity, String size, int userid) {
		super();
		this.orderid = orderid;
		this.productid = productid;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.size = size;
		this.userid = userid;
	}
    
    
	
}
