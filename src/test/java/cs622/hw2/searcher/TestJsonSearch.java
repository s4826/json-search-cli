/**
 * Name: Sean Rawson
 * Date: 1/30/2023
 * Course: CS-622 Spring 2023
 * Assignment 2
 * Description: Tests for JsonSearcher class
 */
package cs622.hw2.searcher;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for JsonSearcher class
 */
public class TestJsonSearch {
	
	private static JsonSearcher searcher;
	private File source;
	

	@BeforeAll
	static void setUpAll() {
		searcher = new JsonSearcher();
	}
	
	
	@BeforeEach
	void setUpTest() throws IOException {
		source = File.createTempFile("test", ".json");
		source.deleteOnExit();
		FileWriter fw = new FileWriter(source);
		fw.write("{\"data\": {\"key1\":\"value1\",\"key2\":\"value2\"}}");
		fw.close();
		
		searcher.setMatchMethod(MatchMethod.KEYWORD);
		searcher.setIgnoreCase(true);
	}
	
	
	@Test
	void testSuccessfulSearchReturnsRecords() {
		String keyword = "value1";
		List<JsonNode> records = searcher.findMatchingJsonNodes(source, keyword);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	void testUnsuccessfulSearchReturnsEmptyList() {
		String keyword = "value3";
		List<JsonNode> records = searcher.findMatchingJsonNodes(source, keyword);
		assertTrue(records.size() == 0);
	}
	

	@Test
	void testSearchDoesntMatchFieldNames() {
		String keyword = "key1";
		List<JsonNode> records = searcher.findMatchingJsonNodes(source, keyword);
		assertTrue(records.size() == 0);
	}
	
	
	@Test
	void testCaseSensitiveSearch() {
		searcher.setIgnoreCase(false);

		String keyword = "Value1";
		List<JsonNode> records = searcher.findMatchingJsonNodes(source, keyword);
		assertTrue(records.size() == 0);
		
		searcher.setMatchMethod(MatchMethod.PATTERN);
		String pattern = "V.*1";
		records = searcher.findMatchingJsonNodes(source, pattern);
		assertTrue(records.size() == 0);
	}
	
	
	@Test
	void testCaseInsensitiveSearch() {
		String keyword = "Value1";
		List<JsonNode> records = searcher.findMatchingJsonNodes(source, keyword);
		assertTrue(records.size() == 1);
		
		searcher.setMatchMethod(MatchMethod.PATTERN);
		String pattern = "V.*1";
		records = searcher.findMatchingJsonNodes(source, pattern);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	void testRecursiveSearchFlatNode() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = "{\"key1\":\"value1\"}";
		JsonNode node = mapper.readTree(json);
		assertTrue(searcher.recursiveMatchNode(node, "value1"));
	}
	
	
	@Test
	void testRecursiveSearchNodeWithDepth() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node1 = mapper.readTree("{\"data\":{\"key1\":{\"key2\":\"value2\", \"key3\":\"value3\"}}}");
		assertTrue(searcher.recursiveMatchNode(node1, "value3"));

		JsonNode node2 = mapper.readTree("{\"data\":{\"key1\":[{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}}");
		assertTrue(searcher.recursiveMatchNode(node2, "value3"));
	}
	
	
	@Test
	void testMatchByPattern() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(source);
		searcher.setMatchMethod(MatchMethod.PATTERN);
		assertTrue(searcher.recursiveMatchNode(node, "^v.*1$"));
		assertFalse(searcher.recursiveMatchNode(node, "value$"));
	}
	
	
	@Test
	void testIncorrectPatternUsage() throws IOException {
		searcher.setMatchMethod(MatchMethod.PATTERN);
		List<JsonNode> results = searcher.findMatchingJsonNodes(source, "value"); 
		assertTrue(results.size() == 0);
	}
	
	
	@Test
	void testKeywordAsPatternMustMatchExactly() throws IOException {
		searcher.setMatchMethod(MatchMethod.PATTERN);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree("{\"key1\":\"value\"}");
		assertTrue(searcher.recursiveMatchNode(node, "value"));
	}
	
	
	@Test
	void testLineByLineFindSucceeds() throws IOException {
		searcher.setMatchMethod(MatchMethod.KEYWORD);
		assertTrue(searcher.findMatchingJsonNodesLineByLine(source, "value1"));
	}
	
	
	@Test
	void testLineByLineFindFails() throws IOException {
		searcher.setMatchMethod(MatchMethod.KEYWORD);
		assertFalse(searcher.findMatchingJsonNodesLineByLine(source, "valueDoesntExist"));
	}
	
	
	@Test
	void testLineByLineFindThrowsIOException() throws IOException {
		File source = File.createTempFile("test", "");
		source.deleteOnExit();
		FileWriter fw = new FileWriter(source);
		fw.write("{\n\t\"key1\":\"value1\",\n\t\"key2\":\"value2\"\n}");
		fw.close();
		assertThrows(IOException.class,
			() -> searcher.findMatchingJsonNodesLineByLine(source, "value2")); 
	}
	
	
	@Test
	void testFindMatchingNodesFromTreeSucceeds() throws IOException {
		File source = File.createTempFile("test", "");
		source.deleteOnExit();
		FileWriter fw = new FileWriter(source);
		fw.write("{\n\t\"key1\":\"value1\",\n\t\"key2\":\"value2\"\n},\n");
		fw.write("{\n\t\"key3\":\"value3\",\n\t\"key4\":\"value4\"\n}");
		fw.close();
		assertTrue(searcher.findMatchingJsonNodesFromTree(source, "value2"));
	}
}
