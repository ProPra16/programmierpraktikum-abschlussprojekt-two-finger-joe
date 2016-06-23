package de.hhu.propra16.project7.catalogue;

/* @author Marvin Cohrs */

public class CatalogueReader {

	public static Catalogue readFromString(String input) throws ParseException {
		return readFromSource(new StringSource(input));
	}
	
	public static Catalogue readFromSource(ParseSource source) throws ParseException {
		CatalogueReader reader = new CatalogueReader(source);
		Catalogue result = reader.parseCatalogue();
		reader.ensureEndReached();
		return result;
	}
	
	private final ParseSource mSource;
	
	private CatalogueReader(ParseSource source) {
		mSource = source;
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
		return new Catalogue();
	}
	
	private Project parseProject() throws ParseException {
		mSource.match("project");
		mSource.forceGap();
		String title = mSource.quotedString();
		mSource.skipWhite();
		mSource.match('{');
		mSource.skipWhite();
		String instructions = "";
		char lookAhead = mSource.peekChar();
		while(Character.isLetter(lookAhead)) {
			
		}
		return null;
	}

	public void ensureEndReached() throws ParseException {
		mSource.skipWhite();
		if(!mSource.endReached()) {
			throw mSource.raise("End of input expected but \""+mSource.peekChar()+"\" found.");
		}
	}
	
}
