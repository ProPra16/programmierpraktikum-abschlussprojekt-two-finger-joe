//import BabySteps.Status;

package de.hhu.propra16.project7.controller;

import java.io.IOException;

import de.hhu.propra16.project7.catalogue.Project;
import de.hhu.propra16.project7.controller.Status;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

import de.hhu.propra16.project7.fileinteraction.*;

public class Logic {
	
	private Status Zustand;
	
	private boolean Baby;
	//private boolean ItWorks;
	private int Minuten;
	
	int seconds;
	
	Opener opener;
	Saver saver;
	Project aktProject;

	/*public static void main(String[] args){
		
		Logic test = new Logic();
		test.test();
		
	}*/
	
	public Logic(String title){		
		opener = new Opener(title);
		saver = new Saver(title);
		aktProject = new Project(title);		
	}
	
	public void Input(Befehl befehl, String classname, String eingabe) throws IOException{
		boolean CompilerWorks = CompileErrors(classname,eingabe);
		boolean TestFehlschlag = TestFehlschlag(classname, eingabe); 
				
		Status status = getStatus();
		/*
		if(status==Status.BabyRed){
			setStatus(Status.Red);
			} */
		
		if(status==Status.Red||status==Status.BabyRed)  Red( befehl,  CompilerWorks,  TestFehlschlag, status, classname, eingabe);
		if(status==Status.Green||status==Status.BabyGreen)  Green( befehl,  CompilerWorks,   TestFehlschlag, status, classname, eingabe);
		if(status==Status.Refactoring)  Refactoring( befehl,  CompilerWorks,  TestFehlschlag, classname, eingabe);
		
		return;
	}
	
	public void Red(Befehl befehl, boolean CompilerWorks, 
						boolean TestFehlschlag, Status status, String classname, String eingabe) throws IOException{
		
		if(getBabyBoolean()==true){StartTimer(getStatus(), classname, eingabe);return;}
		if(befehl==Befehl.DoGreen && (CompilerWorks==false||TestFehlschlag==true)){	
			{	setStatus(Status.Green); 
				opener.open(getStatus(), classname);
				saver.save(getStatus(), eingabe);			
			}
			return;
		}
		setStatus(Status.Red);
		return;
	} 
	
	public void Green(Befehl befehl, boolean CompilerWorks,
							boolean TestFehlschlag,  Status status, String classname, String eingabe) throws IOException{
		if(getBabyBoolean()==true&&befehl!=Befehl.DoRefactoring){
			StartTimer(getStatus(), classname,eingabe);
			return;
		} 
		if(befehl==Befehl.DoRed){ 
			setStatus(Status.Red); 
			opener.open(getStatus(), classname);
			saver.save(getStatus(), eingabe);	
		return;}
		if(befehl==Befehl.DoGreen){ 
			return;}
		if(befehl==Befehl.DoRefactoring && CompilerWorks==true && TestFehlschlag == false){ 
			setStatus(Status.Refactoring);
			opener.open(getStatus(), classname);
			saver.save(getStatus(), eingabe);	
			return;}
			
		if(befehl==Befehl.DoRefactoring && getBabyBoolean()==true && (CompileErrors==true || TestFehlschlag == true)){ 
				Delete.delete(Status.BabyRed, aktProject.getTitle(), classname);
				setStatus(Status.Red); return;}
			
		return;		
	}
	
	public void Refactoring(Befehl befehl, boolean CompilerWorks, 
								boolean TestFehlschlag, String classname, String eingabe) throws IOException{
			if(befehl==Befehl.DoRed && CompilerWorks==true && TestFehlschlag == false){ 
				
				setStatus(Status.Red); 
				opener.open(getStatus(), classname);
				saver.save(getStatus(), eingabe);
				return;}

			
			return ;		
	}
	
	public void setStatus(Status status){
		this.Zustand = status;
	}
	
	public Status getStatus(){
		return Zustand;
	}
	
	
	public JavaStringCompiler CompilerRun(String className, String classContent){
		CompilationUnit unit = new CompilationUnit(className, classContent, false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		compiler.compileAndRunTests();
		return compiler;
	}
	
	public boolean TestFehlschlag(String className, String classContent){
		JavaStringCompiler compiler = CompilerRun(className, classContent);
		TestResult result = compiler.getTestResult();
		
		if(result.getNumberOfFailedTests()>=1){
			return true;	
		
		//TODO Exception falls die Datei nicht vorhanden ist
		}
		
		return false;
	}
	
	public boolean CompileErrors(String className, String classContent){
		JavaStringCompiler compiler = CompilerRun(className, classContent);
		CompilerResult result = compiler.getCompilerResult();
						
		return result.hasCompileErrors();  
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
	
	/*public void setItWorks(boolean ItWorks){
		this.ItWorks = ItWorks;
	}
	
	public boolean getItWorks(){
		
		
		return ItWorks;
	}*/
	
	public void StartTimer(Status status , String classname, String eingabe){

		int Minuten = getMinute();
		long Vergleich = ConvertSeconds(Minuten);
		 Stoppuhrstarte(status, Vergleich, classname, eingabe);
	}
	
	public long ConvertSeconds(int Minuten){
	long Vergleich  = Minuten/2;
	return Vergleich;
	}
	
	void Stoppuhrstarte(Status status, Long Vergleich, String classname, String eingabe)
	{ 
		
		seconds = 0;
		
		
		try {
		while(seconds<=Vergleich){
			 Thread.sleep(1000); seconds++;}
		
		
		if((getStatus()==Status.Green||getStatus()==Status.Green)
				&& (CompileErrors(classname,eingabe)==true || TestFehlschlag("Name","classContent")==true))
				{
				Delete.delete(Status.BabyRed, aktProject.getTitle(), classname);
				setStatus(Status.Red); 
				return;}
		
		if((getStatus()==Status.Green||getStatus()==Status.Green) 
				&& (CompileErrors(classname,eingabe)==false && TestFehlschlag("Name","classContent")==false))
				{
				setStatus(Status.Red); 
				return;}
		

		if( (getStatus()==Status.Red||getStatus()==Status.BabyRed)
				&& (CompileErrors(classname,eingabe)==false && TestFehlschlag("Name","classContent")==false))
				{
				Delete.delete(Status.BabyGreen, aktProject.getTitle(), classname);
				setStatus(Status.Green); 
				return;}	
		
		if( (getStatus()==Status.Red||getStatus()==Status.BabyRed) 
				&& (CompileErrors(classname,eingabe)==true || TestFehlschlag("Name","classContent")==true))
				{
				setStatus(Status.Green); 
				return;}	
		
		
		
		
		/*
		if(getItWorks()==false){
			
			setStatus(Status.BabyRed);
		
		}
		
		if(getItWorks()==true){return;}*/
		
		
		
		}
		catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
}
