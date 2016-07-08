package de.hhu.propra16.project7.catalogue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import de.hhu.propra16.project7.catalogue.CodeTemplate.Category;

/* @author Marvin Cohrs */

public class Project {

	private String mTitle, mInstructions;
	private List<CodeTemplate> mTemplates;
	
	// Create an empty project
	public Project(String title) {
		mTitle = title;
		mInstructions = "";
		mTemplates = new LinkedList<>();
	}
	
	// Get the template list. Templates may be added using getTemplates().add(...);
	public List<CodeTemplate> getTemplates() {
		return mTemplates;
	}
	
	// Get test templates only. Don't try to use add() on this one.
	public List<CodeTemplate> getTestTemplates() {
		return mTemplates.stream()
				.filter(t -> t.getCategory() == Category.Test)
				.collect(Collectors.toList());
	}
	
	// Get implementation templates only. Don't try to use add() on this one 
	public List<CodeTemplate> getImplementationTemplates() {
		return mTemplates.stream()
				.filter(t -> t.getCategory() == Category.Implementation)
				.collect(Collectors.toList());
	}
	
	// Get the project title
	public String getTitle() {
		return mTitle;
	}
	
	// Get the project instructions
	public String getInstructions() {
		return mInstructions;
	}
	
	// Append further instructions. Spaces are added automatically.
	public void appendInstructions(String newInstr) {
		if(mInstructions.equals("") || mInstructions.charAt(mInstructions.length()-1) == ' ') {
			mInstructions += newInstr;
		} else {
			mInstructions += " " + newInstr;
		}
	}
	
}
