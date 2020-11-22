import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;


// 75% chance to pass the ball to same team
// 2% chance to score a goal
// 10% chance to get the ball out of the field

public class ScoreBoard {
	
	// note: time was converted 45mit= 15s, 15mit = 5s
	static final int firstHalfTime = 15;	
	static final int intervalTime = 5;
	static final int secondHalfTime = 15;
	static final int extraTime = 10;

	static void passBall(int[] arr ) {
		Random rn = new Random();
		int peviousPlayer=arr[1];  
		 
		if(rn.nextInt(100) < 2)				// score a goal
			arr[0] = 3;			
		else if(rn.nextInt(100) < 10)		// ball out of ground
			arr[0] = 4;  
		else if(rn.nextInt(100) <= 25) {	// pass the ball to a another player (team change)

			if(arr[0] == 1)				
				arr[0] = 2; 
			else 
				arr[0] = 1;		
			  
			arr[1] = rn.nextInt(11);

		}
		else {									// pass the ball to a another player (team not change)
			arr[1] = rn.nextInt(11);
			while( peviousPlayer == arr[1])   
				arr[1] = rn.nextInt(11);
		}
		// if last time score a goal or ball goes out of ground			
		if(arr[3]==1) {						
			arr[0] = 1;
			arr[3] = -1; 
		}
		else if(arr[3]==2) {
			arr[0] = 2;
			arr[3] = -1;
		}
			
	}
	 
