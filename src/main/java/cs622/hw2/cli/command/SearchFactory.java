package cs622.hw2.cli.command;

public class SearchFactory implements CommandFactory {
	
	public Command create() {
		return new Search();
	}
}
