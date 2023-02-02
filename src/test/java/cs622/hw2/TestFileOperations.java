/**
 * Name: Sean Rawson
 * Date: 1/30/2023
 * Class: CS-622 Spring 2023
 * Assignment 2
 * Description: Tests for file operations
 */
package cs622.hw2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestFileOperations {
	
	private File tempFile1;
	private File tempFile2;
	
	@BeforeEach
	void setUp() throws IOException {
		tempFile1 = File.createTempFile("test", "");
		tempFile1.deleteOnExit();
		
		tempFile2 = File.createTempFile("test", "");
		tempFile2.deleteOnExit();
	}

	@Test
	void testCreateTempFile() {
		assertTrue(tempFile1.isFile());
		assertTrue(tempFile1.getParent().equals(System.getProperty("java.io.tmpdir")));
	}
	
	
	@Test
	void testMergeFiles() throws IOException {
		FileWriter fw1 = new FileWriter(tempFile1);
		fw1.write("one");
		fw1.flush();

		FileWriter fw2 = new FileWriter(tempFile2);
		fw2.write("two");
		fw2.flush();
		
		List<File> files = new ArrayList<>();
		files.add(tempFile1);
		files.add(tempFile2);
		
		
		File dest = File.createTempFile("dest", "");
		assertTrue(FileOperations.mergeFiles(files, dest));

		BufferedReader br = new BufferedReader(new FileReader(dest));
		assertTrue(br.readLine().equals("one"));
		assertTrue(br.readLine().equals("two"));
		
		fw1.close();
		fw2.close();
		br.close();
	}
	
	
	@Test
	void testFilesDontExistReturnsFalse() throws IOException {
		List<File> files = new ArrayList<>();
		files.add(new File("doesntexist1"));
		files.add(new File("doesntexist2"));
		File dest = File.createTempFile("dest", "");
		
		assertFalse(FileOperations.mergeFiles(files, dest));
	}
}
