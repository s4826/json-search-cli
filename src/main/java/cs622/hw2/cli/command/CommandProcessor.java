/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains command (user input) processing functionality.
 */
package cs622.hw2.cli.command;

import java.util.HashMap;
import java.util.Map;


/**
 * CommandProcessor class to encapsulate command object creation. 
 */
public class CommandProcessor {

	private Map<String, CommandFactory> commands;
	
	/**
	 * Default constructor. Initialize command map.
	 */
	public CommandProcessor() {
		commands = new HashMap<>();
	}
	
	
	/**
	 * Add a string and associated command factory to the commands map.
	 * @param commandString string that will trigger command creation
	 * @param commandFactory command factory that will create new command objects
	 */
	public void add(String commandString, CommandFactory commandFactory) {
		commands.put(commandString, commandFactory);
	}
	
	
	public Command process(String commandString) throws InvalidCommandException {
		if (commands.containsKey(commandString))
			return commands.get(commandString).create();
		throw new InvalidCommandException(String.format("Invalid command: %s", commandString));
	}
}
