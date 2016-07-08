//import Logic.Status;

//import Logic.Befehl;
//import Logic.Status;
package de.hhu.propra16.project7.tests;

import de.hhu.propra16.project7.controller.Befehl;
import de.hhu.propra16.project7.controller.Logic;
import de.hhu.propra16.project7.controller.Status;

public class Logic_Test {
	
	public static void main(String[] args){

		
		Logic_Test Test = new Logic_Test();
		
		Test.test();
		
	}
	
	
	public  void test(){
		
		
		
		/*
		 * - Compiliert nicht 
		 * - Tests schlagen fehl
		 * - BabySteps ist aus 
		 * 
		 * */
		
		
		Logic test = new Logic();
		
		
		test.BabySteps(1 ,false); 	
		//test.setCompileErrors(true);
		//test.setTestFehlschlag(true);
	
		int i = 0;
		
		
		test.setStatus(Status.Red); System.out.println(i + ". " + test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		
		test.Input(Befehl.DoRed); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		
		/*
		 * - Compiliert 
		 * - Tests funktionieren
		 * - BabySteps ist aus 
		 * 
		 * */
		test.BabySteps(1 ,false); 	
		//test.setCompileErrors(false);
		//test.setTestFehlschlag(false);
		
		
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Status.Refactoring)); i++;
		test.Input(Befehl.DoGreen); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Status.Refactoring)); i++;
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Status.Refactoring)); i++;
		test.setStatus(Status.Red); System.out.println(i + ". " + test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		//test.setCompileErrors(true);
		//test.setTestFehlschlag(true);
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		test.setStatus(Status.Red); System.out.println(i + ". " + test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		
		
		/************* BABY STEPS ***********************/

		test.BabySteps(1 ,true); 
		
		//test.setCompileErrors(false);
		//test.setTestFehlschlag(true);
		
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		
		test.Input(Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.BabyRed)); i++;
		
		//test.setCompileErrors(true);
		//test.setTestFehlschlag(false);
		
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		
		test.Input(Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.BabyRed)); i++;
		
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		
		//test.setCompileErrors(true);
		//test.setTestFehlschlag(true);
		
		
		test.Input(Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.BabyRed)); i++;
		
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Green)); i++;
		
		//test.setCompileErrors(false);
		//test.setTestFehlschlag(false);
		
		
		test.Input(Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		test.Input(Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.BabyGreen)); i++;
		
		test.Input(Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Refactoring)); i++;
		test.Input(Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Status.Red)); i++;
		
}		
	

}
