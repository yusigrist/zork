package ch.bbw.zork;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

public class TestParser {

	private Parser parser;
	
	@Before
	public void before() throws UnsupportedEncodingException {

		String cmd = "go west";
		InputStream inputStream = new ByteArrayInputStream( cmd.getBytes( "UTF-8"));
		parser = new Parser (inputStream);
	}

	@Test
	public void testConstructor() {
		Command cmd = parser.getCommand();
		assertNotNull(cmd);
	
	}
}