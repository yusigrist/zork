package ch.bbw.zork;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCommand {
	
	private Command cmd1;
	private Command cmd2;
	private Command cmd3;
	
	@Before
	public void before() {
		cmd1 = new Command("go", "west");
		cmd2 = new Command("quit", null);
		cmd3 = new Command(null , null);
	}

	@Test
	public void testConstructor() {
		assertEquals(cmd1.getCommandWord(), "go");
		assertEquals(cmd1.getSecondWord(), "west");
	}
	


	@Test
	public void testUnknown() {
		assertFalse(cmd1.isUnknown());
		assertFalse(cmd2.isUnknown());
	}
	
	@Test
	public void testSecondWord() {
		assertTrue(cmd1.hasSecondWord());
		assertFalse(cmd2.hasSecondWord());
	}
	
	@Test
	public void testConstructorOneWord() {
		assertEquals(cmd2.getCommandWord(), "quit");
		assertEquals(cmd2.getSecondWord(), null);
	}
	
	//Ungültig
	@Test
	public void testConstructorNull() {
		assertEquals(cmd3.getCommandWord(), null);
		assertEquals(cmd3.getSecondWord(), null);
	}
	
	@Test
	public void testMethodes() {
		assertTrue(cmd3.isUnknown());
		assertFalse(cmd3.hasSecondWord());
	}
	
	
}