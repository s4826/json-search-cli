/**
 * Name: Sean Rawson
 * Date: 2/4/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains a search history class which store all searches
 */
package cs622.hw2.cli.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Search history class. Stores keywords and all searches performed
 * for that keyword in a HashMap.
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
			System.out.printf("%s: %d times%n", entry.getValue().size());
		});
	}
	
	
	/**
	 * Print the time stamps of all search terms in this search history.
	 */
	public void printSearchTimeStamps() {
		searches.entrySet().stream().forEach(entry -> {
			System.out.printf("Searches for \'%s\'%n----------------%n", entry.getKey());
			entry.getValue().forEach(System.out::println);
		});
	}
	
	
	/**
	 * Print all searches in this search history.
	 */
	public void printAllSearches() {
		searches.entrySet().stream().forEach(entry -> {
			System.out.println(String.format("Searches for keyword \"%s\"%n--------------------", entry.getKey()));
			entry.getValue().stream().forEach(System.out::println);
		});
	}
	
}