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
	
	public ParseSource replicate() {
		return new ParseHelper(mInnerSource.replicate());
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
	
}
