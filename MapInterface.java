/*
Author: Shingirai Dhoro
Email: sdhoro@u.rochester.edu
 */
import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//@SuppressWarnings("serial")

/** Class Implements the GUI of the map*/
public class MapInterface extends JPanel {
    private HashMap<String, Node> intersections; // hashmap of intersections
    private HashMap<Pair<String, String>, Edge> edges; //hashmap of edges
    private ArrayList<String> path; // arraylists of path from source to target
    private double minLongitude, maxLongitude; //min and max for longitude
    private double minLatitude, maxLatitude; //min and max for longitude
    private double latitudeScaler, longitudeScaler; // scalers

    /** Class Constructor */
    MapInterface(Graph G, ArrayList<String> p) {
        this.intersections = G.getAllIntersections();
        this.edges = G.getEdges();
        this.path = p;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension((int)screenSize.getWidth()-100, (int)screenSize.getHeight()-100)); // set the size of the window
        this.setBackground(Color.BLACK); // background color
    }

    /** Method to set Map parameters, min, max latti/longi and scaling factors */
    public void setDispParameters() {
        Iterator<String> iterata = intersections.keySet().iterator(); // iterator
        Node node;
        if (iterata.hasNext()) { // check to see if there is at least one intersection
            node = intersections.get(iterata.next());
            minLongitude = maxLongitude = node.getLongitude(); // set minimum and maximum  longitude to be the first longi value
            minLatitude = maxLatitude = node.getLatitude(); // set minimum and maximum  latitude to be the first lati value
        }
        while (iterata.hasNext()) { // go through all the intersections
            node = intersections.get(iterata.next());
            if (node.getLongitude() > maxLongitude)
                maxLongitude = node.getLongitude();
            if (node.getLongitude() < minLongitude)
                minLongitude = node.getLongitude();
            if (node.getLatitude() > maxLatitude)
                maxLatitude = node.getLatitude();
            if (node.getLatitude() < minLatitude)
                minLatitude = node.getLatitude();
        }
        longitudeScaler = this.getWidth() / (maxLongitude - minLongitude); // set longitude scale
        latitudeScaler = this.getHeight() / (maxLatitude - minLatitude); // set latitude scale
    }



    /** Method to add nodes and edges to the Map */
    public void paintComponent(Graphics p) {
        setDispParameters(); // sets the display parameters
        Graphics2D graphics2D = (Graphics2D) p; // use 2D graphics
        super.paintComponent(graphics2D);
        graphics2D.setStroke(new BasicStroke(1)); // set the width of the lines to 1
        double longi1, longi2, lati1, lati2;
        for (Edge edge : edges.values()) {
            if (edge.getWeight() >= 0.2) {
                graphics2D.setColor(Color.GREEN);
                graphics2D.setStroke(new BasicStroke((float)3));
            } else {
                graphics2D.setColor(Color.YELLOW);
                graphics2D.setStroke(new BasicStroke((float)3));
            }
            longi1 = edge.getSource().getLongitude(); // longitude value for source intersection
            longi2 = edge.getTarget().getLongitude(); // longitude value for target intersection
            lati1 = edge.getSource().getLatitude(); // latitude value for source intersection
            lati2 = edge.getTarget().getLatitude(); // latitude value for target intersection

            graphics2D.draw(new Line2D.Double((longi1 - minLongitude) * longitudeScaler, getHeight() -
                    ((lati1 - minLatitude) * latitudeScaler), (longi2 - minLongitude) * longitudeScaler,
                    getHeight() - ((lati2 - minLatitude) * latitudeScaler))); // draw edge,
        }

        if (path.size() > 1 && !path.isEmpty() ) { // check to see if we have a path
            graphics2D.setColor(Color.MAGENTA); // set color to mangeta
            graphics2D.setStroke(new BasicStroke(5)); // make path thicker
            for (int i = 1; i < path.size(); i++) { //iterate through all edges and add lines to map
                Node prevnode = intersections.get(path.get(i - 1)); // previous intersection
                longi1 = prevnode.getLongitude(); // longitude coordinate
                lati1 = prevnode.getLatitude(); // latitude coordinates
                Node currentnode = intersections.get(path.get(i)); // current intersection
                longi2 = currentnode.getLongitude(); // longitude coordinate
                lati2 = currentnode.getLatitude(); // latitude coordinates
                graphics2D.draw(new Line2D.Double((longi1 - minLongitude) * longitudeScaler, getHeight() -
                        ((lati1 - minLatitude) * latitudeScaler), (longi2 - minLongitude) * longitudeScaler,
                        getHeight() - ((lati2 - minLatitude) * latitudeScaler))); // draw edge

            }

        }

    }


}

