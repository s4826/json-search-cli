/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains functionality for generating search history queries.
 */
package cs622.hw2.cli.command;


/**
 * Class to generate history queries against a SearchHistory object
 */
public class HistoryQueryFactory implements CommandFactory {
	private SearchHistory historyTarget;
	
	public HistoryQueryFactory(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	
	/**
	 * Create and return a history query.
	 * @return history query as Command object
	 */
	public Command create() {
		HistoryQuery historyQuery = new HistoryQuery();
		historyQuery.setHistoryTarget(historyTarget);
		return historyQuery;
	}
}
