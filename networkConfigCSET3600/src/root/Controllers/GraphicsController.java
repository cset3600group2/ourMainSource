package root.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Map;

public class GraphicsController {//

    public static void interfaceAdd(String labelText, VBox content) {
        //add rows in the VMForm
        HBox formRow = new HBox();
        formRow.getStyleClass().add("vmform-infRow");
        Label rowLabel = new Label(labelText);
        rowLabel.getStyleClass().add("vmform-label");
        TextField rowTF = new TextField();
        formRow.getChildren().addAll(rowLabel, rowTF);
        content.getChildren().add(formRow);
    }

    private static void creatAlert(String input, String type) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(input + " Input Error");
        alert.setHeaderText("Interface Error");
        alert.setContentText("Please check over your '" + input + "' input parameter. The " + type + " " + input
                + " will not be saved.");
        alert.show();
    }

  /* public static void draw(Pane canvas, ContextMenu contextMenu) { //TODO @KYLE based on current NodeController, called after a context menu creates/deletes an object
        canvas.getChildren().clear();
        canvas.getChildren().add(createVlanNode("V2", canvas, contextMenu));

        int tempPosX = PatternConfig.hubStartPosX;
        int tempPosY = PatternConfig.hubStartPosY;

        for (Map.Entry<String, HUB> hubEntry : application.PatternConfig.hubMap.entrySet()) {

            String currentHubName = hubEntry.getKey();
            HUB currentHub = application.PatternConfig.hubMap.get(currentHubName);
            currentHub.setPosX(tempPosX);
            currentHub.setPosY(tempPosY);
            canvas.getChildren().add(Graphics.hubInsertNode(currentHub, canvas, contextMenu));

            boolean haveConnections = !currentHub.getInfs().isEmpty();

            if (haveConnections) {
                createLine(canvas, tempPosX + 100, tempPosY + 50, tempPosX + 150, tempPosY + 50);
            }


            tempPosX += 200;
            for (Map.Entry<String, VM> vmEntry : application.PatternConfig.vmMap.entrySet()) {
                String currentVMName = vmEntry.getKey();
                VM currentVM = application.PatternConfig.vmMap.get(currentVMName);
                for (Map.Entry<String, String> vmInterface : currentVM.getInterfaces().entrySet()) {

                    int ipClass = PatternConfig.getIPClass(application.PatternConfig.hubMap.get(currentHubName).getNetmask());
                    String replaceRegex = "\\.\\d{1,"+ String.valueOf(ipClass) +"}\\z";
                    if (vmInterface.getValue().replaceAll(replaceRegex, "").equals(
                            application.PatternConfig.hubMap.get(currentHubName).getSubnet().replaceAll(replaceRegex, ""))) {
                        application.PatternConfig.hubMap.get(currentHubName).addInf(currentVM.getName()+"."+vmInterface.getKey());
                        currentVM.setPosX(tempPosX);
                        currentVM.setPosY(tempPosY);
                        canvas.getChildren().add(Graphics.VMInsert(currentVM, canvas, contextMenu));

                        if (haveConnections) {
                            createLine(canvas, tempPosX, tempPosY + 50, tempPosX - 50, tempPosY + 50);
                        }

                        tempPosY += 150;

                    }
                }

            }
            if (!haveConnections){
                tempPosY +=150;
            }


            if (haveConnections) {
                createLine(canvas, tempPosX - 50, currentHub.getPosY() + 50, tempPosX - 50, tempPosY - 100);
            }

            createLine(canvas, PatternConfig.vlanstartPosX + 100, currentHub.getPosY() + 50, currentHub.getPosX(),
                    currentHub.getPosY() + 50);
            tempPosX += 200;

        }
    }
    */

}
