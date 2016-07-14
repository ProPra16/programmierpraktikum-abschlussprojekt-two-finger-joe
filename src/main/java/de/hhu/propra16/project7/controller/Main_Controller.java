package de.hhu.propra16.project7.controller;

import de.hhu.propra16.project7.catalogue.*;

import java.io.File;
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

	@FXML
	private Slider minuten;

	@FXML
	private ToggleGroup babyStepsToggleGrp,trackingToggleGrp;

	@FXML
	private Toggle babyStepsYes,babyStepsNo,trackingYes,trackingNo;

	private ObservableList<String> listViewData = FXCollections.observableArrayList();
	
	private Project aktProject;

	private Catalogue catalogue = new Catalogue();

	private File file = new File("Catalogue.cfg");
	
	public Main_Controller(){
		
	}

	public void initialize()
	{

		try {
			catalogue = CatalogueReader.readFromFile(file);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}		
		fillObservableList();

		// Update aktProject and goals if another Project is clicked
		projects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
			
			public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue)
			{
				aktProject = findProject(newValue);
				goals.setText(aktProject.getInstructions());
			}
		});
	}

	@FXML
	private void handleAktButtonAction(ActionEvent event) throws IOException, ParseException
	{
		listViewData.clear();
		try {
			catalogue = CatalogueReader.readFromFile(file);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		fillObservableList();
	}

	@FXML
	private void handleStartButtonAction(ActionEvent event) throws IOException
	{
		if( aktProject == null ) return;
		// Wechselt in das nächste Fenster, wenn der Start Button geklickt wird
		Stage stage = (Stage) startButton.getScene().getWindow();
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/project_window.fxml"));
		if( babyStepsToggleGrp.getSelectedToggle() == babyStepsYes )
			fxmlloader.setController(new Project_Controller(Status.Red,aktProject,true,minuten.getValue()));
		else
			fxmlloader.setController(new Project_Controller(Status.Red,aktProject,false,minuten.getValue()));
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle(aktProject.getTitle());
		stage.show();
	}

	private Project findProject(String title)
	{
		Project project;
		for( int i = 0 ; i < catalogue.getProjects().size() ; i++ )
		{
			project = catalogue.getProjects().get(i);
			if( project.getTitle().equals(title) ) return project;
		}
		return null;
	}

	private void fillObservableList()
	{
		for( int i = 0 ; i < catalogue.getProjects().size() ; i++ )
		{
			listViewData.add(catalogue.getProjects().get(i).getTitle());
		}
		projects.setItems(listViewData);
	}
}
