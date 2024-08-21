package mypackage;

public class Order {
	int userid;
	String email;
	String address;
	String phone;
	Double Totalamount;
	String createdat;
	String status;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getTotalamount() {
		return Totalamount;
	}
	public void setTotalamount(Double totalamount) {
		Totalamount = totalamount;
	}
	public String getCreatedat() {
		return createdat;
	}
	public void setCreatedat(String createdat) {
		this.createdat = createdat;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order [userid=" + userid + ", email=" + email + ", address=" + address + ", phone=" + phone
				+ ", Totalamount=" + Totalamount + ", createdat=" + createdat + ", status=" + status + "]";
	}
	public Order(int userid, String email, String address, String phone, Double totalamount, String createdat,
			String status) {
		super();
		this.userid = userid;
		this.email = email;
		this.address = address;
		this.phone = phone;
		Totalamount = totalamount;
		this.createdat = createdat;
		this.status = status;
	}
	
	

}
