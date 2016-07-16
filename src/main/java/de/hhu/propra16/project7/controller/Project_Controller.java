package de.hhu.propra16.project7.controller;

import de.hhu.propra16.project7.catalogue.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class Project_Controller implements Initializable {

	@FXML Button testButton, codeButton, refractoringButton, backButton;
	@FXML TextArea codewindow;
	@FXML Circle statusLight;
	@FXML Text statusAnweisung;
	@FXML Label lbcounter;
	@FXML Text aufgabenName;

	private Stage _stage;
	private Status _currStatus;
	private Project _project;
	private Logic projectLogic;
	private CodeTemplate ct;
	private boolean _baby;
	private Timer _timer;
	private int babyTime;
	private int _secondsElapsed;

	public Project_Controller()	{

	}

	public void initialize(URL url, ResourceBundle rb) {
		_timer = new Timer();
		_timer.schedule(new TimerTask()	{
			public void run()	{
				Platform.runLater(() -> {
					if (_baby) SetTimerLabel();
				});
			}
		}, 1000, 1000);

		statusLight.setFill(Color.RED);
		statusAnweisung.setText("Write Tests");
	}

	@FXML
	private void handleTestButtonAction(ActionEvent event) throws IOException	{
		projectLogic.Input(Befehl.DoRed, ct.getFilename(), codewindow.getText());
		Status oldStatus = _currStatus;
		_currStatus = projectLogic.getStatus();
		if( _currStatus == Status.Red && oldStatus != _currStatus )	{
			ct = (CodeTemplate) _project.getTestTemplates().get(projectLogic.getAufgabe());
			fillWithTemplate();
			statusLight.setFill(Color.RED);
			statusAnweisung.setText("Write Test");
		}
	}

	@FXML
	private void handleCodeButtonAction(ActionEvent event) throws IOException	{
		projectLogic.Input(Befehl.DoGreen, ct.getFilename(), codewindow.getText());
		Status oldStatus = _currStatus;
		_currStatus = projectLogic.getStatus();
		if( _currStatus == Status.Green && oldStatus != _currStatus ) {
			ct = _project.getImplementationTemplates().get(projectLogic.getAufgabe());
			fillWithTemplate();
			statusLight.setFill(Color.GREEN);
			statusAnweisung.setText("Write Code.");
		}
	}

	@FXML
	private void handleRefractoringButtonAction(ActionEvent event) throws IOException	{
		projectLogic.Input(Befehl.DoRefactoring, ct.getFilename(), codewindow.getText());
		_currStatus = projectLogic.getStatus();
		if( _currStatus == Status.Refactoring )	{
			statusLight.setFill(Color.BLACK);
			statusAnweisung.setText("Refactoring");
		}
	}

	// Wechselt in das Main-Fenster, wenn der Start Button geklickt wird

	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException	{

		Stage stage = (Stage) backButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/main_window.fxml"));
		Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("TDDT");
		stage.show();
	}

	public void initialData(Project project, boolean baby, double time, Stage stage){
		setProject(project);
		setBaby(baby);
		setTime(time);
		_stage = stage;
	}

	public void setProject(Project project){
		_project = project;
		projectLogic = new Logic(_project.getTitle(),Status.Red);
		ct = (CodeTemplate) _project.getTestTemplates().get(0);
    	fillWithTemplate();
	}

	public void setBaby(boolean baby){
		_baby = baby;
	}

	public void setTime(double time){
		double babyTimetemp = 10*time;
		babyTime = (int) babyTimetemp;
		_secondsElapsed = babyTime*6;
		lbcounter.setText("Have Fun");
	}

	public void setStatus(Status status){
		_currStatus = Status.Red;
	}

	private void SetTimerLabel(){

		_secondsElapsed--;
		if(_secondsElapsed == 0) {
			projectLogic.BabySteps(ct.getFilename(), codewindow.getText(), babyTime);
			System.out.println("GAME OVER!");
			_timer.cancel();
			_stage.close();
		}

		int minutes = _secondsElapsed / 60;
		int seconds = _secondsElapsed % 60;

		lbcounter.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
	}

	private void fillWithTemplate()	{
		aufgabenName.setText(ct.getFilename());
		codewindow.setText(ct.getContent());
	}
}
