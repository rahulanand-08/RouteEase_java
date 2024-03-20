# RouteEase_Find_Your_Shortest_Route

## Project Description:

### Title: RouteEase - Route Optimization System

### Introduction: 

RouteEase is a Java-based application designed to facilitate users in finding the optimal routes within a map. It leverages graph theory algorithms, specifically Dijkstra's algorithm, to compute the shortest path and time between two given locations. The system provides functionalities for adding, removing, and displaying locations and their connections, allowing users to interactively explore the map. Additionally, it offers features to compute both the shortest distance and time between locations, catering to users' preferences. JDBC has been used to establish a connection between the code and the database.

### Features:

1. Graph Representation: The application models the map as an undirected graph, with each location represented as a vertex and connections between locations represented as edges.
   
2. Add and Remove Stations: Users can dynamically add or remove locations from the map, allowing for the customization and flexibility of the system to accommodate changes in the map.
   
3. Display Map: RouteEase provides a visual representation of the map, showcasing the connections between stations and the distances associated with each connection. This feature enables users to visualize the entire map layout.
   
4. Shortest Distance Calculation: Using Dijkstra's algorithm, the system computes the shortest distance between two given locations, helping users plan their journeys efficiently based on distance preferences.
   
5. Shortest Time Calculation: RouteEase calculates the shortest time required to travel between two locations, considering the ideal speed of the vehicle. This feature assists users in planning their journeys based on time constraints.
   
6. Path Visualization: Upon computing the shortest distance between locations, the application displays the optimal path, highlighting the sequence of stations to traverse. Users can easily follow this path to reach their destination.
   
7. User-Friendly Interface: The application offers a user-friendly interface with menu-driven options, allowing users to interact with the system seamlessly. Users can input their preferences and receive relevant information quickly.

### Usage Scenario:

Suppose a person in a city wishes to travel from one location to another while optimizing either the distance or time taken for the journey. They can utilize RouteEase to plan their route efficiently. By inputting their source and destination locations, along with their preference for either the shortest distance or time, the person can obtain detailed information about the optimal route, including interchange stations and expected travel duration. This helps the commuter make informed decisions and navigate the metro network effectively.

### Prerequisites:

1. Java
2. Database (SQL)
3. JDBC
