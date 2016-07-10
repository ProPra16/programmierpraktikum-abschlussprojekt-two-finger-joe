package de.hhu.propra16.project7.tracking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hhu.propra16.project7.controller.Status;

/* @author André Juber */

public class Tracker {

	private String project;

	// Saves the project name
	public Tracker(String project) {
		this.project = project;
	}

	// Writes a new line in the log file every time the user decides to change the program's status
	public void statusChanged(Status status) throws IOException {
		String change = getTime() + " - Status changed to ";
		switch(status) {
			case Red:
				change += "Red (Babysteps OFF).";
				break;
			case BabyRed:
				change += "Red (Babysteps ON).";
				break;
			case Green:
				change += "Green (Babysteps OFF).";
				break;
			case BabyGreen:
				change += "Green (Babysteps ON).";
				break;
			case Refactoring:
				change += "Refactoring.";
				break;
		}
		final List<String> content = new ArrayList<>();
		content.add(change);
		saveChanges(content);
		newLine();
	}

	// Adds a compilation error message to the log file
	public void compilationFailed(String error) throws IOException {
		final List<String> message = new ArrayList<>();
		message.add(getTime() + " - Compilation failed:");
		message.add("");
		saveChanges(message);
		String[] lines = error.split("\n");
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < 23; j++) {
				lines[i] = " " + lines[i];
			}
		}
		final List<String> content = Arrays.asList(lines);
		saveChanges(content);
		newLine();
	}

	// Returns the project name
	public String getProjectName() {
		return project;
	}

	// Adds a new line to the log file
	private void newLine() throws IOException {
		final List<String> newline = new ArrayList<>();
		newline.add("");
		saveChanges(newline);
	}

	// Adds text messages to the log file
	private void saveChanges(List<String> content) throws IOException {
		createPath("/data/" + project);
		String path = System.getProperty("user.dir") + "/data/" + project + "/log.txt";
		final Path p = Paths.get(path);
		if (!Files.exists(p)) {
			Files.write(p, content);
		}
		else {
			Files.write(p, content, StandardOpenOption.APPEND);
		}
	}

	// Gets the time for the log file
	private String getTime() {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
		return time.format(formatter);
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