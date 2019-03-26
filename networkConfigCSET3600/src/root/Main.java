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
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;



import javafx.stage.FileChooser;



public class Main extends Application { //holds all windows, components, and events pertaining to front-end


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/root/application.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/root/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("V-Net: Network Mapper");
            primaryStage.show();
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}