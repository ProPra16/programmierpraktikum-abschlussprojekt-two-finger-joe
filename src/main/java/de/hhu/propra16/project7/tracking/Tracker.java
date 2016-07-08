package de.hhu.propra16.project7.tracking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
				change += "Red";
				break;
			case Green:
				change += "Green";
				break;
			case Refactoring:
				change += "Refactoring";
				break;
		}
		final List<String> content = new ArrayList<>();
		content.add(change);
		saveChanges(content);
	}

	// Adds text messages to the log file
	private void saveChanges(List<String> content) throws IOException {
		createPath("/de/hhu/propra16/tracking/projects/" + project);
		String path = System.getProperty("user.dir") + "/de/hhu/propra16/tracking/projects/" + project + "/log.txt";
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy, HH:mm:ss");
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