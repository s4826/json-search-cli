package cs622.hw2.cli.command.history;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cs622.hw2.cli.command.history.HistoryQuery;
import cs622.hw2.cli.command.history.SearchHistory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class TestHistoryQuery {
	
	private PrintStream stdout;

	private HistoryQuery hq;

	private PrintStream out;
	private ByteArrayOutputStream baos;
	
	@BeforeEach
	void setUpEach() {
		hq = new HistoryQuery();
		
		stdout = System.out;
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
	void testHistoryInputPrompts() throws IOException {
		InputStream in = new ByteArrayInputStream("all".getBytes());
		hq.getOptFromInput(new Scanner(in), out);

		String prompt = baos.toString();
		assertTrue(prompt.contains("Print all searches (a/all)"));
		assertTrue(prompt.contains("Number of unique search terms with associated frequency (u/unique)"));
		assertTrue(prompt.contains("Timestamps of all searches (t/time"));
	}
	

	@Test
	void testRunGetAllSearches() {
		SearchHistory history = spy(new SearchHistory());
		hq.addAvailableCommand("all", history::printAllSearches);
		
		InputStream in = new ByteArrayInputStream("all".getBytes());
		InputStream stdin = System.in;
		
		System.setIn(in);
		hq.run();

		verify(history, times(1)).printAllSearches();
		
		System.setIn(stdin);
	}
}
