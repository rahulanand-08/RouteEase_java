import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class InsertDB {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bhubaneswar_Map","root","Rahul@2808");
		System.out.println("Now enter vertices to add to the table VERTICES of database bhubaneswarMap");
        System.out.println("Do you want to add vertices?");
        String flagInput = sc.nextLine().toLowerCase(); // Read input as a string and convert to lowercase
        boolean flag = flagInput.equals("true"); // Check if input is "true"
        
        while (flag) {
            PreparedStatement pt = con.prepareStatement("INSERT INTO vertices (vertex_name) VALUES (?)");
        
            // Assuming the first input is a String
            System.out.print("Enter the vertex name (String): ");
            String s1 = sc.nextLine();
            pt.setString(1, s1);
        
            int i = pt.executeUpdate();
            if (i > 0) {
                System.out.println("Vertex added successfully.");
            }
        
            // Clear the buffer and read the next boolean value
            System.out.print("Do you want to continue? (true/false): ");
            flagInput = sc.nextLine().toLowerCase(); // Read input as a string and convert to lowercase
            flag = flagInput.equals("true"); // Check if input is "true"
        }
           
        System.out.println("Vertices are succesfully added to the table VERTICES of database bhubaneswarMap");
        System.out.println("Now enter edges of the vertex to add to the table EDGES of database bhubaneswarMap");
        boolean flag2 = true;
        while (flag2) {
            System.out.print("Do you want to continue? (true/false): ");
            flag2 = sc.nextBoolean();
            
            if (!flag2)
                break;

            PreparedStatement pt = con.prepareStatement("INSERT INTO edges VALUES (?, ?, ?)");

            // Assuming the first input is a String
            System.out.print("Enter from destination (String): ");
            // Consume the newline character
            sc.nextLine();
            String s1 = sc.nextLine();
            pt.setString(1, s1);

            System.out.print("Enter to destination (String): ");
            String s2 = sc.nextLine();
            pt.setString(2, s2);

            System.out.print("Enter weight of the edge (int): ");
            int wt = sc.nextInt();
            pt.setInt(3, wt);

            // Consume the newline character
            sc.nextLine();

            int i = pt.executeUpdate();
            if (i > 0) {
                System.out.println("Edge added successfully.");
            }
        }

    }
}
