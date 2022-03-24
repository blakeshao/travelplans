package sol;

import src.IBFS;
import src.IGraph;

import java.util.*;

/**
 * This class models the BFS algorithm that helps to find the most direct path
 * @param <V> vertex type of the graph
 * @param <E> edge type of the graph
 */
public class BFS<V, E> implements IBFS<V, E> {

    /**
     * This method traverses through the graph to find the most direct path
     * from the start vertex to the end vertex
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return the most direct path consisting of edges
     */
    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        LinkedList<V> toCheck = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        HashMap<String, E> cameFrom = new HashMap<>();
        LinkedList<E> result = new LinkedList<>();
        try{
            toCheck.add(start);
            while (!toCheck.isEmpty()) {
                V vertex = toCheck.removeFirst();
                System.out.println(vertex);
                if (vertex.toString().equals(end.toString())) {
                    result = (LinkedList<E>) this.backTrack(cameFrom, start, end, graph);
                    break;
                }
                visited.add(vertex.toString());
                System.out.println(visited);
                for (E edge : graph.getOutgoingEdges(vertex)) {
                    V target = graph.getEdgeTarget(edge);
                    if (!visited.contains(target.toString()) && !cameFrom.containsKey(target.toString())) {
                        toCheck.addLast(target);
                        cameFrom.put(target.toString(), edge);
                    }
                }
                System.out.println(cameFrom);
            }
        } catch (NullPointerException n){
            System.out.println("Vertex not found");
        }
        return result;
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name

    /**
     * This is a helper method that goes through a HashMap to return the path from
     * start to end by backtracking the end's connecting edge to the start
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @param cameFrom all the relevant edges in the graph that can be a part of the path
     *                 from start to end
     * @return the path from start to end consisting of edges
     */
    private List<E> backTrack(HashMap<String, E> cameFrom, V start, V end, IGraph<V, E> graph){
        LinkedList<E> path = new LinkedList<>();
        V dest = end;
        while(!dest.toString().equals(start.toString())){
            E edge = cameFrom.get(dest.toString());
            path.addFirst(edge);
            dest = graph.getEdgeSource(edge);
        }
            return path;
    }
}
