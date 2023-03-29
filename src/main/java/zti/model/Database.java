package zti.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

@ManagedBean(name = "databaseBean")
@RequestScoped
public class Database {

    private Person person;
    private Connection conn = null;
    private PreparedStatement prestmt = null;
    private Statement stmt = null;
    private ResultSet rset = null;
    private List<Person> list = new ArrayList<Person>();

    public Database() {
        // System.out.println("Init managed Bean");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        String url = "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vppbhvzu";
        String username = "vppbhvzu";
        String password = "jSQW8UyiwBWLu9xSsjYnRzV3NLU3J8Xs";
        try {
            conn = DriverManager.getConnection(url, username, password);
            // System.out.println("Connect to Database");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Person> getPersonList() {
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM public.person ORDER BY lname";
            rset = stmt.executeQuery(sql);
            while (rset.next()) {
                person = new Person();
                person.setFname(rset.getString("fname"));
                person.setLname(rset.getString("lname"));
                person.setEmail(rset.getString("email"));
                person.setTel(rset.getString("tel"));
                person.setCity(rset.getString("city"));
                person.setId(rset.getInt("id"));
                list.add(person);
            }
            rset.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (prestmt != null) {
                    prestmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return list;
    }

}