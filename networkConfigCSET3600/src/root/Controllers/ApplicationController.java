package root.Controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import root.ConfigFile;
import root.Main;

import javafx.event.ActionEvent;
import root.NodeController;
import root.networkobjects.VM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private void fileNew() {
		//TODO
	}
	
	@FXML
	private void openConfigFile() {//Opens a file for the user to generate a fresh NodeController
		FileChooser fileChooser = new FileChooser();//Allows opening and saving files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CFG files (*.cfg)", "*.cfg"); //sets extension filter
		fileChooser.getExtensionFilters().add(extFilter);
		Scene scene = mainWindow.getScene();//grabs the scene from the window that initialized this event,  required for file selector
		if (scene != null) {
			Window window = scene.getWindow();
			if (window != null) {
				File file = fileChooser.showOpenDialog(window);
				if (file != null) {
					String filePath = file.getAbsolutePath();
					NodeController.getNodeController().clear();//wipe all current nodes for new nodes to be generated
					ConfigFile.readFile(filePath);//generates the controller with the config file


					//TODO TO BE REMOVED, ONLY A TEST
					for (VM vm : NodeController.getNodeController().getCurrentVms()){//test
						System.out.println(vm.getName());

					}
					//TODO TO BE REMOVED END ANCHOR
			}
		}

		}

	}

	@FXML
	private void saveConfigFile() {
	//TODO
	}
	@FXML
	private void exitProgram() {
		Platform.exit();
	}


	/*@FXML
	private void fileSave() {
	//TODO
	}
	*/
	
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





	public static void saveConfigFile(String content, File file){
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

	}


}