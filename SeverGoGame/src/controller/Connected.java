package controller;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author alice
 */
public class Connected {
    protected Connection connect;
   
    public Connected(){
        final String Database = "GoGame";
        final String jdbcURL = "jdbc:mysql://localhost:3306/" + Database + "?useSSL=false";
        final String JDBC_User = "root";
        final String JDBC_Password = "@AdminSQL123";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = (Connection) DriverManager.getConnection(jdbcURL, JDBC_User, JDBC_Password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed");
        }
    }
}
