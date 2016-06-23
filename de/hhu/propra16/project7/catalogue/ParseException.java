package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public class ParseException extends Exception {

	// generated
	private static final long serialVersionUID = -7888869718218381802L;

	public ParseException(String filename, String position, String message) {
		super(String.format("%s [%s]: %s", filename, position, message));
	}
	
}
