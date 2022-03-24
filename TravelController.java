package sol;

import src.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    /**
     * This class models the control portion of the project
     * It has the capability to read CSV files into TravelGraph and
     * to find the most direct, least time-consuming, or cheapest path
     */
    public TravelController() {
    }

    /**
     * Transcribe the information in CVS files regarding city and transports onto TravelGraph,
     * representing them as vertices and edges
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return Message of success
     */
    @Override
    public String load(String citiesFile, String transportFile) {
        // TODO: instantiate a new Graph object in the graph field

        // TODO: create an instance of the TravelCSVParser

        // TODO: create a variable of type Function<Map<String, String>, Void>
        //       that will build the nodes in a graph. Keep in mind
        //       that the input to this function is a hashmap that relates the
        //       COLUMN NAMES of the csv to the VALUE IN THE COLUMN of the csv.
        //       It might be helpful to write a method in this class that takes the
        //       information from the csv needed to create an edge and uses that to
        //       build the edge on the graph.

        // TODO: create another variable of type Function<Map<String, String>, Void> which will
        //  build connections between nodes in a graph.

        // TODO: call parseLocations with the first Function variable as an argument and the right
        //  file

        // TODO: call parseTransportation with the second Function variable as an argument and
        //  the right file

        // hint: note that parseLocations and parseTransportation can
        //       throw IOExceptions. How can you handle these calls cleanly?

        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        Function<Map<String, String>, Void> addTransportation = map -> {
            City origin = this.graph.getVertex(map.get("origin"));
            System.out.println(map.get("type"));
            this.graph.addEdge(origin, new Transport(origin, this.graph.getVertex(map.get("destination")),
                    TransportType.fromString(map.get("type")), Double.parseDouble(map.get("price")),
                    Double.parseDouble(map.get("duration"))));
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);

        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }
        try{
            parser.parseTransportation(transportFile, addTransportation);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }
        return "Successfully loaded cities and transportation files.";
    }

    /**
     * This method uses a Dijkstra object to find the fastest route
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a series of transport that constitutes the fastest route
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Dijkstra newD = new Dijkstra();
        City sourceCity = this.graph.getVertex(source);
        City destinationCity = this.graph.getVertex(destination);
        return newD.getShortestPath(this.graph, sourceCity, destinationCity, this.findTime);
        // TODO: implement this method!
    }

    Function<Transport, Double> findTime =
            edge -> {
        return edge.getMinutes();
            };

    /**
     * This method uses a Dijkstra object to find the cheapest route
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a series of transport that constitutes the cheapest route
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Dijkstra newD = new Dijkstra();
        City sourceCity = this.graph.getVertex(source);
        City destinationCity = this.graph.getVertex(destination);
        return newD.getShortestPath(this.graph, sourceCity, destinationCity, this.findPrice);
        // TODO: implement this method!
    }

    Function<Transport, Double> findPrice =
            edge -> {
        return edge.getPrice();
            };

    /**
     * This method uses a BFS object to find the fastest route
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a series of transport that constitutes the most direct route
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        // TODO: implement this method!
        BFS bfs =  new BFS();
        City sourceCity = this.graph.getVertex(source);
        City destCity = this.graph.getVertex(destination);
        return bfs.getPath(this.graph, sourceCity, destCity);

    }

    public TravelGraph getGraph(){
        return this.graph;
    }

    public void assignGraph(TravelGraph graph) {
        this.graph = graph;
    }

    public double getPrice(List<Transport> lot) {
        double price = 0;
        for (Transport t : lot) {
            price = price + t.getPrice();
        }
        return price;
    }

    public double getTime(List<Transport> lot) {
        double time = 0;
        for (Transport t : lot) {
            time = time + t.getMinutes();
        }
        return time;
    }
}
