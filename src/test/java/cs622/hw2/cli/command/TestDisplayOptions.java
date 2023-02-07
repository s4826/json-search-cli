package cs622.hw2.cli.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import cs622.hw2.cli.command.history.SearchHistory;


public class TestDisplayOptions {
	
	private static DisplayOptions options = DisplayOptions.getInstance();
	
	@Test
	void testSetPrintAllFieldsFlag() {
		options.setPrintAllFieldsFlag(true);
		assertTrue(options.getPrintAllFieldsFlag());
	}
	
	
	@Test
	void testSetDisplayFields() {
		options.setDisplayFields("one", "two");
		assertTrue(options.getDisplayFields().contains("one"));
		assertTrue(options.getDisplayFields().contains("two"));
	}
	
	
	@Test
	void testSetDisplayFieldsLimitsDisplayFields() throws IOException {
		File test = File.createTempFile("test", "");
		FileWriter fw = new FileWriter(test);
		fw.write("{\"key1\":\"value1\", \"key2\":\"value2\"}");
		fw.close();
		
		options.setDisplayFields("key1");
		options.setPrintAllFieldsFlag(false);
		
		JsonSearch jsonSearch = spy(TestSearchHistory.createSearch("value2"));
		jsonSearch.setSearchFile(test);

		PrintStream stdout = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		System.setOut(out);
		
		doNothing().when(jsonSearch).getArgsFromInput(isA(InputStream.class), isA(PrintStream.class));
		jsonSearch.setHistoryTarget(mock(SearchHistory.class));
		jsonSearch.run();
	
		assertTrue(baos.toString().contains("value1"));
		assertFalse(baos.toString().contains("value2"));
		
		
		baos.close();
		out.close();
		System.setOut(stdout);
	}
}
