package de.hhu.propra16.project7.controller;

import de.hhu.propra16.project7.catalogue.*;

import java.io.IOException;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.*;
	 
public class Main_Controller
{
	@FXML
	private Button startButton, aktButton;

	@FXML
	private ListView<Project> projects;

	@FXML
	private TextArea goals;

	private ObservableList<Project> listViewData = FXCollections.observableArrayList();
	
	private Project aktProject;

	public Main_Controller(){
	}

	public void initialize()
	{
		Catalogue catalogue = new Catalogue();
		for( int i = 0 ; i < catalogue.length() ; i++ )
		{
			listViewData.add(catalogue.getProjects().get(i));
		}
		projects.setItems(listViewData);

		// Registriert, wenn anderes Projekt angeklickt wird
		projects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>()
		{
			
			public void changed(ObservableValue<? extends Project> observable,
			Project oldValue, Project newValue)
			{
				goals.setText(newValue.getInstructions());
				aktProject = newValue;
			}
		});
	}

	@FXML
	private void handleAktButtonAction(ActionEvent event) throws IOException
	{
		// Leere die ViewList und fuelle sie dann mit den aktuellen Projekten der Datenbank
		// löscheProjects();
		// fuelleProjects();
		// Bekomme Liste von Marvins Klasse
	}

	@FXML
	private void handleStartButtonAction(ActionEvent event) throws IOException
	{
		if( aktProject == null ) return;
		// Wechselt in das nächste Fenster, wenn der Start Button geklickt wird
		Stage stage = (Stage) startButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("project_window.fxml"));
		Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("TDDT");
		stage.show();
	}
}
