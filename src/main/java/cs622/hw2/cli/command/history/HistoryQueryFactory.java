/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains functionality for generating search history queries.
 */
package cs622.hw2.cli.command.history;

import cs622.hw2.cli.command.Command;
import cs622.hw2.cli.command.CommandFactory;

/**
 * Class to generate history queries against a search history
 */
public class HistoryQueryFactory implements CommandFactory {

	private SearchHistory historyTarget;
	
	
	/**
	 * Constructor. Create a HistoryQueryFactory that will create queries
	 * against a specific SearchHistory.
	 * @param historyTarget
	 */
	public HistoryQueryFactory(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	
	/**
	 * Create and return a history query.
	 * @return history query as Command object
	 */
	public Command create() {
		HistoryQuery historyQuery = new HistoryQuery();
		addCommands(historyQuery);
		return historyQuery;
	}
	
	
	/**
	 * Add available commands to the designated HistoryQuery object.
	 * @param hq
	 */
	public void addCommands(HistoryQuery hq) {
		hq.addAvailableCommand("a", historyTarget::printAllSearches);
		hq.addAvailableCommand("all", historyTarget::printAllSearches);
		
		hq.addAvailableCommand("u", historyTarget::printUniqueSearchTerms);
		hq.addAvailableCommand("unique", historyTarget::printUniqueSearchTerms);
		
		hq.addAvailableCommand("t", historyTarget::printSearchTimeStamps);
		hq.addAvailableCommand("time", historyTarget::printSearchTimeStamps);
	}
}
