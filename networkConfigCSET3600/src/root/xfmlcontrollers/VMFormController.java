package root.xfmlcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import root.Validation;
import root.networkobjects.NodeController;
import root.networkobjects.VM;
import root.GraphicsController;
import java.net.URL;
import java.util.*;













public class VMFormController implements Initializable {


    @FXML
    Button btnFinish, btnCancel;

    @FXML
    TextField tfName;

    @FXML
    ChoiceBox<String> cbOs;

    @FXML
    VBox lblandIpRow;
    int intrfcIndex = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //list of the possible operating system users are allow to add
        ObservableList<String> osList = FXCollections.observableArrayList("LINUX", "WINDOWS");
        cbOs.setItems(osList);


    }


    @FXML
    private void closeForm(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void addLblandIProw(){//add interface
        String eth = "eth"; //set up the label
        String infName = eth + Integer.toString(intrfcIndex);
        intrfcIndex++;
        HBox formRow = new HBox();
        formRow.getStyleClass().add("vmform-infRow");
        Label rowLabel = new Label(infName);
        rowLabel.getStyleClass().add("vmform-label");
        TextField rowTF = new TextField();
        formRow.getChildren().addAll(rowLabel, rowTF);
        lblandIpRow.getChildren().add(formRow);
        lblandIpRow.getScene().getWindow().sizeToScene(); //enlarge window
    }

    @FXML
    private void submitForm(){//Validate input of the form and generate an object, otherwise indicate the error to the user

        String vmName = tfName.getText();
        String vmOs = cbOs.getValue();
        List<String> ipAddresses = this.getIntrfcIPs(lblandIpRow);


        boolean checkName = Validation.checkName(vmName);
        boolean checkOs = Validation.checkOs(vmOs);
        boolean checkIntrfcAddresses = true;
        for (String ip:ipAddresses){//checks each ip if it is in a valid range, and that it doesn't already exist
            checkIntrfcAddresses = (Validation.isIPv4(ip) && Validation.ipDontExist(ip));
        }




        if(checkName && checkOs && checkIntrfcAddresses) {//insert new VM  if all tests pass
            NodeController.getNodeController().addHostVM(new VM(vmName, vmOs, ipAddresses));
            int indexoflast = NodeController.getNodeController().getCurrentVms().size();
            //ApplicationController.drawCurrentImgs();//draws img on canvas
            Stage stage = (Stage) btnFinish.getScene().getWindow();
            stage.close();
        } else { //highlight invalid field and indicate where errors exist to user

            if(!checkName) {
                tfName.getStyleClass().add("vmform-invalid-field");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Name already exists.");
                alert.showAndWait();
            }

            if(!checkOs) {
                cbOs.getStyleClass().add("vmform-invalid-field");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("OS choice required");
                alert.setContentText("Please select an OS");
                alert.showAndWait();
            }
            if(!checkIntrfcAddresses){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid IP(s)");
                alert.setContentText("One or more IP addresses are not in correct format, range,(e.g.'192.168.1.0'), or are taken by other nodes.");
                alert.showAndWait();
                for(Node node : lblandIpRow.getChildren()) {
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




    }






    private List<String> getIntrfcIPs(VBox infRow){ //adds the address of each interface added by the user to a list
        List<String> ipAddresses = new ArrayList<String>();

        for(Node node : infRow.getChildren()){
            String ipAddress = null;
            if(node instanceof HBox){
                for(Node innerNode : ((HBox) node).getChildren()){
                    if(innerNode instanceof TextField){
                        ipAddress = ((TextField) innerNode).getText().trim();
                        ipAddresses.add(ipAddress);

                    }

                }
            }
        }
        return ipAddresses;

    }



}



