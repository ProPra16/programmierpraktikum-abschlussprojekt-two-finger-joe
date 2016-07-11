package de.hhu.propra16.project7.fileinteraction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.hhu.propra16.project7.controller.Status;

/* @author André Juber */

public class Deleter {

	private String project;

	// Saves the project name
	public Deleter(String project) {
		this.project = project;
	}

	// Chooses the right function (based on the user's status)
	public void delete(Status status, String classname) throws IOException {
		switch(status) {
			case Red:
				deleteFile("/data/" + project + "/test/" + classname);
				break;
			case BabyRed:
				deleteFile("/data/" + project + "/babysteps/test/" + classname);
				break;
			case Green:
				deleteFile("/data/" + project + "/temp/" + classname);
				break;
			case BabyGreen:
				deleteFile("/data/" + project + "/babysteps/temp/" + classname);
				break;
			case Refactoring:
				deleteFile("/data/" + project + "/code/" + classname);
				break;
		}
	}

	// Deletes the file
	private void deleteFile(String filepath) throws IOException {
		String path = System.getProperty("user.dir") + filepath + ".java";
		final Path p = Paths.get(path);
		Files.deleteIfExists(p);
	}

}
