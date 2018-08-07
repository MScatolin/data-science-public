
/**
 * @author Marcelo Queiroz
 * 
 * Abstract grader class that includes methods to print the most influent users and their Egonets, .
 *	as well as the users that are on different Egonets, and in which ones.
 * This will run in case of an error like and infinite loop (was used through development).
 *
 */

package graph;


import java.util.HashSet;

public abstract class Output implements Runnable {
	 public String status = "";
	 public String feedback = "";
   


    /* Formats output to look nice */
    public static String printOuptut(int totalUsers,int influentUsers, String status, String feedback) {
        return "Common Users for Facebook Subgroups\n\n" + status + "\n\nThere are " + totalUsers + " users on the Dataset.\n"+ influentUsers + " are influent and have their own ego networks.\n\n "+ feedback;
    }

    /* Print the nets with center user and the followers */
    public static String appendNets(int vertex, HashSet<Integer> edges) {
        return "\n**User #" + vertex + ":\n " + edges;
    }

}
