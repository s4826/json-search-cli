/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains tests for the SearchHistory class
 */
package cs622.hw2.cli.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs622.hw2.cli.command.history.SearchHistory;
import cs622.hw2.searcher.MatchMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

public class TestSearchHistory {
	
	SearchHistory history;
	
	ByteArrayOutputStream baos;
	PrintStream out;
	
	PrintStream stdout;
	
	@BeforeEach
	void setUpEach() {
		stdout = System.out;
		
		history = spy(new SearchHistory());
		baos = new ByteArrayOutputStream();
		out = new PrintStream(baos);
		
		System.setOut(out);
	}
	
	
	@AfterEach
	void tearDownEach() throws IOException {
		baos.close();
		out.close();
		System.setOut(stdout);
	}
	
	
	@Test
	void testPrintUniqueSearchTerms() {
		history.put("robot", createSearch("robot"));
		history.put("robot", createSearch("robot"));
		String[] lines;
		
		history.printUniqueSearchTerms();
		lines = baos.toString().split("\\n");
		assertTrue(lines[1].equals("robot: 2 times"));
		baos.reset();
		
		history.put("dance", createSearch("dance"));
		history.printUniqueSearchTerms();

		lines = baos.toString().split("\\n");
		assertTrue(lines[1].equals("robot: 2 times"));
		assertTrue(lines[2].equals("dance: 1 time"));
	}
	
	
	@Test
	void testPrintTimeStamps() {
		history.put("robot", createSearch("robot"));
		history.put("robot", createSearch("robot"));
		history.put("dance", createSearch("dance"));
		history.printSearchTimeStamps();

		Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		Matcher m = p.matcher(baos.toString());
		
		assertTrue(m.results().count() == 3);
	}

	
	/**
	 * Create a fake search for search history testing
	 * @param s search string for this fake search
	 * @return JsonSearch object
	 */
	private JsonSearch createSearch(String s) {
		JsonSearch search = new JsonSearch();
		search.setSearchString(s);
		search.setIgnoreCase(false);
		search.setMatchMethod(MatchMethod.KEYWORD);
		search.setTimeStamp();
		
		return search;
	}

}
