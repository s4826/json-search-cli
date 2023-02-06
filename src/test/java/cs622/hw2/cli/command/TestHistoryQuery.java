package cs622.hw2.cli.command;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestHistoryQuery {
	
	@Test
	void testHistoryInputPrompts() throws IOException {
		HistoryQuery hq = new HistoryQuery();
		
		PrintStream stdout = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream out = new PrintStream(baos);
		InputStream in = new ByteArrayInputStream("all".getBytes());
		
		System.setOut(out);
		
		hq.getOptsFromInput(new Scanner(in), out);
		String prompt = baos.toString();
		assertTrue(prompt.contains("Print all searches (a/all)"));
		assertTrue(prompt.contains("Number of unique search terms with associated frequency (u/unique)"));
		assertTrue(prompt.contains("Timestamps of all searches (t/time"));
		
		System.setOut(stdout);
		
		baos.close();
		out.close();
	}
}
