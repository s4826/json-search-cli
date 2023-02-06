/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains functionality for history queries.
 */
package cs622.hw2.cli.command;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;


/**
 * Class to encapsulate a history query.
 */
public class HistoryQuery implements Command {
	
	private SearchHistory historyTarget;
	private Map<String, Callable<Void>> historyFunctions;
	
	public HistoryQuery() {
		historyFunctions = new HashMap<>();
	}
	
	
	/**
	 * Set the SearchHistory object that will be the target for this history query
	 */
	public void setHistoryTarget(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	
	/**
	 * Run the query represented by this history query object.
	 */
	@Override
	public void run() {
		Scanner in = new Scanner(System.in);
		String historyCommand = getOptFromInput(in, System.out);
		try {
			historyFunctions.get(historyCommand).call();
		} catch (Exception e) {
			System.out.println("Error running history command");
		}
	}
	
	
	/**
	 * Get the history query option from the specified input source, usually STDIN.
	 * @param in Scanner to read input stream which will supply the option
	 * @param out output stream to display prompts
	 * @return history query option as a string
	 */
	protected String getOptFromInput(Scanner in, PrintStream out) {
		List<String> availableOpts = Arrays.asList(new String[]{"a", "all", "u", "unique", "t", "time"});
		out.print("Select a stats option. Available stats are:\n");
		String optionString = String.format("%s%n%s%n%s%n? ",
					"Print all searches (a/all)",
					"Number of unique search terms with associated frequency (u/unique)",
					"Timestamps of all searches (t/time)");
		out.print(optionString);

		String input = in.nextLine();
		while (!availableOpts.contains(input))
			out.printf("Invalid option. Please choose one of the following:%n%s", optionString);

		return input;
	}
}
