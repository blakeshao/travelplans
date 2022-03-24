package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class models the map that graphs each city as vertices and transport as edges
 * It implements IGraph Interface and has the capability to add vertex, add edge, find the
 * source and target of an edge, get a vertex, and get all the out-going edges of a vertex
 */
public class TravelGraph implements IGraph<City, Transport> {
    private HashMap<String, City> nameToCity;
    private HashSet<City> cities;

    /**
     * This is the constructor of the TravelGraph class,
     * It initializes the two instance variables, a HashMap of strings and cities
     * and a HashSet of cities
     */
    public TravelGraph() {
        this.nameToCity = new HashMap<>();
        this.cities = new HashSet<>();
    }

    /**
     * This is a method that adds a vertex to the graph, it is implemented by the IGraph Interface
     * It adds the vertex along with its toString to the nameToCity HashMap
     * and adds the new city vertex into the list of cities
     * @param vertex the vertex
     */
    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!
        this.nameToCity.put(vertex.toString(), vertex);
        this.cities.add(vertex);
    }

    /**
     * This method is implemented by the IGraph interface,
     * it adds the transport as an edge if its origin city exits in the graph
     * @param origin the origin of the edge.
     * @param edge
     */
    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
        City subject = this.nameToCity.get(origin.toString());
        if(subject != null){
            subject.addOut(edge);
        }
    }

    /**
     * This is a method that implemented by the IGraph interface
     * It returns all the vertices in the graph
     * @return all the vertices in the graph
     */
    @Override
    public Set<City> getVertices() {
        // TODO: implement this method!
        return this.cities;
    }

    /**
     * This method is implemented by the IGraph Interface
     * it accesses the source of an edge
     * @param edge the edge
     * @return the source city of an edge
     */
    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return edge.getSource();
    }

    /**
     * This method is implemented by the IGraph Interface
     * @param edge the edge
     * @return the target city of the edge
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return edge.getTarget();
    }

    /**
     * This method is implemented by the IGraph Interface
     * It returns the list of transport edges going out of the city vertex
     * @param fromVertex the vertex
     * @return the list of transport edges going out of the city vertex
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!
        City c = this.nameToCity.get(fromVertex.toString());
        return c.getOutgoing();
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name

    /**
     * This is a method that uses a string name to get the city object
     * @param name the name of the city
     * @return the city
     */
    public City getVertex(String name){
        return this.nameToCity.get(name);
    }

}