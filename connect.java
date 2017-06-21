import java.sql.*;
public class TestExternal {
    public static void main(String[] args) throws SQLException {
	Connection con = DriverManger.getConnection(
		"jdbc:postgres://localhost:5432/gaba",
		"username",
		"password");
	System.out.println(conn);
    }
}
