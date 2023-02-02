/**
 * Name: Sean Rawson
 * Date: 1/30/2023
 * Class: CS-622 Spring 2023
 * Assignment 2
 * Description: The main entry point for assignment two's search functions
 */
package cs622.hw2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;

import cs622.hw2.cli.InputLoop;
import cs622.hw2.searcher.JsonSearcher;
import cs622.hw2.searcher.MatchMethod;

public class Main {
	
	/**
	 * Search a file for a pattern
	 * @param source file to search 
	 * @param searchPattern pattern to search for
	 */
	public static void printMatches(File sourceFile, String searchPattern, MatchMethod matchMethod) {
		JsonSearcher jsonSearcher = new JsonSearcher();
		jsonSearcher.setMatchMethod(matchMethod);
		List<JsonNode> results = jsonSearcher.findMatchingJsonNodes(sourceFile, searchPattern);
		String field1 = "funds_raised_percent";
		String field2 = "close_date";
		for (JsonNode node : results) {
			node = node.path(jsonSearcher.getStartingField());
			System.out.printf("{\"%s\": %.1f%%, \"%s\": %s}%n",
								field1,
								node.path(field1).asDouble() * 100,
								field2,
								node.path(field2).asText());
		}
		System.out.printf("Matching results: %d%n%n", results.size());
	}

	
	/**
	 * Get a list of all files (excluding directories) in a directory. Said another
	 * way, get a list of all files, excluding directories, at a depth of 1 in the
	 * data directory.
	 * @param dataDir data directory to search
	 * @return
	 */
	public static List<File> getFilesInDataDirectory(File dataDir) {
		List<File> filesToMerge = new ArrayList<>();
		if (dataDir.isDirectory())
			filesToMerge.addAll(
				Arrays.asList(dataDir.listFiles())
					.stream()
					.filter(f -> !f.isDirectory() && f.getName().contains("json") && !f.getName().contains("merged"))
					.collect(Collectors.toList()));
		
		return filesToMerge;
	}
	
	
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		InputLoop loop = new InputLoop();
		loop.run();
	}
}
