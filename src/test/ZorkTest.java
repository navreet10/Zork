package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Map;
import ZorkApp.ZorkApp;
import ZorkApp.ZorkRoom;

public class ZorkTest {
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ZorkApp.initializeGame();			
		ZorkApp.currentRoom = ZorkApp.listOfRooms.get(1);
		ZorkApp.updateRoom();
	}		

	@Test
	public void UpdateReportTest() {
		ZorkApp.report.setUserActivity(new LinkedList<String>());
		ZorkApp.indexList =0;
		ZorkApp.updateReport("Testing Report");
		assertTrue(ZorkApp.report.getUserActivity().size() == 1);
		assertTrue(ZorkApp.report.getUserActivity().get(0).equals("Testing Report"));
		assertEquals(ZorkApp.report.getUserActivity().get(0), "Testing Report");
		ZorkApp.indexList =0;
		ZorkApp.report.setUserActivity(new LinkedList<String>());
		
	}
	
	@Test
	public void setNameTest() {
		Map<Character, Integer> goesTo = new HashMap<Character, Integer>();
		goesTo.put('S', 3);
		ZorkRoom zorkRoom = new ZorkRoom(5,"dining room","dusty emptybox",100,goesTo);
		zorkRoom.setName("bedroom");
		assertTrue(zorkRoom.getName().equals("bedroom"));
	}
	
	@Test
	public void getPromptTest() {
		Map<Character, Integer> goesTo = new HashMap<Character, Integer>();
		goesTo.put('S', 3);
		ZorkRoom zorkRoom = new ZorkRoom(5,"dining room","dusty emptybox",100,goesTo);
		ZorkApp.currentRoom = zorkRoom;
		assertEquals(ZorkApp.getPrompt("additional text"), 'N');
	}
	
	@Test
	public void updateRoomTest() {
		ZorkApp.report.setRoomCount(0);
		ZorkApp.report.setTotalWinnings(0);
		ZorkApp.report.setUserActivity(new LinkedList<String>());
		ZorkApp.indexList =0;
		Map<Character, Integer> goesTo = new HashMap<Character, Integer>();
		goesTo.put('S', 3);
		ZorkRoom zorkRoom = new ZorkRoom(5,"dining room","dusty emptybox",100,goesTo);
		ZorkApp.currentRoom = zorkRoom;
		ZorkApp.updateRoom();
		assertEquals(ZorkApp.report.getRoomCount(),1);
		System.out.println(ZorkApp.report.getTotalWinnings());
		assertTrue(ZorkApp.report.getTotalWinnings() == 100.0);
	}
	
	@Test
	public void updateDescriptionTest() {
		ZorkApp.initializeGame();
		ZorkApp.updateDescription();
		ZorkApp.history();
		assertEquals(ZorkApp.listOfRooms.get(3).getDesc(), 
				"spiders, and a scroll on the wall");
	}
	
	@Test
	public void getNextRoomIndexTest() {
		Map<Character, Integer> goesTo = new HashMap<Character, Integer>();
		goesTo.put('S', 3);
		ZorkRoom zorkRoom = new ZorkRoom(5,"dining room","dusty emptybox",100,goesTo);
		ZorkApp.currentRoom = zorkRoom;
		ZorkApp.response = 'S';		
		ZorkApp.special = false;
		ZorkApp.nextRoom = ZorkApp.getNextRoomIndex();
		assertTrue(ZorkApp.nextRoom == 3);
	}
	
	@Test
	public void initializeGameTest() {
		ZorkApp.initializeGame();
		ZorkApp.history();
		assertEquals(ZorkApp.listOfRooms.get(8).getDesc(), 
				"piles of gold");
	}

}
