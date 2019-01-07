package test;

import mdsd.model.Environment;
import mdsd.model.GraphOfPoints;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A terrible temporary test of the pathfinding in GraphOfPoints.
 * TODO: A more proper test.
 *
 * @author Mattias
 */
public class PathfindingTest {

    public static void main(String[] args) {
    	//------------------------------------@before this bit I think?--------------------------
    	
    	/* 		1 2 3
    	 * 
    	 *3 	O-O-T
    	 *  	| |/|
    	 *2 	O-O-O  using distance between nodes as their cost => 11 -> 12 -> 22 -> 33
    	 *  	| | |
    	 *1 	S O-O
    	 */
    	
    	GraphOfPoints.Node n00 = new GraphOfPoints.Node(new Point2f(0, 0));
    	GraphOfPoints.Node n10 = new GraphOfPoints.Node(new Point2f(1, 0));
    	GraphOfPoints.Node n20 = new GraphOfPoints.Node(new Point2f(2, 0));

    	GraphOfPoints.Node n01 = new GraphOfPoints.Node(new Point2f(0, 1));
    	GraphOfPoints.Node n11 = new GraphOfPoints.Node(new Point2f(1, 1));
    	GraphOfPoints.Node n21 = new GraphOfPoints.Node(new Point2f(2, 1));
    	
    	GraphOfPoints.Node n02 = new GraphOfPoints.Node(new Point2f(0, 2));
    	GraphOfPoints.Node n12 = new GraphOfPoints.Node(new Point2f(1, 2));
    	GraphOfPoints.Node n22 = new GraphOfPoints.Node(new Point2f(2, 2));
    	
    	
    	//Which edges are added on the next few lines:
    	/* O
    	 * |
    	 * O
    	 * |
    	 * S
    	 */
    	n00.edges.add(new GraphOfPoints.Edge(1,n01));
    	n01.edges.add(new GraphOfPoints.Edge(1,n00));

    	n01.edges.add(new GraphOfPoints.Edge(1,n02));
    	n02.edges.add(new GraphOfPoints.Edge(1,n01));

    	/*O-O
    	 *
    	 *O-O
    	 *
    	 *S O
    	 */
    	n02.edges.add(new GraphOfPoints.Edge(1,n12));
    	n12.edges.add(new GraphOfPoints.Edge(1,n02));
    	
    	n01.edges.add(new GraphOfPoints.Edge(1,n11));
    	n11.edges.add(new GraphOfPoints.Edge(1,n01));
    	
     	/* O
    	 * |
    	 * O
    	 * |
    	 * O
    */
    	n10.edges.add(new GraphOfPoints.Edge(1,n11));
    	n11.edges.add(new GraphOfPoints.Edge(1,n10));

    	n11.edges.add(new GraphOfPoints.Edge(1,n12));
    	n12.edges.add(new GraphOfPoints.Edge(1,n11));

    	
   	 	/*
   	 	 *O-T
   	 	 * /
   	 	 *O-O
   	 	 * 
   	 	 *O-O
   	 	 */
    	n12.edges.add(new GraphOfPoints.Edge(1,n22));
    	n22.edges.add(new GraphOfPoints.Edge(1,n12));

    	n11.edges.add(new GraphOfPoints.Edge(1.414f,n22));
    	n22.edges.add(new GraphOfPoints.Edge(1.414f,n11));
    	
    	n11.edges.add(new GraphOfPoints.Edge(1,n21));
    	n21.edges.add(new GraphOfPoints.Edge(1,n11));
    	
    	n10.edges.add(new GraphOfPoints.Edge(1,n20));
    	n20.edges.add(new GraphOfPoints.Edge(1,n10));
    	
     	/* T
    	 * |
    	 * O
    	 * |
    	 * O
    */
    	n20.edges.add(new GraphOfPoints.Edge(1,n21));
    	n21.edges.add(new GraphOfPoints.Edge(1,n20));

    	n21.edges.add(new GraphOfPoints.Edge(1,n22));
    	n22.edges.add(new GraphOfPoints.Edge(1,n21));
    	
    	
    	
    	//---------Assert equals the next bit-------------------------------------------------
    	
    	
    	//[(0.0, 0.0), (0.0, 1.0), (1.0, 1.0), (2.0, 2.0)]      should be the output from this, due to the diagonal between 22 and 33 
    	//causing this path to become the shortest one. 
        System.out.println(Arrays.toString(GraphOfPoints.shortestPathArray(n00, n22)));
    }
}
