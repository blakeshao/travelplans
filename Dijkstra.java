package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.List;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!
        return null;
    }

    // TODO: feel free to add your own methods here!
}
