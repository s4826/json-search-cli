package cs622.hw2.cli.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;


public class CommandProcessor {
	private Map<String, CommandFactory> commands;
	
	public CommandProcessor() {
		commands = new HashMap<>();
	}
	
	
	public void add(String commandString, CommandFactory commandFactory) {
		commands.put(commandString, commandFactory);
	}
	
	
	public Command process(String commandString) throws InvalidCommandException {
		if (commands.containsKey(commandString))
			return commands.get(commandString).create();
		throw new InvalidCommandException("Invalid command");
	}
}
