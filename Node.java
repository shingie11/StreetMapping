
/** Implements node class for the graph. Node is just an intersection */
public class Node {

    private String NodeID; // this is the intersection ID
    private boolean visited; //indicates whether the intersection has been visited
    private double longitude;   //longitude
    private double latitude; //latitude
    private double heuristic; //stores heuristic value

    /** class constructor */
    Node (String IntersectionID, double lati, double longi) {
        NodeID = IntersectionID;
        longitude = longi;
        latitude= lati;
        visited = false;
        heuristic = 0; //
    }


    /** Class methods */

    //returns  Euclidean heuristic
    public double getEheuristic(Node trgt){
        //Math.abs(src.getLongitude() - trgt.getLongitude());
      return Math.sqrt((Math.pow(trgt.getLongitude()-longitude, 2)) + (Math.pow(trgt.getLatitude()-latitude, 2)));

    }

    //returns Manhattan heuristic
    public double getMheuristic(Node trgt){
        return Math.abs(longitude - trgt.getLongitude()) + Math.abs(latitude - trgt.getLatitude());
    }



    //returns whether or not the intersection was visited
    public boolean wasVisited(){
        return visited;
    }

    //sets visited status
    public void setVisited(boolean status){
        visited = status;
    }

    //returns longitude of the intersection
    public double getLongitude(){
        return longitude;
    }

    //returns latitude of the intersection
    public double getLatitude(){
        return latitude;
    }

    //returns node ID
    public String getNodeID(){
        return NodeID;
    }

    // //returns heuristic
    // public double getHeuristic(Boolean heuriType, Node trgt){
    //     Node t = Graph.
    //     return heuristic;
    // }

}
