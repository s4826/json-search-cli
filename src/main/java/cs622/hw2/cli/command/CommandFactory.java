/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: Command factory interface for creating CLI commands
 */
package cs622.hw2.cli.command;

/**
 * Interface to create CLI command objects.
 */
public interface CommandFactory {
	Command create();
}
