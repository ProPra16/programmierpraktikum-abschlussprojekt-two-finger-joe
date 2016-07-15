package de.hhu.propra16.project7.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.*;
import de.hhu.propra16.project7.catalogue.Project;
import de.hhu.propra16.project7.fileinteraction.Deleter;
import de.hhu.propra16.project7.fileinteraction.Opener;
import de.hhu.propra16.project7.fileinteraction.Saver;
import de.hhu.propra16.project7.tracking.Tracker;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;

public class Logic {

	private Status Zustand;

	private boolean Baby;
	private boolean CounterActive;
	private int Minuten;
	private Text counter;
	long RunTime; 
	private Timeline timeline;
	
	private int aufgaben;

	Opener opener;
	Saver saver;
	Deleter deleter;
	Tracker tracker;
	Project aktProject;
	
	private Integer STARTTIME;

	public Logic(String title, Text counter, Status currStatus) {
		opener = new Opener(title);
		saver = new Saver(title);
		aktProject = new Project(title);
		deleter = new Deleter(title);
		tracker = new Tracker(title);
		this.counter = counter;
		aufgaben = 0;
		Zustand = currStatus;
		starteRunTime(); 
	}

	public int getAufgabe() {
		return aufgaben;
	}

	public void Input(Befehl befehl, String classname, String eingabe) throws IOException {
		

		Status status = getStatus();

		System.out.println("Status" + status);
	
	if (getBabyBoolean() == true) {
			System.out.println("Starte BabySteps...");
			starteBabyTime();  
			StartTimer(befehl, getStatus(), classname, eingabe);
			return;
		}

		if (status == Status.Red)
			Red(befehl, status, classname, eingabe);
		if (status == Status.Green)
			Green(befehl, status, classname, eingabe);
		if (status == Status.Refactoring)
			Refactoring(befehl, classname, eingabe);

		return;
	}

