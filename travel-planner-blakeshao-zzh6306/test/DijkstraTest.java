package test;

import org.junit.Assert;
import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.IDijkstra;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class DijkstraTest {


    @Test
    public void testTime() {
        TravelGraph myGraph = new TravelGraph();

        City Providence = new City("Providence");
        City Boston = new City("Boston");
        City NYC = new City("New York");
        City CHO = new City("Charlottesville");


        Transport pToBostonBus = new Transport(Providence, Boston, TransportType.BUS, 15.0, 70.0);
        Transport pToBostonTrain =  new Transport(Providence, Boston, TransportType.TRAIN, 12.0, 60.0);
        Transport bToProvidenceBus = new Transport(Boston, Providence, TransportType.BUS, 13.0, 70.0);
        Transport bToProvidenceTrain =  new Transport(Boston, Providence, TransportType.TRAIN, 14.0, 60.0);
        Transport nToProvidencePlane = new Transport(NYC, Providence, TransportType.PLANE, 150.0, 70.0);
        Transport nToBostonPlane = new Transport(NYC, Boston, TransportType.PLANE, 300.0, 80.0);
        Transport cToPVD = new Transport(CHO, Providence, TransportType.TRAIN, 250.0, 420.0);
        Transport cToNYC = new Transport(CHO, NYC, TransportType.TRAIN, 100.0, 300.0);


        Providence.addOut(pToBostonBus);
        Providence.addOut(pToBostonTrain);

        Boston.addOut(bToProvidenceBus);
        Boston.addOut(bToProvidenceTrain);

        NYC.addOut(nToProvidencePlane);
        NYC.addOut(nToBostonPlane);

        CHO.addOut(cToNYC);
        CHO.addOut(cToPVD);

        myGraph.addVertex(Providence);
        myGraph.addVertex(Boston);
        myGraph.addVertex(NYC);
        myGraph.addVertex(CHO);
        myGraph.addEdge(Providence, pToBostonBus);
        myGraph.addEdge(Providence, pToBostonTrain);
        myGraph.addEdge(Boston, bToProvidenceBus);
        myGraph.addEdge(Boston, bToProvidenceTrain);
        myGraph.addEdge(NYC, nToProvidencePlane);
        myGraph.addEdge(NYC, nToBostonPlane);
        myGraph.addEdge(CHO, cToNYC);
        myGraph.addEdge(CHO, cToPVD);

        TravelController myTC = new TravelController();
        myTC.assignGraph(myGraph);
        List<Transport> pvdToBosTime = myTC.fastestRoute("Providence", "Boston");
        assertEquals(60.0, myTC.getTime(pvdToBosTime), DELTA);
        List<Transport> bosToPvdTime = myTC.fastestRoute("Boston", "Providence");
        assertEquals(60.0, myTC.getTime(bosToPvdTime), DELTA);
        List<Transport> bosToPvdMoney = myTC.cheapestRoute("Boston", "Providence");
        assertEquals(13.0, myTC.getPrice(bosToPvdMoney), DELTA);
        List<Transport> pvdToBosMoney = myTC.cheapestRoute("Providence", "Boston");
        assertEquals(12.0, myTC.getPrice(pvdToBosMoney), DELTA);
        List<Transport> nycToBosMoeny = myTC.cheapestRoute("New York", "Boston");
        assertEquals(162.0, myTC.getPrice(nycToBosMoeny), DELTA);
        List<Transport> choToBosMoney = myTC.cheapestRoute("Charlottesville", "Boston");
        assertEquals(262.0, myTC.getPrice(choToBosMoney), DELTA);
        List<Transport> choToBosTime = myTC.fastestRoute("Charlottesville", "Boston");
        assertEquals(380.0, myTC.getTime(choToBosTime), DELTA);
    }

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!

}
