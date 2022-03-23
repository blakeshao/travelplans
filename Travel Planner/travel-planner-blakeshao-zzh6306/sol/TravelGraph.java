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

    public TravelGraph() {
        this.nameToCity = new HashMap<>();
        this.cities = new HashSet<>();
    }
    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!
        this.nameToCity.put(vertex.toString(), vertex);
        this.cities.add(vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
        City subject = this.nameToCity.get(origin.toString());
        if(subject != null){
            subject.addOut(edge);
        }
    }

    @Override
    public Set<City> getVertices() {
        // TODO: implement this method!
        return this.cities;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!
        City c = this.nameToCity.get(fromVertex.toString());
        return c.getOutgoing();
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
    public City getVertex(String name){
        return this.nameToCity.get(name);
    }
}