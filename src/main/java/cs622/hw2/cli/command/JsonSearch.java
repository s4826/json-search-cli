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

import cs622.hw2.searcher.JsonSearcher;


/**
 * JsonSearch class, adds additional functionality to base class Search
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
	
	
	public void setHistoryTarget(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}

	
	/**
	 * Run the search parameterized by this JsonSearch object and store the results
	 * in the history object that this class is linked with.
	 */
	@Override
	public void run() {
		setTimeStamp();
		getArgsFromInput(System.in, System.out);
		
		results = searcher.runSearch(this); 
		
		historyTarget.put(getSearchString(), this);
	}	
	
	
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ String.format("Total results: %s", this.results.size());
	}
}
