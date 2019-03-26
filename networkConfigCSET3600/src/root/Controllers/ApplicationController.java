package root.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	private void insertHub(){
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/root/insertnewhubform.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/root/application.css").toExternalForm());
			Stage formWindow = new Stage();
			formWindow.setScene(scene);
			formWindow.setTitle("Insert New Hub");
			formWindow.setResizable(false);
			formWindow.setFullScreen(false);


			formWindow.initModality(Modality.WINDOW_MODAL); //forces entry to be done on the form before closing
			formWindow.initOwner(scrollPane.getScene().getWindow());
			formWindow.showAndWait();


			/*TODO
			GraphicsController.draw(canvas, contextMenu);
			GraphicsController.draw(canvas, contextMenu);
			*/
			//TODO
			//FileWriter.writeFile(textEditor);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@FXML
	private void insertVM(){
		//TODO

	}
}