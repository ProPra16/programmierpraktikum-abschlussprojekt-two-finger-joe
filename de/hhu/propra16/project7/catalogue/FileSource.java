package de.hhu.propra16.project7.catalogue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileSource extends StringSource {

	private final String mFilename;
	private int mRow, mCol;
	
	public static FileSource fromFile(File file) throws ParseException {
		try {
			FileInputStream stream = new FileInputStream(file);
			InputStreamReader streamReader = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			String accumulator = "", lastLine;
			try {
				while((lastLine = bufferedReader.readLine()) != null) {
					accumulator += lastLine + "\n";
				}
			} finally {
				
				bufferedReader.close();
			}
			return new FileSource(accumulator, file.getName());
		} catch(IOException e) {
			throw new ParseException(file.getName(), "-", e.getMessage());
		}
	}
	
	private FileSource(String buffer, String filename) {
		super(buffer);
		mRow = 1;
		mCol = 0;
		mFilename = filename;
	}
	
	@Override
	public ParseSource replicate() {
		FileSource fs = new FileSource(mBuffer, mFilename);
		fs.mCol = mCol;
		fs.mRow = mRow;
		fs.mPosition = mPosition;
		return fs;
	}
	
	@Override
	public void proceed() throws ParseException {
		char prev = peekChar();
		super.proceed();
		if(prev == '\n') {
			mRow++;
			mCol = 0;
		} else {
			mCol++;
		}
	}
	
	@Override
	public ParseException raise(String message) {
		return new ParseException(mFilename, String.format("line %d col %d", mRow, mCol), message);
	}
	
}