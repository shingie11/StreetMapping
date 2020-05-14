/*
Author: Shingirai Dhoro
Email: sdhoro@u.rochester.edu
 */
/** implements the edge class of the graph */
public class Edge {

    private String EdgeID; //intersection ID
    private double weight; // weight of the edge
    private Node source; //source node
    private Node target; //target node
    //private double cost; //total cost including the heuristic of current node

    /** Class constructor */
    Edge(String ID, Node src, Node trgt, double w) {
        EdgeID = ID;
        source = src;
        target = trgt;
        weight = w;
       // cost = 0;//trgt.getHeuristic() + w;
        //distance(source.getLatitude(), source.getLongitude(), target.getLatitude(), target.getLongitude());
    }

    /** Member methods */

    // calculates the distance between two nodes (length of edge), harvesine formula
    private double distance(double sourceLat, double sourceLongi, double targetLat, double targetLongi) {
        double longitude = Math.toRadians((targetLongi - sourceLongi))/2;
        double latitude = Math.toRadians((targetLat - sourceLat))/2;
        double temp = Math.sin(latitude) * Math.sin(latitude)+ Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(targetLat)) * Math.sin(longitude) * Math.sin(longitude);
        double dist = 2 * Math.atan2(Math.sqrt(temp), Math.sqrt(1 - temp))*3963;
        return dist;
    }

    //returns EdgeID
    public String getEdgeID() {
        return EdgeID;
    }

    //returns edge weight
    public double getWeight() {
        return weight;
    }

    //returns source node
    public Node getSource() {
        return source;
    }

    //returns target node
    public Node getTarget() {
        return target;
    }

//    //returns edge cost (heuristic + weight)
//    public double getCost(){
//        return cost;
//    }
}
