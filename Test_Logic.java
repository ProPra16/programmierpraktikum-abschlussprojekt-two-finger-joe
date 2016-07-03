//import BabySteps.Status;

/* Erste Skizze der Logik Architektur*/

//package de.hhu.propra16.project7;

//import vk.core.api.CompilationUnit;
//import vk.core.api.CompilerFactory;
//import vk.core.api.JavaStringCompiler;
//import vk.core.internal.InternalResult



// übersichtlichkeit halber: https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-two-finger-joe/blob/Logik/test





public class Logic {
	
	private Status Zustand;
	
	private boolean Baby;
	private int Minuten;
	
	int seconds;
	
	public static void main(String[] args){
	
		test();
	}
	


	
	public void Input(Befehl befehl, 
				boolean TestFehlschlag, int BabySteps){
				boolean CompilerWorks = true;
				
				
			//	boolean CompilerWorks = CompileErrors();
				
				Status status = getStatus();
		
		if(status==Status.Red)  Red( befehl,  CompilerWorks,  TestFehlschlag, status, BabySteps);
		if(status==Status.Green)  Green( befehl,  CompilerWorks,  TestFehlschlag);
		if(status==Status.Refactoring)  Refactoring( befehl,  CompilerWorks,  TestFehlschlag);
		
		
		return;
	}
	

	
	public void Red(Befehl befehl, 
			boolean CompilerWorks, boolean TestFehlschlag, Status status, int BabySteps){
		

				if(getBabyBoolean()==true){StartTimer(getStatus());return;}
		
			if(befehl==Befehl.DoGreen && (CompilerWorks==false||TestFehlschlag==true)){	
				setStatus(Status.Green);
				return;
				}
			
			setStatus(Status.Red);

		return;
				
	} 
	
	

	
	public void Green(Befehl befehl, 
			boolean CompilerWorks, boolean TestFehlschlag){
		
				if(getBabyBoolean()==true){StartTimer(getStatus());return;}


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
	
	
/*	public boolean CompileErrors(String className, String classContent){
			CompilationUnit unit = new CompilationUnit(className, classContent, false);
			JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		return  compiler.hasCompileErrors();
	
	} */
	
	
	
	

	
	public static void test(){
		
		
			
		
			
			Logic log = new Logic(); 	
			
			log.BabySteps(1 ,false);
			
			log.setStatus(Status.Red); //Standard
			
			log.Input(Befehl.DoGreen,true, 0);
			
			System.out.println(log.getStatus());
			
			log.Input(Befehl.DoRed,true, 0);
			
			System.out.println(log.getStatus());
			
			log.Input(Befehl.DoGreen,true, 0);
			
			System.out.println(log.getStatus());
			
			
			log.Input(Befehl.DoRefactoring,false, 0);
			
			System.out.println(log.getStatus());
			
				log.Input(Befehl.DoGreen,false, 0);
			
			System.out.println(log.getStatus());
			
			log.Input(Befehl.DoRed,false, 0);
			
			System.out.println(log.getStatus());
			
			log.Input(Befehl.DoRefactoring,false, 0);
			
			System.out.println(log.getStatus());
			
			log.Input(Befehl.DoGreen,true, 0);
			
			System.out.println(log.getStatus());
			
	
	
		
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
	

	public void StartTimer(Status status ){

		int Minuten = getMinute();
		long Vergleich = ConvertSeconds(Minuten);
		 Stoppuhrstarte(status, Vergleich);
	}
	
	public long ConvertSeconds(int Minuten){
	long Vergleich  = Minuten*6;
	return Vergleich;
	}
	
	void Stoppuhrstarte(Status status, Long Vergleich)
	{ 
		
		seconds = 0;
		try {
		while(seconds<=Vergleich){
			 Thread.sleep(1000); seconds++;}
		
		if(status==Status.Green){;setStatus(Status.Red);}
		if(status==Status.Red){setStatus(Status.Green);}
		

		
		
		
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
	
	
//ENUM * * * * * * * * * * * * * * * *            

public enum Status
{
	Red, Green, Refactoring
}

public enum Befehl
{
	DoRed, DoGreen, DoRefactoring
}



	
		
}
