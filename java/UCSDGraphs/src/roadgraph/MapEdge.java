package roadgraph;

import geography.GeographicPoint;


//Similar to the MapNode class, this class was made to represent the edges of the graph.

public class MapEdge 
{

	// initializing all the parameters of an edge, based on what is asked in the method addEdge in GraphMap (starter code).
	private MapNode start;
	private MapNode end;
	private String name;
	private String type; 
	private double length;
	private int avgSpeed;
	

	static final double MIN_LENGTH = 0.01; //necessary cause some maps don't have length information.

	
	MapEdge(MapNode edgeStart, MapNode edgeEnd, String edgeName, String edgeType,double edgeLength)
	{
		this.name = edgeName;
		start = edgeStart;
		end = edgeEnd;
		this.type = edgeType;
		this.length = edgeLength;
	}
	
	MapEdge(MapNode edgeStart, MapNode edgeEnd,
			String edgeName, String edgeType)  //in case the data of roads length is missing, we use our constant.
			
	{
		this(edgeStart, edgeEnd, edgeName, edgeType, MIN_LENGTH);
	}


	
	MapNode getUpsideDown(MapNode node){ //helper method to find the other vertex in an edge, no matter in which direction
		// Also made a little reference to Stranger Things, :P
		if (node.equals(start)) 
			return end;
		else if (node.equals(end))
			return start;
		throw new IllegalArgumentException("No connection between these points");
	}
	
		// return the location of the start point
		GeographicPoint getStartPoint()
		{
			return start.getLocation();
		}
		
		// return the location of the end point
		GeographicPoint getEndPoint()
		{
			return end.getLocation();
		}
		
		// return the MapNode for the end point
		MapNode getEndNode() {
		   return end;
		}

		Double getLength() {
			return length;
		}
		
		//private Hashset roadTypeDuration = {"unclassified", , , "trunk_link", "primary_link", "secondary_link", "tertiary_link", "living_street"};

		
		int getAvgSpeed(){ //returns the average speed in kilometers per hour for each type of road. 
			int speed = 0;
			
			if (this.type.contains("motorway")){ speed = 120;}
			else if (this.type.contains("trunk")){ speed = 100;}
			else if (this.type.contains("primary")){ speed = 80;}
			else if (this.type.contains("secondary")){ speed = 60;}
			else if (this.type.contains("tertiary")){ speed = 50;}
			else if (this.type.equals("residential")){speed = 30;}
			else if (this.type.equals("living_streetk")){speed = 30;}
			else {speed = 20;}
			
			return speed;
		}
	
}