	// function to make a delay
	public static void wait(int ms) {
	    try {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}

	public static void main(String[] args) {
		
		// creating objects of classes
		Team team1 = null,team2 = null;
		Referee mainRef;
		Referee[] ref = new Referee[4];
		mainRef = new Referee("Jeff");
		ref[0] = new Referee("Mason");
		ref[1] = new Referee("Hardy");
		ref[2] = new Referee("Jack");
		ref[3] = new Referee("Ken");
		
		Random rn = new Random();
		
		// variable declaration
		int previewsPlayer = 1;
        String previewsTeam = ""; 
		int[] arr = new int[4];
		Arrays.fill(arr, 0); 			// fill the array with zeros
		long startTime = 0;
        long remainTime = 0;
        boolean flag = true;
        
        // file reading part
		try{  
			File file = new File("team1.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
	
			// read names line by line team 1
			team1 = new Team(br.readLine());
			team1.setCouch(br.readLine());
			team1.setDoctor(br.readLine());
			for(int i=0;(line = br.readLine()) != null;i++) 	//read players
				team1.addPlayer(line,i);
			
			br.close();
			fr.close();
			file = new File("team2.txt");
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			// read names line by line team 2
			team2 = new Team(br.readLine());
			team2.setCouch(br.readLine());
			team2.setDoctor(br.readLine());
			for(int i=0;(line = br.readLine()) != null;i++)		//read players
			    team2.addPlayer(line,i);
		
			br.close();    
	        fr.close(); 
		
		}
		catch(IOException ex) {
			System.out.println("\n\nError : File not found !!!!");
			System.exit(0);  
		}
		
		// display teams and referees
		System.out.println("\nMain referee: "+ mainRef.getName());
		for(int i=0;i<4;i++)
			System.out.println("Referee " + i + ": " + ref[i].getName());

		System.out.println("\nTeam: "+ team1.getteamName()+ "\t\tTeam: "+ team2.getteamName());
		System.out.println("\nCouch: "+ team1.getCouch()+ "\t\tCouch: " + team2.getCouch());
		System.out.println("Doctor: "+ team1.getDoctor()+ "\t\tDoctor: " + team2.getDoctor());
		for(int i=0;i<10;i++)	
			System.out.println(team1.getPlayer(i)+ "\t\t\t" + team2.getPlayer(i));
		System.out.println(team1.getPlayer(10)+ "(goalie)" + "\t\t" + team2.getPlayer(10) + "(goalie)");
		 
		// toss the coin to select who is going to play first
		int fistPlay = rn.nextInt(2);
		
		if(fistPlay==0) {
			arr[0]= 1;
			System.out.println("\n\nTeam: " + team1.getteamName()+ " won the toss and will play first"); 
		}
		else {
			arr[0]= 1; 
			System.out.println("\n\nTeam: " + team2.getteamName()+ " won the toss and will play first\n\n");
		}
			
		
		/*
		 * round 1 - first half
		 * round 2 - interval
		 * round 3 - second half
		 * round 4 - extra time
		 * */
		for(int round = 1; round <= 4;round++) {
			
			// set play time
			startTime =   System.currentTimeMillis()/1000;
			if(round==1)
				remainTime = firstHalfTime;
        	else if(round==2) 
        		remainTime = intervalTime;
        	else if(round==3)
        		remainTime = secondHalfTime;
        	else
        		remainTime = extraTime;
			

            while(remainTime>0) {  
            	
            	wait(100);
            	
            	// display the round 
            	if(round==1)
           	 		System.out.println("\n\nRound : "+ round);
            	else if(round==2) 
            		System.out.println("\n\nResting Time"); 
            	else if(round==3)
           	 		System.out.println("\n\nRound : "+(round-1)); 
            	else if(team1.getScore() == team2.getScore()) {
            		if(flag) {
            			System.out.println("\n\nScores are equal extra time will be given");
            			flag = false;
            		}
            		
            		System.out.println("\n\nExtra Time");
            	}
            		
            	
            	
           	 	System.out.println(" _______________________________________________");
           	 	
           	 	// display remaining time
           	 	if(round==1) { 
                	System.out.print("|\t"+"   Frist half ["+remainTime+"] min left\t\t|\n");
                	remainTime = firstHalfTime - (System.currentTimeMillis()/1000 - startTime);
           	 	}
           	 	else if(round==2) {
           	 		System.out.print("|\t"+"   Interval ["+remainTime+"] min left\t\t|\n");
          			remainTime = intervalTime - (System.currentTimeMillis()/1000 - startTime);
           	 	}
           	 	else if(round==3) {           	 		
	               	System.out.print("|\t"+"   Second half ["+remainTime+"] min left\t\t|\n");
	                remainTime = secondHalfTime - (System.currentTimeMillis()/1000 - startTime);           	 
           	 	}
           	 	else if(round==4 && (team1.getScore() == team2.getScore())) {           	 		
	               	System.out.print("|\t"+"   Extra Time ["+remainTime+"] min left\t\t|\n");
	                remainTime = extraTime - (System.currentTimeMillis()/1000 - startTime);           	 
        	 	}
           	 	else 
           	 		remainTime =-1;
           		 
           		 
           	 	
             
	           	// display team name and score 	
	            System.out.print("|" + team1.getteamName()); 
	            System.out.print("\t\t\t"); 
	            System.out.print(team2.getteamName() + "\t|\n"); 
	            
	            System.out.print("|" + team1.getScore());
	            System.out.print("\t\t\t\t");
	            System.out.print(team2.getScore() + "\t\t|\n"); 
	             
	            // display the last goaler
	            if(team1.getScore() > 0) 
	            	System.out.print("|Last Goal: " + team1.getPlayer(team1.getlastGoaler()) + "\t\t");
	            else
	            	System.out.print("|Last Goal: " +  "\t\t\t");
	            if(team2.getScore() > 0) 
	            	System.out.println("Last Goal: " + team2.getPlayer(team2.getlastGoaler()) + " |");
	            else
	            	System.out.println("Last Goal: " + "\t|");
	            
	            if(round!=2) {
	            	
		            passBall(arr);	// pass the ball to a another player or out of ground or score goal
		            
		            // display player who had the ball previously
		            if(arr[0] == 1 || arr[0] == 2) {
			            if(previewsTeam == team1.getteamName() && fistPlay!=0 && fistPlay!=1) 
			    			System.out.println("|Team: " + previewsTeam + " Player: " + team1.getPlayer(previewsPlayer)+" pass the ball\t|");
			
			            else if(fistPlay!=0 && fistPlay!=1)
			    			System.out.println("|Team: " + previewsTeam + " Player: " + team2.getPlayer(previewsPlayer)+" pass the ball\t|");
		            } 
		            
		            // team 1 player got the ball
		            if(arr[0] == 1) {   			
		
		    			System.out.println("|Team: " + team1.getteamName() + " Player: "  + team1.getPlayer(arr[1]) + " has the ball\t|");
		    			previewsPlayer = arr[1];
		    			previewsTeam = team1.getteamName();
		    			fistPlay = -1;
		    		}
		            
		            // team 2 player got the ball
		    		else if(arr[0] == 2) {    
		    			
		    			System.out.println("|Team: " + team2.getteamName() + " Player: "  + team2.getPlayer(arr[1]) + " has the ball\t|");
		    			previewsPlayer = arr[1];
		    			previewsTeam = team2.getteamName();     			 
		    			fistPlay = -1;
		    		}
		            // scoring a goal
		    		else if(arr[0] == 3) {	    			
		    			
		    			if(previewsTeam == team1.getteamName()) {
		    				fistPlay = 1;
		    				arr[3] = 2;
		    				System.out.println("|Team: " + team1.getteamName() + " " + team1.getPlayer(previewsPlayer) + " scored a goal!!!\t\t|");
		    				team1.setScore(team1.getScore()+1);
		    				team1.setlastGoaler(previewsPlayer);
		    			}
		    			else {
		    				fistPlay = 0;
		    				arr[3] = 1;
		    				System.out.println("|Team: " + team2.getteamName() + " "+ team2.getPlayer(previewsPlayer) + " scored a goal!!!\t\t|");
		    				team2.setScore(team2.getScore()+1);
		    				team1.setlastGoaler(previewsPlayer);
		    			}
		    		}
		            
		            // play the ball out of the ground
		    		else if(arr[0] == 4) {
		    			
		    			if(previewsTeam == team1.getteamName()) {
		    				System.out.println("|" + team1.getPlayer(previewsPlayer) + " played the ball out of the ground  \t|");
		    				arr[3] = 2;
		    				fistPlay = 1;
		    			}
		    				
		    			else {
		    				System.out.println("|" + team2.getPlayer(previewsPlayer) + " played the ball out of the ground  \t|");
		    				arr[3] = 1;
		    				fistPlay = 0;
		    			}
		    				
		    		}
	    			 
	            }

	            	System.out.print("|_______________________________________________|\n\n");
       	
            }            
            	
			
		}
		
		// check for penalty
		if(team1.getScore() == team2.getScore()) {
			
			System.out.println("Scores are Equal Penalty will be played\n");
			
			System.out.println("\nTeam: " + team1.getteamName()+" is playing\n");
			for(int x = 0 ;x < 5;x++) {
                if(rn.nextInt(5) == 1) {
                	int player =rn.nextInt(11);
                	System.out.println(team1.getPlayer(player)+" has Scored a goal");
                	team1.setScore(team1.getScore()+1);
                	team2.setlastGoaler(player);
                }
                else
                	System.out.println(team1.getPlayer(rn.nextInt(11))+" didn't Scored a goal");
            } 
			
			System.out.println("\n\nTeam: " + team2.getteamName()+" is playing\n");			
			for(int x = 0 ;x < 5;x++) {
                if(rn.nextInt(5) == 1) {
                	int player =rn.nextInt(11);
                	System.out.println(team2.getPlayer(player)+" has Scored a goal");
                	team2.setScore(team2.getScore()+1);
                	team2.setlastGoaler(player);
                }
                else
                	System.out.println(team2.getPlayer(rn.nextInt(11))+" didn't Scored a goal");
             }
       
			// display team name and score 	
			System.out.println("\n\n _______________________________________________");
	        System.out.print("|" + team1.getteamName()); 
	        System.out.print("\t\t\t"); 
	        System.out.print(team2.getteamName() + "\t|\n"); 
	        
	        System.out.print("|" + team1.getScore());
	        System.out.print("\t\t\t\t");
	        System.out.print(team2.getScore() + "\t\t|\n"); 
	        
	        // display the last goaler
            if(team1.getScore() > 0) 
            	System.out.print("|Last Goal: " + team1.getPlayer(team1.getlastGoaler()) + "\t\t");
            else
            	System.out.print("|Last Goal: " +  "\t\t\t");
            if(team2.getScore() > 0) 
            	System.out.println("Last Goal: " + team2.getPlayer(team2.getlastGoaler()) + " |");
            else
            	System.out.println("Last Goal: " + "\t|");
	        System.out.print("|_______________________________________________|\n\n");
		}
		
		
        
        
		if(team1.getScore() > team2.getScore())
			System.out.println(team1.getteamName() +" has won the game");
		else if(team1.getScore() < team2.getScore())
			System.out.println(team2.getteamName() +" has won the game");
		else {
			System.out.println(" Toss will be played to select the winners\n");
			if(rn.nextInt(2) == 0)
				System.out.println(team1.getteamName() +" has won the game");
			else
				System.out.println(team2.getteamName() +" has won the game");
			
		}
			 
	
	}
}
