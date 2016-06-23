package de.hhu.propra16.project7.catalogue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import de.hhu.propra16.project7.catalogue.CodeTemplate.Category;

/* @author Marvin Cohrs */

public class CatalogueReader {

	public static Catalogue readFromString(String input) throws ParseException {
		System.out.println("[debug] readFromString: "+input);
		return readFromSource(new StringSource(input));
	}
	
	public static Catalogue readFromFile(File file) throws ParseException {		
		try {
			FileInputStream stream = new FileInputStream(file);
			InputStreamReader streamReader = new InputStreamReader(stream);
			BufferedReader bufferedReader = new BufferedReader(streamReader);
			String accumulator = "", lastLine;
			try {
				while((lastLine = bufferedReader.readLine()) != null) {
					accumulator += lastLine + "\n";
				}
			} finally {
				
				bufferedReader.close();
			}
			return readFromString(accumulator);
		} catch(IOException e) {
			throw new ParseException(file.getName(), "-", e.getMessage());
		}
	}
	
	public static Catalogue readFromSource(ParseSource source) throws ParseException {
		CatalogueReader reader = new CatalogueReader(source);
		Catalogue result = reader.parseCatalogue();
		reader.ensureEndReached();
		return result;
	}
	
	private final ParseHelper mSource;
	
	private CatalogueReader(ParseSource source) {
		mSource = new ParseHelper(source);
	}
	
	public Catalogue parseCatalogue() throws ParseException {
		mSource.skipWhite();
		mSource.match("catalogue");
		mSource.skipWhite();
		mSource.match('{');
		mSource.skipWhite();
		Catalogue catalogue = new Catalogue();
		while(mSource.peekChar() == 'p') {
			Project project = parseProject();
			catalogue.getProjects().add(project);
			mSource.skipWhite();
		}
		mSource.match('}');
		System.out.println("[debug] after catalogue{} block");
		return new Catalogue();
	}
	
	private Project parseProject() throws ParseException {
		System.out.println("Enter parseProject");
		mSource.match("project");
		mSource.forceGap();
		String title = mSource.quotedString();
		mSource.skipWhite();
		mSource.match('{');
		mSource.skipWhite();
		Project project = new Project(title);
		char lookAhead = mSource.peekChar();
		while(Character.isLetter(lookAhead)) {
			System.out.println("[debug] parseProject loop");
			if(lookAhead == 't') {
				mSource.match("test");
				mSource.forceGap();
				CodeTemplate template = parseCodeTemplate(CodeTemplate.Category.Test);
				project.getTemplates().add(template);
			} else {
				mSource.match('i');
				lookAhead = mSource.peekChar();
				if(lookAhead == 'm') {
					mSource.match("mplementation");
					mSource.forceGap();
					CodeTemplate template = parseCodeTemplate(CodeTemplate.Category.Implementation);
				} else {
					mSource.match("nstruction");
					mSource.forceGap();
					String instr = mSource.quotedString();
					project.appendInstructions(instr);
					mSource.skipWhite();
					mSource.match(';');
				}
			}
			mSource.skipWhite();
		}
		return null;
	}

	private CodeTemplate parseCodeTemplate(Category test) throws ParseException {
		mSource.match("template");
		return null;
	}

	public void ensureEndReached() throws ParseException {
		mSource.skipWhite();
		if(!mSource.endReached()) {
			throw mSource.raise("End of input expected but \""+mSource.peekChar()+"\" found.");
		}
	}
	
}
