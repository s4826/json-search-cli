package cs622.hw2.cli.command;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;

import cs622.hw2.Main;
import cs622.hw2.searcher.JsonSearcher;
import cs622.hw2.searcher.MatchMethod;

public class Search implements Command {
	
	private String searchString;
	private MatchMethod matchMethod;
	private LocalDateTime timeStamp;
	
	private File fileToSearch;
	
	
	public Search() {}
	
	
	public Search setSearchString(String searchString) {
		this.searchString = searchString;
		return this;
	}
	

	public Search setMatchMethod(MatchMethod method) {
		this.matchMethod = method;
		return this;
	}


	public String getSearchString() {
		return this.searchString; 
	}
	
	
	public MatchMethod getMatchMethod() {
		return this.matchMethod;
	}
	
	
	public String getAbsoluteSearchFilePath() {
		return this.fileToSearch.getAbsolutePath();
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	
	/**
	 * Run the search represented by this search object. The time stamp
	 * is created on execution, <strong>not</strong> creation, of the search.
	 */
	public Result run() {
		timeStamp = LocalDateTime.now();
		getArgsFromInput(System.in, System.out);
		return new Result();
	}
	
	
	/**
	 * Get all required arguments for this search
	 * @param in input stream that will supply arguments
	 * @param out
	 */
	protected void getArgsFromInput(InputStream inputStream, PrintStream out) {
		Scanner in = new Scanner(inputStream);

		getSearchFileFromInput(in, out);
		getMatchMethodFromInput(in, out);
		getSearchStringFromInput(in, out);
	}
	

	/**
	 * Get name of file to be used for this search.
	 * @param in input stream to supply file name
	 * @param out output stream to display prompts
	 */
	protected void getSearchFileFromInput(Scanner input, PrintStream out) {

		out.print(String.format("Enter a file name. The current directory is %s.%n", System.getProperty("user.dir")));
		out.print("A file name can be a relative path ('file.json') or a \n" +
					"fully qualified path ('/home/user/project/file.json').\n? ");

		fileToSearch = new File(input.nextLine());
		while (!fileToSearch.exists()) {
			out.print("File does not exist. Is the file under the current directory hierarchy?\n" +
						"If not, the file name needs to be of the form '/full/path/to/file'.\n" +
						"Enter a file name.\n? ");
			fileToSearch = new File(input.nextLine());
		}
	}


	/**
	 * Get the matching method to be used for this search
	 * @param in input stream to supply matching method selection
	 * @param out output stream to display prompts
	 */
	protected void getMatchMethodFromInput(Scanner input, PrintStream out) {

		out.print("Enter a matching method ('k' for keyword, 'p' for pattern)\n? ");
		String match = input.nextLine();
		while (!(match.equalsIgnoreCase("k") || match.equalsIgnoreCase("p"))) {
			out.print("Invalid match method. Please enter 'k' or 'p'\n? ");
			match = input.nextLine();
		}
		
		matchMethod = (match.equals("k")) ?
				MatchMethod.KEYWORD : MatchMethod.PATTERN;
	}

	
	/**
	 * Get the search string to be used for this search
	 * @param in input stream to supply search string
	 * @param out output stream to display prompts
	 */
	protected void getSearchStringFromInput(Scanner input, PrintStream out) {

		out.print("Enter a search string\n? ");
		searchString = input.nextLine();
	}
}
