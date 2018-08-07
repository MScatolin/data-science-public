/**
 * @author Marcelo Queiroz
 * 
 * This class implements Graph and is the main data strucuture of the code. Holds the keys that are each user's code, and the friends 
 * that user have.
 * 
 * Besides the structure implemented for the warm up assignment, I added a few simple methods to facilitate reading the code at
 * WhosInfluent.
 * 
 */
package graph;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;



public class CapGraph implements Graph {
	
	private Map<Integer, HashSet<Integer>> nodes = new HashMap<>();
	
	
	@Override //method that returns the number of users.
	public Integer size(){
		return nodes.size();
	}
	
	@Override //method to add a vertex
	public void addVertex(int num) {
		if (!nodes.containsKey(num))
			nodes.put(num, new HashSet<>());
	}
	


	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		nodes.get(from).add(to);
	}
	

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		Graph egoNet = new CapGraph();
		egoNet.addVertex(center);
		HashSet<Integer> neighbors = nodes.get(center);
		for (Integer node : neighbors){
			
			egoNet.addVertex(node);
			
			egoNet.addEdge(center, node);
			
			nodes.get(node).stream().filter(neighbors::contains).forEach(nodeNeighbour -> {
                egoNet.addVertex(nodeNeighbour);
                egoNet.addEdge(node, nodeNeighbour);
            });
		}

		
		return egoNet;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		List<Graph> SCCs = new ArrayList<>();
        Stack<Integer> vertices = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> ordered = new HashMap<>();
        Counter counter = new Counter();

        nodes.keySet().stream().filter(currNode -> visited.contains(currNode) == false)
                .forEach(currNode -> tarjanSCC(currNode, SCCs, vertices, visited, ordered, counter));

        return SCCs;
    }

    private void tarjanSCC(Integer currNode, List<Graph> SCCs, Stack<Integer> vertices, Set<Integer> visited,
                           Map<Integer, Integer> ordered, Counter counter) {
    	ordered.put(currNode, counter.increment());
        visited.add(currNode);
        vertices.push(currNode);
        boolean isRoot = true;

        for (Integer neighbour : nodes.get(currNode)) {
            if (!visited.contains(neighbour))
                tarjanSCC(neighbour, SCCs, vertices, visited, ordered, counter);
            if (ordered.get(currNode) > ordered.get(neighbour)) {
            	ordered.put(currNode, ordered.get(neighbour));
                isRoot = false;
            }
        }
        
        if (isRoot) {
            Graph component = new CapGraph();

            Integer node;
            do {
                node = vertices.pop();
                component.addVertex(node);
                ordered.put(node, Integer.MAX_VALUE);
            } while (!currNode.equals(node));

            SCCs.add(component);
        }
    }

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		return new HashMap<>(nodes);
    }
	@Override
	public HashSet<Integer> exportFriends(Integer center){
		return nodes.get(center);
	}
	
	@Override
	public Boolean containVertex(Integer vertex){
		if (nodes.containsKey(vertex)){
			return true;
		}
		return false;
	}
	
	
    private static class Counter {
        private int c = 0;

        int increment() {
            return c++;
        }
	}


}
