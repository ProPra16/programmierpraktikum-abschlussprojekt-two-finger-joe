package de.hhu.propra16.project7.controller;

import de.hhu.propra16.project7.catalogue.*;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
	 
public class Project_Controller
{
	@FXML
	private Button testButton, codeButton, refractoringButton, backButton;

	@FXML
	private TextArea codewindow;

	@FXML
	private Circle statusLight;

	@FXML
	private Text statusAnweisung, counter, aufgabenName;

	private Status currStatus;
	private Project project;
	private Logic projectLogic;
	private CodeTemplate ct;
	
	public Project_Controller(Status currStatus,Project project)
	{
		this.currStatus = currStatus;
		this.project = project;
		projectLogic = new Logic(project.getTitle(),counter);
		ct = project.getTestTemplates().get(0);
	}

	public void initialize()
	{
		counter.setText("0");
		fillWithTemplate();
		statusLight.setFill(Color.RED);
		statusAnweisung.setText("Write Tests");
	}
	
	@FXML
	private void handleTestButtonAction(ActionEvent event) throws IOException 
	{
		projectLogic.Input(Befehl.DoRed, ct.getFileName(), currStatus.toString());
		Status oldStatus = currStatus;
		currStatus = projectLogic.getStatus();
		if( ( currStatus == Status.Red || currStatus == Status.BabyRed ) && oldStatus != currStatus )
		{
			ct = project.getTestTemplates().get(getAufgabe());
			fillWithTemplate();
			statusLight.setFill(Color.RED);
			statusAnweisung.setText("Write Test");
		}
	}
	
	@FXML
	private void handleCodeButtonAction(ActionEvent event) throws IOException 
	{
		projectLogic.Input(Befehl.DoGreen, ct.getFileName(), currStatus.toString());
		Status oldStatus = currStatus;
		currStatus = projectLogic.getStatus();
		if( ( currStatus == Status.Green || currStatus == Status.BabyGreen ) && oldStatus != currStatus )
		{
			ct = project.getImplementationTemplates().get(getAufgabe());
			fillWithTemplate();
			statusLight.setFill(Color.GREEN);
			statusAnweisung.setText("Write Code");
		}
	}
	
	@FXML
	private void handleRefractoringButtonAction(ActionEvent event) throws IOException
	{
		projectLogic.Input(Befehl.DoRefactoring, ct.getFileName(), currStatus.toString());
		currStatus = projectLogic.getStatus();
		if( currStatus == Status.Refactoring )
		{
			statusLight.setFill(Color.BLACK);
			statusAnweisung.setText("Refactoring");
		}
	}

	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException 
	{
		// Wechselt in das Main-Fenster, wenn der Start Button geklickt wird
		Stage stage = (Stage) backButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
		Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("TDDT");
		stage.show();
	}

	private void fillWithTemplate()
	{
		aufgabenName.setText(ct.getFileName());
		codewindow.setText(ct.getContent());
	}
}