	public void Red(Befehl befehl, Status status, String classname, String eingabe) throws IOException {

		if (befehl == Befehl.DoGreen && (CompileErrors(classname, eingabe) == true || TestFehlschlag(classname, eingabe) == true)) {
			{
				setStatus(Status.Green);
				stoppeRunTime();
				tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0); // Look here
				starteRunTime();
				opener.open(getStatus(), classname);
				saver.save(getStatus(), eingabe);
			}
			return;
		}
		// setStatus(Status.Red);
		return;
	}

	public void Green(Befehl befehl, 
			 Status status, String classname, String eingabe) throws IOException {
	
		if (befehl == Befehl.DoRed) {
			setStatus(Status.Red);
			stoppeRunTime();
			tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0); // Look here
			starteRunTime();
			saver.save(getStatus(), eingabe);
			opener.open(getStatus(), classname);
			
			if(CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false) aufgaben++;
			return;
		}
		if (befehl == Befehl.DoGreen) {
			return;
		}
		if (befehl == Befehl.DoRefactoring && CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false) {
			setStatus(Status.Refactoring);
			stoppeRunTime();
			tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0); // Look here
			starteRunTime();
			saver.save(getStatus(), eingabe);
			opener.open(getStatus(), classname);
			
			return;
		}



		return;
	}

	public void Refactoring(Befehl befehl, 
			String classname, String eingabe) throws IOException {
		if (befehl == Befehl.DoRed && CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false) {

			setStatus(Status.Red);
			
			stoppeRunTime();
			tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0); // Look here
			starteRunTime();
			saver.save(getStatus(), eingabe);
			opener.open(getStatus(), classname);
			
			aufgaben++;
			return;
		}

		return;
	}


	public void starteBabyTime(){
		if (timeline != null) {
	            timeline.stop();
	        }
		
		IntegerProperty Minuten = new SimpleIntegerProperty(STARTTIME);
			counter.textProperty().bind(Minuten.asString());
	        Minuten.set(STARTTIME);
	        timeline = new Timeline();
	        timeline.getKeyFrames().add(
	                new KeyFrame(Duration.minutes(STARTTIME+1),
	                new KeyValue(Minuten, 0)));
	        timeline.playFromStart();
	    }
	
	//Idee für Minuten und Sekunden:
	
    /*public void resetTimer() {
        timeline.stop();
        startTimeSec = 60; 
        startTimeMin = min-1;
        timerText.setText(String.format("%d min, %02d sec", startTimeMin, startTimeSec));*/
	

		void starteRunTime()
		{ RunTime = RunTime + System.currentTimeMillis();
			}
		void stoppeRunTime()
		{ RunTime = System.currentTimeMillis() - RunTime;
		}
		long returnRunTime()
		{ return RunTime;
		}

		/*void starteBabyTime()
		{ RunTime = System.currentTimeMillis();
			}
		void stoppeBabyTime()
		{ RunTime = System.currentTimeMillis() - RunTime;
		}
		long returnBabyTime()
		{ return RunTime;
		}*/


	public void setStatus(Status status) {
		this.Zustand = status;
	}

	public Status getStatus() {
		return Zustand;
	}

	public void CounterActive(boolean status) {
		this.CounterActive = CounterActive;
	}

	public boolean getCounterActive() {
		return CounterActive;
	}

	public JavaStringCompiler CompilerRun(String className, String classContent, boolean isTest) {
		CompilationUnit unit = new CompilationUnit(className, classContent, isTest);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		Collection<CompileError> errs = compilerResult.getCompilerErrorsForCompilationUnit(unit);
		for (CompileError e : errs) {
			System.out.println(e.getMessage());
			System.out.println(e.getCodeLineContainingTheError());
		}
		System.out.println("End of errors");
		return compiler;
	}

	public boolean TestFehlschlag(String className, String classContent) {

		if(CompileErrors(className, classContent) == false){
		JavaStringCompiler compiler = CompilerRun(className, classContent, true);

		int result = compiler.getTestResult().getNumberOfFailedTests();
		System.out.println("Failed tests " + result);
		if (result != 0) {
			return true;
		}
		return false;

		} return false;
		// TODO Exception falls die Datei nicht vorhanden ist
	}

	public boolean CompileErrors(String className, String classContent) {
		JavaStringCompiler compiler = CompilerRun(className, classContent, false);

		return compiler.getCompilerResult().hasCompileErrors();
	}

	// BabySteps * * * * * * * * * * * * * * * *

	public void BabySteps(int Minuten, boolean Baby) {

		setMinute(Minuten);
		setBabyBoolean(Baby);

	}

	public void setMinute(int Minuten) {
		this.Minuten = Minuten;

	}

	public int getMinute() {
		return Minuten;
	}

	public void setBabyBoolean(boolean Baby) {
		this.Baby = Baby;

	}

	public boolean getBabyBoolean() {
		return Baby;
	}

	/*public void StartTimer(Befehl befehl, Status status, String classname, String eingabe) throws IOException {

		int Minuten = getMinute();
		long Vergleich = ConvertSeconds(Minuten);
		
		Stoppuhrstarte(befehl, status, Vergleich, classname, eingabe, Minuten);
	}

	public long ConvertSeconds(int Minuten) {
		long Vergleich = Minuten * 600000;
		return Vergleich;
	}

	void Stoppuhrstarte(Befehl befehl, Status status, long Vergleich, String classname, String eingabe, int Minuten) throws IOException {
				
		CounterActive(true);




			
			stoppeBabyTime();

			if(returnBabyTime()>=Vergleich)System.out.println("fertig"); //Für Thais: hier prüft er den Counter. Wenn Zeit nicht abgelaufen, springt er zurück.
			else return;

	
	


			
			
			CounterActive(false);

			if (getStatus() == Status.Green
					&& (CompileErrors(classname, eingabe) == true || TestFehlschlag("Name", "classContent") == true)) {

				stoppeRunTime();
				tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int)Minuten); // Look here
				starteRunTime();
				deleter.delete(Status.BabyRed, classname);
				setStatus(Status.Red);
				return;
			}

			if (getStatus() == Status.Green
					&& (CompileErrors(classname, eingabe) == false
							&& TestFehlschlag("Name", "classContent") == false)) {

				stoppeRunTime();
				tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int)Minuten); // Look here
				starteRunTime();
				setStatus(Status.Red);
				return;
			}

			if (getStatus() == Status.Red
					&& (CompileErrors(classname, eingabe) == false
							&& TestFehlschlag(classname, eingabe) == false)) {

				stoppeRunTime();
				tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int)Minuten); // Look here
				starteRunTime();
				deleter.delete(Status.BabyGreen, classname);
				setStatus(Status.Green);
				return;
			}

			if (getStatus() == Status.Red
					&& (CompileErrors(classname, eingabe) == true || TestFehlschlag(classname, eingabe) == true)) {

				stoppeRunTime();
				tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int)Minuten); // Look here
				starteRunTime();
				setStatus(Status.Green);
				return;
			}

		
	}*/

}
