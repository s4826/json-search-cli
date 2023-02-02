/**
 * Name: Sean Rawson
 * Date: 1/30/2023
 * Course: CS-622 Spring 2023
 * Assignment 2
 * Description: This file contains operations for merging source files into a destination file.
 */

package cs622.hw2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * File operations required by hw2
 */
public class FileOperations {
	
	/**
	 * Merge a list of files into a destination file, overwriting the destination
	 * file if it already exists.
	 * 
	 * @param files list of files to merge
	 * @param dest destination file for the result of the merge
	 * @return true on success, false otherwise
	 */
	public static boolean mergeFiles(List<File> files, File dest) {
		return mergeFiles(files, dest, false);
	}
	
	
	/**
	 * Merge all of the files in a list into a destination file. This method
	 * will overwrite the destination file unless the append flag is set.
	 * 
	 * @param files list of files to merge
	 * @param dest destination file for the result of the merge
	 * @return true on success, otherwise false
	 */
	public static boolean mergeFiles(List<File> files, File dest, boolean append) {
		if (!append)
			dest.delete();

		boolean success = false;
		try (BufferedWriter destWriter = new BufferedWriter(new FileWriter(dest))) {
			BufferedReader sourceReader;
			for (File source : files) {
				sourceReader = new BufferedReader(new FileReader(source));
				sourceReader.lines().forEach(line -> {
					try {
						destWriter.write(line + System.lineSeparator());
					} catch (IOException e) {
						System.out.println("Error writing to destination file.");
						System.out.println(String.format("The following line will not be copied:\n\t%s", line));
					}
				});
			}
			success = true;
		} catch (IOException io) {
			System.out.println("Error merging files");
			io.printStackTrace();
			success = false;
		}
		
		if (success)
			System.out.println("Files merged successfully.\n");
		else
			System.out.println("Files not merged successfully.\n");
		return success;
	}
}
