package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public class CodeTemplate {

	public static enum Category {
		Test,
		Implementation
	}
	
	private final Category mCategory;
	private final String mFilename;
	private final String mContent;
	
	public CodeTemplate(Category category, String filename, String content) {
		mCategory = category;
		mFilename = filename;
		mContent = content;
	}
	
	public Category getCategory() {
		return mCategory;
	}
	
	public String getFilename() {
		return mFilename;
	}
	
	public String getContent() {
		return mContent;
	}
	
}
