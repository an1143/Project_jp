import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//EUC-KR

public class AppDB {

	static Connection conn = null;
	static Statement stmt = null;
	
	
	
	
	//DB ½ÃÀÛ½Ã Àü ¸ñ·Ï ºÒ·¯¿È.
	//DB¡¡ª¼ªóªâª¯ªíª¯¡¡û¼ªÖ
	public static void DB() {
		
		Connection conn = null;
		java.sql.Statement stmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://121.147.26.119:3306/project?useSSL=false&serverTimezone=UTC","an", "a1641418");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select name, phone, regin, address, product, amount from project ORDER BY name ASC;");
	
			
			
			while(rs.next()) {
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String regin = rs.getString("regin");
				String address = rs.getString("address");
				String product = rs.getString("product");
				int amount = rs.getInt("amount");
			
				User user = new User();
				user.setName(name);
				user.setPhone(phone);
				user.setRegin(regin);
				user.setAddress(address);
				user.setProduct(product);
				user.setAmount(amount);
			 
				AppController.list.add(user);
			}
			
			
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			
			System.out.println("classª¬ªÊª¤ªÇª¹."+e.getMessage());
		}
	
		catch (SQLException e2) {
			// TODO: handle exception
			System.out.println(e2.getMessage());
			 
          
			               
			
		}
		
		finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		
		}
		
	
	
	
	public static void AddDB() {
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unused")
	private static String toUnicode(String str){
		try {
			byte[] b = str.getBytes("EUC-KR");
			return new String(b);
			
		} catch (java.io.UnsupportedEncodingException uee) {
			System.out.println(uee.getMessage());
			return null;
		}

	
}

}


