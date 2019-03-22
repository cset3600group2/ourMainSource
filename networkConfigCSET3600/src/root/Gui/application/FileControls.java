package root.Gui.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import root.Gui.controller.MyController;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class FileControls {
	public static void newFile(TextArea editor, Pane canvas, ContextMenu contextMenu) {
		if(!editor.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Save File?");
			alert.setHeaderText("Save File?");
			alert.setContentText("Would you like to save file before creating a new one?");
			
			ButtonType yesButton = new ButtonType("Yes");
			ButtonType noButton = new ButtonType("No");
			ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			
			alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
			
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == yesButton) {
				//allow the user to save the file before clearing it
				File selectedFile = FileControls.fileSaveAsConf();
				if (selectedFile != null) {
					FileControls.writeFile(selectedFile, editor.getParagraphs());
				}
				editor.clear();
				PatternConfig.clearData();
				Graphics.draw(canvas, contextMenu);
				MyController.currentFile = null;
			}else if(result.get() == noButton) {
				//user don't want to save before making new file
				editor.clear();
				PatternConfig.clearData();
				MyController.currentFile = null;
			}
			else {
				alert.close();
			}
		}else {
			//if there nothing in the text editor don't ask to save
			editor.clear();
			PatternConfig.clearData();
			Graphics.draw(canvas, contextMenu);
			MyController.currentFile = null;
		}
	}
	
	public static String readFile(File inFile) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {

			bufferedReader = new BufferedReader(new FileReader(inFile));

			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text + System.getProperty("line.separator"));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return stringBuffer.toString();
	}

	public static void writeFile(File outFile, ObservableList<CharSequence> paragraph) {
		Iterator<CharSequence> iter = paragraph.iterator();
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(new File(outFile.toString())));
			while (iter.hasNext()) {
				CharSequence seq = iter.next();
				bf.append(seq);
				bf.newLine();
			}
			bf.flush();
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File fileOpenConf() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		MyController.currentFile = selectedFile;
		@SuppressWarnings("unused")
		FileParser fileParser = new FileParser(selectedFile);
		return selectedFile;
	}

	public static File fileSaveAsConf() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".cfg", "*.cfg"));
		File selectedFile = fileChooser.showSaveDialog(null);
		MyController.currentFile = selectedFile;
		return selectedFile;
	}

}
