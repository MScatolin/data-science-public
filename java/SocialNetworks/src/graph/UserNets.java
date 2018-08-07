/**
 * @author marceloqueiroz
 * 
 * Class implemented for output presentation. This is a structure that stores the users as keys and the number of important egonets
 * that he belongs to. This classes implements Comparable for sorting purposes and has simple methods for manipulating the structure.
 * 
 */

package graph;

public class UserNets implements Comparable<UserNets> {
	 Integer vertex;
	 Integer numNets;
	
	public UserNets(Integer vertex){ //add a new users, without any nets.
		this.vertex = vertex;
		this.numNets = new Integer(0);
	}
	
	@Override //used to sort theses elements by number of egonets, not by the key.
	public int compareTo(UserNets o) {
		return (this.numNets - o.numNets);
	}
	
	public void addFriend(){ //increments the number of egonets this users belongs.
		this.numNets ++;	
		
	}
	
	public Integer getKey(){
		return this.vertex; //return the Integer that is the user code.
	}
	
	public Integer getFriends(){ //return the number of egonets that user belongs to.
		return this.numNets;
	}
	
	public String printFriends(){ //print method used for output.
		return (("User:" + this.vertex + ", Nets: " + this.numNets));
	}
}
