package ZorkApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import NumberUility.NumberUtility;

public class ZorkApp {
	public static Map<Integer, ZorkRoom> listOfRooms;
	public static int thief;
	public static int lamp;
	public static boolean special = false;
	public static boolean lampFound = false;
	public static ZorkRoom currentRoom;
	public static Scanner in;
	public static char response;
	public static Report report;
	public static List<String> userActivity ;
	public static int indexList;
	public static Random rnd;
	public static Integer nextRoom;	
	
	public static void main(String[] args) {
		try {
			initializeGame();
			
			rnd = new Random();
			thief = rnd.nextInt(7) + 1;
			lamp = rnd.nextInt(7) + 1;	
			currentRoom = listOfRooms.get(nextRoom);
			updateRoom();
			
			while (true) {	
				if (currentRoom.getNum() == lamp && !lampFound) {								
					response = getPrompt(" or Get Lamp 'L'");
					updateReport("Received lamp");
				} else if (currentRoom.getNum() == thief){
					report.setTotalWinnings(0);
					updateReport("Encountered thief");
					System.out.println("Winnings: " + NumberUtility.formatInCcurrency( report.getTotalWinnings()));
					response = getPrompt("");
				} else if (currentRoom.getNum() == 3 && lampFound){				
					response = getPrompt(" or Press 'R' to read scroll");
				} else {
					response = getPrompt("");
				}
							
				if (response == 'N' || response == 'S' || response == 'W' || response == 'E' || response == 'R') {				
					int roomIndex = getNextRoomIndex();
					if (roomIndex != -1) {
						System.out.println(nextRoom);
						currentRoom = listOfRooms.get(nextRoom);
						updateRoom();
					}	
					if (nextRoom == 9) {
						System.out.println("Sorry! you lose");
						break;
					}
				} else if (response == 'Q'){
					break;
				} else if (response == 'H') {
					history();				
				} else if (response == 'L') {
					updateDescription();
					lampFound = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
			
		
		

	}
	public static void updateReport(String message) {
		
		userActivity = report.getUserActivity();
		userActivity.add(indexList,message);
		report.setUserActivity(userActivity);
		indexList++;
	}
	public static char getPrompt (String moreText) {
		System.out.println("You are standing in the " + currentRoom.getName());
		System.out.println("You see "+currentRoom.getDesc());
		System.out.println("You can exit to the north 'N' or south 'S'"
				+ " or west 'W' or east'E' or type 'Q' quit or type 'H' "
				+ "to get history "+moreText);		
		in = new Scanner(System.in);
		return(in.next().toCharArray()[0]);		
	}
	
	public static void updateRoom() {
		userActivity = report.getUserActivity();
		report.setTotalWinnings(report.getTotalWinnings() + currentRoom.getMoney());
		currentRoom.setMoney(0);
		report.setRoomCount(report.getRoomCount()+1);
		userActivity.add(indexList,"Room Visited " + currentRoom.getNum() 
		+ " and Winnings: " + report.getTotalWinnings());
		report.setUserActivity(userActivity);
		indexList++;
	}
	
	public static int getNextRoomIndex() {
		
		if (response == 'R') {
			nextRoom = 8;
		} else if (currentRoom.getNum() == 6) {
			int randomInt = rnd.nextInt(4);
			if (randomInt == 3) {
				special = true; 
				nextRoom = 8;
			} else {
				nextRoom = 7;				
			}			
		} else if (currentRoom.getNum() == 3) {
			if (lampFound) {
				nextRoom = 8;
			} else {
				int randomInt = rnd.nextInt(4);
				if (randomInt == 3 || randomInt == 2) {
					
					nextRoom = 9;
				} else {
					nextRoom = 5;				
				}	
			}
					
		} else {
			nextRoom = currentRoom.getGoesTo().get(response);
			if (nextRoom == null) {
				System.out.println("You selected an invalid room. Try again...");
				nextRoom = -1;
			} 
		}
		return nextRoom;
		
		
		
	}

	public static void history() {
		for(String out: report.getUserActivity()) {
			System.out.println(out);
		}
		System.out.println("Winnings: " +  NumberUtility.formatInCcurrency( report.getTotalWinnings()));
		System.out.println("Rooms visited: " + report.getRoomCount());
	}

	public static void updateDescription() {		
		listOfRooms.get(1).setDesc(listOfRooms.get(1).getDesc() + " and a spider web of gold and silver");
		listOfRooms.get(2).setDesc(listOfRooms.get(2).getDesc() + " and sheet music for Blank Space");
		listOfRooms.get(3).setDesc(listOfRooms.get(3).getDesc() + " and a scroll on the wall");
		listOfRooms.get(4).setDesc(listOfRooms.get(4).getDesc() + " and refrigerator full of sweets and ice cream");
		listOfRooms.get(5).setDesc("a dusty box with a gift card");
		listOfRooms.get(6).setDesc(listOfRooms.get(6).getDesc() + " and bats");
		listOfRooms.get(7).setDesc(listOfRooms.get(7).getDesc() + " and SRK portrait with tickets to Conjuring2");
		
		
	}

	public static void initializeGame() {
		report = new Report();	
		indexList = 0;
		nextRoom = 1;
		listOfRooms = new HashMap<Integer, ZorkRoom>();
		Random rnd = new Random();
		Map<Character, Integer> goesTo = new HashMap<Character, Integer>();
		goesTo.put('S', 3);
		ZorkRoom zr1 = new ZorkRoom(5,"dining room","dusty emptybox",rnd.nextInt(1000),goesTo);
		listOfRooms.put(5,zr1);
		
		goesTo = new HashMap<Character, Integer>();		
		goesTo.put('E', 7);		
		ZorkRoom zr2 = new ZorkRoom(6,"vault","3 walking skeletons",rnd.nextInt(1000),goesTo);
		listOfRooms.put(6,zr2);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('W', 6);
		goesTo.put('S', 4);		
		ZorkRoom zr3 = new ZorkRoom(7,"parlor", "treasurechest",rnd.nextInt(1000),goesTo);
		listOfRooms.put(7, zr3);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('E', 2);
		goesTo.put('N', 5);		
		ZorkRoom zr4 = new ZorkRoom(3,"library", "spiders,",rnd.nextInt(1000),goesTo);
		listOfRooms.put(3, zr4);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('S', 1);
		goesTo.put('W', 3);	
		goesTo.put('E', 4);
		ZorkRoom zr5 = new ZorkRoom(2,"front room", "piano,",rnd.nextInt(1000),goesTo);
		listOfRooms.put(2, zr5);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('W', 2);
		goesTo.put('N', 7);			
		ZorkRoom zr6 = new ZorkRoom(4,"kitchen", "bats",rnd.nextInt(1000),goesTo);		
		listOfRooms.put(4, zr6);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('N', 2);			
		ZorkRoom zr7 = new ZorkRoom(1,"foyer", "dead scorpion",rnd.nextInt(1000),goesTo);
		listOfRooms.put(1, zr7);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('W', 6);			
		ZorkRoom zr8 = new ZorkRoom(8,"secret room", "piles of gold",rnd.nextInt(1000),goesTo);
		listOfRooms.put(8, zr8);
		
		goesTo = new HashMap<Character, Integer>();
		goesTo.put('E', 1);			
		ZorkRoom zr9 = new ZorkRoom(9,"dungeon", "dragon in the jail next to you",rnd.nextInt(1000),goesTo);
		listOfRooms.put(9, zr9);
		
	}

}