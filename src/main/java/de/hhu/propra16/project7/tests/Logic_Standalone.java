package de.hhu.propra16.project7.tests;

public class Logic_Standalone {
	/*
	private Status Zustand;
	private boolean Baby;
	private int Minuten;
	private boolean TestFehlschlag;
	private boolean CompileErrors;
	
	int seconds;
	
	



	
	public void Input(Befehl befehl){
		
		boolean CompileErrors = CompileErrors();  
		boolean TestFehlschlag = TestFehlschlag();
				

				
				Status status = getStatus();

		
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
				
				
				if(befehl!=Befehl.DoRefactoring&&getBabyBoolean()==true){StartTimer(getStatus()); return;} 


			if(befehl==Befehl.DoRed){ setStatus(Status.Red); return;}
			if(befehl==Befehl.DoGreen){ setStatus(Status.Green); return;}
			if(befehl==Befehl.DoRefactoring && CompileErrors==false && TestFehlschlag == false){ setStatus(Status.Refactoring); return;}
			
			
			if(befehl==Befehl.DoRefactoring && getBabyBoolean()==true && (CompileErrors==true || TestFehlschlag == true)){ 
				 System.out.println(" - Delete Code - Refactoring not possible");
				setStatus(Status.Red); return;}
			
			
			
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
	long Vergleich  = Minuten/2;  //*60;
	return Vergleich;
	}
	
	void Stoppuhrstarte(Status status, Long Vergleich)
	{ 
		
		seconds = 0;
		try {
		while(seconds<=Vergleich){
			
		
			
			 Thread.sleep(1000); seconds++;}
		
		
		
		if((getStatus()==Status.Green) && 
					(CompileErrors()==true || TestFehlschlag()==true)){  System.out.println(" - Delete Code"); setStatus(Status.Red); return;} //Änderung
		
		if((getStatus()==Status.Green) && 
				(CompileErrors()==false && TestFehlschlag()==false)){setStatus(Status.Red); return;}
		

		if( (getStatus()==Status.Red) && 
				(CompileErrors()==false && TestFehlschlag()==false)){ System.out.println(" - Delete Test"); setStatus(Status.Green); return;} //Änderung
		
		if( (getStatus()==Status.Red) && 
				(CompileErrors()==true || TestFehlschlag()==true)){setStatus(Status.Green); return;}	
		
		
		
		
		
		
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
	
        

public enum Status
{
	Red, Green, Refactoring, BabyRed, BabyGreen
}

public enum Befehl
{
	DoRed, DoGreen, DoRefactoring
}

*/

	
		
}
