//import BabySteps.Status;

/* Erste Skizze der Logik Architektur*/

package de.hhu.propra16.project7;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;



// übersichtlichkeit halber: https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-two-finger-joe/blob/Logik/test





public class Logic {
	
	private Status Zustand;
	
	private boolean Baby;
	private boolean Interrupt;
	private int Minuten;
	
	int seconds;
	
	
	public static void main(String[] args){
		
		Logic test = new Logic();
		test.test();
		
	}
	


	
	public void Input(Befehl befehl, boolean TestFehlschlag){
							
			boolean CompilerWorks = CompileErrors("Name","Content");

				
				Status status = getStatus();
		
		if(status==Status.Red||status==Status.BabyRed)  Red( befehl,  CompilerWorks,  TestFehlschlag, status);
		if(status==Status.Green)  Green( befehl,  CompilerWorks,   TestFehlschlag, status);
		if(status==Status.Refactoring)  Refactoring( befehl,  CompilerWorks,  TestFehlschlag);
		
		
		return;
	}
	

	
	public void Red(Befehl befehl, 
			boolean CompilerWorks, boolean TestFehlschlag, Status status){
		

				if(getBabyBoolean()==true){StartTimer(getStatus());return;}
		
			if(befehl==Befehl.DoGreen && (CompilerWorks==false||TestFehlschlag==true)){	
				setStatus(Status.Green);
				return;
				}
			
			setStatus(Status.Red);

		return;
				
	} 
	
	

	
	public void Green(Befehl befehl, 
			boolean CompilerWorks, boolean TestFehlschlag,  Status status){
		
				if(getBabyBoolean()==true&&befehl!=Befehl.DoRefactoring){StartTimer(getStatus()); return;} 


			if(befehl==Befehl.DoRed){ setStatus(Status.Red); return;}
			if(befehl==Befehl.DoGreen){ setStatus(Status.Green); return;}
			if(befehl==Befehl.DoRefactoring && CompilerWorks==true && TestFehlschlag == false){ setStatus(Status.Refactoring); return;}
			
			setStatus(Status.Green);
			return;		
	}
	

	
	
	public void Refactoring(Befehl befehl, 
			boolean CompilerWorks, boolean TestFehlschlag){
			if(befehl==Befehl.DoRed && CompilerWorks==true && TestFehlschlag == false){ setStatus(Status.Red); return;}
			
			setStatus(Status.Refactoring);
			return ;		
	}
	
	public void setStatus(Status status){
		this.Zustand = status;
		
	}
	
	public Status getStatus(){
		return Zustand;
	}
	
	
	
	public boolean TestFehlschlag(String className, String classContent){
		CompilationUnit unit = new CompilationUnit(className, classContent, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		compiler.compileAndRunTests();
		TestResult result = compiler.getTestResult();
		
		if(result.getNumberOfFailedTests()>=1) return true;				
		
		return false;
		
		
	}
	
	
	public boolean CompileErrors(String className, String classContent){
			CompilationUnit unit = new CompilationUnit(className, classContent, false);
			JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
			compiler.compileAndRunTests();
			CompilerResult result = compiler.getCompilerResult();
						
		return result.hasCompileErrors();  
	
	}

	
	public  void test(){
		
		
			
		
			
			
			
			BabySteps(1 ,true); 		
		
			
			
			
			setStatus(Status.Red); //Standard
			
			Input(Befehl.DoGreen,true);
			
			
			System.out.println(getStatus());
			
			Input(Befehl.DoRed,true);
			
			System.out.println(getStatus());
			
			Input(Befehl.DoGreen,true);
			
			System.out.println(getStatus());
		
			Input(Befehl.DoRefactoring,false);
			
			System.out.println(getStatus());
	
			
				Input(Befehl.DoGreen,false);
			
			System.out.println(getStatus());
			
			Input(Befehl.DoRed,false);
			
			System.out.println(getStatus());
			
			Input(Befehl.DoRefactoring,false);
			
			System.out.println(getStatus());
			
			Input(Befehl.DoGreen,true);
			
			System.out.println(getStatus());
			
	
	
		
	}
	
	


	
//BabySteps * * * * * * * * * * * * * * * *     
	
	public void BabySteps(int Minuten, boolean Baby ){
		
		setMinute(Minuten);
		setBabyBoolean(Baby);
		
	}
	
	public void setMinute(int Minuten){
		this.Minuten = Minuten;
		
	}
	
	public int getMinute(){
		return Minuten;
	}
	
	
	public void setBabyBoolean(boolean Baby){
		this.Baby = Baby;
		
	}
	
	public boolean getBabyBoolean(){
		return Baby;
	}
	
	public void setInterrupt(boolean Interrupt){
		this.Interrupt = Interrupt;
	}
	/*
	public boolean getInterrupt(){
		return Interrupt;
	}*/
	

	public void StartTimer(Status status ){

		int Minuten = getMinute();
		long Vergleich = ConvertSeconds(Minuten);
		 Stoppuhrstarte(status, Vergleich);
	}
	//*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
	public long ConvertSeconds(int Minuten){
	long Vergleich  = Minuten/2;  //*60;
	return Vergleich;
	}
	
	void Stoppuhrstarte(Status status, Long Vergleich)
	{ 
		
		seconds = 0;
		try {
		while(seconds<=Vergleich){
			
		
			
			 Thread.sleep(1000); seconds++;}
		
		//if(getInterrupt()==false){
			
		if(status==Status.Green||status==Status.BabyGreen){;setStatus(Status.BabyRed);}
		if(status==Status.Red||status==Status.BabyRed){setStatus(Status.BabyRed);}
			
			
			
		//}
		/*
		if(getInterrupt()==true){
		if(status==Status.Green||status==Status.BabyGreen){;setStatus(Status.Red);}
		if(status==Status.Red||status==Status.BabyRed){setStatus(Status.Green);}}*/
		
		
		
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
	
	
//ENUM * * * * * * * * * * * * * * * *            

public enum Status
{
	Red, Green, Refactoring, BabyRed
}

public enum Befehl
{
	DoRed, DoGreen, DoRefactoring
}



	
		
}
