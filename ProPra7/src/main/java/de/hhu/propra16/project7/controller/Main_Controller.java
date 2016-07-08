package de.hhu.propra16.project7.controller;

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
	private ListView<String> projects;

	@FXML
	private TextArea goals;

	// private ObservableList<KLASSE VON MARVIN> listViewData = FXCollections.observableArrayList();
	private ObservableList<String> listViewData = FXCollections.observableArrayList();

	// private Project aktProject;
	private String aktProject;

	public Main_Controller(){
	}

	public void initialize()
	{
		// Wenn Main Fenster aufgerufen wird, fuellt die Methode die "projects" ListView
		// fuelleProjects();
		
		listViewData.add("Heinz");
		listViewData.add("Horst");

		projects.setItems(listViewData);

		// Registriert, wenn anderes Projekt angeklickt wird
		// projects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KLASSE VON MARVIN>()
		projects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
			
			public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue)
			{
				goals.setText(newValue);
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