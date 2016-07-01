package de.hhu.propra16.project7;

/* Erste Skizze der Logik Architektur*/


//Nutzer = 0; Wählt RED
//Nutzer = 1; Wählt GREEN
//Nutzer = 1; BLACK

//Status = 0; Befindet sich in  RED
//Status = 1;  Befindet sich in   GREEN
//Status = 1;  Befindet sich in  BLACK



public class Logic {
	
	/*public static void main(String[] args){
		//Logic log = new Logic();
		//int a = log.Input(0,true,false,2);
		//System.out.println(a);
		
	}*/
	
	/* 
	 * In Input werden Nutzerwahl, Status und Zustände [Kompiliert der Code / Schlagen Tests fehl?] 
	 * des Codes eingelesen. llten wir uns in Status Green befinden, 
	 * der User in Refactoring befinden, werden alle Daten eingelesen und der nächste Status wird ermittelt. 
	 * 
	 */
	

	
	public int Input(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag, int Status){
		
		if(Status==0)Status = Red( Nutzer,  CompilerWorks,  TestFehlschlag);
		if(Status==1)Status = Green(Nutzer,  CompilerWorks, TestFehlschlag);
		if(Status==2) Status =  Black(Nutzer,  CompilerWorks,  TestFehlschlag);
		
		return Status*-1;
	}
	
	/* 
	 * In Input werden Nutzerwahl, Status und Zustände [Kompiliert der Code / Schlagen Tests fehl?] 
	 * des Codes eingelesen. llten wir uns in Status Green befinden, 
	 * der User in Refactoring befinden, werden alle Daten eingelesen und der nächste Status wird ermittelt. 
	 * 
	 */
	
	public int Red(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag){
		
			if(Nutzer==1 && (CompilerWorks==false||TestFehlschlag==true)){	
				return -1;
				}

		return 0;
				
	}
	
	
	/*Befinden wir uns Status Red, wird geschaut, ob entweder ein Test fehlschlägt, 
	 * oder der Code nicht kompiliert. Sollte die Nutzerwahl = 1 (GREEN) sein, 
	 * wird der nächste Status gespeichert. Wir wechseln zu Status Green. 
	 * Wollten wir in Refactoring gelangen, muss der Code alle Tests bestehen und der Code muss kompilieren. 
	 * Nutzerwahl 2 wird dann erkannt.
	 */
	
	
	public int Green(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag){

			if(Nutzer==0)return 0;
			if(Nutzer==1)return -1;
			if(Nutzer==2 && CompilerWorks==true && TestFehlschlag == false)return -2;
			return -1;		
	}
	
	/* Tests müssen vor und nach dem Refactoring funktionieren. 
	 * Wollten wir also die Tests bearbeiten, muss dies geprüft werden. Erst dann wird Nutzer = 0 erkannt. 
	 */
	
	
	public int Black(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag){
			if(Nutzer==0 && CompilerWorks==true && TestFehlschlag == false)return 0;
			return -2;		
	}
}