/**
 * Name: Sean Rawson
 * Date: 2/4/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: Search class containing search parameters and methods
 */
package cs622.hw2.cli.command;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cs622.hw2.searcher.MatchMethod;


/**
 * Abstract search class, which encapsulates information
 * common to all searches.
 */
public abstract class Search implements Command {
	
	private String searchString;
	private MatchMethod matchMethod;
	private boolean ignoreCase;
	private LocalDateTime timeStamp;
	
	private File fileToSearch;
	
	
	public Search() {}
	
	
	/**
	 * Set the search string/pattern for this search object
	 * @param searchString search string or pattern
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	

	/**
	 * Set the match method used for this search
	 * @param method MatchMethod.KEYWORD or MatchMethod.PATTERN
	 */
	public void setMatchMethod(MatchMethod method) {
		this.matchMethod = method;
	}
	

	/**
	 * Set this search to ignore or consider upper/lower case
	 * @param ignore true for ignore case/case insensitive, false for case sensitive
	 */
	public void setIgnoreCase(boolean ignore) {
		this.ignoreCase = ignore;
	}
	
	
	/**
	 * Set the timestamp of this search.
	 */
	public void setTimeStamp() {
		timeStamp = LocalDateTime.now();
	}
	
	
	/**
	 * Set the file to search.
	 * @param fileName name of file to search, as a string 
	 */
	public void setSearchFile(String fileName) {
		this.fileToSearch = new File(fileName);
	}
	
	
	/**
	 * Set search file.
	 * @param file file object designating search file
	 */
	public void setSearchFile(File file) {
		this.fileToSearch = file;
	}


	/**
	 * Get the search string used for this search
	 * @return search string
	 */
	public String getSearchString() {
		return this.searchString; 
	}
	
	
	/**
	 * Get the match method used for this search
	 * @return MatchMethod.KEYWORD or MatchMethod.PATTERN
	 */
	public MatchMethod getMatchMethod() {
		return this.matchMethod;
	}
	
	
	/**
	 * Get the ignore case flag for this search
	 * @return true for ignore case, false for consider case
	 */
	public boolean getIgnoreCase() {
		return this.ignoreCase;
	}
	
	
	/**
	 * Get an Optional containing the time stamp of this search. If the
	 * search hasn't been run yet, the optional will be empty. 
	 * @return
	 */
	public Optional<LocalDateTime> getTimeStamp() {
		if (timeStamp == null)
			return Optional.empty();
		return Optional.of(timeStamp);
	}
	
	
	/**
	 * Get the absolute path of the search file for this search
	 * @return absolute path as a string
	 */
	public String getAbsoluteSearchFilePath() {
		return this.fileToSearch.getAbsolutePath();
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
		getIgnoreCaseFromInput(in, out);
		getSearchStringFromInput(in, out);
	}
	

	/**
	 * Get name of file to be used for this search.
	 * @param in input stream to supply the file name
	 * @param out output stream to display prompts
	 */
	protected void getSearchFileFromInput(Scanner input, PrintStream out) {

		out.print(String.format("Enter a file name. The current directory is %s.%n", System.getProperty("user.dir")));
		out.print("A file name can be a relative path ('file.json') or a \n" +
					"fully qualified path ('/home/user/project/file.json').\n? ");

		fileToSearch = new File(input.nextLine());
		while (!fileToSearch.exists()) {
			out.print("File does not exist. Is the file in the current directory?\n"
						+ "If the file is in a child directory of the current directory, you may\n"
						+ "specify the file name as a partial path, such as 'childDir/file'."
						+ "If not, the file name needs to be of the form '/full/path/to/file' (macOS/Linux)\n"
						+ "or C:\\path\\to\\file (Windows).\n"
						+ "Enter a file name.\n? ");
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
	 * Get the case sensitivity of this search from the user
	 * @param input input stream to supply case sensitivity selection
	 * @param out output stream to display prompts
	 */
	protected void getIgnoreCaseFromInput(Scanner input, PrintStream out) {
		out.print("Ignore case when matching ('y' for case insensitive, 'n' for case sensitive)\n? ");
		String ignore = input.nextLine();
		while (!(ignore.equals("y") || ignore.equals("n"))) {
			out.print("Invalid option. Please enter 'y' for case insensitive matching\n" +
						"\tor 'n' for case sensitive matching\n? ");
			ignore = input.nextLine();
		}
		
		ignoreCase = ignore.equals("y");
	}
	
	
	/**
	 * Get the search string to be used for this search
	 * @param in input stream to supply search string
	 * @param out output stream to display prompts
	 */
	protected void getSearchStringFromInput(Scanner input, PrintStream out) {

		out.print("Enter a search string\n? ");
		String testString = input.nextLine();
		if (matchMethod == MatchMethod.KEYWORD) {
			searchString = testString;
			return;
		}
		
		boolean success = false;
		while (!success) {
			try {
				Pattern.compile(testString);
				success = true;
			} catch (PatternSyntaxException pse) {
				System.out.printf("Invalid regex pattern. The error was: \"%s\"%n", pse.getMessage());
				System.out.print("Please try again.\n? ");
				testString = input.nextLine();
			}
		}
		
		searchString = testString;
	}
	
	
	/**
	 * Custom search object printing
	 */
	@Override
	public String toString() {
		return String.format("Search file: %s%nSearch string: %s%nMatch method: %s%nIgnore case: %s%nTimestamp: %s",
							 fileToSearch,
							 searchString,
							 matchMethod,
							 ignoreCase,
							 timeStamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("\\.")[0]);
	}
}
