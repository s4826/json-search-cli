/**
 * Name: Sean Rawson
 * Date: 2/4/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains a search history class which store all searches
 */
package cs622.hw2.cli.command.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs622.hw2.cli.command.Search;


/**
 * Search history class. Central object for search history storage
 * and retrieval.
 */
public class SearchHistory {
	
	private Map<String, List<Search>> searches;
	
	/**
	 * Default constructor. Initialize HashMap.
	 */
	public SearchHistory() {
		searches = new HashMap<>();
	}
	
	
	/**
	 * Add a search to the search history
	 * @param searchString search string for this search
	 * @param search search object containing search state/parameters
	 * @return true if the search was successfully added to the history, false otherwise
	 */
	public boolean put(String searchString, Search search) {
		boolean success = false;
		if (searches.containsKey(searchString)) {
			success = searches.get(searchString).add(search);
		}
		else {
			List<Search> searchList = new ArrayList<>();
			searchList.add(search);
			searches.put(searchString, searchList);
			success = true;
		}
		return success;
	}
	
	
	/**
	 * Get all of the searches in this history.
	 * @return Map of keywords to lists of search objects
	 */
	protected Map<String, List<Search>> getAllSearches() {
		return searches;
	}
	
	
	/**
	 * Print all unique search terms and their frequencies.
	 */
	public void printUniqueSearchTerms() {
		System.out.println("Search terms and frequencies");
		searches.entrySet().forEach(entry -> {
			String timeString = (entry.getValue().size() == 1) ? "time" : "times";
			System.out.printf("%s: %d %s%n", entry.getKey(), entry.getValue().size(), timeString);
		});
	}
	
	
	/**
	 * Print the time stamps of all search terms in this search history.
	 */
	public void printSearchTimeStamps() {
		System.out.printf("Search time stamps%n----------------%n");
		searches.entrySet().stream().forEach(entry -> {
			System.out.printf("Search term: \'%s\'%n", entry.getKey());
			entry.getValue().stream()
				.filter(search -> search.getTimeStamp().isPresent())
				.forEach(search -> System.out.printf("\t%s%n", search.getTimeStamp().get()));
		});
	}
	
	
	/**
	 * Print all searches in this search history.
	 */
	public void printAllSearches() {
		searches.entrySet().stream().forEach(entry -> {
			System.out.printf("Searches for string \"%s\"%n-----------------%n", entry.getKey());
			entry.getValue().stream().forEach(value -> System.out.printf("%n%s%n", value));
		});
	}
	
}