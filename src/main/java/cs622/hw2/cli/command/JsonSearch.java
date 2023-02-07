/**
 * Name: Sean Rawson
 * Date: 2/4/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file is an extension of the Search class, to parameterize
 * searches of JSON files specifically.
 */
package cs622.hw2.cli.command;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import cs622.hw2.cli.command.history.SearchHistory;
import cs622.hw2.searcher.JsonSearcher;


/**
 * JsonSearch class, adds additional functionality to base Search class
 */
public class JsonSearch extends Search {
	
	private SearchHistory historyTarget;
	private JsonSearcher searcher;
	private List<JsonNode> results;
	
	
	/**
	 * Default constructor. Call superclass constructor and initialize
	 * the JSON searcher and the results array.
	 */
	public JsonSearch() {
		super();
		this.searcher = new JsonSearcher();
		this.results = new ArrayList<>();
	}
	
	
	/**
	 * Get the results of this search.
	 * @return list of JSON nodes matching the search parameters
	 */
	public List<JsonNode> getResults() {
		return this.results;
	}
	
	
	/**
	 * Set the search history object that will save this search and its results. 
	 * @param historyTarget SearchHistory object to save to
	 */
	public void setHistoryTarget(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}

	
	/**
	 * Run the search parameterized by this JsonSearch object. Then store the results
	 * and print them.
	 */
	public void run() {
		setTimeStamp();
		getArgsFromInput(System.in, System.out);
		
		results = searcher.runSearch(this); 
		historyTarget.put(this.getSearchString(), this);
		
		DisplayOptions options = DisplayOptions.getInstance();
		printSearchResults(options);
	}	
	
	
	/**
	 * Print the results of this search, according to the provided display options
	 * @param options DisplayOptions object containing search result display preferences
	 */
	public void printSearchResults(DisplayOptions options) {
		if (options.getPrintAllFieldsFlag())
			results.forEach(System.out::println);
		else {
			List<String> fields = options.getDisplayFields();
			results.stream().forEach(result -> printFormattedSearchResult(result, fields));
		}
		System.out.printf("Total results: %d%n", results.size());
	}
	
	
	/**
	 * Print a single search result 
	 * @param result
	 * @param fields
	 */
	public void printFormattedSearchResult(JsonNode result, List<String> fields) {
		StringBuilder format = new StringBuilder();
		format.append("{");
		for (String field : fields) {
			format.append(String.format("\"%s\": %s, ", field, result.path(field).toString()));
		}
		
		format.delete(format.length() - 2, format.length());
		format.append("}");
		
		System.out.println(format.toString());
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ String.format("Total results: %s", this.results.size());
	}
}
