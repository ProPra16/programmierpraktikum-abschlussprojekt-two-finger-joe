package de.hhu.propra16.project7.catalogue;

public class ParseHelper implements ParseSource {

	private final ParseSource mInnerSource;
	
	public ParseHelper(ParseSource innerSource) {
		mInnerSource = innerSource;
	}
	
	public void proceed() throws ParseException {
		mInnerSource.proceed();
	}
	
	public void match(char expected) throws ParseException {
		mInnerSource.match(expected);
	}
	
	public void match(String expected) throws ParseException {
		for(char c : expected.toCharArray()) {
			match(c);
		}
	}
	
	public ParseException raise(String message) {
		return mInnerSource.raise(message);
	}
	
	public char peekChar() throws ParseException {
		return mInnerSource.peekChar();
	}
	
	public boolean endReached() {
		return mInnerSource.endReached();	
	}
	
	public boolean isWhite() throws ParseException {
		char c = peekChar();
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}
	
	public void skipWhite() throws ParseException {
		while(!endReached() && isWhite()) proceed();
	}
	
	public void forceGap() throws ParseException {
		char c = peekChar();
		if(Character.isLetterOrDigit(c)) {
			throw raise("Space expected but \""+c+"\" found.");
		} else {
			skipWhite();
		}
	}
	
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
			if(lookAhead == '{' && !quoted && !lineComment && !blockComment) {
				open++;
			} else if(lookAhead == '}' && !quoted && !lineComment && !blockComment) {
				open--;
			} else if(lookAhead == '\"' && !wasMasked && !lineComment && !blockComment) {
				quoted = !quoted;
			} else if(lookAhead == '\\' && quoted) {
				masked = !wasMasked;
			} else if(lookAhead == '/' && slashSeen) {
				lineComment = true;
			} else if(lookAhead == '\n' && lineComment) {
				lineComment = false;
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
	
	private static String layouttrim(String str) {
		String indent = "";
		int pos = 0;
		char c = str.charAt(pos);
		while(pos < str.length() && (c == ' ' || c == '\t' || c == '\n')) {
			if(c == '\n') {
				indent = "";
			} else {
				indent += c;
			}
			pos++;
			c = str.charAt(pos);
		}
		return str.substring(pos).replace("\n"+indent, "\n").trim();
	}
	
}
