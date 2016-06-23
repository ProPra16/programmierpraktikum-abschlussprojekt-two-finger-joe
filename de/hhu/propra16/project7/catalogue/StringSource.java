package de.hhu.propra16.project7.catalogue;

import java.util.Arrays;

/* @author Marvin Cohrs */

public class StringSource implements ParseSource {
	
	private final String mBuffer;
	private int mPosition;
	
	public StringSource(String buffer) {
		mBuffer = buffer;
		mPosition = 0;
	}
	
	public StringSource(StringSource original) {
		mBuffer = original.mBuffer;
		mPosition = original.mPosition;
	}
	
	public ParseSource replicate() {
		return new StringSource(this);
	}

	public void proceed() throws ParseException {
		if(!endReached()) {
			mPosition++;
		} else {
			throw raise("Tried to proceed, but end is already reached.");
		}
	}

	public void match(char expected) throws ParseException {
		if(endReached()) {
			throw raise("Tried to match\""+expected+"\", but found the end of input.");
		} else if(mBuffer.charAt(mPosition) != expected) {
			throw raise("Tried to match\""+expected+"\", but found \""+mBuffer.charAt(mPosition)+"\".");
		} else {
			proceed();
		}
	}

	public void match(String expected) throws ParseException {
		for(char c : expected.toCharArray()) {
			match(c);
		}
	}

	public ParseException raise(String message) {
		return new ParseException("<StringSource>", "char "+Integer.toString(mPosition), message);
	}

	public char peekChar() throws ParseException {
		if(endReached()) {
			throw raise("Tried to peek the next char of input, but found the end of input.");
		} else {
			return mBuffer.charAt(mPosition);
		}
	}
	
	public boolean endReached() {
		return mPosition >= mBuffer.length();
	}
	
	public boolean isWhite() throws ParseException {
		char c = peekChar();
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}
	
	public void skipWhite() throws ParseException {
		if(!endReached()) {
			while(isWhite()) proceed();
		}
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
		}
		match('"');
		return yet;
	}

}
