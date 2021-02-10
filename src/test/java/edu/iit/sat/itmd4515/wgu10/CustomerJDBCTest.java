package edu.iit.sat.itmd4515.wgu10;

import edu.iit.sat.itmd4515.wgu10.Domain.Customer;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wenganGu
 */
public class CustomerJDBCTest {

    private Connection connection;

    // helper utility methods - to help us DRY (don't repeat yourself, at least
    // not in your code.  Repeat yourself all you want in your speech
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Chinook?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
        String username = "itmd4515";
        String password = "itmd4515";
        return DriverManager.getConnection(url, username, password);
    }

    private void createACustomer(Customer c) throws SQLException {

        String INSERT_SQL = "insert into Customer "
                + "(CustomerId, FirstName, LastName, Email) "
                + "values (?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
        ps.setInt(1, c.getId());
        ps.setString(2, c.getFirstName());
        ps.setString(3, c.getLastName());
        ps.setString(4, c.getEmail());

        ps.executeUpdate();


    }

    // returns either null, or the valid customer found in the database
    private Customer findACustomer(Integer id) throws SQLException {
        Customer c = null;
        String FIND_SQL = "select * from customer where CustomerId = ?";

        PreparedStatement ps = connection.prepareStatement(FIND_SQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            c = new Customer();
            c.setId(rs.getInt("CustomerId"));
            c.setFirstName(rs.getString("FirstName"));
            c.setLastName(rs.getString("LastName"));
            c.setEmail(rs.getString("Email"));
        }

        return c;

    }

    private void readFromDatabaseAfterUpdate(Customer c) throws SQLException {
        String UPDATE_SQL = "update customer set "
                + "FirstName = ?, "
                + "LastName = ?, "
                + "Email = ? "
                + "where CustomerId = ?";

        PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
        ps.setString(1, c.getFirstName());
        ps.setString(2, c.getLastName());
        ps.setString(3, c.getEmail());
        ps.setInt(4, c.getId());

        ps.executeUpdate();

    }

    private void deleteACustomer(Integer id) throws SQLException {
        String DELETE_SQL = "delete from Customer where CustomerId = ?";
        PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    @BeforeAll
    public static void beforeAll() {

    }

    @AfterAll
    public static void afterAll() {

    }

    // before each test, you can stage any test data you need to work with
    // you won't want to rely on the data in the tables, because the next time
    // you run your test, it might be different
    @BeforeEach
    public void beforeEach() throws SQLException {
        connection = getConnection();
        Customer c = new Customer(9999, "Scott", "Spyrison", "spyrison@iit.edu");
        createACustomer(c);
    }

    // after each test, you can clean up any test data that you staged earlier
    @AfterEach
    public void afterEach() throws SQLException {
        deleteACustomer(9999);
        connection.close();
    }

    // testing C R U D
    //
    // you never want test methods to depend on one another!
    // don't create data in the createTest, to test it with the readTest!
    // test methods should be atomic!
    @Test
    public void createCustomerTest() throws SQLException {
        // this is test data I am staging within this method
        Customer customerToCreate = new Customer(9998, "Test", "Method", "sspyriso@iit.edu");
        createACustomer(customerToCreate);

        // now that I've created the customer - I need to find it, and assert
        // the create was successful
        Customer foundInDatabase = findACustomer(9998);
        assertNotNull(foundInDatabase);
        assertEquals(customerToCreate.getId(), foundInDatabase.getId());
        assertEquals(customerToCreate.getFirstName(), foundInDatabase.getFirstName());
        assertEquals(customerToCreate.getLastName(), foundInDatabase.getLastName());
        assertEquals(customerToCreate.getEmail(), foundInDatabase.getEmail());

        System.out.println(foundInDatabase.toString());

        // clean up the data I staged within this method
        deleteACustomer(9998);
    }

    @Test
    public void readCustomerTest() throws SQLException {
        Customer readFromDatabase = findACustomer(9999);
        assertNotNull(readFromDatabase);
        assertEquals(9999, readFromDatabase.getId());
    }

    @Test
    public void updateCustomerTest() throws SQLException {
        Customer readFromDatabaseBeforeUpdate = findACustomer(9999);
        readFromDatabaseBeforeUpdate.setLastName("UpdatedSuccessfully");
        readFromDatabaseAfterUpdate(readFromDatabaseBeforeUpdate);

        Customer updatedInDatabase = findACustomer(9999);
        assertEquals(readFromDatabaseBeforeUpdate.getLastName(),updatedInDatabase.getLastName());
    }

    @Test
    public void deleteCustomerTest() throws SQLException {
        // this is test data I am staging within this method
        Customer customerToDelete = new Customer(9998, "Test", "Method", "sspyriso@iit.edu");
        createACustomer(customerToDelete);

        // now that I've created the customer - I need to delete it, and then
        // try to re-find it, in order to assert that I can not - and therefore
        // it has been successfully deleted
        deleteACustomer(9998);
        Customer tryAndFindDeletedCustomerInDatabase = findACustomer(9998);
        assertNull(tryAndFindDeletedCustomerInDatabase);
    }

}