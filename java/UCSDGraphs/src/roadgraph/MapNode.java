package roadgraph;

/* Auxiliary class to week 2 implementation
 * Author: Marcelo Queiroz
 *
 * Should store the location of a Nodes in the graph as well as a HashSet 
 * of the edges leaving nodes. Similar to what Leo shows in support video, 
 * but using a HashSet for performance purpose (if you know the edge, you know
 * where it's pointing to. 
 *
 */
 import geography.GeographicPoint;
 import java.util.HashSet;
 import java.util.Set;

 import java.time.*;






class MapNode implements Comparable //in order to create the distances we need to be able to compare nodes.
{

	private GeographicPoint coordinates;
	
	private HashSet<MapEdge> edges;

	private double distance;
	
	private double trueDistance;
	
	private double eta; // added estimated time of arrival at the node in hours
	
	private double trueEta;
	
	
	
	MapNode (GeographicPoint local){
		coordinates = local;
		edges = new HashSet<MapEdge>();
		
	}
	
	GeographicPoint getLocation(){//implemented to be used in "converting" the HashMap element into one location. Will be used in the addEdge method in GraphMap class.
		return coordinates;
	}
	
	Set<MapNode> getNeighbors() //used in GraphMap class to find the neighbors of the current MapNode.
	{
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getUpsideDown(this));	
		}
		return neighbors;
	}
	
	//Method to add an edge to the edges set when I already created the structure. 
	//This will be used  in the helper addEdge method in the MapGraph Class.
	void addEdge(MapEdge edge)
	{
		edges.add(edge);
	}
	
	void setDistance(double distance) {
	    this.distance = distance;
	}
	
	double getDistance(){
		return this.distance;
	}
	
	// need to make new set/get distance methods to be able to change the distances while the algorithm iterates through nodes. 
	// also became useful in the A* algorithm!
	public void setTrueDistance(double trueDistance){
		this.trueDistance = trueDistance;
	}
	
	double getTrueDistance(){
		return this.trueDistance;
	}
	
	//use to get the edges associated with that node. I will use in djistrika's and A* algorithms.
	Set<MapEdge> getEdges()
	{
		return edges;
	}
	
	//the way I found online to compare just one part of mapNode:
	public boolean equals(Object o)
	{
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}
		MapNode node = (MapNode)o;
		return node.coordinates.equals(this.coordinates);
	}

	@Override
	public int compareTo(Object o) {
		// convert to map node, may throw exception
		MapNode m = (MapNode)o; 
		return ((Double)this.getDistance()).compareTo((Double)m.getDistance());
	}
	
	public double getArrivalTime(){
		return this.eta;
	}
	
	public void setArrivalTime(double eta){
		this.eta = eta;
	}
	
	public double getTrueArrivalTime(){
		return this.trueEta;
	}
	
	public void setTrueArrivalTime(double eta){
		this.eta = trueEta;
	}
	


	
}

