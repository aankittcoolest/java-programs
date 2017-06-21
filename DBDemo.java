import java.sql.*;
import java.util.*;
import java.util.Properties;

public class DBDemo
{
				// The JDBC Connector Class.
				private static final String dbClassName = "com.mysql.jdbc.Driver";

				// Connection string. whatever is the database the program
				// is connecting to. You can include user and password after this
				// by adding (say) ?user=root&password=root. Not recommended!

				private static final String CONNECTION =
								"jdbc:mysql://127.0.0.1/whatever";

				private static Connection con;

				public final static DBDemo instance;

				static {
								instance = new DBDemo();
				}

				public static void main(String[] args) throws SQLException
				{
								instance.testConnection();
								try {
												switch(args[0]) {
																case "testUpdate":  instance.testUpdate();break;
																case "testSelect":  instance.testSelect();break;
												}

								} finally {
												con.close();
								}
				}

				private void testConnection() {
								try {

												System.out.println(dbClassName);
												// Class.forName(xxx) loads the jdbc classes and
												// creates a drivermanager class factory
												Class.forName(dbClassName);

												// Properties for user and password. Here the user and password are both 'root'
												Properties p = new Properties();
												p.put("user","root");
												p.put("password","Gaba@123");

												// Now try to connect
												con = DriverManager.getConnection(CONNECTION,p);
												System.out.println("It works !");
								} catch(ClassNotFoundException | SQLException e) {
												System.out.println(e);
								}
				}

				//test CUD of yogurt!
				private void testUpdate() throws SQLException {
								Statement stmt = con.createStatement();

								//expected result 1
								int result = stmt.executeUpdate("INSERT INTO Employee VALUES(1,'Saiko', 25)");
								System.out.println(result);

								//expected result 0
								result = stmt.executeUpdate("UPDATE Employee SET name='error not found!' WHERE name='Sakurai'");
								System.out.println(result);

								//expected result 1
								result = stmt.executeUpdate("UPDATE Employee SET name='Forever 25!' WHERE name='Saiko'");
								System.out.println(result);

								//expected result 1
								result = stmt.executeUpdate("DELETE FROM Employee WHERE id = 1");
								System.out.println(result);
				}

				//test R of yogurt!
				private void testSelect() throws SQLException {

								instance.loadtestData();

								Map<Integer, String> idToNameMap = new HashMap<>();
								Statement stmt = con.createStatement();
								ResultSet rs = stmt.executeQuery("SELECT id, name from Employee");

								while(rs.next()) {
												int id = rs.getInt("id");
												String name = rs.getString("name");
												idToNameMap.put(id,name);
								}

								System.out.println(idToNameMap);

								instance.cleantestData();
				}

				private void loadtestData() throws SQLException {
								Statement stmt = con.createStatement();
								int result=0;

								result += stmt.executeUpdate("INSERT INTO Employee VALUES(1,'Saiko', 25)");
								result += stmt.executeUpdate("INSERT INTO Employee VALUES(2,'Lara', 25)");
								result += stmt.executeUpdate("INSERT INTO Employee VALUES(3,'Jaq', 25)");
								result += stmt.executeUpdate("INSERT INTO Employee VALUES(4,'Sakurai', 25)");
								result += stmt.executeUpdate("INSERT INTO Employee VALUES(5,'Ian', 25)");
								result += stmt.executeUpdate("INSERT INTO Employee VALUES(6,'Nagata', 25)");
								result += stmt.executeUpdate("INSERT INTO Employee VALUES(7,'Jun', 25)");

								System.out.println(result + " Results inserted successfully!");
				}

				private void cleantestData() throws SQLException {
								Statement stmt = con.createStatement();
								int result;

								result = stmt.executeUpdate("DELETE FROM Employee WHERE age = 25");
								System.out.println(result + " Results removed successfully!");
								System.out.println(result);
				}


}
