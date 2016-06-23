package de.hhu.propra16.project7.tests;

import de.hhu.propra16.project7.catalogue.CatalogueReader;
import de.hhu.propra16.project7.catalogue.Catalogue;

public class CatalogueTest {

	public static void main(String[] args) {
		String testCode = "catalogue { } ";
		try {
			Catalogue cat = CatalogueReader.readFromString(testCode);
			System.out.println("Success");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
