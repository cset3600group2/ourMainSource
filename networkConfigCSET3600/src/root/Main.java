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
    private Label label = new Label();
    private Desktop desktop = Desktop.getDesktop();
    public static NodeController nodeController = new NodeController();//Holds all current hosts and vms


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/root/Gui/application/application.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/root/Gui/application/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("V-Net: Network Mapper");
            primaryStage.show();
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void openConfigFile(File file) {//generates objects from config file
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine;
            String vm = "vm";
            String rightBrace = "}";
            String os = "os";
            String eth = "eth";
            java.util.List<String> supplierNames1 = new ArrayList<String>();

            while ((currentLine = br.readLine()) != null)//reads line by line
                if(currentLine.contains(vm)){
                    int lengthofVm = vm.length();
                    int firstIndex = currentLine.indexOf(vm);
                    int spaceLength = 1;
                    int indexOfName = spaceLength + firstIndex + lengthofVm;
                    System.out.println(currentLine.substring(indexOfName));
                }


        }
        catch (IOException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}