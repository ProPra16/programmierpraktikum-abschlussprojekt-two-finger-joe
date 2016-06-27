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
	
	// Is this a test or an implementation template?
	public Category getCategory() {
		return mCategory;
	}
	
	// Preferred filename for this template
	public String getFilename() {
		return mFilename;
	}
	
	// The actual code
	public String getContent() {
		return mContent;
	}
	
}
