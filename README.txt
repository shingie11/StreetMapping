README
CSC 172: Data Structures and Algorithms
Project 4: Street Mapping
Author: Shingirai Dhoro
Email: sdhoro@u.rochester.edu
Partner: None


Files Included: Files: Graph.java, Node.java, Edge.java, SteetMapping.java, MapInterface.java

Description of the Code:

Abstract:
The code implements the requirements of project 3, Street mapping. It takes in arguments in command line (please make sure javafx is enabled for command line for this to work) and put them in an ArrayList of arguments. After that, it determines what needs to be done, that is what the commands in the ArrayList are. If you want to show a map, the code will create a map object and show a really beautiful map. To find the shortest path between two nodes, I implemented Dijkstra's algorithm in the shortestPath method. The code prints whether or not the path is found and if it is found, it prints the distance between the nodes and the actual path followed to go from source to target node. In the next paragraphs, I am going to discuss the classes included in this file and their most important methods.


1. Graph.class
This class implements the graph with the following attributes:
private HashMap<Pair<String, String>, Edge> edges; //this is an HashMap of edges and a pair of nodes that are connected by that HashMap\
private HashMap<String, ArrayList<String>> vertexes; // this is a HashMap of vertexes that stores the adjacency list of the nodes that are connected to the current node
private HashMap<String, Node > intersections; // hashmap of all the intersections in the graph

The methods in this class are as follows:
Graph() // class construtor
public HashMap<String, Node> getAllIntersections() // returns the hashmap of all the intersections in graph
public Node getIntersection(String intersection) // returns the specified intersection in argument
public void addInterSection(String ID, double latitude, double longitude) //adds an intersection in the graph/map
public ArrayList<String> getNeighbors(String intersection) // gets the neighbors of the given intersection
public HashMap<Pair<String, String>, Edge> getEdges() // returns hashmap of  edges
public void addEdge(String edgeID, String source, String target) 
public Edge getEdge(String source, String target) // returns the edge between two nodes (intersections)
public double getDistance(String source, String target) // returns the weight between two nodes (intersections)
public int nquantity() // returns number of nodes
public int equantity() // returns number of edges


2. Edge.class
This class implements an Edge in the graph with the following attributes:
private  String EdgeID; //edge ID that identifies which which edge it is
private double weight; // weight of the edge
private Node source; //source node of edge
private Node target ; //target node of edge


The class has the following methods:
Edge(String ID, Node src, Node trgt) // class constructor, intiates edge with its ID, source and target node
private double distance(double sourceLat, double targetLat, double sourceLongi, double targetLongi) // calculates the distance between two nodes (length of edge), harvesine formula. 
*Source of Harvesine formula is https://en.wikipedia.org/wiki/Haversine_formula. 

**This version, the harvesine formula is commented out, and I'm using eauclidean & Manhattan heuristic to calculate the shortest path.

public String getEdgeID() //returns EdgeID
public double getWeight() //returns edge weight
public Node getSource() //returns source node
public Node getTarget() //returns target node

3. Node.class
This class implements node/intersection with the following attributes:
private String NodeID; // this is the intersection ID
private boolean visited; //indicates whether the intersection has been visited or not when we are doing Dijkstra's Algorithm
private double longitude;   //longitude of the node
private double latitude; //latitude of the node

The class has the following member methods:
Node (String IntersectionID, double lati, double longi) //class constructor. Initiates node with its ID, and coordinates. visited status is initially false
public boolean wasVisited()//returns whether or not the intersection was visited
public void setVisited(boolean status) // changes the visited status
public double getLongitude() //returns longitude of the intersection
public double getLatitude()//returns latitude of the intersection
public String getNodeID()//returns node ID

4. MapInterface.class
This class implements the graphics to draw the map. This was the most difficult part of the project and I worked hard on it to create a beautiful map which I believe deserves extra credit. 

The class has the following attributes:
private HashMap<String, Node> intersections; // hashmap of intersections
private HashMap<Pair<String, String>, Edge> edges; //hashmap of edges
private ArrayList<String> path; // arraylists of path from source to target
private double minLongitude, maxLongitude; //min and max for longitude
private double minLatitude, maxLatitude; //min and max for longitude
private double latitudeScaler, longitudeScaler; // scalers to make sure map fits in window

The class has the following memeber methods:
Constructor MapGUI(Graph G, ArrayList<String> path) // class constructor, initiliazes private variables.
public void paintComponent(Graphics p) // this is the method that prints the map. It first sets the parameters by finding 
minimum and maximum latitide and longitudes, and their scaling factors.It then goes though all the edges in the graph and draws them on the map in white color if the length is less than 0.2 miles. If the length of the road is longer than 0.2 miles, the method draws the edge in green color. If needed, it also draws the shortest path between source and target node. This path is thicker and in mangenta color.
public void setParameters() //goes through  all the intersections and find the ones with minimum and 
maximum latitudes and longitudes, and scaling factor.

5. StreetMap.class
This is the main class that contains the driver method. In this class, we have 2 methods which are briefly described below:

private static Pair<Double, ArrayList<String>> shortestPath(Graph G, String source, String target) // This method implements A* algorithm to find the shortest path between and target node in graph G. It returns a pair of the distance of the shortest path and an ArrayList containing the actual shortest path. The algorithm made use of a hashmap to store the cost of each path so as to find the minimum path and I also added a hashmap to store the prev node to store the previous node on a path. This was useful for calculating the actual shortest path. I implemented this algorithm using a priority queue, with a custom comparator which implements priority by checking the cost/weight hashmap. I dequeued a node, mark it visited and enqueue all of it's unvisited neighbors to the queue. At each dequeue, if the node is a target, then we stop there, get the weight/cost from source and the path.
	
private static ArrayList<String> getPath(HashMap<String, String> prev, String source, String target) // this is the method that calculate the actual path. It takes the HashMap storing the parent of each node and intersection ID of each node and returns the ArrayList of intersection IDs taken in the shortest path.

6. Complexity Analysis:
Map time Complexity = O(N) // where N is number of edges since we iterate over all edges
Dijkstra's Complexity = O(Nlong(M)) //where N is number of edges and M is the number of vertexes, since i used the adjacency list implementation method. This is a known result.



