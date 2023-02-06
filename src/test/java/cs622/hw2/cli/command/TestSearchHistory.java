package cs622.hw2.cli.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs622.hw2.searcher.MatchMethod;

import static org.junit.jupiter.api.Assertions.*;

public class TestSearchHistory {
	
	SearchHistory history;
	
	@BeforeEach
	void setUpEach() {
		history = new SearchHistory();
	}
	
	
	@Test
	void testGetUniqueSearchTerms() {
		history.put("robot", createSearch("robot"));
		history.put("robot", createSearch("robot"));
		
		assertTrue(history.getUniqueSearchTerms().size() == 1);
		
		JsonSearch three = createSearch("dance");
		history.put("dance", three);
		
		assertTrue(history.getUniqueSearchTerms().size() == 2);
	}
	
	
	@Test
	void testUniqueSearchesWithFrequency() {
		history.put("robot", createSearch("robot"));
		history.put("robot", createSearch("robot"));
		history.put("dance", createSearch("dance"));
	
		Map<String, List<Search>> searches = history.getAllSearches();
		assertTrue(searches.get("robot").size() == 2);
		assertTrue(searches.get("dance").size() == 1);
	}
	
	
	@Test
	void testGetTimeStamps() {
		history.put("robot", createSearch("robot"));
		Map<String, List<LocalDateTime>> timeStamps = history.getSearchTimeStamps();
		assertTrue(timeStamps.containsKey("robot"));
		assertTrue(timeStamps.get("robot").get(0) instanceof LocalDateTime);
		
		history.put("robot", createSearch("robot"));
		timeStamps = history.getSearchTimeStamps();
		assertTrue(timeStamps.get("robot").size() == 2);
		
		history.put("dance", createSearch("dance"));
		timeStamps = history.getSearchTimeStamps();
		assertTrue(timeStamps.entrySet().size() == 2);
	}

	
	/**
	 * Create a fake search for search history testing
	 * @param s search string for this fake search
	 * @return JsonSearch object
	 */
	private JsonSearch createSearch(String s) {
		JsonSearch search = new JsonSearch();
		search.setSearchString(s);
		search.setIgnoreCase(false);
		search.setMatchMethod(MatchMethod.KEYWORD);
		search.setTimeStamp();
		
		return search;
	}

}
