package de.hhu.propra16.project7.fileinteraction;

import java.io.*;

import de.hhu.propra16.project7.controller.Logic;
import de.hhu.propra16.project7.controller.Status;

public class Delete {
	

	
	public static void delete() {
				
	if(status==BabyRed){        
		DeleteTest(); }
	
	if(status==BabyGreen){        
		DeleteCode(); }
		
	}
		

	public static void DeleteCode(){
		bin(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/code.java");
		ReplaceCode();
		
	}
	
	
	public static void DeleteTest(){
		bin(System.getProperty("user.dir") + "/de/hhu/propra16/fileinteraction/projects/test.java");
		ReplaceTest();
	}
	
	public static void bin(String Path) {
        File file = new File(Path);
        if(file.exists()){
            file.delete();
        }
    }
	
	public static void ReplaceCode() {
  
		//get last backup
		
    }
	
	public static void ReplaceTest() {
		
		//get last backup
		  
    }
	
}
