//package de.hhu.propra16.project7.fileinteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/* @author André Juber */

public class Open {

	// Returns the content of Test.java if status is 0
	// Returns the content of Temp_code.java if status is 1
	// Returns null if the file doesn't exist
	// Lines are separated by \n
	public static String open(Logic.Status status, String project) throws IOException {
	
		

			if(status==Logic.Status.Red)
				return readTest(project);
			if(status==Logic.Status.Green)
				return readTempCode(project);
				
			
		//	if(status==Logic.Status.Refactoring)  	//--was ist mit Refactoring?
				//return CodeToTemp(project);
			
				
		
		
		
		return null;
	}

	// Reads the test file's content
	private static String readTest(String project) throws IOException {
		String path = System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + project + "/Test.java";
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
	private static String readTempCode(String project) throws IOException {
		String path = System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + project + "/Temp_code.java";
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
	private static boolean fileExists(Path p) {
		return Files.exists(p);
	}

}
