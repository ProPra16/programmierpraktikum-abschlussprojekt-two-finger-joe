package de.hhu.propra16.project7.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
	 
public class Project_Controller
{
	@FXML
	private Button resetButton, weiterButton, backButton;

	@FXML
	private TextArea codewindow;

	@FXML
	private Circle statusLight;

	@FXML
	private Text statusAnweisung;

	private Color[] status = {Color.RED,Color.GREEN,Color.BLACK};
	private String[] buttonText = {"Coden","Prüfen","Testen"};
	private String[] statusAnw = {"Einen fehlschlagenden Test schreiben","Code schreiben","Code optimieren"};

	private int currStatus = 0;

	public Project_Controller()
	{}

	public void initialize()
	{
		statusLight.setFill(status[currStatus]);
		weiterButton.setText(buttonText[currStatus]);
		statusAnweisung.setText(statusAnw[currStatus]);
	}

	@FXML
	private void handleResetButtonAction(ActionEvent event) throws IOException
	{
		// Ersetze den momentanen Code mit dem Inhalt von Code.java
	}

	@FXML
	private void handleWeiterButtonAction(ActionEvent event) throws IOException
	{
		// if( status == 0 ) Speichere Code in Test.java -> Öffne Code.java
		// if( status == 1 ) Teste und speichere den Code in Code.java, wenn er die Tests erfüllt, sonst gib Fehler aus
		// if( status == 2 ) Teste und speichere den Code in Code.java, wenn er die Tests erfüllt, sonst gib Fehler aus -> Öffne Test.java
		currStatus++;
		if( currStatus > 2 ) currStatus = 0;
		statusLight.setFill(status[currStatus]);
		weiterButton.setText(buttonText[currStatus]);
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
