package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public class ParseException extends Exception {

	public ParseException(String filename, String position, String message) {
		super(String.format("%s [%s]: %s", new String[] {filename, position, message}));
	}
	
}
