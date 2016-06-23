package de.hhu.propra16.project7.tests;

import de.hhu.propra16.project7.catalogue.CatalogueReader;

import java.io.File;

import de.hhu.propra16.project7.catalogue.Catalogue;

public class CatalogueTest {

	public static void main(String[] args) {
		String filename = "src/Catalogue.cfg";
		try {
			Catalogue cat = CatalogueReader.readFromFile(new File(filename));
			System.out.println("Success");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
 