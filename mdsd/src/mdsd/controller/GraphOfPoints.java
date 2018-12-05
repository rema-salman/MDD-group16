package mdsd.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Class representing a graph of points in a plane, contains function to
 * calculate the shortest path between nodes in this graph.
 * 
 * @author Mattias
 *
 */
public class GraphOfPoints {
	Node[] nodes;

	/**
	 * Given two nodes returns the shortest path using A*, with euclidian
	 * distance as the heuristic function, between the two nodes if it exists,
	 * otherwise null.
	 * 
	 * @param start
	 *            The starting node for the path
	 * @param target
	 *            The final targetted node for the path
	 * @return List of the nodes to visit in order to travel from start to
	 *         target.
	 */
	public List<NodeWithCost> shortestPath(Node start, Node target) {
		List<NodeWithCost> closedSet = new ArrayList<>();
		//List<Node> openSet = new ArrayList<Node>();
		PriorityQueue<NodeWithCost> openSet = new PriorityQueue<>();

		openSet.add(new NodeWithCost(start, target, 0f));

		while (!openSet.isEmpty()) {
			NodeWithCost current = openSet.poll();
			if (current.node == target)
				return reconstructPath(current, new ArrayList<NodeWithCost>());

			closedSet.add(current);

			for (Edge edge : current.node.edges) {
				Node neighbor = edge.to;
				if (!closedSet.contains(neighbor)) {
					Float tentativeBaseCost = current.baseCost + edge.cost;

					boolean openSetContains = false;

					Iterator<NodeWithCost> iter = openSet.iterator();
					while (iter.hasNext()) {
						NodeWithCost currentIter = iter.next();
						if (currentIter.equals(neighbor)) {
							if (currentIter.baseCost >= tentativeBaseCost) {
								//This reorders the priority queue.
								openSet.remove(neighbor);
								openSet.add(new NodeWithCost(neighbor, target, tentativeBaseCost, current));
								openSetContains = true;
								break;
							}
						}
					}
					if (!openSetContains) {
						openSet.add(new NodeWithCost(neighbor, target, tentativeBaseCost, current));
					}

				}
			}
		}
		return null; //No path found
	}

	/**
	 * Given a list of NodeWithCost, returns an array containing the points
	 * associated with each of those nodes. Can be used to translate the
	 * solution from
	 * 
	 * @see shortestPath back to points again.
	 * @param l
	 *            List of NodeWithCost,
	 * @return
	 */
	public Point[] NodesWithCostToPointArray(List<NodeWithCost> l) {
		Point[] points = new Point[l.size()];
		for (int i = 0; i < l.size(); i++) {
			points[i] = l.get(i).node.point;
		}
		return points;
	}

	/**
	 * Given a node, returns a list containing all the nodes used to reach the
	 * given node. If there is a cycle (no end to the path used to reach the
	 * node) then this will probably cause a stack overflow exception.
	 * 
	 * @param n
	 *            Node at the end of a path
	 * @param l
	 *            The list of nodes that will be returned, due to the recursive
	 *            implementation of this function, the initial call of the
	 *            function should send in an empty list here.
	 * @return The
	 */
	private List<NodeWithCost> reconstructPath(NodeWithCost n, List<NodeWithCost> l) {
		if (n == null)
			throw new NullPointerException(); //Shouldn't happen unless initial call has n == null

		if (n.cameFrom != null) {
			l.add(n);
			return reconstructPath(n.cameFrom, l);
		} else { //Base case for start node which doesn't have a from.
			l.add(n);
			return l;
		}
	}

	/**
	 * Returns the euclidianDistance between the two points associated with the
	 * given nodes.
	 * 
	 * @param a
	 *            First node/point
	 * @param b
	 *            Second node/point
	 * @return Euclidian distance between the two points.
	 */
	private static float euclidianDistance(Node a, Node b) {
		return (float) a.point.distance(b.point);
	}

	/**
	 * "Extension" of the Node class used by @see shortestPath, in addition to a
	 * node it also contains the cost to travel to this node, the heuristic
	 * cost/distance to the target node, as well as a reference to the node used
	 * to reach this one. Furthermore it overrides compareTo and equals for easy
	 * use in shortestPath.
	 * 
	 * @author Mattias
	 *
	 */
	private static class NodeWithCost implements Comparable<NodeWithCost> {
		private Node node;
		private NodeWithCost cameFrom;
		private Float euclidianCost;
		private Float baseCost = Float.MAX_VALUE;

		private NodeWithCost(Node node, Node target, Float baseCost) {
			this.baseCost = baseCost;
			this.node = node;
			this.euclidianCost = euclidianDistance(node, target);
		}

		private NodeWithCost(Node node, Node target, Float baseCost, NodeWithCost cameFrom) {
			this.baseCost = baseCost;
			this.node = node;
			this.euclidianCost = euclidianDistance(node, target);
			this.cameFrom = cameFrom;
		}

		private Float fullCost() {
			return euclidianCost + baseCost;
		}

		@Override
		public int compareTo(NodeWithCost o) {
			return Float.compare(this.fullCost(), o.fullCost());
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (o instanceof NodeWithCost) {
				return this == o;
			}
			if (o instanceof Node) {
				return node == o;
			}
			return false;
		}
	}

	public static class Node {
		public Point point;
		public List<Edge> edges = new ArrayList<Edge>();

		public Node(Point point) {
			this.point = point;
		}
	}

	public static class Edge {
		private int cost;
		private Node to;

		public Edge(int cost, Node to) {
			this.cost = cost;
			this.to = to;
		}
	}
}
