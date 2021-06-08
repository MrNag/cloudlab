import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
class AWSDB{
	public static void main(String[] args){
		String dbURL = "jdbc:mysql://newcmm1.c83armj3covj.us-west-2.rds.amazonaws.com:3306/newcmm1";
		String username = "root";
		String password = "manideep";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(dbURL, username, password);
			if (conn != null) {
				System.out.println("Connected");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		String sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
 
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, "bill");
		statement.setString(2, "secretpass");
		statement.setString(3, "Bill Gates");
		statement.setString(4, "bill.gates@microsoft.com");
		 
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("A new user was inserted successfully!");
		}
	}
}