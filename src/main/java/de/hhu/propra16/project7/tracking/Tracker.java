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
	private Status laststatus;
	private String lasterror;

	// Saves the project name
	public Tracker(String project) {
		this.project = project;
	}

	// Writes a new message in the log file every time the user decides to change the program's status
	// seconds = time (seconds) the user has spent working on the code
	// maxtime = the highest time allowed by Babysteps (minutes)
	public void statusChanged(Status status, int seconds, int maxtime) throws IOException {
		if (laststatus != null && status == laststatus) {
			return;
		}
		int sec = seconds % 60;
		int min = (seconds - sec) / 60;
		String change = getTime() + " - Status changed to ";
		switch(status) {
			case Red:
				change += "RED (Babysteps OFF).";
				break;
			case BabyRed:
				change += "RED (Babysteps ON).";
				break;
			case Green:
				change += "GREEN (Babysteps OFF).";
				break;
			case BabyGreen:
				change += "GREEN (Babysteps ON).";
				break;
			case Refactoring:
				change += "REFACTORING.";
				break;
		}
		final List<String> content = new ArrayList<>();
		content.add(change);
		content.add(toTheRight("The status was successfully changed after " + min + " minute/s and " + sec + " second/s.", 23));
		if (status == Status.BabyRed || status == Status.BabyGreen) {
			String graph = "Graphical representation: [";
			for (int i = 0; i < 100; i++) {
				if (i <= 100.0 / (maxtime * 60.0 / seconds)) {
					graph += "*";
				}
				else {
					graph += " ";
				}
			}
			graph += "] <- " + maxtime + " minutes (Babysteps maxtime)";
			content.add(toTheRight(graph, 23));
			content.add(toTheRight("^ " + (int) (100.0 / (maxtime * 60.0 / seconds)) + "%", (int) (23 + 100.0 / (maxtime * 60.0 / seconds) + "Graphical representation: [".length())));
		}
		saveChanges(content);
		newLine();
		laststatus = status;
	}

	// Adds a "Time's Up" message to the log file (for Babysteps)
	public void timesUp(int maxtime) throws IOException {
		final List<String> message = new ArrayList<>();
		message.add(getTime() + " - Time's up! All changes made during the last " + maxtime + " minutes have been removed by Babysteps.");
		saveChanges(message);
		newLine();
	}

	// Adds a compilation error message to the log file
	public void compilationFailed(String error) throws IOException {
		final List<String> message = new ArrayList<>();
		if (lasterror != null && lasterror.equals(error)) {
			message.add(getTime() + " - Compilation failed; same error message.");
			saveChanges(message);
			newLine();
			return;
		}
		message.add(getTime() + " - Compilation failed:");
		saveChanges(message);
		newLine();
		String[] lines = error.split("\n");
		for (int i = 0; i < lines.length; i++) {
			lines[i] = toTheRight(lines[i], 23);
		}
		final List<String> content = Arrays.asList(lines);
		saveChanges(content);
		newLine();
		lasterror = error;
	}

	// Returns the project name
	public String getProjectName() {
		return project;
	}

	// Shifts a string to the right by <times> digits
	private String toTheRight(String text, int times) {
		for (int i = 0; i < times; i++) {
			text = " " + text;
		}
		return text;
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