package cs622.hw2.cli.command;

@SuppressWarnings("serial")
public class InvalidCommandException extends Exception {

	public InvalidCommandException() {
		super("Invalid command");
	}
	
	
	public InvalidCommandException(String message) {
		super(message);
	}
}
