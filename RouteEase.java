import java.io.*;
import java.util.*;
import java.sql.*;
public class RouteEase {

    public class Vertex {
        //store neighbour of each vertex , its name and wt(dist)
        HashMap<String, Integer> neighbours = new HashMap<>();
    }

    static HashMap<String, Vertex> vertices; //store each vertex having neighbour and wt

    public RouteEase() {
        vertices = new HashMap<>();
    }

    public int num_of_vertex() {
        return this.vertices.size();
    }

    public boolean containsVertex(String name) {
        return this.vertices.containsKey(name);
    }

    public void addVertex(String name) {
        Vertex vtx = new Vertex();
        vertices.put(name, vtx);
    }

   
    public void removeVertex(String name) {
        Vertex vtx = vertices.get(name);
        ArrayList<String> keys = new ArrayList<>(vtx.neighbours.keySet());

        for (String key : keys) {
            Vertex nbrVtx = vertices.get(key);
            nbrVtx.neighbours.remove(name);
        }

        vertices.remove(name);
    }

    public int numEdges() {
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int count = 0;

        for (String key : keys) {
            Vertex vtx = vertices.get(key);
            count = count + vtx.neighbours.size();
        }

        return count / 2;
    }

    public boolean containsEdge(String vname1, String vname2) {
        Vertex vtx1 = vertices.get(vname1);
        Vertex vtx2 = vertices.get(vname2);

        if (vtx1 == null || vtx2 == null || !vtx1.neighbours.containsKey(vname2)) {
            return false;
        }

        return true;
    }

    public void addEdge(String vname1, String vname2, int value) {
        Vertex vtx1 = vertices.get(vname1);
        Vertex vtx2 = vertices.get(vname2);

        if (vtx1 == null || vtx2 == null || vtx1.neighbours.containsKey(vname2)) {
            return;
        }

        vtx1.neighbours.put(vname2, value);
        vtx2.neighbours.put(vname1, value);
    }

    public void removeEdge(String vname1, String vname2) {
        Vertex vtx1 = vertices.get(vname1);
        Vertex vtx2 = vertices.get(vname2);

        // check if the vertices given or the edge between these vertices exist or not
        if (vtx1 == null || vtx2 == null || !vtx1.neighbours.containsKey(vname2)) {
            return;
        }

        vtx1.neighbours.remove(vname2);
        vtx2.neighbours.remove(vname1);
    }

    public void showMap()
    {
        System.out.println("\t Choose your fastest route now!!!");
        System.out.println("\t-----------------------\n");
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());//stores set of all vertices

