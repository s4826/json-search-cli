/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: Factory for creating JsonSearch objects
 */
package cs622.hw2.cli.command;

import cs622.hw2.cli.command.history.SearchHistory;

/**
 * JsonSearch factory class, used to create a command object for each JSON search.
 */
public class JsonSearchFactory implements CommandFactory {
	
	private SearchHistory historyTarget;
	
	public JsonSearchFactory(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	public Command create() {
		JsonSearch jsonSearch = new JsonSearch();
		jsonSearch.setHistoryTarget(historyTarget);
		return jsonSearch;
	}
}
