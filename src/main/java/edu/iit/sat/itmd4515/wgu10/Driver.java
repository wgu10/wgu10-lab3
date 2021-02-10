package edu.iit.sat.itmd4515.wgu10;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wenganGu    
 */
public class Driver {
    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/world?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
        String username = "itmd4515";
        String password = "itmd4515";
        String query = "select * from country where Code = ?";
        try{
            Connection c = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, "MEX");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LOG.info("data from database: " + rs.getString("Name"));
            }

            ps.setString(1, "drop table country; ");
            ResultSet rs2 = ps.executeQuery();
            while (rs2.next()) {
                LOG.info("data from database: " + rs2.getString("Name"));
            }
            rs.close();
            rs2.close();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, String.valueOf(ex));
        }
    }
}
