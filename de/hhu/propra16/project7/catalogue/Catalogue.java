package de.hhu.propra16.project7.catalogue;

import java.util.LinkedList;
import java.util.List;

/* @author Marvin Cohrs */

public class Catalogue {
	
	private final List<Project> mProjects;
	
	public Catalogue() {
		mProjects = new LinkedList<>();
	}
	
	// Get the list of associated projects. Use getProjects().add(...) to add one.
	public List<Project> getProjects() {
		return mProjects;
	}

}
