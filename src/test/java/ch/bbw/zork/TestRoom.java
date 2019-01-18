package ch.bbw.zork;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestRoom {

	private Room mainRoom;
	
	@Before
	public void before() {
		mainRoom = new Room("Main Room");
	}

	@Test
	public void testExits() {
		Room room1 = Mockito.mock(Room.class);
		mainRoom.setExits(room1, room1, room1, room1);
		
		assertEquals(mainRoom.nextRoom("north"), room1);
		assertEquals(mainRoom.nextRoom("east"), room1);
		assertEquals(mainRoom.nextRoom("south"), room1);
		assertEquals(mainRoom.nextRoom("west"), room1);
		
		}
}