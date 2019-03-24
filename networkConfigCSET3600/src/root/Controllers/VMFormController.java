package root.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VMFormController {
    @FXML
    Button btnFinish, btnCancel;

    @FXML
    TextField tfName, tfVer, tfSrc;

    @FXML
    ChoiceBox<String> cbOs, cbSrc;

    @FXML
    VBox infRow;

    int index = 1;


    @FXML
    private void closeForm(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void addInfRow(){
        //TODO
    }

    @FXML
    private void submitForm(){//VALIDATES ALL VALUES IN THE FORM
        //grab all the values from the input fields
        String vmName = tfName.getText();
        String vmOs = cbOs.getValue();
        String vmVer = tfVer.getText();
        String vmSrc = cbSrc.getValue();



        }
}



