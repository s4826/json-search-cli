package cs622.hw2.cli;

import java.util.Scanner;

import cs622.hw2.cli.command.Command;
import cs622.hw2.cli.command.CommandProcessor;
import cs622.hw2.cli.command.InvalidCommandException;
import cs622.hw2.cli.command.SearchFactory;
import cs622.hw2.cli.command.SearchHistory;

public class InputLoop {
	
	public void promptUser() {
		System.out.println("What would you like to do? Enter one of the following commands.");
		System.out.println("Search (s/search)");
		System.out.println("Show stats (stats)");
		System.out.println("Quit (q/quit)");
		System.out.print("? ");
	}
	
	public void run() {
		CommandProcessor proc = new CommandProcessor();
		proc.add("s", (new SearchFactory())::create);
		SearchHistory history = new SearchHistory();
		Scanner in = new Scanner(System.in);
		String input;
		while (true) {
			promptUser();
			input = in.nextLine(); 
			try {
				Command command = proc.process(input);
				command.run();
			} catch (InvalidCommandException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
