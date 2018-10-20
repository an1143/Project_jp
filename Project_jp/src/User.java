import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
//EUC-KR
public class User {
	private SimpleStringProperty name ;
	private SimpleStringProperty phone;
	private SimpleStringProperty regin;
	private SimpleStringProperty address;
	private SimpleStringProperty product;
	private SimpleIntegerProperty amount;
	
	
	
	
	public User() {
		this.name = new SimpleStringProperty();
		this.phone = new SimpleStringProperty();
		this.regin = new SimpleStringProperty();
		this.address = new SimpleStringProperty();
		this.product = new SimpleStringProperty();
		this.amount = new SimpleIntegerProperty();
		
	}
	
	
	
	
	public User(String name, String phone, String regin,String address, String product ,int amount) {
		this.name = new SimpleStringProperty(name);
		this.phone = new SimpleStringProperty(phone);
		this.regin = new SimpleStringProperty(regin);
		this.address = new SimpleStringProperty(address);
		this.product = new SimpleStringProperty(product);
		this.amount = new SimpleIntegerProperty(amount);	
	}
	
	
	public String getName() {
		return this.name.get();
	}
	
   public void setName(String name) {
		 this.name.set(name);
	 }
	
   
   
	public String getPhone() {
		return this.phone.get();
	}
	
   public void setPhone(String phone) {
		 this.phone.set(phone);
	 }
		
   
   
   
	public String getRegin() {
		return this.regin.get();
	}
	
   public void setRegin(String regin) {
		 this.regin.set(regin);
	 }
		
   
   
   
   
	public String getAddress() {
		return this.address.get();
	}
	
   public void setAddress(String address) {
		 this.address.set(address);
	 }
		
		
   
	public String getProduct() {
		return this.product.get();
	}
	
   public void setProduct(String product) {
		 this.product.set(product);
	 }
		
   
   
   
	public int getAmount() {
		return this.amount.get();
	}
	
    public void setAmount(int price) {
		 this.amount.set(price);
	 }
   
   
		
}
