package org.mifos.chatbot.database;

import java.sql.*;

import org.mifos.chatbot.core.model.DatabaseSettings;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseHandler {

    @Autowire
    private DatabaseSettings databaseSettings;

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mifos_chatbot", "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users_creds");
            while (rs.next())
                System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4) + "  " + rs.getString(5) + "  " + rs.getString(6));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
