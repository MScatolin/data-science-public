/**
 * @author Marcelo Queiroz
 * 
 * I modified the methods given in the warm up project to attend mine.
 * 
 * This method is the main one.
 * 
 * The project idea is to find out who are the users with more friends, build their respective egonets and later iterate through
 * these egonets in order to find out the users that are in more egonets, i.e., the users that influences the biggest number of 
 * egonets. 
 *
 * To achieve that, I used the proposed CapGraph class with some small changes (added a containVertex method to it's interface Graph).
 * I also created two new similar classes, the friendsNumber, with the user number and the amount of friends that user has, and UserNets,
 * with the user number and the amount of egonets he belongs.
 * 
 * The rest is made of for loops and list methods.
 * 
 * Thanks for reading this code!
 */

package graph;


import java.util.ArrayList;
import java.util.Collections;
import util.GraphLoader;
import graph.CapGraph;
import graph.Graph;

public class WhosInfluent extends Output {

    private static final int TH = 1; //percent of users considered locally influent
	private static final int TH2 = 35; //minimum percent of egonets one must participate to be considered globally influent.
	private static final String FILEPATH = "data/facebook_ucsd.txt";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
        Output output = new WhosInfluent();
        Thread thread = new Thread(output);
        thread.start();
       

        // Safeguard against infinite loops
        long endTime = System.currentTimeMillis() + 30000;
        boolean infinite = false;
        while (thread.isAlive()) {
            if (System.currentTimeMillis() > endTime) {
                thread.stop();
                infinite = true;
                break;
            }
        }
        if (infinite) {
            output.status += "Infinite loop or took longer than 30 seconds to finish.";
        }else {
        	output.status = "Graph Loaded: " + output.feedback;
        }
        
    }

    /* Main grading method */
    public void run() {
    	try {
    		
    		
    		System.out.println("Facebook User's True influence\n\n\nPARAMETERS:\nFile loaded: "+ FILEPATH + "\nLocal Influencers were considered: "+ TH +"%\nGlobal influencers must be on " + TH2 +"% of the EgoNets");;
    		
            Graph graph = new CapGraph();
            ArrayList<FriendsNumber> sortedUsers = new ArrayList<FriendsNumber>(); //list where all users are sorted based on number of Friends
            ArrayList<Graph> topEgonets = new ArrayList<Graph>();//sublist of sortedUsers with the most local influent users only.
            ArrayList<UserNets> userNets = new ArrayList<UserNets>(); //list with the global influent users and the number of nets they belong.
            
            GraphLoader.loadGraph(graph, FILEPATH, sortedUsers);
            
            
            Integer threshold; //threshold for max number of local center
            
            /* uncomment here if want to print the data set for check and/or debug.
               //print whole data set.
            for (Integer i=0;i< sortedUsers.size(); i++) {
                System.out.println(sortedUsers.get(i).printFriends());
            }*/
            
            System.out.println("\nTotal number of Users: " + sortedUsers.size());
            
            threshold = (sortedUsers.size() * TH / 100); //TH percent of users will be considered as local influencer.
            
            System.out.println("\nNumber of Local Influent Users: " + threshold + ". They are: \n");
            
            ArrayList<FriendsNumber> topUsers = new ArrayList<FriendsNumber>(sortedUsers.subList(sortedUsers.size() - threshold, sortedUsers.size()));
            
            Collections.reverse(topUsers);
            
            for (Integer i=0;i< topUsers.size(); i++) {
                System.out.println(topUsers.get(i).printFriends());
                
                Graph egonet = graph.getEgonet(topUsers.get(i).getKey());
                
                topEgonets.add(egonet); //list that contains all the important egonets. I will iterate through this list later.

            }
            
            //iterate through all users in the graph:
            for (Integer users : graph.exportGraph().keySet()){
            	UserNets auxUser = new UserNets(users);         	
            	
            	
            	//iterate for each egonet in topEgonets looking for the current user in the vertices of the Egonets
            	for (Graph egonets : topEgonets){
            		
            	if (egonets.containVertex(users)){
            		
            		//check for current user in that vertex, if true, increment UserNets
            		auxUser.addFriend();
            	}
            		
            	}
            	if(auxUser.getFriends()>topEgonets.size()*TH2/100){ //if the user pass the TH2 threshold, we add to the output list
            	userNets.add(auxUser);
            	}
            	
            }
            
          
            
            System.out.println("\nNumber of truly Influent Users: " + userNets.size() + ". They are: \n");
            
            Collections.sort(userNets);
            Collections.reverse(userNets);
            
            for (Integer j = 0; j < userNets.size(); j++){
            	System.out.println(userNets.get(j).printFriends()); //printing output
            }
            
            
            //exception in case some error happens
        } catch (Exception e) {
            feedback = "An error occurred during runtime.\n" + feedback + "\nError during runtime: " + e;
            e.printStackTrace();
        }
    }
    
   
}
