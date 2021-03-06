package de.hhu.propra16.project7.controller;

import de.hhu.propra16.project7.catalogue.*;
import de.hhu.propra16.project7.controller.Project_Controller;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class Main_Controller {
	@FXML private Button startButton, aktButton;
	@FXML private ListView<String> projects;
	@FXML private TextArea goals;
	@FXML private Slider minuten;
	@FXML private ToggleGroup babyStepsToggleGrp,trackingToggleGrp;
	@FXML private Toggle babyStepsYes,babyStepsNo,trackingYes,trackingNo;

	private ObservableList<String> listViewData = FXCollections.observableArrayList();
	private Project aktProject;
	private Catalogue catalogue = new Catalogue();
	private File file = new File("Catalogue.cfg");
	private boolean _baby;
	private double _time;
	private Project_Controller pc;

	public Main_Controller(){

	}

	public void initialize(){

		try {
			catalogue = CatalogueReader.readFromFile(file);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}

		fillObservableList();

		// Update aktProject and goals if another Project is clicked
		projects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable,	String oldValue, String newValue) {
				aktProject = findProject(newValue);
				goals.setText(aktProject.getInstructions());
			}
		});
	}

	@FXML
	private void handleAktButtonAction(ActionEvent event) throws IOException, ParseException {
		listViewData.clear();

		try {
			catalogue = CatalogueReader.readFromFile(file);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		fillObservableList();
	}

	@FXML
	private void handleStartButtonAction(ActionEvent event) throws IOException {

		if(babyStepsToggleGrp.getSelectedToggle() == babyStepsYes) _baby = true;
		else _baby = false;

		_time = minuten.getValue();

		if(aktProject == null ) return;

		// Wechselt in das nächste Fenster, wenn der Start Button geklickt wird
		Stage stage = (Stage) startButton.getScene().getWindow();
		FXMLLoader fxmlloader = new FXMLLoader();
		Parent root = fxmlloader.load(getClass().getResource("/project_window.fxml").openStream());
		pc = (Project_Controller) fxmlloader.getController();
    pc.initialData(aktProject, _baby, _time, stage);
    Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("TDDT");
		stage.show();
	}

	private Project findProject(String title) {
		Project project;
		for( int i = 0 ; i < catalogue.getProjects().size() ; i++ )	{
			project = catalogue.getProjects().get(i);
			if( project.getTitle().equals(title) ) return project;
		}
		return null;
	}

	private void fillObservableList()	{
		for( int i = 0 ; i < catalogue.getProjects().size() ; i++ )	{
			listViewData.add(catalogue.getProjects().get(i).getTitle());
		}
		projects.setItems(listViewData);
	}
}
