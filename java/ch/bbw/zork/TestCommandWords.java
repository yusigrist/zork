package ch.bbw.zork;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCommandWords {

	private CommandWords cws1;
	
	@Before
	public void before() {
		cws1 = new CommandWords();
	}

	@Test
	public void testSecoundWord() {
		String showCmds = cws1.showAll();
		String[] cmds = showCmds.split("  ");
		for (String cmd : cmds) {
			assertTrue(cws1.isCommand(cmd));
		}
	}
}