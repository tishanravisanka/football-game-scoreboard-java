
public class Player extends Person {

	private
	int goals;
	
	public Player(String name) {	//creating a player
		super(name);
		goals = 0;
	}
	
	void setGoals(int goals) {this.goals = goals;}
	int getGoals() {return goals;}
	


}
