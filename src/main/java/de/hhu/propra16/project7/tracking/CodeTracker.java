package de.hhu.propra16.project7.tracking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeTracker {

	/* @author André Juber */

	private String project;
	private List<List<String>> archive;

	// Saves the project name
	public CodeTracker(String project) {
		this.project = project;
		archive = new ArrayList<>();
		try {
			updateContent();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Adds new code to the list and to the log file
	public void addCode(String code) throws IOException {
		List<String> data = new ArrayList<>();
		data.add(0, getTime());
		if (archive.size() == 0) {
			String output = "";
			List<String> list = Arrays.asList(code.split("\n"));
			for (int i = 0; i < list.size(); i++) {
				output += " + | " + list.get(i);
				if (i < list.size() - 1) {
					output += "\n";
				}
			}
			data.add(1, output);
			archive.add(0, data);
			textToLog(listToText());
		}
		else if (!equalsCode(archive.get(0).get(1), code)) {
			data.add(1, changesMade(archive.get(0).get(1), code));
			archive.add(0, data);
			archive = shortenList(archive);
			textToLog(listToText());
		}
	}

	// Returns the project name
	public String getProjectName() {
		return project;
	}

	// Checks if there is any difference between an old formatted code an a new code
	private boolean equalsCode(String oldcode, String newcode) {
		List<String> oldlist = Arrays.asList(oldcode.split("\n"));
		List<String> newlist = Arrays.asList(newcode.split("\n"));
		if (oldlist.size() != newlist.size()) {
			return false;
		}
		for (int i = 0; i < oldlist.size(); i++) {
			if (!oldlist.get(i).substring(5, oldlist.get(i).length()).equals(newlist.get(i))) {
				return false;
			}
		}
		return true;
	}

	// Adds + or - to the code if lines have been removed / added / changed
	private String changesMade(String oldcode, String newcode) {
		List<String> oldlist = Arrays.asList(oldcode.split("\n"));
		List<String> newlist = Arrays.asList(newcode.split("\n"));
		List<String> modified = new ArrayList<>(newlist);
		int position = -1;
		int totheright = 0;
		for (int i = 0; i < oldlist.size(); i++) {
			if (oldlist.get(i).length() == 5 || (i < newlist.size() && newlist.get(i).length() == 0)) {
				break;
			}
			if (!oldlist.get(i).substring(0, 5).equals(" - | ")) {
				if (newlist.contains(oldlist.get(i).substring(5, oldlist.get(i).length()))) {
					for (int j = 0; j < newlist.size(); j++) {
						if (j > position && oldlist.get(i).substring(5, oldlist.get(i).length()).equals(newlist.get(j))) {
							position = j;
							modified.set(j + totheright, "   | " + newlist.get(j));
							break;
						}
						if (j > position) {
							modified.set(j + totheright, " + | " + newlist.get(j));
						}
					}
				}
				else {
					if (position != -1) {
						modified.add(position + totheright + 1, " - | " + oldlist.get(i).substring(5, oldlist.get(i).length()));
					}
					else {
						modified.add(0, " - | " + oldlist.get(i).substring(5, oldlist.get(i).length()));
					}
					totheright++;
				}
			}
		}
		if (!oldlist.get(oldlist.size() - 1).equals(newlist.get(newlist.size() - 1))) {
			for (int j = 0; j < newlist.size(); j++) {
				if (j > position) {
					modified.set(j + totheright, " + | " + newlist.get(j));
				}
			}
		}
		for (int i = oldlist.size(); i < newlist.size(); i++) {
			if (i > position) {
				modified.set(i + totheright, " + | " + newlist.get(i));
			}
		}
		String output = "";
		for (int i = 0; i < modified.size(); i++) {
			output += modified.get(i);
			if (i < modified.size() - 1) {
				output += "\n";
			}
		}
		return output;
	}

	// Converts the archive's sublists into a single String
	private String listToText() {
		String content = "";
		for (List<String> list : archive) {
			content += createMultipleChars("-", 100) + "\n\n";
			content += list.get(0) + "\n\n";
			content += list.get(1) + "\n\n";
		}
		return content;
	}

	// Gets the time for the log file
	private String getTime() {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
		return "Changes made on " + time.format(formatter);
	}

	// Reads the log file's content and updates the "archive" list
	private void updateContent() throws IOException {
		String path = System.getProperty("user.dir") + "/data/" + project + "/code_log.txt";
		final Path p = Paths.get(path);
		if (fileExists(p)) {
			final List<String> content = Files.readAllLines(p);
			List<Integer> borders = new ArrayList<>();
			for (int i = 0; i < content.size(); i++) {
				if (content.get(i).equals(createMultipleChars("-", 100))) {
					borders.add(i);
				}
			}
			List<List<String>> archive = new ArrayList<>();
			for (int i = 0; i < borders.size(); i++) {
				List<String> data = new ArrayList<>();
				data.add(content.get(borders.get(i) + 2));
				String code = "";
				if (i < borders.size() - 1) {
					for (int j = borders.get(i) + 4; j < borders.get(i + 1) - 1; j++) {
						code += content.get(j);
						if (j < borders.get(i + 1) - 2) {
							code += "\n";
						}
					}
				}
				else {
					for (int j = borders.get(borders.size() - 1) + 4; j < content.size(); j++) {
						code += content.get(j);
						if (j < content.size() - 1) {
							code += "\n";
						}
					}
				}
				data.add(code);
				archive.add(data);
			}
			this.archive = archive;
		}
	}

	// Updates the log file
	private void textToLog(String code) throws IOException {
		final List<String> content = Arrays.asList(code.split("\n"));
		createPath("/data/" + project);
		String path = System.getProperty("user.dir") + "/data/" + project + "/code_log.txt";
		final Path p = Paths.get(path);
		Files.write(p, content);
	}

	// Copies a String <times> times
	private String createMultipleChars(String s, int times) {
		String temp = "";
		for (int i = 0; i < times; i++) {
			temp += s;
		}
		return temp;
	}

	// Makes sure that the "archive" list doesn't contain more than 10 elements
	private List<List<String>> shortenList(List<List<String>> list) {
		while (list.size() > 10) {
			list.remove(list.size() - 1);
		}
		return list;
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
