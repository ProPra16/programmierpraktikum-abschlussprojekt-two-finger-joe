package de.hhu.propra16.project7.fileinteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/* import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler; */

/* @author André Juber */

public class Save {

	private static String input;

	// Chooses the right function (based on the user's status)
	public static void save(Logic.Status status, String eingabe) throws IOException {
		input = eingabe;
		
		if(status==Logic.Status.Red)
			saveTest();
		if(status==Logic.Status.Green)
			codeToTemp();
		if(status==Logic.Status.Refactoring)	
			tempToCode();
		
	
	}

	// Gets the class name
	private static String getFileName() {
		String[] words = input.split(" ");
		return words[0];
	}

	// The file names "Test", "Temp_code" and "Code" are only placeholders...

	// Saves the user's test code
	private static void saveTest() throws IOException {
	/*	CompilationUnit unit = new CompilationUnit(getFileName(), input, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		compiler.compileAndRunTests(); */
		final List<String> output = Arrays.asList(input.split("\n"));
		createDirectory(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/");
		createDirectory(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + getFileName());
		String path = System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + getFileName() + "/Test.java";
		final Path p = Paths.get(path);
		Files.write(p, output);
	}

	// Replaces the temporary code with the actual code
	private static void codeToTemp() throws IOException {
		final List<String> output = Arrays.asList(input.split("\n"));
		createDirectory(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/");
		createDirectory(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + getFileName());
		String path = System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + getFileName() + "/Temp_code.java";
		final Path p = Paths.get(path);
		Files.write(p, output);
	}

	// Replaces the actual code with the temporary code
	private static void tempToCode() throws IOException {
		final List<String> output = Arrays.asList(input.split("\n"));
		createDirectory(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/");
		createDirectory(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + getFileName());
		String path = System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/" + getFileName() + "/Code.java";
		final Path p = Paths.get(path);
		Files.write(p, output);
	}

	// Creates a new folder
	private static void createDirectory(String path) throws IOException {
		final Path p = Paths.get(path);
		if (!fileExists(p)) {
			Files.createDirectory(p);
		}
	}

	// Checks if the file exists, in order to avoid any kind of exception
	private static boolean fileExists(Path p) {
		return Files.exists(p);
	}

}
