/*
Author: Shingirai Dhoro
Email: sdhoro@u.rochester.edu
 */
import javafx.util.Pair;
import javax.swing.*;
import java.io.*;
import java.util.*;

/** Main class that implements Driver method */
public class StreetMap {

    /** Method to find the shortest path from source to target */
    private static Pair<Double, ArrayList<String>> shortestPath(Graph G, String source, String target, Boolean euclidHeuri) {
        HashMap<String, String> prev = new HashMap<>(); // stores previous node
        HashMap<String, Double> weights = new HashMap<>(); // stores weights/distance between two node
        HashMap<String, Double> heuris = new HashMap<>(); //heuristic plus weight
        for (String intersection : G.getAllIntersections().keySet()) {
            weights.put(intersection, Double.MAX_VALUE);
            prev.put(intersection, null);
             if(euclidHeuri == true)
                heuris.put(intersection, G.getIntersection(intersection).getEheuristic(G.getIntersection(target)));
            else
                heuris.put(intersection, G.getIntersection(intersection).getMheuristic(G.getIntersection(target)));

           // heuris.put(intersection, G.getIntersection(intersection).getHeuristic(euclidHeuri, ));

        }

        String fname = "";
        if(euclidHeuri == true){
             fname += "e_OpenClosedStates";
        }
        else{
             fname += "m_OpenClosedSates";
        }


        File newfile = new File( fname + ".txt");

        try (BufferedWriter buffedwriter = new BufferedWriter(new FileWriter(newfile));){
            buffedwriter.write("List of Open and closed States for each iteration.\n\n\n");

        weights.put(source, 0.0); // put source into hashmap of weights
        Comparator<String> weightsComparator = new Comparator<String>() { // create custom comparator for weih
            @Override     //overrides defaultecomparator
            public int compare(String s1, String s2) {
                double cost1 = weights.get(s1) + heuris.get(s1);
                double cost2 = weights.get(s2)+ heuris.get(s2);
                if (cost1 == cost2)
                    return 0;
                else if (cost2 > cost1)
                    return -1;
                else
                return 1;
            }
        };

        PriorityQueue<String> queue = new PriorityQueue<>(weightsComparator); // Priority queue to store nodes
        queue.add(source); // add source
        String current_intersection;
        int f = 0;
        while (!queue.isEmpty()) { // implementation of  A* algorithm starts here

            buffedwriter.write("Open States for Iteration "+ f + ":\n");
            Iterator<String> itr = queue.iterator();
            while(itr.hasNext()){
                String currNode = itr.next();
                double cost = weights.get(currNode) + heuris.get(currNode);
                buffedwriter.write(G.getIntersection(currNode).getNodeID() + "( f = " + cost + ", g = " + weights.get(currNode) + ", h = " + heuris.get(currNode) + ", BP = " + prev.get(currNode) + " )\n");
            }
            buffedwriter.write("\nClosed States for Iteration " + f +":\n");
            for (String prn : G.getAllIntersections().keySet()){
                if(G.getIntersection(prn).wasVisited() == true){
                    double cost = weights.get(prn) + heuris.get(prn);
                    buffedwriter.write(G.getIntersection(prn).getNodeID() + "( f = " + cost + ", g = " + weights.get(prn) + ", h = " + heuris.get(prn) + ", BP = " + prev.get(prn) + " )\n");

                }
            }

            buffedwriter.write("\n\n");

            current_intersection = queue.remove();
            G.getIntersection(current_intersection).setVisited(true);
            if (current_intersection.equals(target)) // check to see if we are at target
                return new Pair<Double, ArrayList<String>>(weights.get(current_intersection), showPath(prev, source, target)); //return length of path and path pair
            for (int i = 0; i < G.getNeighbors(current_intersection).size(); i++) { //go through all neighbors
                String neighbor = G.getNeighbors(current_intersection).get(i); //get neighboring node
                if (!G.getIntersection(neighbor).wasVisited()) {
                    if ((weights.get(current_intersection) + G.getEdge(current_intersection, neighbor).getWeight()) < weights.get(neighbor) ) {
                        weights.put(neighbor, (weights.get(current_intersection) + G.getEdge(current_intersection, neighbor).getWeight()));
                        prev.put(neighbor, current_intersection);
                        queue.remove(neighbor); // remove so we can update the value and not duplicate
                        queue.add(neighbor);
                    }
                }
            }
            f++;
        }
            buffedwriter.flush();
        buffedwriter.close();
        } catch (FileNotFoundException e) { // file not found
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null; //no path found
    }
    /** Method to show path from source to target */
    private static ArrayList<String> showPath(HashMap<String, String> prev, String source, String target) {
        ArrayList<String> path = new ArrayList<>();
        //go through all the prev nodes from the target until you get to source
        while (source != target) {
            path.add(target);
            target = prev.get(target);
        }
        path.add(source);
        Collections.reverse(path);
        return path;
    }

    /** Main method */
    public static void main(String[] args) {
        Boolean euclidHeuri = false; //checks to see if we are using euclidean heuristic
        Graph G = new Graph(); // creates graph
        Pair<Double, ArrayList<String>> path = new Pair<Double, ArrayList<String>>(0.0, new ArrayList<String>()); // pairs of path length and intersections
        try {
            Scanner scan; // scanner
            if (args.length > 0) // check if we are using terminal
                scan = new Scanner(new File(args[0])); // open the file
            else
                scan = new Scanner(new File("hwm.txt")); // open default files
            while (scan.hasNextLine()) {
                String[] line = scan.nextLine().split("\t"); // split the input file

                if (line[0].equals("i")) { // intersection
                    G.addInterSection(line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3])); // add intersection
                }
                if(line[0].equals("r")){
                    G.addEdge(line[1], (line[2]), (line[3]), Double.parseDouble(line[4])); // add edge
                }
                if(line[0].equals("h")){
                    if(line[1].equals("e"))
                        euclidHeuri = true;
                }
            }
            scan.close();// close the scanner
            ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(args)); // commandline arguments
            if (arguments.contains("--directions")) { //  finding direction
                String source = arguments.get(arguments.indexOf("--directions") + 1); // source intersection
                String destination = arguments.get(arguments.indexOf("--directions") + 2); // target intersection
                path = shortestPath(G, source, destination, euclidHeuri); // find the shortest path from source to target
                System.out.println("Length of shortest path  = " + path.getKey()); // print length
                System.out.println("Actual Path = " + path.getValue().toString()); // print path
            }
            if (arguments.contains("--show")) { // show the map
                MapInterface myMap = new MapInterface(G, path.getValue()); // create Map Interface
                JFrame frame = new JFrame("Map");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(myMap);
                frame.pack();
                frame.setVisible(true);
            }
            else if (arguments.size() == 0) { // default operation for testing
                path = shortestPath(G, "F", "T", euclidHeuri);
                System.out.println("Length = " + path.getKey());
                System.out.println("Path = " + path.getValue().toString());
                MapInterface myMap = new MapInterface(G, path.getValue());
                JFrame frame = new JFrame("Map");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(myMap);
                frame.pack();
                frame.setVisible(true);
            }
            else{
                System.out.println("No commands specified."); //no commands specified
            }

        } catch (IOException e) { // IOException
            e.printStackTrace();
        }

    }
}
