
package de.hhu.propra16.project7.controller;

import java.io.IOException;

import de.hhu.propra16.project7.catalogue.Project;
import de.hhu.propra16.project7.controller.Status;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;
import javafx.scene.text.*;

import de.hhu.propra16.project7.fileinteraction.*;
import de.hhu.propra16.project7.tracking.Tracker;

public class Logic {

	private Status Zustand;

	private boolean Baby;
	private boolean CounterActive;
	private int Minuten;
	private Text counter;

	int seconds;

	private int aufgaben;

	Opener opener;
	Saver saver;
	Deleter deleter;
	Tracker tracker;
	Project aktProject;

	int RunTime = 0; // FIXME

	public Logic(String title, Text counter) {
		opener = new Opener(title);
		saver = new Saver(title);
		aktProject = new Project(title);
		deleter = new Deleter(title);
		tracker = new Tracker(title);
		this.counter = counter;
		aufgaben = 0;

	}

	public int getAufgabe() {
		return aufgaben;
	}

	public void Input(Befehl befehl, String classname, String eingabe) throws IOException {
		boolean CompilerWorks = CompileErrors(classname, eingabe);
		boolean TestFehlschlag = TestFehlschlag(classname, eingabe);

		Status status = getStatus();

		if (status == Status.Red)
			Red(befehl, CompilerWorks, TestFehlschlag, status, classname, eingabe);
		if (status == Status.Green)
			Green(befehl, CompilerWorks, TestFehlschlag, status, classname, eingabe);
		if (status == Status.Refactoring)
			Refactoring(befehl, CompilerWorks, TestFehlschlag, classname, eingabe);

		return;
	}

	public void Red(Befehl befehl, boolean CompilerWorks,
			boolean TestFehlschlag, Status status, String classname, String eingabe) throws IOException {

		if (getBabyBoolean() == true) {
			StartTimer(getStatus(), classname, eingabe);
			return;
		}

		if (befehl == Befehl.DoGreen && (CompilerWorks == false || TestFehlschlag == true)) {
			{
				setStatus(Status.Green);

				tracker.statusChanged(getStatus(), RunTime, -1); // Look here

				opener.open(getStatus(), classname);
				saver.save(getStatus(), eingabe);
			}
			return;
		}
		// setStatus(Status.Red);
		return;
	}

	public void Green(Befehl befehl, boolean CompilerWorks,
			boolean TestFehlschlag, Status status, String classname, String eingabe) throws IOException {
		if (getBabyBoolean() == true && befehl != Befehl.DoRefactoring) {
			StartTimer(getStatus(), classname, eingabe);
			return;
		}
		if (befehl == Befehl.DoRed) {
			setStatus(Status.Red);

			tracker.statusChanged(getStatus(), RunTime, -1); // Look here

			opener.open(getStatus(), classname);
			saver.save(getStatus(), eingabe);
			aufgaben++;
			return;
		}
		if (befehl == Befehl.DoGreen) {
			return;
		}
		if (befehl == Befehl.DoRefactoring && CompilerWorks == true && TestFehlschlag == false) {
			setStatus(Status.Refactoring);

			tracker.statusChanged(getStatus(), RunTime, -1); // Look here

			opener.open(getStatus(), classname);
			saver.save(getStatus(), eingabe);
			return;
		}

		if (befehl == Befehl.DoRefactoring && getBabyBoolean() == true
				&& (CompilerWorks == true || TestFehlschlag == true)) {
			deleter.delete(Status.BabyRed, classname);
			setStatus(Status.Red);
			return;
		}

		return;
	}

	public void Refactoring(Befehl befehl, boolean CompilerWorks,
			boolean TestFehlschlag, String classname, String eingabe) throws IOException {
		if (befehl == Befehl.DoRed && CompilerWorks == true && TestFehlschlag == false) {

			setStatus(Status.Red);

			tracker.statusChanged(getStatus(), RunTime, -1); // Look here

			opener.open(getStatus(), classname);
			saver.save(getStatus(), eingabe);
			aufgaben++;
			return;
		}

		return;
	}

	public void setStatus(Status status) {
		this.Zustand = status;
	}

	public Status getStatus() {
		return Zustand;
	}

	public void CounterActive(boolean status) {
		this.CounterActive = CounterActive;
	}

	public boolean CounterActive() {
		return CounterActive;
	}

	public JavaStringCompiler CompilerRun(String className, String classContent, boolean isTest) {
		CompilationUnit unit = new CompilationUnit(className, classContent, isTest);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		compiler.compileAndRunTests();
		return compiler;
	}

	public boolean TestFehlschlag(String className, String classContent) {
		JavaStringCompiler compiler = CompilerRun(className, classContent, true);
		int result = compiler.getTestResult().getNumberOfFailedTests();
		if (result != 0) {
			return true;
		}
		return false;
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

	public void StartTimer(Status status, String classname, String eingabe) throws IOException {

		int Minuten = getMinute();
		long Vergleich = ConvertSeconds(Minuten);
		CounterActive(true);
		Stoppuhrstarte(status, Vergleich, classname, eingabe);
	}

	public long ConvertSeconds(int Minuten) {
		long Vergleich = Minuten * 60;
		return Vergleich;
	}

	void Stoppuhrstarte(Status status, Long Vergleich, String classname, String eingabe) throws IOException {

		seconds = 0;

		try {
			while (seconds <= Vergleich) {
				Thread.sleep(1000);
				seconds++;
				counter.setText(String.valueOf(Vergleich - seconds));
			}
			CounterActive(false);

			if (getStatus() == Status.Green
					&& (CompileErrors(classname, eingabe) == true || TestFehlschlag("Name", "classContent") == true)) {

				tracker.statusChanged(getStatus(), RunTime, -1); // Look here

				deleter.delete(Status.BabyRed, classname);
				setStatus(Status.Red);
				return;
			}

			if (getStatus() == Status.Green
					&& (CompileErrors(classname, eingabe) == false
							&& TestFehlschlag("Name", "classContent") == false)) {

				tracker.statusChanged(getStatus(), RunTime, -1); // Look here

				setStatus(Status.Red);
				return;
			}

			if (getStatus() == Status.Red
					&& (CompileErrors(classname, eingabe) == false
							&& TestFehlschlag("Name", "classContent") == false)) {

				tracker.statusChanged(getStatus(), RunTime, -1); // Look here

				deleter.delete(Status.BabyGreen, classname);
				setStatus(Status.Green);
				return;
			}

			if (getStatus() == Status.Red
					&& (CompileErrors(classname, eingabe) == true || TestFehlschlag("Name", "classContent") == true)) {

				tracker.statusChanged(getStatus(), RunTime, -1); // Look here

				setStatus(Status.Green);
				return;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
