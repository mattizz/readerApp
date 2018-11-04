package pl.textreader;

import java.sql.*;

import static java.sql.Types.NULL;

public class ConnectionDB {

    private Connection connection = null;

    private static final String URL = "jdbc:mysql://localhost:3306/textreader?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static final String USER = "user";
    private static final String PASSWORD = "user";

    private static final String sqlToCustomerTable = "INSERT INTO customers(`name`, surname, age, city) VALUES (?, ?, ?, ?)";
    private static final String sqlToContactTable = "INSERT INTO contacts(id_customer, `type`, contact) VALUES (?, ?, ?)";

    private int lastInsertedId;

    public void tryConnect() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed connection");
        }
    }

    public void insertIntoCustomerTable(String name, String surname, String age, String city) throws SQLException {

        PreparedStatement pstForCustomer = this.connection.prepareStatement(sqlToCustomerTable, Statement.RETURN_GENERATED_KEYS);

        pstForCustomer.setString(1, name);
        pstForCustomer.setString(2, surname);

        if (age.isEmpty()) {
            pstForCustomer.setNull(3, NULL);
        } else {
            pstForCustomer.setInt(3, Integer.parseInt(age));
        }

        pstForCustomer.setString(4, city);

        pstForCustomer.executeUpdate();

        ResultSet result = pstForCustomer.getGeneratedKeys();
        if (result.next()) lastInsertedId = result.getInt(1);
    }

    public void insertIntoContactsTable(int typeOfContact, String contact) throws SQLException {

        PreparedStatement pstForContact = this.connection.prepareStatement(sqlToContactTable);

        pstForContact.setInt(1, lastInsertedId);
        pstForContact.setInt(2, typeOfContact);
        pstForContact.setString(3, contact);
        pstForContact.executeUpdate();
    }

    public Connection getConnection() {
        return connection;
    }
}
