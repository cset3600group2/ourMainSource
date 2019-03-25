package root.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
}
