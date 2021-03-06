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

/* @author Gareth Bilaney & Thaís */

public class Logic {

	private Status Zustand;
	private long RunTime;
	private int aufgaben;

	Opener opener;
	Saver saver;
	Deleter deleter;
	Tracker tracker;
	Project aktProject;

	public Logic(String title, Status currStatus) {
		opener = new Opener(title);
		saver = new Saver(title);
		aktProject = new Project(title);
		deleter = new Deleter(title);
		tracker = new Tracker(title);
		aufgaben = 0;
		Zustand = currStatus;
		starteRunTime();
	}

	public void Input(Befehl befehl, String classname, String eingabe) throws IOException {

		Status status = getStatus();
		System.out.println("Status" + status);

		if (status == Status.Red)
			Red(befehl, classname, eingabe);
		if (status == Status.Green)
			Green(befehl, classname, eingabe);
		if (status == Status.Refactoring)
			Refactoring(befehl, classname, eingabe);

		return;
	}

	public void Red(Befehl befehl, String classname, String eingabe) throws IOException {

		if (befehl == Befehl.DoGreen && (CompileErrors(classname, eingabe) == true || TestFehlschlag(classname, eingabe) == true)) {
				setStatus(Status.Green);
				stoppeRunTime();
				tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0);
				starteRunTime();
				opener.open(getStatus(), classname);
				saver.save(getStatus(), eingabe);
			}
			return;
		}

	public void Green(Befehl befehl, String classname, String eingabe) throws IOException {

		if (befehl == Befehl.DoRed) {
			setStatus(Status.Red);
			stoppeRunTime();
			tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0);
			starteRunTime();
			saver.save(getStatus(), eingabe);
			opener.open(getStatus(), classname);

			if(CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false) aufgaben++;

			return;
		}

		if (befehl == Befehl.DoGreen) return;

		if (befehl == Befehl.DoRefactoring && CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false) {
			setStatus(Status.Refactoring);
			stoppeRunTime();
			tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0);
			starteRunTime();
			saver.save(getStatus(), eingabe);
			opener.open(getStatus(), classname);

			return;
		}

		return;
	}

	public void Refactoring(Befehl befehl, String classname, String eingabe) throws IOException {

		if (befehl == Befehl.DoRed && CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false) {

			setStatus(Status.Red);
			stoppeRunTime();
			tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, 0);
			starteRunTime();
			saver.save(getStatus(), eingabe);
			opener.open(getStatus(), classname);

			aufgaben++;
			return;
		}

		return;
	}

		void starteRunTime(){
			RunTime = RunTime + System.currentTimeMillis();
		}

		void stoppeRunTime(){
			RunTime = System.currentTimeMillis() - RunTime;
		}

		long returnRunTime(){
			return RunTime;
		}

	//Setters und Getters
	public void setStatus(Status status) {
		this.Zustand = status;
	}

	public Status getStatus() {
		return Zustand;
	}

	public int getAufgabe() {
		return aufgaben;
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

	}

	public boolean CompileErrors(String className, String classContent) {
		JavaStringCompiler compiler = CompilerRun(className, classContent, false);

		return compiler.getCompilerResult().hasCompileErrors();
	}



	public void BabySteps(String classname, String eingabe, int time) {
			try{
				//Falls Code nicht kompiliert, oder ein Test fehlschlägt: Bedingung nicht erfüllt!
				if (getStatus() == Status.Green && (CompileErrors(classname, eingabe) == true || TestFehlschlag(classname, eingabe) == true)) {
					stoppeRunTime();
					setStatus(Status.BabyRed);
					tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int) time);
					deleter.delete(Status.BabyRed, classname);
					return;
					}
				//Falls Code  kompiliert und  ein Test nicht fehlschlägt: Bedingung erfüllt!
				if (getStatus() == Status.Green	&& (CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false)) {
					stoppeRunTime();
					setStatus(Status.BabyGreen);
					tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int) time);
					return;
					}
				//Falls Code kompiliert und  ein Test nicht fehlschlägt: Bedingung nicht erfüllt!
				if (getStatus() == Status.Red && (CompileErrors(classname, eingabe) == false && TestFehlschlag(classname, eingabe) == false)) {
					stoppeRunTime();
					setStatus(Status.BabyGreen);
					tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int) time);
					starteRunTime();
					deleter.delete(Status.BabyGreen, classname);
					return;
					}
				//Falls Code kompiliert oder  ein Test  fehlschlägt. Bedingung erfüllt!
				if (getStatus() == Status.Red	&& (CompileErrors(classname, eingabe) == true || TestFehlschlag(classname, eingabe) == true)) {
					stoppeRunTime();
					setStatus(Status.Green);
					tracker.statusChanged(getStatus(), (int)returnRunTime()/1000, (int) time);
					starteRunTime();
					return;
					}
			} catch(IOException e){
				System.out.println(e.getMessage());
				}
			}
}
