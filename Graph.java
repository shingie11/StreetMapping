/*
Author: Shingirai Dhoro
Email: sdhoro@u.rochester.edu
 */
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

/** Graph Class implements the graph*/
public class Graph {

    private HashMap<Pair<String, String>, Edge> edges; //hashmap of edges
    private HashMap<String, ArrayList<String>> vertexes;  //hashmap of vertices (neighbors)
    private HashMap<String, Node > intersections; //hashmap of nodes

    /** class constructor */
    Graph() {
        vertexes = new HashMap<String, ArrayList<String>>();
        edges = new HashMap<>();
        intersections = new HashMap<>();
    }

    /** member methods */


    // returns the hashmap of all the intersections in graph
    public HashMap<String, Node> getAllIntersections() {
        return intersections;
    }

    // returns the intersection
    public Node getIntersection(String intersection) {
        return intersections.get(intersection);
    }
    //adds an intersection in the graph/map
    public void addInterSection(String ID, double latitude, double longitude) { //adds an intersection to the graph
        intersections.put(ID, new Node(ID, latitude, longitude)); //puts intersection in to the hashmap
        vertexes.put(ID, new ArrayList<>());  //adds the intersection into the vertexes hashmap
    }

    // gets the neighbors of the given intersection
    public ArrayList<String> getNeighbors(String intersection) {
        return vertexes.get(intersection);
    }


    // returns the hashmap of vertexes
    public HashMap<String, ArrayList<String>> getVertex() {
        return vertexes;
    }

    // returns hashmap of  edges
    public HashMap<Pair<String, String>, Edge> getEdges() {
        return edges;
    }

    // adds an edge between two intersections
    public void addEdge(String edgeID, String source, String target, double weight){
        vertexes.get(source).add(target); //add target to vertex hashmap of source
        vertexes.get(target).add(source); //add source to vertex hashmap of target
        edges.put(new Pair<String, String>(source, target), new Edge(edgeID, intersections.get(source), intersections.get(target), weight)); //add new edge
    }


    // returns the edge between two nodes (intersections)
    public Edge getEdge(String source, String target) {
        if (edges.containsKey(new Pair<String, String>(source, target))) //check if the source-target pair exists in edges hashmap
            return edges.get(new Pair<String, String>(source, target));
        else
            return edges.get(new Pair<String, String>(target, source));
    }

    // returns the weight between two nodes (intersections)
    public double getDistance(String source, String target) {
        return edges.get(new Pair<String, String>(source, target)).getWeight();
    }

//    //returns cost to go to that edge
//     public double getCostt(String source, String target) {
//        return edges.get(new Pair<String, String>(source, target)).getCost();
//    }


    // returns number of nodes
    public int nquantity() {
        return vertexes.size();
    }


    // returns number of edges
    public int equantity() {
        return edges.size();
    }

}

