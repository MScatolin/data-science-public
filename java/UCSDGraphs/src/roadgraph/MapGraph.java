/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap; //needed to import to use HasHMap in vertices.
import java.util.HashSet; //needed to import to use HashSet in edges.
import java.util.LinkedList; //needed to import to use the queue in the bfs method.
import java.util.List;
import java.util.PriorityQueue;//needed to implement djistrika and A* algorithms
import java.util.Queue; // needed to import to use the queue in the bfs method.
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**New libraries download from www.apixu.com that contains the methods for the weather forecast.
 * 
 */

 

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	
	private HashMap<GeographicPoint, MapNode> vertices;
	private HashSet<MapEdge> edges;
	
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		vertices = new HashMap<GeographicPoint, MapNode>();
		edges = new HashSet<MapEdge>();
		
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{		
		return vertices.values().size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return  vertices.keySet();
	}
	
	public void printNodes() { //as suggested I implemented a method to print the Nodes. This helped debugging.
		System.out.println("Number of Nodes:" + getNumVertices());
		for (GeographicPoint node : vertices.keySet()) {
			MapNode n = vertices.get(node);
			System.out.println(n);
			}
		}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return edges.size();
	}

	public void printEdges() { //similar to printNodes. This helped debugging.
		System.out.println("Number of Edges:" + getNumVertices());
		for (MapEdge edge : edges) {
			System.out.println(edge);
			}
		}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		MapNode newVertex = vertices.get(location);//test if the same location has already in the graph
		if (newVertex == null){//if not:
			newVertex = new MapNode(location);
			vertices.put(location, newVertex);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		MapNode init = vertices.get(from);
		MapNode end = vertices.get(to);
		
		if (init == null){
			throw new IllegalArgumentException("Initial vertex is not in the graph yet. Please insert it or check it");
		}
		else if (end == null){
			throw new IllegalArgumentException("End vertex is not in the graph yet. Please insert it or check it");
		}
		addEdge(init, end, roadName, roadType, length);
	}
	
	//Added an alternative method to addEdge based on two existing mapNodes. This was necessary cause 
	//when using in the previous addEdge method:
	// 				addEdge(init.getLocation(), end.getLocation(), roadName, roadType, length);
	//I got an stack overflow!
	
	public void addEdge(MapNode point1, MapNode point2, String name,
			String type, double length) {
		MapEdge edge = new MapEdge (point1, point2, name, type, length);
		edges.add(edge);
		point1.addEdge(edge);
		
		
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// First, we need to check if arguments are valid.
		if (start == null || goal == null) {
			throw new NullPointerException("Null input parameters");
		}
		if (vertices.get(start) == null) {
			throw new IllegalArgumentException("Start point do note exist");
		}
		if (vertices.get(goal) == null) {
			throw new IllegalArgumentException("Goal point do note exist");
		}
		
		MapNode init = vertices.get(start);
		MapNode end = vertices.get(goal);
		
		
		
		// initialize the structures given in the examples videos:
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		Queue<MapNode> candidatesQueue = new LinkedList<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		MapNode curr = null;
		
		// at first we add the start point (better say it's coordinate, using the getter method)
		
		candidatesQueue.add(init);
		visited.add(init);
		
		
		while (!candidatesQueue.isEmpty()) {
			curr = candidatesQueue.remove();

			if (curr.equals(end))
				break;
			
			for (MapNode neighbor : getNeighbors(curr)) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, curr);
					candidatesQueue.add(neighbor);
				}
			}
		}
		return reconstructPath(parentMap, init, end, curr.equals(end));
	
	}	

		private List<GeographicPoint> reconstructPath(HashMap<MapNode, MapNode> parentMap, MapNode start, MapNode goal,
				boolean pathFound) {
			if (!pathFound) {
				System.out.println("No path found");
				return null;
			}
			LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
			MapNode current = goal;

			while (!current.equals(start)) {
				path.addFirst(current.getLocation());
				current = parentMap.get(current);
			}

			// add start
			path.addFirst(start.getLocation());
			return path;
		}
		
		
		private Set<MapNode> getNeighbors(MapNode vertex) 
		{
			return vertex.getNeighbors();
		}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// First, we need to check if arguments are valid. Same exceptions from the bfs method.
				if (start == null || goal == null) {
					throw new NullPointerException("Null input parameters");
				}
				if (vertices.get(start) == null) {
					throw new IllegalArgumentException("Start point do note exist");
				}
				if (vertices.get(goal) == null) {
					throw new IllegalArgumentException("Goal point do note exist");
				}
				
				MapNode init = vertices.get(start);
				MapNode end = vertices.get(goal);
				MapNode curr = null;
				
				//initialize: PriorityQueue (PQ), visited HashSet, Parent HashMap, and distances do infinity
				PriorityQueue<MapNode> candidatesQueue = new PriorityQueue<MapNode>();
				HashSet<MapNode> visited = new HashSet<MapNode>();
				HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
				initializeDistances();
				int count = 0;// to see node's count to quiz answer.
				double totalDistance = 0; // to store the final distance
				double hours = 0; //to store the estimated time of the trip.
				
				
				
				//Enqueue {S,0} to the PQ:
				init.setDistance(0.0);
				init.setTrueDistance(0.0);
				candidatesQueue.add(init);
				
				//while PQ is not empty:
				while (!candidatesQueue.isEmpty()){	
					curr = candidatesQueue.remove();
					nodeSearched.accept(curr.getLocation());
					count++;
					

					if (curr.equals(end)) break;
					
					
					//if(curr is not visited)
					if (!visited.contains(curr)){
						visited.add(curr);
						
						Set<MapEdge> edges = curr.getEdges();
						
						//for (each of curr's neighbors, n, not visited in set:
						for (MapEdge edge : edges){
							MapNode neighbor = edge.getEndNode();
							
							if (!visited.contains(neighbor)){
								
								//if path thorough curr to n is shorter:
								double distance = curr.getDistance() + edge.getLength();
								
								double currTime = distance/edge.getAvgSpeed();
								
								double predictedETA = currTime + (neighbor.getLocation()).distance(end.getLocation())/67; //assuming 67 will be the average speed in all roads.

								if (distance < neighbor.getDistance()){
									
									//update n's distance 
									neighbor.setDistance(distance);
									neighbor.setArrivalTime(currTime);
									
									//update curr as n's parent in parent map
									parentMap.put(neighbor, curr);
									
									//enqueue {n, distance} into the PQ
									
									candidatesQueue.add(neighbor);
									
									totalDistance += distance;
									hours += currTime;
									
									
								
									
									

								}
								
							}
						}
					}
					
				}
				if (!curr.equals(end)) {//if we get here, then there's no path
					System.out.println("No path found ");
					return null;
				}
				
				
				
				System.out.println("Nodes visited in Djistrika search: "+count+"\n Distance of path: "+totalDistance+ "\n Estimated total time: "+hours);
			
		return reconstructPath(parentMap, init, end, true);
	}
	
	// decided to implement a method to initialize distances as soon as I will use in both searches.
	private void initializeDistances(){
		for (MapNode node : vertices.values()) {
			node.setTrueDistance(Double.MAX_VALUE);
			node.setDistance(Double.MAX_VALUE);
		}
	}
	

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// First, we need to check if arguments are valid. Same exceptions from the bfs method.
		if (start == null || goal == null) {
			throw new NullPointerException("Null input parameters");
		}
		if (vertices.get(start) == null) {
			throw new IllegalArgumentException("Start point do note exist");
		}
		if (vertices.get(goal) == null) {
			throw new IllegalArgumentException("Goal point do note exist");
		}
		
		MapNode init = vertices.get(start);
		MapNode end = vertices.get(goal);
		MapNode curr = null;
		double totalDistance = 0; // to store the final distance
		double hours = 0; //to store the estimated time of the trip.
		
		//initialize: PriorityQueue (PQ), visited HashSet, Parent HashMap, and distances do infinity
		PriorityQueue<MapNode> candidatesQueue = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		initializeDistances();
		int count = 0;
	
		
		//Enqueue {S,0} to the PQ:
		init.setDistance(0.0);
		init.setTrueDistance(0.0);
		init.setArrivalTime(0.0);
		candidatesQueue.add(init);
		
		//while PQ is not empty:
		while (!candidatesQueue.isEmpty()){	
			curr = candidatesQueue.remove();
			nodeSearched.accept(curr.getLocation());
			count++;
			// System.out.println("\nA* visiting " + curr +"\nActual = "+
				//		curr.getTrueDistance()+", Pred: "+curr.getDistance());
			if (curr.equals(end)) break;
			
			
			//if(curr is not visited)
			if (!visited.contains(curr)){
				visited.add(curr);
					
				Set<MapEdge> edges = curr.getEdges(); //You'll also need to add a way of keeping a "current distance" from your start node to each node as your search progresses
				
				//for (each of curr's neighbors, n, not visited in set:
				for (MapEdge edge: edges){
					MapNode neighbor = edge.getEndNode();
					if (!visited.contains(neighbor)){
						
						
						//calculating distances and time of arrival for the nodes:
						double currDistance = curr.getTrueDistance() + edge.getLength();
						
						double currTime = currDistance/edge.getAvgSpeed();
						
						double predictedDistance = currDistance + (neighbor.getLocation()).distance(end.getLocation());

						double predictedETA = currTime + (neighbor.getLocation()).distance(end.getLocation())/67; //assuming 67 will be the average speed in all roads.
						
						//Change the comment lines bellow to change from Time/Distance weighting system.

						//if (predictedDistance < neighbor.getDistance()){
						if (predictedETA < neighbor.getArrivalTime()){	
							parentMap.put(neighbor, curr);
							neighbor.setArrivalTime(currTime);
							neighbor.setTrueDistance(currDistance);
							neighbor.setTrueArrivalTime(currTime);
							neighbor.setDistance(predictedDistance);
							neighbor.setArrivalTime(predictedETA);
							candidatesQueue.add(neighbor);
							
							totalDistance += currDistance;
							hours += currTime;
							
							}
						
						
						
					}
				}
			}
		}
		if (!curr.equals(end)) {//if we get here, then there's no path
			System.out.println("No path found ");
			return null;
		}
		
		System.out.println("Nodes visited in A* search: "+count+"\n Distance of path: "+totalDistance+ "\n Estimated total time: "+hours);
	
return reconstructPath(parentMap, init, end, true);
}
	
	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		System.out.println("Num nodes: " + firstMap.getNumVertices());
		System.out.println("Num edges: " + firstMap.getNumEdges());
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		/**MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/**MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);*/

		
		
	}
	
}
