package root;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;



import javafx.stage.FileChooser;



public class Main extends Application { //holds all windows, components, and events pertaining to front-end



    private Label label = new Label();
    private Desktop desktop = Desktop.getDesktop();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        /* TO BE ADDED(AFTER XML FILE GENERATED FROM SCENE BUILDER)---------------------------------------------
        Parent root = FXMLLoader.load(getClass().getResource("/application/application.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("V-Net: Network Mapper");
        stage.show();
        */


        final FileChooser fileChooser = new FileChooser();//Allows opening and saving files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CFG files (*.cfg)", "*.cfg"); //sets extension filter
        fileChooser.getExtensionFilters().add(extFilter);




        //<TO BE REMOVED ANCHOR START>-----------------------------------------------------------------------
        stage.setTitle("Network Configurator");
        stage.setWidth(500);
        stage.setHeight(800);
        final String Scarry_Terry_Song =
                        "A, B\n"
                        + "His name is Scary Terry\n"
                        + "C, D\n"
                        + "He's very scary\n"
                        + "E, F\n"
                        + "J, K, he'll really ruin your day\n"
                        + "Q, R, you won't get very far;\n";

        label.setFont(Font.font("Times New Roman", 22));
        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);
        HBox hbox1 = new HBox();


        Button saveAs = new Button("SaveAs");
        saveAs.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

        Button open = new Button("Open");
        open.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

        hbox1.getChildren().add(open);
        hbox1.getChildren().add(label);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.BOTTOM_CENTER);


        vbox.getChildren().add(saveAs);
        vbox.getChildren().add(hbox1);
        vbox.setSpacing(10);
        ((Group)scene.getRoot()).getChildren().add(vbox);

        stage.setScene(scene);
        stage.show();

        //<TO BE REMOVED ANCHOR END>-----------------------------------------------------------------------




        saveAs.setOnAction(new EventHandler<ActionEvent>() { //TO BE EDITED BASED ON CALLING GUI ELEMENT TYPE(BUTTON NOW)
            @Override public void handle(ActionEvent e) {





                //Show save file dialog
                File file = fileChooser.showSaveDialog(stage);

                if(file != null){
                    saveConfigFile(Scarry_Terry_Song, file);
                }
                label.setText("Saved");
            }
        });



        open.setOnAction(new EventHandler<ActionEvent>() { //TO BE EDITED BASED ON CALLING GUI ELEMENT TYPE(BUTTON NOW)
            @Override public void handle(ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    openConfigFile(file);
                }
                label.setText("Loaded");
            }
        });


    }



    private void saveConfigFile(String content, File file){
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void openConfigFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}