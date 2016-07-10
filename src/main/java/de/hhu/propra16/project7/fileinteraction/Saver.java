package de.hhu.propra16.project7.fileinteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import de.hhu.propra16.project7.controller.Status;

/* @author André Juber */

public class Saver {

	private String project;

	// Saves the project name
	public Saver(String project) {
		this.project = project;
	}

	// Chooses the right function (based on the user's status)
	public void save(Status status, String eingabe) throws IOException {
		switch (status) {
			case Red:
				saveTest(eingabe);
				break;
			case Green:
				codeToTemp(eingabe);
				break;
			case Refactoring:
				tempToCode(eingabe);
				break;
		}
	}

	// Returns the project name
	public String getProjectName() {
		return project;
	}

	// Gets the class name
	private String getFileName(String eingabe) {
		String[] words = eingabe.split(" ");
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("public") && i <= words.length) {
				if (words[i+1].equals("class") && i+1 <= words.length) {
					return words[i+2];
				}
			}
		}
		return null;
	}

	// Saves the user's test code
	private void saveTest(String eingabe) throws IOException {
		if (getFileName(eingabe) != null) {
			final List<String> output = Arrays.asList(eingabe.split("\n"));
			createPath("/data/" + project);
			String path = System.getProperty("user.dir") + "/data/" + project + "/" + getFileName(eingabe) + ".java";
			final Path p = Paths.get(path);
			Files.write(p, output);
		}
	}

	// Replaces the temporary code with the actual code
	private void codeToTemp(String eingabe) throws IOException {
		if (getFileName(eingabe) != null) {
			final List<String> output = Arrays.asList(eingabe.split("\n"));
			createPath("/data/" + project + "/temp");
			String path = System.getProperty("user.dir") + "/data/" + project + "/temp/" + getFileName(eingabe) + ".java";
			final Path p = Paths.get(path);
			Files.write(p, output);
		}
	}

	// Replaces the actual code with the temporary code
	private void tempToCode(String eingabe) throws IOException {
		if (getFileName(eingabe) != null) {
			final List<String> output = Arrays.asList(eingabe.split("\n"));
			createPath("/data/" + project);
			String path = System.getProperty("user.dir") + "/data/" + project + "/" + getFileName(eingabe) + ".java";
			final Path p = Paths.get(path);
			Files.write(p, output);
		}
	}

	// Creates new folders
	private void createPath(String path) throws IOException {
		String[] folders = path.split("/");
		String temppath = "";
		for (int i = 0; i < folders.length; i++) {
			temppath += "/" + folders[i];
			createDirectory(System.getProperty("user.dir") + temppath);
		}
	}

	// Creates a new folder
	private void createDirectory(String path) throws IOException {
		final Path p = Paths.get(path);
		if (!fileExists(p)) {
			Files.createDirectory(p);
		}
	}

	// Checks if the file / folder exists, in order to avoid any kind of exception
	private boolean fileExists(Path p) {
		return Files.exists(p);
	}

}