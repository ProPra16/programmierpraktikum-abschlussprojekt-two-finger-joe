// Anregung oder Grundgerüst für Thais, als Main-Klasse des Programms

package de.hhu.propra16.project7.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
	 
public class Main extends Application
{
	public static void main(String[] args)
	{
		new Logic();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));

		Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("TDDT");
		stage.show();
	}
	
}