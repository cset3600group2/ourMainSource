package root;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.*;
import org.controlsfx.control.PopOver;
import root.networkobjects.VM;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import root.networkobjects.HubNode;
import root.networkobjects.VMinterface;
//import javax.swing.JLabel;


public class GraphicsController{

    private static final int NODE_LENGTH = 100;
    private static final int NODE_WIDTH = 100;

    private static Node hubInsertNode(HubNode hubNode, Pane canvas , ContextMenu contextMenu) {//returns image of hub to be added to main canvas
        Image image = new Image("root/Images/Hub.png");
        ImagePattern imagePattern = new ImagePattern(image);

        Rectangle node = new Rectangle(NODE_LENGTH, NODE_WIDTH);
        node.setFill(imagePattern);

        Label lnodeName = new Label("HUB: " + hubNode.getName()+ "\n" + "IP: " + hubNode.getSubnet() + "\n" + "NetMask: " + hubNode.getNetmask());

        //lnodeName.setOpacity(0.5);
        lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(node, lnodeName);
        nodeContainer.relocate(hubNode.getPosx(), hubNode.getPosy());


        nodeContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {//TODO DELETE SHIT RIGHT
            @Override
            public void handle(MouseEvent event) {
                NodeController nodeController = NodeController.getNodeController();
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.getItems().get(2).setDisable(false);//allows deletion option of the stack pane object 'nodeContainer'
                    contextMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            String hubName = hubNode.getName();
                            nodeController.removeHubNode(hubName);
                        }
                    });

                    contextMenu.setOnHidden(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent e) {
                            contextMenu.getItems().get(2).setDisable(true);
                        }
                    });
                }
            }
        });

        return nodeContainer;
    }


    private static Node vmInsertNode(VM vmNode, Pane canvas , ContextMenu contextMenu) {//TODO DELETE SHIT RIGHT
        Image image = new Image("root/Images/VM.png");
        ImagePattern imagePattern = new ImagePattern(image);

        Rectangle node = new Rectangle(NODE_LENGTH, NODE_WIDTH);
        node.setFill(imagePattern);

        Label lnodeName = new Label("VM: " + vmNode.getName()+ "\n" + "IP: " + vmNode.getIntrfces()+ "\n" + vmNode.getOs());

        //lnodeName.setOpacity(0.5);
        lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


        lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(node, lnodeName);
        nodeContainer.relocate(vmNode.getPosx(), vmNode.getPosy());

       /* nodeContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                NodeController nodeController = NodeController.getNodeController();
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.getItems().get(2).setDisable(false);//allows deletion option of the stack pane object 'nodeContainer'
                    contextMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            String hubName = hubNode.getName();
                            nodeController.removeHubNode(hubName);
                        }
                    });

                    contextMenu.setOnHidden(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent e) {
                            contextMenu.getItems().get(2).setDisable(true);
                        }
                    });
                }
            }
        });
        */

        return nodeContainer;
    }







    public static void draw(Pane canvas, ContextMenu contextMenu) {//redraws everything when a form is completed, file is loaded, or node deleted
        canvas.getChildren().clear();
        canvas.getChildren().add(createVlanNode());//creates router and its height

        int tempPosX = 250;
        int tempPosY = 50;


        for (HubNode hubNode : NodeController.getNodeController().getHubNodes()) {

            String currentHubName = hubNode.getName();
            hubNode.setPosx(tempPosX);
            hubNode.setPosy(tempPosY);
            canvas.getChildren().add(hubInsertNode(hubNode, canvas, contextMenu));

            boolean haveConnections = !hubNode.getVmInterfaceNames().isEmpty();

            if (haveConnections) {
                createLine(canvas, tempPosX + 100, tempPosY + 50, tempPosX + 150, tempPosY + 50);
            }


            tempPosX += 200;
            for (VM vmNode: NodeController.getNodeController().getCurrentVms()) {
                String currentVMName = vmNode.getName();
                VM currentVM = vmNode;
                for (VMinterface vmInterface: vmNode.getIntrfces()) {
                    String vmIntrfcPair = currentVM.getName() + "." + vmInterface.getIntrfcLabel();
                    if (hubNode.containsInterfaceName(vmIntrfcPair)) {
                        currentVM.setPosx(tempPosX);
                        currentVM.setPosy(tempPosY);
                        canvas.getChildren().add(GraphicsController.vmInsertNode(currentVM, canvas, contextMenu));

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
                createLine(canvas, tempPosX - 50, hubNode.getPosy() + 50, tempPosX - 50, tempPosY - 100);
            }

            createLine(canvas, /* vlanstartPosX = */ 50 + 100, hubNode.getPosy() + 50, hubNode.getPosx(),
                    hubNode.getPosy() + 50);
            tempPosX += 200;

        }
    }

    private static void createLine(Pane canvas, int startX, int startY, int endX, int endY) {
        Line createLine = new Line();
        createLine.setStartX(startX);
        createLine.setStartY(startY);
        createLine.setEndX(endX);
        createLine.setEndY(endY);
        canvas.getChildren().add(createLine);

    }

    private static Node createVlanNode() {//creates the router and it's height
        int infNumber = 0;
        NodeController nodeController = NodeController.getNodeController();

        // Calculate the vertical height multiplier
        if (nodeController.getHubNodes().size() == 0) {
            infNumber =1;
        } else {
            for (HubNode hubNode : nodeController.getHubNodes()) {
                if (hubNode.getVmInterfaceNames().size() == 0) {
                    infNumber += 1;
                } else {
                    infNumber += hubNode.getVmInterfaceNames().size();
                }
        }
        }

        int vlanHeight = ((infNumber * 100) + ((infNumber -1) * 50));


        Image routerImg = new Image("root/Images/ROUTER.png");  //Selecting the Location of the router
        // each hub is represented by a blue rectangle
        //imageContainer.addAll(new Rectangle(64, 48, Color.CORNFLOWERBLUE), image); //new Rectangle(PatternConfig.nodeLength, PatternConfig.nodeWidth);

        //node.setFill(imagePattern);

        ImageView imageView = new ImageView(routerImg);
        Rectangle routerBox = new Rectangle(100 /* TODO: Make global constant*/, vlanHeight);
        routerBox.setFill(Color.FUCHSIA);

        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(routerBox, imageView);
        nodeContainer.relocate(50, 50);

        return nodeContainer;
    }






}