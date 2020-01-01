
package jsp_azure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataHandler {
	private Connection conn;
	// Azure SQL connection credentials
	private String server = "113380402-sql-server.database.windows.net";
	private String database = "cs-dsa-4513-sql-db";
	private String username = "ZAKI-113380402";
	private String password = "Sims31997^";
	// Resulting connection string final private

	String url = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustS erverCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
			server, database, username, password);

	// Initialize and save the database connection
	private void getDBConnection() throws SQLException, ClassNotFoundException {
		if (conn != null) {
			return;
		}
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		this.conn = DriverManager.getConnection(url);
	}

	public boolean addCustomer(String customerName, String customerAddress, int customerCategory)
			throws SQLException, ClassNotFoundException {
		getDBConnection(); // Prepare the database connection
		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Customer " + "(customer_name, customer_address, customer_category)"
				+ "VALUES " + "(?, ?, ?)";

		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		System.out.println();
		System.out.println(customerName + " " + customerAddress + " " + customerCategory);
		// Replace the '?' in the above statement with the given attribute values
		stmt.setString(1, customerName);
		stmt.setString(2, customerAddress);
		stmt.setInt(3, customerCategory);
		// Execute the query, if only one record is updated, then we indicate success by
		// returning true
		return stmt.executeUpdate() == 1;
	}

	// Get customers in range
	public ResultSet getCustomersInRange(int range1, int range2) throws SQLException, ClassNotFoundException {
		getDBConnection();

		final String sqlQuery = "SELECT * FROM Customer WHERE customer_category BETWEEN ? AND ?";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		// Replace ? with range numbers
		stmt.setInt(1, range1);
		stmt.setInt(2, range2);

		return stmt.executeQuery();
	}
}
