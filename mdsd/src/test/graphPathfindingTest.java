package test;

import mdsd.controller.GraphOfPoints;
import mdsd.model.Environment;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A terrible temporary test of the pathfinding in GraphOfPoints.
 * TODO: A more proper test.
 *
 * @author Mattias
 */
public class graphPathfindingTest {

    public static void main(String[] args) {
        GraphOfPoints g = new GraphOfPoints();
        GraphOfPoints.Node n1 = new GraphOfPoints.Node(new Point2f(0, 0));
        GraphOfPoints.Node n2 = new GraphOfPoints.Node(new Point2f(0, 10));
        GraphOfPoints.Node n3 = new GraphOfPoints.Node(new Point2f(10, 0));
        GraphOfPoints.Node n4 = new GraphOfPoints.Node(new Point2f(10, 10));

        GraphOfPoints.Node n5 = new GraphOfPoints.Node(new Point2f(5, 5));

        n1.edges = new ArrayList<>();
        n1.edges.add(new GraphOfPoints.Edge(5, n2));
        n1.edges.add(new GraphOfPoints.Edge(5, n3));
        n1.edges.add(new GraphOfPoints.Edge(5, n5));

        n2.edges = new ArrayList<>();
        n2.edges.add(new GraphOfPoints.Edge(5, n4));

        n5.edges = new ArrayList<>();
        n5.edges.add(new GraphOfPoints.Edge(5, n4));

        n3.edges = new ArrayList<>();
        n3.edges.add(new GraphOfPoints.Edge(5, n4));

        System.out.println(Arrays.toString(g.NodesWithCostToPointArray(g.shortestPath(n1, n4))));

        g = new GraphOfPoints(new Environment(), 4, 3);
        System.out.println(g);
    }
}
