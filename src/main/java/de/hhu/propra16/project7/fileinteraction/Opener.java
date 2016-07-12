package de.hhu.propra16.project7.fileinteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.hhu.propra16.project7.controller.Status;

/* @author André Juber */

public class Opener {

	private String project;

	// Saves the project name
	public Opener(String project) {
		this.project = project;
	}

	// Returns the content of the .java files (based on the user's status)
	// Returns null if the file doesn't exist
	// Lines are separated by \n
	public String open(Status status, String classname) throws IOException {
		switch (status) {
			case Red:
				return readFromFile("/data/" + project + "/test/" + classname);
			case BabyRed:
				return readFromFile("/data/" + project + "/babysteps/test/" + classname);
			case Green:
				return readFromFile("/data/" + project + "/temp/" + classname);
			case BabyGreen:
				return readFromFile("/data/" + project + "/babysteps/temp/" + classname);
			case Refactoring:
				return readFromFile("/data/" + project + "/code/" + classname);
		}
		return null;
	}

	// Returns the project name
	public String getProjectName() {
		return project;
	}

	// Reads content from file
	private String readFromFile(String filepath) throws IOException {
		String path = System.getProperty("user.dir") + filepath + ".java";
		final Path p = Paths.get(path);
		if (!fileExists(p)) return null;
		final List<String> code = Files.readAllLines(p);
		String content = "";
		for (String line : code) {
			content += line + "\n";
		}
		return content;
	}

	// Checks if the file exists, in order to avoid any kind of exception
	private boolean fileExists(Path p) {
		return Files.exists(p);
	}

}