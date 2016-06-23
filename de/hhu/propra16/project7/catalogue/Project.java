package de.hhu.propra16.project7.catalogue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import de.hhu.propra16.project7.catalogue.CodeTemplate.Category;

/* @author Marvin Cohrs */

public class Project {

	private String mTitle, mInstructions;
	private List<CodeTemplate> mTemplates;
	
	public Project(String title, String instructions) {
		mTitle = title;
		mInstructions = instructions;
		mTemplates = new LinkedList<>();
	}
	
	public List<CodeTemplate> getTemplates() {
		return mTemplates;
	}
	
	public List<CodeTemplate> getTestTemplates() {
		return mTemplates.stream()
				.filter(t -> t.getCategory() == Category.Test)
				.collect(Collectors.toList());
	}
	
	public List<CodeTemplate> getImplementationTemplates() {
		return mTemplates.stream()
				.filter(t -> t.getCategory() == Category.Implementation)
				.collect(Collectors.toList());
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getInstructions() {
		return mInstructions;
	}
	
}