        for(String k:keys)
        {
            String str=k+"-->\n";
            Vertex vtx = vertices.get(k);
            ArrayList<String> vrtx_nbrs = new ArrayList<>(vtx.neighbours.keySet()); // neighbours of particular vertex

            for(String nbr: vrtx_nbrs)
            {
                str=str+"\t"+nbr+"\t";
                if (nbr.length() < 16) //Adds 1 extra tab space for long strings
                    str = str + "\t";
                if (nbr.length() < 8) //Adds 2 extra tab space so that all align properly
                    str = str + "\t";
                str = str + vtx.neighbours.get(nbr) + "\n";
            }
            System.out.println(str);
        }
        System.out.println("\n------------------------------------------------------------------------------------------------------------\n");
    }

    public boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> visited)
    {
        if(containsEdge(vname1, vname2))
        return true;

        visited.put(vname1,true);

        Vertex vrtx=vertices.get(vname1);
        ArrayList<String> nbrs = new ArrayList<>(vrtx.neighbours.keySet());

        // traverse the nrighbours of the vertex
        for (String nbr : nbrs) {

            if (!visited.containsKey(nbr))
                if (hasPath(nbr, vname2, visited))
                    return true;
        }

        return false;
    }

    public void display_Stations()
    {
        System.out.println("\n--------------------------------------------------------------------------------------------------------------\n");
        System.out.println("LIST OF ALL LOCATIONS :-\n");
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int i = 1;
        for (String k : keys) {
            System.out.println(i + ". " + k);
            i++;
        }
        System.out.println("\n--------------------------------------------------------------------------------------------------------------\n");
    }

    private class DijkstraPair implements Comparable<DijkstraPair> { 
        String vname; // vertex name
        String psf;//path so far
        int cost; // cost to reach the vertex

        @Override
        public int compareTo(DijkstraPair o) { // to keep the min wt at top
            return o.cost - this.cost;
        }
    }
     

         public int dijkstra(String src,String des)
         {
            // if nan = true then find shortest time else shortest distance
            int value=0;
            ArrayList<String> visited=new ArrayList<>();
            HashMap<String,DijkstraPair> map=new HashMap<>(); //Priority Queue

            Heap<DijkstraPair> heap=new Heap<>();

            for (String key : vertices.keySet()) {
                DijkstraPair np = new DijkstraPair(); //np-new pair
                np.vname = key; 
                // np.psf = "";
                np.cost = Integer.MAX_VALUE; // first initialise all with infinity
    
                if (key.equals(src)) 
                { 
                    np.cost = 0;
                    np.psf = key;
                }
    
                heap.add(np); // added to priority que
                map.put(key, np); //name and each pair , and put all in map
            }
    
            // keep removing the pairs while heap(Priority queue) is not empty 
            while (!heap.isEmpty()) {
                DijkstraPair rp = heap.remove(); // rp=removed pair
    
                if (rp.vname.equals(des)) { // valid when the src is connected directly to dest
                    value = rp.cost;
                    break;
                }
    
                map.remove(rp.vname);
                visited.add(rp.vname); 
    
                Vertex v = vertices.get(rp.vname); //.....jagamara
                for (String nbr : v.neighbours.keySet()) {
                    if (map.containsKey(nbr)) //agar visited hai toh false hoga
                    { 
                        int oc = map.get(nbr).cost; //oc = old cost //5
                        int nc; // new cost
                        nc = rp.cost + v.neighbours.get(nbr);
    
                        if (nc < oc) {
                            DijkstraPair gp = map.get(nbr); // khandagiri
                            gp.psf = rp.psf + nbr; 
                            gp.cost = nc;
    //The updatePriority method in the Heap class should adjust the position of the element in the priority queue
    // based on its updated priority (in this case, the updated cost
                            heap.updatePriority(gp); 
                        }
                    }
                }
            }
            return value;
        }
        private class Pair {
            String vname;
            String psf;
            int min_dis;
            int min_time;
        }

        

        public float Get_Minimum_Time(String src, String dst)
        {
            int dist=dijkstra(src, dst);
            float speed=28.5f; // ideal speed
            float time=(dist/speed)*60;
            return time;
        }

    public String Get_Minimum_Distance(String src, String des)
        {
            int val=0;
            String path="";
            ArrayList<String> visited = new ArrayList<>(); // to store visited vertices
            HashMap<String, DijkstraPair> map = new HashMap<>(); //PQ
    
            Heap<DijkstraPair> heap = new Heap<>();
    
            for (String key : vertices.keySet()) {
                DijkstraPair np = new DijkstraPair(); 
                np.vname = key; 
                // np.psf = "";
                np.cost = Integer.MAX_VALUE; 
    
                if (key.equals(src)) 
                {
                    np.cost = 0;
                    np.psf = key+" -> ";
                }
    
                heap.add(np); // added to priority que
                map.put(key, np); //name and each pair , and put all in map
            }
    
            // keep removing the pairs while heap(Priority queue) is not empty 
            while (!heap.isEmpty()) {
                DijkstraPair rp = heap.remove(); // rp=removed pair
    
                if (rp.vname.equals(des)) 
                { 
                    // valid when the src is connected directly to dest
                    val = rp.cost;
                    path=rp.psf;
                    break;
                }
    
                map.remove(rp.vname);
    
                visited.add(rp.vname); //visited
    
                Vertex v = vertices.get(rp.vname); //.....jagamara
                for (String nbr : v.neighbours.keySet()) {
                    if (map.containsKey(nbr)) //agar visited hai toh false hoga
                    { 
                        int oc = map.get(nbr).cost; //oc = old cost //5
                        int nc; // new cost
                        nc = rp.cost + v.neighbours.get(nbr);
    
                        if (nc < oc) {
                            DijkstraPair gp = map.get(nbr); // khandagiri
                            gp.psf = rp.psf + nbr+" -> "; 
                            gp.cost = nc;
    //The updatePriority method in the Heap class should adjust the position of the element in the priority queue
    // based on its updated priority (in this case, the updated cost
                            heap.updatePriority(gp); 
                        }
                    }
                }
            }
            return path;
        }

    public ArrayList<String> get_Interchanges(String str) {
        ArrayList<String> arr = new ArrayList<>();
        String res[] = str.split("  ");
        arr.add(res[0]);
        int count = 0;
        for (int i = 1; i < res.length - 1; i++) {
            int index = res[i].indexOf('-');
            String s = res[i].substring(index + 1);

            if (s.length() == 2) {
                String prev = res[i - 1].substring(res[i - 1].indexOf('-') + 1);
                String next = res[i + 1].substring(res[i + 1].indexOf('-') + 1);

                if (prev.equals(next)) {
                    arr.add(res[i]);
                } else {
                    arr.add(res[i] + " ==> " + res[i + 1]);
                    i++;
                    count++;
                }
            } else {
                arr.add(res[i]);
            }
        }
        arr.add(Integer.toString(count));
        arr.add(res[res.length - 1]);
        return arr;
    }

    public static String[] printCodelist() {
        System.out.println("List of station along with their codes:\n");
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int i = 1, j = 0, m = 1;
        StringTokenizer stname;//station name
        String temp = "";
        String codes[] = new String[keys.size()];
        char c;
        for (String key : keys) {
            stname = new StringTokenizer(key);
            codes[i - 1] = "";
            j = 0;
            while (stname.hasMoreTokens()) {
                temp = stname.nextToken();
                c = temp.charAt(0);
                while (c > 47 && c < 58) {
                    codes[i - 1] += c;
                    j++;
                    c = temp.charAt(j);
                }
                if ((c < 48 || c > 57) && c < 123)
                    codes[i - 1] += c;
            }
            if (codes[i - 1].length() < 2)
                codes[i - 1] += Character.toUpperCase(temp.charAt(1));

            System.out.print(i + ". " + key + "\t");
            if (key.length() < (22 - m))
                System.out.print("\t");
            if (key.length() < (14 - m))
                System.out.print("\t");
            if (key.length() < (6 - m))
                System.out.print("\t");
            System.out.println(codes[i - 1]);
            i++;
            if (i == (int) Math.pow(10, m))
                m++;
        }
        return codes;
    }


    public static void Create_Map(RouteEase g) {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bhubaneswar_map", "root", "root");

            // Creating a statement
            Statement stmt = con.createStatement();

            // Executing the query to fetch data from the "movies" table
            ResultSet rs = stmt.executeQuery("SELECT * FROM vertices");

            while (rs.next()) {
                // System.out.println(rs.getString("vertex_name"));
                g.addVertex(rs.getString("vertex_name"));
            }

            //adding edges

            Statement stmt2 = con.createStatement();

            // Executing the query to fetch data from the "movies" table
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM edges");

            while (rs2.next()) {
                // System.out.println(rs.getString("vertex_name"));
                g.addEdge(rs2.getString("from_destination"),rs2.getString("to_destination"),rs2.getInt("edge_weight"));
            }
            
            // // Closing resources
            // rs.close();
            // stmt.close();
            // con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        
        // g.addVertex("Sum Hospital-SH");
        // g.addVertex("Patrapada-PD");
        // g.addVertex("Khandagiri-KH");
        // g.addVertex("Fire Station-FS");
        // g.addVertex("Jagamara-JG");
        // g.addVertex("Soubhagya Nagar-SN");
        // g.addVertex("Pokhariput-PKT");
        // g.addVertex("Airport-AP");
        // g.addVertex("Accountant General-AG");
        // g.addVertex("Lingaraj Temple-LGT");
        // g.addVertex("Jaydev Bihar-JB");
        // g.addVertex("Acharya Bihar-AB");
        // g.addVertex("Kalinga Hospital-KGH");

        // g.addEdge("Sum Hospital-SH", "Patrapada-PD", 8);
        // g.addEdge("Sum Hospital-SH", "Khandagiri-KH", 6); 
        // g.addEdge("Sum Hospital-SH", "Fire Station-FS", 4);
        // g.addEdge("Patrapada-PD", "Khandagiri-KH", 3);
        // g.addEdge("Patrapada-PD", "Jagamara-JG", 5);
        // g.addEdge("Khandagiri-KH", "Fire Station-FS", 3);
        // g.addEdge("Khandagiri-KH", "Jagamara-JG", 2);
        // g.addEdge("Fire Station-FS", "Soubhagya Nagar-SN", 1);
        // g.addEdge("Fire Station-FS", "Jaydev Bihar-JB", 4);
        // g.addEdge("Jagamara-JG", "Soubhagya Nagar-SN", 4);
        // g.addEdge("Jagamara-JG", "Pokhariput-PKT", 4);
        // g.addEdge("Soubhagya Nagar-SN", "Airport-AP", 3);
        // g.addEdge("Pokhariput-PKT", "Lingaraj Temple-LGT", 5);
        // g.addEdge("Pokhariput-PKT", "Airport-AP", 3);
        // g.addEdge("Airport-AP", "Accountant General-AG", 2);
        // g.addEdge("Airport-AP", "Lingaraj Temple-LGT", 5);
        // g.addEdge("Accountant General-AG", "Acharya Bihar-AB", 3);
        // g.addEdge("Accountant General-AG", "Jaydev Bihar-JB", 4);
        // g.addEdge("Accountant General-AG", "Lingaraj Temple-LGT", 6);
        // g.addEdge("Lingaraj Temple-LGT", "Acharya Bihar-AB", 8);
        // g.addEdge("Jaydev Bihar-JB", "Kalinga Hospital-KGH", 5);
        // g.addEdge("Jaydev Bihar-JB", "Acharya Bihar-AB", 1);
        // g.addEdge("Acharya Bihar-AB", "Kalinga Hospital-KGH", 4);

    }

    public static void main(String[] args) throws IOException 
    {
        RouteEase g = new RouteEase();
        Create_Map(g);

        g.showMap();

        System.out.println("\n\t\t\t----------GET YOUR EASY ROUTE NOW!!----------\n");
        Scanner sc=new Scanner(System.in);
        while (true)
        {
            System.out.println("\t\t\t\t-------LIST OF ACTIONS-------\n\n");
            System.out.println("1) LIST ALL LOCATIONS");
            System.out.println("2) SHOW THE MAP");
            System.out.println("3) GET SHORTEST DISTANCE FROM  'SOURCE' LOCATION TO 'DESTINATION' LOCATION");
            System.out.println("4) GET SHORTEST TIME TO REACH FROM A 'SOURCE' LOCATION TO 'DESTINATION' LOCATION");
            System.out.println(
                    "5) GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' LOCATION");
            System.out.println("6) EXIT FROM THE MENU");
            System.out.print("\nPLEASE ENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 7) : ");
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                // default will handle
            }
            System.out.print("\n----------------------------------------------------------------------------------------------------------\n");
            if (choice == 7) {
                System.exit(0);
            }
            switch (choice) {
                case 1:
                    g.display_Stations();
                    break;

                case 2:
                    g.showMap();
                    break;

                case 3:
                    ArrayList<String> keys = new ArrayList<>(vertices.keySet());
                    String codes[] = printCodelist();
                    System.out.println(
                            "\n1. TO ENTER SERIAL NO. OF LOCATION\n2. TO ENTER CODE OF LOCATION\n3. TO ENTER NAME OF LOCATION\n");
                    System.out.println("ENTER YOUR CHOICE:");
                    int ch = Integer.parseInt(sc.nextLine());
                    int j;

                    String st1 = "", st2 = "";
                    System.out.println("ENTER THE SOURCE AND DESTINATION LOCATION");
                    if (ch == 1) {
                        st1 = keys.get(Integer.parseInt(sc.nextLine()) - 1);
                        st2 = keys.get(Integer.parseInt(sc.nextLine()) - 1);
                    } else if (ch == 2) {
                        String a, b;
                        a = (sc.nextLine()).toUpperCase();
                        for (j = 0; j < keys.size(); j++)
                            if (a.equals(codes[j]))
                                break;
                        st1 = keys.get(j);
                        b = (sc.nextLine()).toUpperCase();
                        for (j = 0; j < keys.size(); j++)
                            if (b.equals(codes[j]))
                                break;
                        st2 = keys.get(j);
                    } else if (ch == 3) {
                        st1 = sc.nextLine();
                        st2 = sc.nextLine();
                    } else {
                        System.out.println("Invalid choice");
                        System.exit(0);
                    }

                    HashMap<String, Boolean> processed = new HashMap<>();
                    if (!g.containsVertex(st1) || !g.containsVertex(st2) || !g.hasPath(st1, st2, processed))
                        System.out.println("THE INPUTS ARE INVALID");
                    else
                        System.out.println("SHORTEST DISTANCE FROM " + st1 + " TO " + st2 + " IS "
                                + g.dijkstra(st1, st2) + "KM\n");
                    break;

                case 4:
                    System.out.print("ENTER THE SOURCE LOCATION:- ");
                    String sat1 = sc.nextLine();
                    System.out.print("ENTER THE DESTINATION LOCATION:- ");
                    String sat2 = sc.nextLine();

                    HashMap<String, Boolean> processed1 = new HashMap<>();
                    System.out.println("SHORTEST TIME FROM (" + sat1 + ") TO (" + sat2 + ") IS "
                            + g.Get_Minimum_Time(sat1, sat2) + " MINUTES\n\n");
                    break;

                case 5:
                    System.out.println("ENTER THE SOURCE AND DESTINATION LOCATION");
                    String s1 = sc.nextLine();
                    String s2 = sc.nextLine();

                    HashMap<String, Boolean> processed2 = new HashMap<>();
                    if (!g.containsVertex(s1) || !g.containsVertex(s2) || !g.hasPath(s1, s2, processed2))
                        System.out.println("THE INPUTS ARE INVALID");
                    else {
                        ArrayList<String> str = g.get_Interchanges(g.Get_Minimum_Distance(s1, s2));
                        int len = str.size();
                        System.out.println("SOURCE LOCATION : " + s1);
                        System.out.println("DESTINATION LOCATION : " + s2);
                        System.out.println("SHORTEST PATH : " + str.get(len - 1)+"END");
                        for (int i = len/2; i < len; i++) {
                           // System.out.println(str.get(i));
                        }
                        System.out.println("\n----------------------------------------------------------------------------------");
                    }
                    break;
                    default: 
                    System.out.println("Please enter a valid option! ");
                    System.out.println("Please choose the options from 1 to 5");

            }
        }

    }
}
