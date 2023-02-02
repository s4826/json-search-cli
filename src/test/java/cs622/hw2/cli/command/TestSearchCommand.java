package cs622.hw2.cli.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs622.hw2.searcher.MatchMethod; 

public class TestSearchCommand {
	
	private Search search;
	private ByteArrayOutputStream baos;
	private PrintStream out;
	
	
	@BeforeEach
	void setUpEach() {
		search = new Search(); 
		baos = new ByteArrayOutputStream();
		out = new PrintStream(baos);
	}
	
	
	@AfterEach
	void tearDownEach() throws IOException {
		baos.close();
		out.close();
	}

	@Test
	void testSearchCreatesTimestamp() {
		Search spySearch = spy(search);
		doNothing().when(spySearch).getArgsFromInput(isA(InputStream.class), isA(PrintStream.class));
		assertNull(spySearch.getTimeStamp());
		spySearch.setSearchString("string").setMatchMethod(MatchMethod.PATTERN).run();
		assertNotNull(spySearch.getTimeStamp());
	}
	
	
	@Test
	void testGetSearchFilePrompt() throws IOException {
		File test = File.createTempFile("test", "");
		test.deleteOnExit();
		String fileName = test.getAbsolutePath(); 
		
		InputStream inputStream = new ByteArrayInputStream(fileName.getBytes());
		Scanner in = new Scanner(inputStream);
		
		search.getSearchFileFromInput(in, out);
		assertTrue(search.getAbsoluteSearchFilePath().equals(fileName));
		
		inputStream.close();
	}
	
	
	@Test
	void testSearchFileDoesntExist() throws IOException {
		File test = File.createTempFile("test", "");
		test.deleteOnExit();
		String exists = test.getAbsolutePath();
		String doesntExist = "/this/file/doesnt/exist";
	
		InputStream inputStream = new ByteArrayInputStream((doesntExist + "\n" + exists + "\n").getBytes());
		Scanner in = new Scanner(inputStream);
		
		search.getSearchFileFromInput(in, out);
		String output = baos.toString();
		assertTrue(output.contains("File does not exist."));
		assertTrue(search.getAbsoluteSearchFilePath().equals(exists));
		
		in.close();
	}
	
	
	@Test
	void testGetSearchStringPrompt() throws IOException {
		
		String testString = "string\n";

		InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
		Scanner in = new Scanner(inputStream);
	
		search.getSearchStringFromInput(in, out);

		String output = baos.toString();
		assertTrue(output.contains("Enter a search string\n? "));
		assertTrue(search.getSearchString().equals("string"));

		in.close();
	}
	
	
	@Test
	void testGetMatchMethodPrompt() throws IOException {
		
		String testString = "p\n";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
		Scanner in = new Scanner(inputStream);
		
		search.getMatchMethodFromInput(in, out);
		
		String output = baos.toString();
		assertTrue(output.contains("Enter a matching method"));
		assertTrue(search.getMatchMethod() == MatchMethod.PATTERN);
		
		in.close();
	}
	
	
	@Test
	void testInvalidMatchMethod() throws IOException {
		
		String testString = "a\np\n";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes());
		Scanner in = new Scanner(inputStream);
		
		search.getMatchMethodFromInput(in, out);
		
		String output = baos.toString();
		assertTrue(output.contains("Invalid match method"));
		
		in.close();
	}
}
