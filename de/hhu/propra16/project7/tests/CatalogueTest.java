package de.hhu.propra16.project7.tests;

import de.hhu.propra16.project7.catalogue.CatalogueReader;
import de.hhu.propra16.project7.catalogue.CodeTemplate;
import de.hhu.propra16.project7.catalogue.Project;

import java.io.File;

import de.hhu.propra16.project7.catalogue.Catalogue;

public class CatalogueTest {

	public static void main(String[] args) {
		String filename = "src/Catalogue.cfg";
		try {
			Catalogue cat = CatalogueReader.readFromFile(new File(filename));
			System.out.println("Found "+Integer.toString(cat.getProjects().size())+ " projects");
			for(Project p : cat.getProjects()) {
				System.out.println("Project "+p.getTitle());
				System.out.println(p.getInstructions());
				for(CodeTemplate t : p.getTestTemplates()) {
					System.out.println("test template: "+t.getFilename());
					System.out.println(t.getContent());
				}
				for(CodeTemplate t : p.getImplementationTemplates()) {
					System.out.println("impl template: "+t.getFilename());
					System.out.println(t.getContent());
				}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
