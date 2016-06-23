package de.hhu.propra16.project7.catalogue;

public class ParseHelper implements ParseSource {

	private final ParseSource mInnerSource;
	
	public ParseHelper(ParseSource innerSource) {
		mInnerSource = innerSource;
	}
	
	// proxy ParseSource.proceed()
	public void proceed() throws ParseException {
		mInnerSource.proceed();
	}
	
	// proxy ParseSource.match()
	public void match(char expected) throws ParseException {
		mInnerSource.match(expected);
	}
	
	// For convenience: Matches multiple chars in sequence
	public void match(String expected) throws ParseException {
		for(char c : expected.toCharArray()) {
			match(c);
		}
	}
	
	// proxy ParseSource.raise()
	public ParseException raise(String message) {
		return mInnerSource.raise(message);
	}
	
	// proxy ParseSource.peekChar()
	public char peekChar() throws ParseException {
		return mInnerSource.peekChar();
	}
	
	// proxy ParseSource.endReached()
	public boolean endReached() {
		return mInnerSource.endReached();	
	}
	
	// Check if current char is some kind of white space
	public boolean isWhite() throws ParseException {
		char c = peekChar();
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}
	
	// Skip white space, if there is any
	public void skipWhite() throws ParseException {
		while(!endReached() && isWhite()) proceed();
	}
	
	// Ensures that there is a gap between two words, e.g. "test template"
	// but not "testtemplate". However, in front of special characters like
	// +, -, {, }, etc. no space is enforced.
	public void forceGap() throws ParseException {
		char c = peekChar();
		if(Character.isLetterOrDigit(c)) {
			throw raise("Space expected but \""+c+"\" found.");
		} else {
			skipWhite();
		}
	}
	
	// Read a double-quoted string. The sequences \\ and \" are treated as expected.
	public String quotedString() throws ParseException {
		match('"');
		boolean masked = false, run = true;
		String yet = "";
		while(run) {
			char c = peekChar();
			boolean wasMasked = masked;
			masked = false;
			switch(c) {
			case '"':
				run = wasMasked;
				break;
			case '\\':
				masked = !wasMasked;
				break;
			}
			if(run && !masked) {
				yet += c;
			}
			proceed();
		}
		return yet;
	}
	
	// Read a braced block that may contain Java code; inner braces are counted,
	// except in quoted strings and Java comments (// or /* */)
	public String bracedBlock() throws ParseException {
		match('{');
		int open = 0;
		char lookAhead = peekChar();
		boolean masked = false, quoted = false, 
				lineComment = false, blockComment = false,
				slashSeen = false, astSeen = false;
		String yet = "";
		while(open >= 0) {
			boolean wasMasked = masked;
			masked = false;
			// Counted braces
			if(lookAhead == '{' && !quoted && !lineComment && !blockComment) {
				open++;
			} else if(lookAhead == '}' && !quoted && !lineComment && !blockComment) {
				open--;
			// Handle quoted strings
			} else if(lookAhead == '\"' && !wasMasked && !lineComment && !blockComment) {
				quoted = !quoted;
			} else if(lookAhead == '\\' && quoted) {
				masked = !wasMasked;
			// Handle line comments
			} else if(lookAhead == '/' && slashSeen) {
				lineComment = true;
			} else if(lookAhead == '\n' && lineComment) {
				lineComment = false;
			// Handle block comments
			} else if(lookAhead == '*' && slashSeen) {
				blockComment = true;
			} else if(lookAhead == '/' && astSeen) {
				blockComment = false;
			}
			astSeen = lookAhead == '*' && !lineComment && blockComment && !slashSeen;;
			slashSeen = lookAhead == '/' && !lineComment && !blockComment && !quoted;
			if(open >= 0) {
				yet += lookAhead;
				proceed();
				lookAhead = peekChar();
			}
		}
		match('}');
		System.out.println("Will now call untrim");
		return layouttrim(yet);
	}
	
	// Remove excessive indentation
	private static String layouttrim(String str) {
		String indent = "";
		int pos = 0;
		char c = str.charAt(pos);
		// Measure the indentation width
		while(pos < str.length() && (c == ' ' || c == '\t' || c == '\n')) {
			if(c == '\n') {
				indent = "";
			} else {
				indent += c;
			}
			pos++;
			c = str.charAt(pos);
		}
		// Remove the indentation
		return str.substring(pos).replace("\n"+indent, "\n").trim();
	}
	
}
