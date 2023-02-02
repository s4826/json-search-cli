/**
 * Name: Sean Rawson
 * Date: 1/30/2023
 * Course: CS-622 Spring 2023
 * Assignment 2
 * Description: This file contains the Searcher class, which implements search capabilities for JSON files.
 */
package cs622.hw2.searcher;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Searcher class to provide for searching JSON files for search patterns 
 */
public class JsonSearcher {

	private String startingField;
	private MatchMethod matchMethod;
	private boolean ignoreCase;
	
	private List<JsonNode> matchingNodes;
	
	/**
	 * Default constructor. Creates a searcher with field name "data"
	 * as the top-level starting point, keyword matching as the default
	 * matching method, and case insensitive matching.
	 */
	public JsonSearcher() {
		this("data", MatchMethod.KEYWORD, false);
		matchingNodes = new ArrayList<>();
	}
	

	/**
	 * Construct a searcher with a JSON field name, match method, and
	 * case sensitive or insensitive search toggled.
	 * @param field JSON field name
	 */
	public JsonSearcher(String field, MatchMethod method, boolean ignoreCase) {
		this.startingField = field;
		this.matchMethod = method;
		this.ignoreCase = ignoreCase;
	}

	
	/**
	 * Set the default top-level JSON field that should be searched by this searcher.
	 * @param field JSON field name
	 */
	public void setStartingField(String field) {
		this.startingField = field;
	}
	
	
	/**
	 * Get the default top-level JSON field from which searches are started.
	 * @return field JSON field name 
	 */
	public String getStartingField() {
		return this.startingField;
	}
	
	
	/**
	 * Set the matching method for this searcher. The two choices
	 * are match by keyword and by pattern.
	 * @param method matching method, MatchMethod.KEYWORD or MatchMethod.PATTERN
	 */
	public void setMatchMethod(MatchMethod method) {
		this.matchMethod = method;
	}
	
	
	/**
	 * Get the matching method used by this searcher.
	 * @return MatchMethod.PATTERN or MatchMethod.KEYWORD
	 */
	public MatchMethod getMatchMethod() {
		return this.matchMethod;
	}
	
	
	/**
	 * Set the case sensitivity of this searcher
	 * @param ignoreCase true for case insensitive, false for case sensitive
	 */
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	
	
