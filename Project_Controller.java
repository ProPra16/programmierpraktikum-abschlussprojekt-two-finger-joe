package de.hhu.propra16.project7.controller;

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
	private Text statusAnweisung;

	private Status currStatus = Status.Red;
	private Logic projectLogic = new Logic();
	
	public Project_Controller(){
	}

	public void initialize()
	{
		statusLight.setFill(Color.RED);
		statusAnweisung.setText("Test schreiben");
	}
	
	@FXML
	private void handleTestButtonAction(ActionEvent event) 
	{
		projectLogic.Input(Befehl.DoRed);
		currStatus = projectLogic.getStatus();
		statusLight.setFill(Color.RED);
		statusAnweisung.setText("Test schreiben");
	}
	
	@FXML
	private void handleCodeButtonAction(ActionEvent event) 
	{
		projectLogic.Input(Befehl.DoGreen);
		currStatus = projectLogic.getStatus();
		statusLight.setFill(Color.GREEN);
		statusAnweisung.setText("Coden");
	}
	
	@FXML
	private void handleRefractoringButtonAction(ActionEvent event)
	{
		projectLogic.Input(Befehl.DoRefactoring);
		currStatus = projectLogic.getStatus();
		statusLight.setFill(Color.BLACK);
		statusAnweisung.setText("Refactoring");
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
}
