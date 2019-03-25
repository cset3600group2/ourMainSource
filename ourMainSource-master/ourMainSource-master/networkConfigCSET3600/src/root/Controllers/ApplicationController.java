package root.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;

public class ApplicationController {
	@FXML
	TextArea textEditor;
	@FXML
	TabPane tabPane;
	@FXML
	Pane canvas;
	@FXML
	AnchorPane anchorPane, mainWindow;
	@FXML
	ScrollPane scrollPane;
	@FXML
	ContextMenu contextMenu;
	@FXML
	MenuItem deleteMenu;
	// These are the toggle button and the VBox container for the form
	public static ToggleButton btnEdit = new ToggleButton("Edit");
	public static Button btnAddInf = new Button("Add Infterface");
	public static File currentFile = null;

	@FXML
	private void fileClose() {
		Platform.exit();
	}
	
	@FXML
	private void fileNew() {
		//TODO
	}
	
	@FXML
	private void fileOpen() {
    //TODO
	}

	@FXML
	private void fileSaveAs() {
	//TODO
	}

	@FXML
	private void fileSave() {
	//TODO
	}
	
	@FXML
	private void tabEditor() {
		//TODO
	}
	
	@FXML
	private void tabGraphical() {
		//TODO
	}
	
	@FXML
	private void insertNewHub(){
      //TODO
	}
	
	@FXML
	private void insertNewVm(){
		//TODO

	}
}