/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: Invalid command exception
 */
package cs622.hw2.cli.command;

/**
 * InvalidCommandException class, used to codify invalid commands
 * as exceptional events for the command line interface.
 */
@SuppressWarnings("serial")
public class InvalidCommandException extends Exception {

	public InvalidCommandException() {
		super("Invalid command");
	}
	
	
	public InvalidCommandException(String message) {
		super(message);
	}
}
