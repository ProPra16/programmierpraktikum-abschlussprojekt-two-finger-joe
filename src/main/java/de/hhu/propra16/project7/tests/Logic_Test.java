//import Logic.Status;

//import Logic.Befehl;
//import Logic.Status;
package de.hhu.propra16.project7.tests;

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
		test.setCompileErrors(true);
		test.setTestFehlschlag(true);
	
		int i = 0;
		
		
		test.setStatus(Logic.Status.Red); System.out.println(i + ". " + test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		
		test.Input(Logic.Befehl.DoRed); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		
		/*
		 * - Compiliert 
		 * - Tests funktionieren
		 * - BabySteps ist aus 
		 * 
		 * */
		test.BabySteps(1 ,false); 	
		test.setCompileErrors(false);
		test.setTestFehlschlag(false);
		
		
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Logic.Status.Refactoring)); i++;
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Logic.Status.Refactoring)); i++;
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". "+test.getStatus() + " is " + (test.getStatus()==Logic.Status.Refactoring)); i++;
		test.setStatus(Logic.Status.Red); System.out.println(i + ". " + test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.setCompileErrors(true);
		test.setTestFehlschlag(true);
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		test.setStatus(Logic.Status.Red); System.out.println(i + ". " + test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		
		
		/************* BABY STEPS ***********************/

		test.BabySteps(1 ,true); 
		
		test.setCompileErrors(false);
		test.setTestFehlschlag(true);
		
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		
		test.Input(Logic.Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.BabyRed)); i++;
		
		test.setCompileErrors(true);
		test.setTestFehlschlag(false);
		
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		
		test.Input(Logic.Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.BabyRed)); i++;
		
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		
		test.setCompileErrors(true);
		test.setTestFehlschlag(true);
		
		
		test.Input(Logic.Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.BabyRed)); i++;
		
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Green)); i++;
		
		test.setCompileErrors(false);
		test.setTestFehlschlag(false);
		
		
		test.Input(Logic.Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		test.Input(Logic.Befehl.DoGreen); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.BabyGreen)); i++;
		
		test.Input(Logic.Befehl.DoRefactoring); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Refactoring)); i++;
		test.Input(Logic.Befehl.DoRed); System.out.println(i + ". " +test.getStatus() + " is " + (test.getStatus()==Logic.Status.Red)); i++;
		
}		
	

}
