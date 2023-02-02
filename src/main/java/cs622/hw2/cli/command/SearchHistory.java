package cs622.hw2.cli.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.time.LocalDateTime;

public class SearchHistory {
	
	private Map<String, Search> searches;
	
	public SearchHistory() {
		searches = new HashMap<>();
	}
	
	public Map<String, Search> getSearches() {
		return searches;
	}
	
	
	/**
	 * Add a search to the search history
	 * @param key search string for this search
	 * @param search search object containing search state/parameters
	 * @return
	 */
	public boolean put(String searchString, Search search) {
		String timeStamp = LocalDateTime.now().toString();
		boolean success = false;
		try {
			searches.put(searchString + "_" + timeStamp, search);
			success = true;
		} catch (Exception e) {
			System.out.println("error adding search to search history");
			success = false;
		}
		return success;
	}

	
	public void execute() {}
}
