package cs622.hw2.cli.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		search = new JsonSearch(); 
		baos = new ByteArrayOutputStream();
		out = new PrintStream(baos);
	}
	
	
	@AfterEach
	void tearDownEach() throws IOException {
		baos.close();
		out.close();
	}

	@Test
	void testSetTimeStamp() {
		Search spySearch = spy(search);
		assertFalse(spySearch.getTimeStamp().isPresent());
		spySearch.setTimeStamp();
		assertTrue(spySearch.getTimeStamp().isPresent());
	}
	
	
	@Test
	void testRunSearchPopulatesResultObject() throws IOException {
		File f = File.createTempFile("test", "");
		FileWriter fw = new FileWriter(f);
		fw.write("{\"key1\":\"value1\",\"key2\":\"value2\"}\n");
		fw.write("{\"key1\":\"value1 value2\",\"key2\":\"value3\"}");
		fw.close();

		JsonSearch spySearch = spy(new JsonSearch());
		
		// this test is not concerned with getting input arguments from the user or storing search history
		doNothing().when(spySearch).getArgsFromInput(isA(InputStream.class), isA(PrintStream.class));
		SearchHistory historyTarget = mock(SearchHistory.class);
		spySearch.setHistoryTarget(historyTarget);

		spySearch.setSearchString("value1");
		spySearch.setMatchMethod(MatchMethod.KEYWORD);
		spySearch.setIgnoreCase(true);
		spySearch.setSearchFile(f);
		spySearch.run();

		assertTrue(spySearch.getResults().size() == 2);
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
	
	
	@Test
	void testSearchToString() {
		search.setIgnoreCase(false);
		search.setMatchMethod(MatchMethod.KEYWORD);
		search.setSearchFile("file");
		search.setTimeStamp();
		
		String searchParameters = search.toString();
		System.out.println(searchParameters);
		assertTrue(searchParameters.contains("Ignore case: false"));
		assertTrue(searchParameters.contains("Match method: KEYWORD"));
		assertTrue(searchParameters.contains("Search file: file"));
		
		Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");
		Matcher m = p.matcher(searchParameters);
		assertTrue(m.find());
	}
}
