/**
 * Name: Sean Rawson
 * Date: 2/4/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains the user input loop functionality
 * for searching JSON files.
 */
package cs622.hw2.cli;

import java.util.Scanner;

import cs622.hw2.cli.command.Command;
import cs622.hw2.cli.command.CommandProcessor;
import cs622.hw2.cli.command.DisplayOptions;
import cs622.hw2.cli.command.InvalidCommandException;
import cs622.hw2.cli.command.JsonSearchFactory;
import cs622.hw2.cli.command.history.HistoryQueryFactory;
import cs622.hw2.cli.command.history.SearchHistory;


/**
 * Input loop class
 */
public class InputLoop {
	
	/**
	 * Prompt the user to input a command
	 */
	public void promptUser() {
		System.out.println("What would you like to do? Enter one of the following commands.");
		System.out.println("Search (s/search)");
		System.out.println("Show stats (stats)");
		System.out.println("Set options (o)");
		System.out.println("Quit (q/quit)");
		System.out.print("? ");
	}
	
	
	/**
	 * Run the main input loop, wait for commands from user.
	 */
	public void run() {
		CommandProcessor proc = new CommandProcessor();
		SearchHistory history = new SearchHistory();
		JsonSearchFactory jsonFactory = new JsonSearchFactory(history);
		HistoryQueryFactory historyQueryFactory = new HistoryQueryFactory(history);
		DisplayOptions options = DisplayOptions.getInstance();

		proc.add("s", jsonFactory::create);
		proc.add("search", jsonFactory::create);
		proc.add("stats", historyQueryFactory::create);
		proc.add("o", options::create);

		Scanner in = new Scanner(System.in);
		String input;
		while (true) {
			promptUser();
			input = in.nextLine(); 
			if (!(input.equals("q") || input.equals("quit"))) {
				try {
					Command command = proc.process(input);
					command.run();
					
					System.out.println();
				} catch (InvalidCommandException e) {
					System.out.println(e.getMessage());
				}
			}
			else
				break;
		}
	}
}
