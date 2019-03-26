package root.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import root.Main;
import root.Validation;
import root.networkobjects.HubNode;

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
		ObservableList<String> vlanList = FXCollections.observableArrayList("v2");

	}

	@FXML
	private void closeForm(){
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void submitForm(){

		//Receieve input from user
		String hubName = tfName.getText();
		String hubSubnet = tfSubnet.getText();
		String hubNetmask = tfNetmask.getText();


		//Check integrity of all the values
		boolean checkName = Validation.checkName(hubName);
		boolean checkSubnetandNetmask = Validation.isValidNetwork(hubSubnet, hubNetmask);

		if(checkName && checkSubnetandNetmask) {//validation accepted
			Main.nodeController.addHub(new HubNode(hubName, hubSubnet, hubNetmask));//create a hub
			Stage stage = (Stage) btnFinish.getScene().getWindow();
			stage.close();
		}else {
			//create a popup to warn user
			//also highlight the input fields that are invalid



			if(!checkName) {
				tfName.getStyleClass().add("hubform-invalid-field");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Input Error");
				alert.setHeaderText("Input Error");
				alert.setContentText("Name already exists.");
				alert.showAndWait();
			}

			if(!checkSubnetandNetmask) {
				tfSubnet.getStyleClass().add("hubform-invalid-field");
				tfNetmask.getStyleClass().add("hubform-invalid-field");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Input Error");
				alert.setHeaderText("Input Error");
				alert.setContentText("Ip address must be a network address with a compatible netmask. Fields must also be ipv4 addresses.");
				alert.showAndWait();
			}

		}



		}
	}

