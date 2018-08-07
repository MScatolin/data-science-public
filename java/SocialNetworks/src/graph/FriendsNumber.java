package graph;

public class FriendsNumber implements Comparable<FriendsNumber> {
	 Integer vertex;
	 Integer numFriends;
	
	public FriendsNumber(Integer vertex){
		this.vertex = vertex;
		this.numFriends = new Integer(0);
	}
	
	@Override
	public int compareTo(FriendsNumber o) {
		return (this.numFriends - o.numFriends);
	}
	
	
	public void addFriend(){
		this.numFriends ++;	
		
	}
	
	public Integer getKey(){
		return this.vertex;
	}
	
	public Integer getFriends(FriendsNumber user){
		return this.numFriends;
	}
	
	public String printFriends(){
		return (("User:" + this.vertex + ", Friends: " + this.numFriends));
	}
}
