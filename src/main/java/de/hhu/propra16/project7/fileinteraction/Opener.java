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

	// Returns the content of the test file if status is 0
	// Returns the content of the temp file if status is 1
	// Returns null if the file doesn't exist
	// Lines are separated by \n
	public String open(Status status, String classname) throws IOException {
		switch (status) {
			case Red:
				return readTest(classname);
			case Green:
				return readTempCode(classname);
		}
		return null;
	}

	// Returns the project name
	public String getProjectName() {
		return project;
	}

	// Reads the test file's content
	private String readTest(String classname) throws IOException {
		String path = System.getProperty("user.dir") + "/data/" + project + "/" + classname + ".java";
		final Path p = Paths.get(path);
		if (!fileExists(p)) return null;
		final List<String> code = Files.readAllLines(p);
		String content = "";
		for (String line : code) {
			content += line + "\n";
		}
		return content;
	}

	// Reads the temp file's content
	private String readTempCode(String classname) throws IOException {
		String path = System.getProperty("user.dir") + "/data/" + project + "/temp/" + classname + ".java";
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