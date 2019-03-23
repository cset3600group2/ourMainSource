package root.GuiController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import root.Gui.application.HUB;
import root.Gui.application.PatternConfig;
import root.Gui.application.valueChecker;
import root.Validation;

import java.net.URL;
import java.util.ResourceBundle;

public class HUBFormController implements Initializable {
	private Validation validation = new Validation();

	@FXML
	VBox formPane;

	@FXML
	TextField tfName, tfSubnet, tfNetmask;

	@FXML
	Button btnFinish, btnCancel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	private void closeForm(){
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void submitForm(){
		//grab all input values

		String hubName = tfName.getText();
		String hubSubnet = tfSubnet.getText();
		String hubNetmask = tfNetmask.getText();

		//check test the values
		boolean checkName = valueChecker.checkName(hubName);
		boolean checkSubnet = valueChecker.checkIp(hubSubnet);
		boolean checkNetmask = valueChecker.checkNetmask(hubNetmask);

		if(checkName && checkSubnet && checkNetmask) {
			HUB hubObject = new HUB();
			hubObject.setName(hubName);
			hubObject.setNetmask(hubNetmask);
			hubObject.setSubnet(hubSubnet);

			//add it to all the other hubObjects in our hashmap
			PatternConfig.hubMap.put(hubName, hubObject);
			Stage stage = (Stage) btnFinish.getScene().getWindow();
			stage.close();
		}else {
			//create a popup to warn user
			//also highlight the input fields that are invalid
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Input");
			alert.setHeaderText("Error Input");
			alert.setContentText("Please check over your input parameters and resubmit.");
			alert.showAndWait();


			if(!checkName) {
				tfName.getStyleClass().add("hubform-invalid-field");
			}

			if(!checkSubnet) {
				tfSubnet.getStyleClass().add("hubform-invalid-field");
			}

			if(!checkNetmask) {
				tfNetmask.getStyleClass().add("hubform-invalid-field");
			}
		}
	}
}
