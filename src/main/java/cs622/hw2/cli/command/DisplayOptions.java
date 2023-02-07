/**
 * Name: Sean Rawson
 * Date: 2/6/2023
 * Class: CS-622 Spring 2023
 * Assignment 3
 * Description: This file contains functionality for setting search result display options.
 */
package cs622.hw2.cli.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Display options singleton
 * 
 * source: https://www.geeksforgeeks.org/singleton-design-pattern/ 
 */
public class DisplayOptions implements CommandFactory, Command {
	private static DisplayOptions options;
	
	private List<String> fields;
	private boolean printAllFields;
	
	
	/**
	 * Private constructor. Instances provided by 'getInstance'
	 */
	private DisplayOptions() {
		fields = new ArrayList<>();
		printAllFields = true;
	}

	
	/**
	 * Static method to control access to single DisplayOptions instance
	 * @return DisplayOptions object
	 */
	public static DisplayOptions getInstance() {
		if (options == null)
			options = new DisplayOptions();
		return options;
	}
	
	
	/**
	 * Get the list of field names to print
	 * @return list of field name strings
	 */
	public List<String> getDisplayFields() {
		return this.fields;
	}
	
	
	/**
	 * Set/choose the fields that will be printed for each search result
	 * @param fields names of fields to include when printing search results
	 */
	public void setDisplayFields(String... fields) {
		this.fields.clear();
		this.fields.addAll(Arrays.asList(fields));
	}
	
	
	/**
	 * Set all fields to be printed
	 */
	public void setPrintAllFieldsFlag(boolean printAllFields) {
		this.printAllFields = printAllFields;
	}
	
	
	/**
	 * Get the state of the printAllFields flag 
	 * @return true or false
	 */
	public boolean getPrintAllFieldsFlag() {
		return this.printAllFields;
	}
	
	public Command create() {
		return(this);
	}
	
	public void run() {
		String message = "Choose a display options command.\n"
					   + "Reset to display all fields (a)\n"
					   + "Print only certain display fields (f)\n"
					   + "Show current display options (s)\n"
					   + "Go back to main menu (b)\n? ";
		System.out.print(message);
		
		String updated = "Options updated";

		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while (!input.equals("b")) {
			switch (input) {
				case "a":
					setPrintAllFieldsFlag(true);
					System.out.println(updated);
					break;
				case "f":
					System.out.print("Enter a comma separated list of field names\n? ");
					input = in.nextLine();
					setDisplayFields(input.split("[\\s,]+"));
					setPrintAllFieldsFlag(false);
					System.out.println(updated);
					break;
				case "s":
					System.out.printf("%nPrint all fields: %s%n%n", printAllFields);
					fields.forEach(System.out::println);
					break;
				default:
					System.out.println("Invalid command.");
					System.out.print(message);
			}
			
			System.out.print(message);
			input = in.nextLine();
		}
	}
}