	/**
	 * Get the case sensitivity of this searcher
	 * @return true if case insensitive, false if case sensitive
	 */
	public boolean getIgnoreCase() {
		return this.ignoreCase;
	}
	
	
	/**
	 * Find JSON nodes from a source file that match a search pattern 
	 * @param source file to search
	 * @param searchPattern search pattern
	 * @return list of matching JSON nodes
	 */
	public List<JsonNode> findMatchingJsonNodes(File source, String searchPattern) {

		try {
			findMatchingJsonNodesLineByLine(source, searchPattern);
		} catch (IOException io) {
			System.out.println("Error processing JSON file line by line.");
			System.out.println("Attempting to process file as a JSON tree.");
			try {
				findMatchingJsonNodesFromTree(source, searchPattern);
			} catch (IOException treeIO) {
				System.out.println("Error processing JSON file as JSON tree.");
				System.out.println("Please check that the input file is formatted correctly.");
			}
		}
		
		return matchingNodes;
	}
	
	
	/**
	 * Search a JSON file for JSON nodes that match a search string. This method
	 * assumes that the source file contains one min-ified JSON node per line
	 * (i.e. no 'pretty printed' JSON).
	 * 
	 * The following format is expected on each line:
	 *   {"key1":"value1","key2":"value2","key3":{"key4":"value4"}}
	 *  
	 * The following format will not be read correctly:
	 * {
	 *   "key1": "value1",
	 *   "key2": "value2",
	 *   "key3":
	 *   {
	 *     "key4":"value4"
	 *   }
	 * }
	 * @param source file to search
	 * @param searchPattern pattern to search for
	 * @return true if any matches were found, false otherwise
	 * @throws IOException on failure to read a source file line into JsonNode
	 */
	protected boolean findMatchingJsonNodesLineByLine(File source, String searchPattern) throws IOException {
		matchingNodes.clear();
		
		ObjectMapper mapper = new ObjectMapper();
		BufferedReader br = new BufferedReader(new FileReader(source));
		String in;
		JsonNode node;
		while ((in = br.readLine()) != null) {
			node = mapper.readTree(in);
			if (jsonNodeMatches(node, searchPattern))
				matchingNodes.add(node);
		}
		
		br.close();
		
		return (matchingNodes.size() > 0);
	}
	
	
	/**
	 * Find JSON nodes that match a search pattern, treating the source file
	 * as a JSON tree with either one JSON object or an array of JSON objects.
	 * 
	 * Single JSON object:
	 * {
	 *   "key1":"value1",
	 *   "key2":"value2",
	 *   
	 *   etc...
	 * }
	 * 
	 * Multiple JSON objects:
	 * [
	 *   {
	 *     "key1":"firstObjectValue1",
	 *     "key2":"firstObjectValue2"
	 *   },
	 *   {
	 *     "key1":"secondObjectValue1",
	 *     "key2":"secondObjectValue2"
	 *   }
	 * ]
	 * 
	 * Note that without the brackets designating an array of JSON objects,
	 * this method will only process the first object in the file.
	 * 
	 * @param source JSON file to search
	 * @param searchPattern pattern to search for
	 * @return true if any matches were found, false otherwise
	 * @throws IOException
	 */
	protected boolean findMatchingJsonNodesFromTree(File source, String searchPattern) throws IOException {
		matchingNodes.clear();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(source);
		if (root.isArray()) {
			for (JsonNode node : root)
				if (jsonNodeMatches(root, searchPattern))
					matchingNodes.add(node);
		}
		else
			if (jsonNodeMatches(root, searchPattern))
				matchingNodes.add(root);

		return (matchingNodes.size() > 0);
	}

	
	/**
	 * Search all elements of the specified JsonNode for a search string
	 * @param node JsonNode to search
	 * @param searchPattern string to search for
	 * @return true if the string is found anywhere in the node, false otherwise
	 */
	protected boolean jsonNodeMatches(JsonNode node, String searchPattern) {
		if (!node.has(startingField)) {
			System.out.println(
				String.format("Json node has no '%s' field. Falling back to search of entire node.", startingField));
			System.out.println("This may match on fields that you aren't interested in.");
			return recursiveMatchNode(node, searchPattern);
		}
		return recursiveMatchNode(node.get(startingField), searchPattern);
		
	}
	
	
	/**
	 * Recursively search a JsonNode for values matching a search string
	 * @param node JsonNode to search
	 * @param searchPattern search string
	 * @return true on success, otherwise false
	 */
	protected boolean recursiveMatchNode(JsonNode node, String searchPattern) {
		boolean found = false;
		List<String> fields = new ArrayList<>();
		node.fieldNames().forEachRemaining(fields::add);
		for (String field : fields) {
			JsonNode child = node.get(field);
			if (child.isArray()) {
				for (JsonNode grandChild : child) {
					found = recursiveMatchNode(grandChild, searchPattern);
					if (found)
						break;
				}
			}
			else if (child.isContainerNode())
				found = recursiveMatchNode(child, searchPattern);
			else
				found = matchSingleNode(child, searchPattern);
			
			if (found)
				break;
		}
			
		return found;
	}
	
	
	/**
	 * Match a single json node of the form {"key":"value"}
	 * @param node JSON node to match
	 * @param searchPattern pattern to match
	 * @return true if matches, false otherwise
	 */
	private boolean matchSingleNode(JsonNode node, String searchPattern) {
		boolean found = false;
		if (node.textValue() != null) {
			if (ignoreCase) {
				found = (matchMethod == MatchMethod.PATTERN) ?
					node.textValue().matches("(?i)" + searchPattern) :
						node.textValue().toLowerCase().contains(searchPattern.toLowerCase());
			}
			else {
				found = (matchMethod == MatchMethod.PATTERN) ?
					node.textValue().matches(searchPattern) : node.textValue().contains(searchPattern);
			}
		}
		
		return found;
		
	}
}
