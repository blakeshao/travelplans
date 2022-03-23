package test;

import org.junit.Assert;
import org.junit.Test;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

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

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!

    public void testGraphs() {
        TravelGraph myGraph = new TravelGraph();

        City Providence = new City("Providence");
        City Boston = new City("Boston");
        City NYC = new City("New York");
        City CHO = new City("Charlottesville");


        Transport pToBostonBus = new Transport(Providence, Boston, TransportType.BUS, 15.0, 70.0);
        Transport pToBostonTrain = new Transport(Providence, Boston, TransportType.TRAIN, 12.0, 60.0);
        Transport bToProvidenceBus = new Transport(Boston, Providence, TransportType.BUS, 13.0, 70.0);
        Transport bToProvidenceTrain = new Transport(Boston, Providence, TransportType.TRAIN, 14.0, 60.0);
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
        assertTrue(myGraph.getVertices().contains(Providence));
        assertTrue(myGraph.getVertices().contains(Boston));
        assertTrue(myGraph.getVertices().contains(NYC));
        assertTrue(myGraph.getVertices().contains(CHO));
        myGraph.addEdge(Providence, pToBostonBus);
        myGraph.addEdge(Providence, pToBostonTrain);
        myGraph.addEdge(Boston, bToProvidenceBus);
        myGraph.addEdge(Boston, bToProvidenceTrain);
        myGraph.addEdge(NYC, nToProvidencePlane);
        myGraph.addEdge(NYC, nToBostonPlane);
        myGraph.addEdge(CHO, cToNYC);
        myGraph.addEdge(CHO, cToPVD);
        Assert.assertEquals(2, Providence.getOutgoing().size());
        Assert.assertEquals(2, Boston.getOutgoing().size());
        Assert.assertEquals(2, NYC.getOutgoing().size());
        Assert.assertEquals(2, CHO.getOutgoing().size());


        TravelController myTC = new TravelController();
        myTC.assignGraph(myGraph);

        // Testing


    }
}
