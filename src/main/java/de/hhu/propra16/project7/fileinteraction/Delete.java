package de.hhu.propra16.project7.fileinteraction;

import java.io.*;

import de.hhu.propra16.project7.catalogue.Project;
import de.hhu.propra16.project7.controller.Status;

public class Delete{
	
	public static void delete(Status status, String title) {
		
	if(status==Status.BabyRed){        
		DeleteTest(title); }
	
	if(status==Status.BabyGreen){        
		DeleteCode(title); }
		
	}
		

	public static void DeleteCode(String title){
		bin(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/code.java");
		ReplaceCode(title);
		
	}
	
	
	public static void DeleteTest(String title){
		bin(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/test.java");
		ReplaceTest(title);
	}
	
	public static void bin(String Path) {
        File file = new File(Path);
        if(file.exists()){
            file.delete();
        }
    }
	
	public static void ReplaceCode(String title) {
		Project project = new Project(title);
		project.getInstructions();
	}
	
	public static void ReplaceTest(String title) {
		Project project = new Project(title);
		project.getTestTemplates();		  
    }
	
}
