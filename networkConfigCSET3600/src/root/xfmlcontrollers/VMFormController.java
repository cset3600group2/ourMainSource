package root.xfmlcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.TreeMap;

public class VMFormController {
    @FXML
    Button btnFinish, btnCancel;

    @FXML
    TextField tfName, tfVer;

    @FXML
    ChoiceBox<String> cbOs, cbSrc;

    @FXML
    VBox infRow;
    int intrfcIndex = 1;


    @FXML
    private void closeForm(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void addInfRow(){
        String eth = "eth";
        String infName = eth + Integer.toString(intrfcIndex);
        //TODO Graphics.interfaceAdd(infName, infRow);
        intrfcIndex++;      //add interface
        infRow.getScene().getWindow().sizeToScene(); //enlarge window
    }

    @FXML
    private void submitForm(){//VALIDATES ALL VALUES IN THE FORM
        //grab all the values from the input fields
        String vmName = tfName.getText();
        String vmOs = cbOs.getValue();
        /*TreeMap<String, String> vmInf = getInterfaces(infRow);

        //only insert new VM object if all test passes
        if(checkName && checkOs && checkVer && checkSrc && checkInterfaces) {
            VM vmObject = new VM();
            vmObject.setName(vmName);
            vmObject.setOs(vmOs);
            vmObject.setSrc(vmSrc);
            vmObject.setVer(Double.parseDouble(vmVer));
            vmObject.setInterfaces(vmInf);

            PatternConfig.vmMap.put(vmName, vmObject);
            Stage stage = (Stage) btnFinish.getScene().getWindow();
            stage.close();
        }else {
            //create a popup to warn user
            //also highlight the input fields that are invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Input");
            alert.setHeaderText("Error Input");
            alert.setContentText("Please check over your input parameters and resubmit.");
            alert.showAndWait();

            if(!checkName) {
                tfName.getStyleClass().add("vmform-invalid-field");
            }

            if(!checkOs) {
                cbOs.getStyleClass().add("vmform-invalid-field");
            }

            if(!checkVer) {
                tfVer.getStyleClass().add("vmform-invalid-field");
            }

            if(!checkSrc) {
                cbSrc.getStyleClass().add("vmform-invalid-field");
            }

            if(!checkInterfaces) {
                for(Node node : infRow.getChildren()) {
                    if(node instanceof HBox) {
                        for(Node innerNode : ((HBox) node).getChildren()) {
                            if(innerNode instanceof TextField) {
                                innerNode.getStyleClass().add("vmform-invalid-field");
                            }
                        }
                    }
                }
            }
        }
        */

    }




    //this method looks into the infRow (which contains more values if they continue to add more rows)
    //and create a Treemap from the ones that don't have empty textfields
    /*TODO
    private TreeMap<String, String> getInterfaces(VBox formInfRow){

         TreeMap<String, String> lblandIp = new TreeMap<String, String>();
        for(Node node : formInfRow.getChildren()){
            String key = null;
            String value = null;
            if(node instanceof HBox){
                for(Node innerNode : ((HBox) node).getChildren()){
                    if(innerNode instanceof Label){
                        key = ((Label) innerNode).getText();
                    }
                    if(innerNode instanceof TextField){
                        value = ((TextField) innerNode).getText().trim();
                    }
                    if(key != null && value != null){
                        lblandIp.put(key, value);
                    }
                }
            }
        }
        return infTree;

    }
     */

}



