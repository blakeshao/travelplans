package test;

import org.junit.Assert;
import org.junit.Test;
import sol.BFS;
import sol.TravelController;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;
    private TravelController control;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    public void loadGraph(String cities, String transport){
        this.control = new TravelController();
        this.control.load(cities, transport);
    }

    @Test
    public void testBFSLoaded(){
        this.loadGraph("/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/cities1.csv",
                "/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/transport1.csv");
        Assert.assertEquals(this.control.mostDirectRoute("Boston", "Providence").size(), 1); //normal
        Assert.assertEquals(this.control.mostDirectRoute("Boston", "Boston").size(), 0); //self
    }

    @Test
    public void testBFSLoaded2(){
        this.loadGraph("/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/cities2.csv",
                "/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/transport2.csv");
        Assert.assertEquals(this.control.mostDirectRoute("Boston", "Chicago").size(), 2); //intermediate
       // System.out.println(this.control.mostDirectRoute("Boston", "Chicago"));
        //Assert.assertEquals(this.control.mostDirectRoute("Washington", "Boston"),  new LinkedList<>()); //normal
    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    @Test
    public void testBFSCantReach(){
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.b, this.f);
        assertEquals(path, new LinkedList<>());
    }

    @Test
    public void testBFSSelf(){
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.b, this.b);
        assertEquals(path.size(), 0);
    }

    @Test
    public void testBFSSelfMadeGraph1(){
        this.loadGraph("/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/BFSSelfMadeGraph1 - cities.csv",
                "/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/BFSSelfMadeGraph1 - transportation.csv");
        Assert.assertEquals(this.control.mostDirectRoute("a", "b").size(), 1); //basic single-edge
        Assert.assertEquals(this.control.mostDirectRoute("a", "a").size(), 0); //travel to self
        Assert.assertEquals(this.control.mostDirectRoute("a", "e").size(), 0); //vertex not on graph
        Assert.assertEquals(this.control.mostDirectRoute("a", "d").size(), 3); //multiple-edge route on circular graph
        Assert.assertEquals(this.control.mostDirectRoute("d", "a").size(), 1); //multiple-edge route on circular graph
    }

    @Test
    public void testBFSSelfMadeGraph2(){
        this.loadGraph("/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/BFSSelfMadeGraph2 - cities.csv",
                "/Users/tianyishao/Documents/CS20/PRJCT/Travel Planner/travel-planner-blakeshao-zzh6306/data/BFSSelfMadeGraph2 - transportation.csv");
        Assert.assertEquals(this.control.mostDirectRoute("A", "D").size(), 3); //two best routes
        Assert.assertEquals(this.control.mostDirectRoute("D", "C").size(), 0); //cannot reach
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!
}
