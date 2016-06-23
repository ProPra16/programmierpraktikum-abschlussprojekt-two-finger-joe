package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public interface ParseSource {
	
	public void proceed() throws ParseException;
	public void match(char expected) throws ParseException;
	public void match(String expected) throws ParseException;
	public ParseException raise(String message);
	public char peekChar() throws ParseException;
	public boolean endReached();
	public ParseSource replicate();
    public boolean isWhite() throws ParseException;
    public void skipWhite() throws ParseException;
    public void forceGap() throws ParseException;
    public String quotedString() throws ParseException;
}
