import java.sql.*;
import java.util.Scanner;

public class FetchDB {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/laptop", "root", "root");

            // Creating a statement
            Statement stmt = con.createStatement();

            // Executing the query to fetch data from the "movies" table
            ResultSet rs = stmt.executeQuery("SELECT * FROM laptop");

            // Printing the data from the result set
            System.out.println("laptops:");
            System.out.println("Name\tAge");
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name + "\t" + age);
            }

            // Closing resources
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
