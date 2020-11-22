
public class Team {
	private
	String teamName;
	int lastGoaler;
	int score;
	
	Player[] team = new Player[11];
	Couch couch;
	Doctor doc;
		
	public
	
	Team(String teamName) {	//creating a team
		this.teamName = teamName;
		this.score = 0; 
	
	}
	
	// handling players
	void addPlayer(String teamName,int no){ team[no] = new Player(teamName);}
	String getPlayer(int no) { return team[no].getName();}
	
	// handling couches
	void setCouch(String couchName) {couch = new  Couch(couchName);}
	String getCouch() {return couch.getName();}
	
	// handling doctors
	void setDoctor(String docName) {doc = new  Doctor(docName);}
	String getDoctor() {return doc.getName();}

	// handling score
	void setScore(int score) {this.score = score;}
	int getScore() {return score;}
	
	// handling last goaler
	void setlastGoaler(int lastGoaler) {this.lastGoaler = lastGoaler;}
	int getlastGoaler() {return lastGoaler;}
	
	String getteamName() {return teamName;}
	
	
}
