package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public class StringSource implements ParseSource {
	
	protected final String mBuffer;
	protected int mPosition;
	
	public StringSource(String buffer) {
		mBuffer = buffer;
		mPosition = 0;
	}

	// Implements ParseSource.proceed()
	public void proceed() throws ParseException {
		if(!endReached()) {
			mPosition++;
		} else {
			throw raise("Tried to proceed, but end is already reached.");
		}
	}

	// Implements ParseSource.match()
	public void match(char expected) throws ParseException {
		if(endReached()) {
			throw raise("Tried to match\""+expected+"\", but found the end of input.");
		} else if(mBuffer.charAt(mPosition) != expected) {
			throw raise("Tried to match\""+expected+"\", but found \""+mBuffer.charAt(mPosition)+"\".");
		} else {
			proceed();
		}
	}

	// Implements ParseSource.raise()
	public ParseException raise(String message) {
		return new ParseException("<StringSource>", "char "+Integer.toString(mPosition), message);
	}

	// Implements ParseSource.peekChar()
	public char peekChar() throws ParseException {
		if(endReached()) {
			throw raise("Tried to peek the next char, but found the end of input.");
		} else {
			return mBuffer.charAt(mPosition);
		}
	}
	
	// Implements ParseSource.endReached()
	public boolean endReached() {
		return mPosition >= mBuffer.length();
	}
	
}
