/**
 * @author UCSD MOOC development team and Marcelo Queiroz	
 * 
 * Utility class to add vertices and edges to a graph.
 * 
 * I modified to fit the needs of my WhosInfluent class. Basically I added the userSorted list to the main method loadGraph.
 * This was made for code clarity and performance purposes, once the sorted list will be build the same time the graph is.
 *
 */
package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


import graph.FriendsNumber;

public class GraphLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
    public static void loadGraph(graph.Graph graph, String filename, ArrayList<FriendsNumber> usersSorted) {
        Set<Integer> seen = new HashSet<Integer>();
        Set<Integer> seenAux = new HashSet<Integer>();
        FriendsNumber user = null;
        Integer index = 0;
        
        
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
        	
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            
            
            if (!seen.contains(v1)) {
                graph.addVertex(v1);
                seen.add(v1);
            } 
            
            if (!seenAux.contains(v1)){
            	seenAux.add(v1);
            	index++;
                user = new FriendsNumber(v1);
                usersSorted.add(user);
            	
            }
            if (!seen.contains(v2)) {
                graph.addVertex(v2);
                seen.add(v2);
            }
            
            graph.addEdge(v1, v2);
            user.addFriend();
            usersSorted.set(index-1, user);
            //user.printFriends(user);
            
            
        }

        Collections.sort(usersSorted);
        
        sc.close();
    }
}
