//import BabySteps.Status;

/* Erste Skizze der Logik Architektur*/
/*
//package de.hhu.propra16.project7;

//import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;*/



// übersichtlichkeit halber: https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-two-finger-joe/blob/Logik/test


public class Logic {
	
	private Status Zustand;
	
	private boolean Baby;
	//private boolean ItWorks;
	private int Minuten;
	
	private boolean TestFehlschlag;
	private boolean CompileErrors;
	
	int seconds;
	
	



	
	public void Input(Befehl befehl){
		
		boolean CompileErrors = CompileErrors();  
		boolean TestFehlschlag = TestFehlschlag();
				
		//boolean CompilerWorks = CompileErrors("Name","Content");
		//boolean TestFehlschlag = TestFehlschlag("Name","Content");

				
				Status status = getStatus();
			/*	if(status==Status.BabyRed){
					
					setStatus(Status.Red);
				} */
		
		if(status==Status.Red||status==Status.BabyRed)  Red( befehl,  CompileErrors,  TestFehlschlag, status);
		if(status==Status.Green||status==Status.BabyGreen)  Green( befehl,  CompileErrors,   TestFehlschlag, status);
		if(status==Status.Refactoring)  Refactoring( befehl,  CompileErrors,  TestFehlschlag);
		
		
		return;
	}
	

	
	public void Red(Befehl befehl, 
			boolean CompilerWorks, boolean TestFehlschlag, Status status){
		

				if(getBabyBoolean()==true){StartTimer(getStatus());return;}
		
			if(befehl==Befehl.DoGreen && (CompileErrors==true||TestFehlschlag==true)){	
				setStatus(Status.Green);
				return;
				}
			
			setStatus(Status.Red);

		return;
				
	} 
	
	

	
	public void Green(Befehl befehl, 
			boolean CompileErrors, boolean TestFehlschlag,  Status status){
		
				if(getBabyBoolean()==true&&befehl!=Befehl.DoRefactoring){StartTimer(getStatus()); return;} 


			if(befehl==Befehl.DoRed){ setStatus(Status.Red); return;}
			if(befehl==Befehl.DoGreen){ setStatus(Status.Green); return;}
			if(befehl==Befehl.DoRefactoring && CompileErrors==false && TestFehlschlag == false){ setStatus(Status.Refactoring); return;}
			
			setStatus(Status.Green);
			return;		
	}
	

	
	
	public void Refactoring(Befehl befehl, 
			boolean CompileErrors, boolean TestFehlschlag){
			if(befehl==Befehl.DoRed && CompileErrors==false && TestFehlschlag == false){ setStatus(Status.Red); return;}
			
			setStatus(Status.Refactoring);
			return ;		
	}
	
	public void setStatus(Status status){
		this.Zustand = status;
		
	}
	
	public Status getStatus(){
		return Zustand;
	}
	
	/*
	
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
	
	
	
	
	/**/
	//TEST: TestFehlschlag & CompileErrors
	
	
	public void setTestFehlschlag(boolean TestFehlschlag){
				
		
		this.TestFehlschlag = TestFehlschlag;
		
		
	}
	
	
	public void setCompileErrors(boolean CompileErrors){
		
		this.CompileErrors =CompileErrors;
		
	} 
	
	
	public boolean TestFehlschlag(){
				
		
		return TestFehlschlag;
		
		
	}
	
	
	public boolean CompileErrors(){
		
		return CompileErrors;
		
	} 

	
	
	/**/
	//END TEST: TestFehlschlag & CompileErrors
	


	
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
	
	/* public void setItWorks(boolean ItWorks){
		this.ItWorks = ItWorks;
	}
	
	public boolean getItWorks(){
		
		
		return ItWorks;
	} */
	

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
		
		
		
		if((getStatus()==Status.Green||getStatus()==Status.Green) && (CompileErrors()==true || TestFehlschlag()==true)){setStatus(Status.BabyRed); return;}
		
		if((getStatus()==Status.Green||getStatus()==Status.Green) && (CompileErrors()==false && TestFehlschlag()==false)){setStatus(Status.Red); return;}
		

		if( (getStatus()==Status.Red||getStatus()==Status.BabyRed) && (CompileErrors()==false && TestFehlschlag()==false)){setStatus(Status.BabyGreen); return;}	
		
		if( (getStatus()==Status.Red||getStatus()==Status.BabyRed) && (CompileErrors()==true || TestFehlschlag()==true)){setStatus(Status.Green); return;}	
		
		
		
		
		
		
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
	
	
//ENUM * * * * * * * * * * * * * * * *            

public enum Status
{
	Red, Green, Refactoring, BabyRed, BabyGreen
}

public enum Befehl
{
	DoRed, DoGreen, DoRefactoring
}



	
		
}
