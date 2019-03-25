package root.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		boolean checkName = Validation.checkName(hubName);
		boolean checkSubnet = Validation.isNetworkAddress(hubSubnet, hubNetmask);


		}
	}

