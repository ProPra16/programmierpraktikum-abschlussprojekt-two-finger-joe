package de.hhu.propra16.project7.catalogue;

import java.io.File;

import de.hhu.propra16.project7.catalogue.CodeTemplate.Category;

/* @author Marvin Cohrs */

public class CatalogueReader {

	public static Catalogue readFromString(String input) throws ParseException {
		return readFromSource(new StringSource(input));
	}
	
	public static Catalogue readFromFile(File file) throws ParseException {		
		return readFromSource(FileSource.fromFile(file));
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
		return catalogue;
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
					project.getTemplates().add(template);
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
			lookAhead = mSource.peekChar();
		}
		mSource.match('}');
		return project;
	}

	private CodeTemplate parseCodeTemplate(Category category) throws ParseException {
		mSource.match("template");
		mSource.forceGap();
		String filename = mSource.quotedString();
		mSource.skipWhite();
		String content = mSource.bracedBlock();
		return new CodeTemplate(category, filename, content);
	}

	public void ensureEndReached() throws ParseException {
		mSource.skipWhite();
		if(!mSource.endReached()) {
			throw mSource.raise("End of input expected but \""+mSource.peekChar()+"\" found.");
		}
	}
	
}
