package sol;

import src.City;
import src.IDijkstra;
import src.IGraph;
import src.Transport;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {
    private HashMap<V, Double> distMap;

    public Dijkstra() {
        this.distMap = new HashMap<>();
    }

    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!
        HashMap<V, E> cameFrom = new HashMap<>();

        Comparator<V> routeShortest = (v1, v2) -> {
            return this.distMap.get(v1).compareTo(this.distMap.get(v2));
        };

        PriorityQueue<V> toCheckQueue = new PriorityQueue<V>(routeShortest);
        // initialize a PQ, where keys are all the vertices and doubles are the distance from the vertices
        // to the source, 0 if the vertices is the source, infinity otherwise.
        for (V c : graph.getVertices()) {
            if (c.equals(source)) {
                this.distMap.put(c, (double) 0);
            } else {
                double posInf = Double.POSITIVE_INFINITY;
                this.distMap.put(c, posInf);
            }
            toCheckQueue.add(c);
        }

        while (!toCheckQueue.isEmpty()) {
            V checkingV = toCheckQueue.poll();
            for (E edge : graph.getOutgoingEdges(checkingV)) {
                V target = graph.getEdgeTarget(edge);
                double newWeight = this.distMap.get(checkingV) + edgeWeight.apply(edge);
                if (newWeight < this.distMap.get(target)) {
                    toCheckQueue.remove(target);
                    this.distMap.put(target, newWeight);
                    toCheckQueue.add(target);
                    cameFrom.put(target, edge);
                }
            }
        }
        return this.backTrack(cameFrom, source, destination, graph);
    }

    // TODO: feel free to add your own methods here!
    private List<E> backTrack(HashMap<V, E> cameFrom, V start, V end, IGraph<V, E> graph){
        LinkedList<E> path = new LinkedList<>();
        V dest = end;
        while(dest != start) {
            E edge = cameFrom.get(dest);
            path.addFirst(edge);
            dest = graph.getEdgeSource(edge);
        }
        return path;
    }
}
