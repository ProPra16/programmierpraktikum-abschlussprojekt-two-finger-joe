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

	private Color[] status = {Color.RED,Color.GREEN,Color.BLACK};
	private String[] statusAnw = {"Einen fehlschlagenden Test schreiben","Code schreiben","Code optimieren"};

	private int currStatus = 0;
	private Logic projectLogic;
	
	
	public Project_Controller(){
		
	}

	public void initialize()
	{
		statusLight.setFill(status[currStatus]);
		statusAnweisung.setText(statusAnw[currStatus]);
	}

	@FXML
	private void handleWeiterButtonAction(ActionEvent event) throws IOException
	{
	}
	
	@FXML
	private void handleTestButtonAction(ActionEvent event) 
	{
	Input(Befehl.DoRed, classname, eingabe);
		currStatus = projectLogic.getStatus().ordinal();
		statusLight.setFill(status[currStatus]);
		statusAnweisung.setText(statusAnw[currStatus]);
	}
	
	@FXML
	private void handleCodeButtonAction(ActionEvent event) 
	{
		projectLogic.Input(Befehl.DoGreen, classname, eingabe);
		currStatus = projectLogic.getStatus().ordinal();
		statusLight.setFill(status[currStatus]);
		statusAnweisung.setText(statusAnw[currStatus]);
	}
	
	@FXML
	private void handleRefractoringButtonAction(ActionEvent event)
	{
		projectLogic.Input(Befehl.DoRefactoring, classname, eingabe);
		currStatus = projectLogic.getStatus().ordinal();
		statusLight.setFill(status[currStatus]);
		statusAnweisung.setText(statusAnw[currStatus]);
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