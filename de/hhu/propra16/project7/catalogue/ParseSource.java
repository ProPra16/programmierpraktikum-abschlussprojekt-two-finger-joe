package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public interface ParseSource {
	
	// Move to the next char
	public void proceed() throws ParseException;
	// Assert that the current char matches the given one, and proceed
	public void match(char expected) throws ParseException;
	// Instantiate a ParseException at the current position
	public ParseException raise(String message);
	// Get the current char
	public char peekChar() throws ParseException;
	// Check if the end of input is reached
	public boolean endReached();
}
