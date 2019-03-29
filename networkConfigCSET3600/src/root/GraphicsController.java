package root;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.PopOver;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
//import javax.swing.JLabel;


public class GraphicsController{

    private static final int NODE_LENGTH = 100;
    private static final int NODE_WIDTH = 100;

    private static Node hubInsertNode(HubNode hubNode, Pane canvas /*, ContextMenu contextMenu */) {
        Image image = new Image("Images/HUB.png");
        ImagePattern imagePattern = new ImagePattern(image);

        Rectangle node = new Rectangle(NODE_LENGTH, NODE_WIDTH);
        node.setFill(imagePattern);

        Label lnodeName = new Label("HUB: " + hubNode.getName()+ "\n" + "IP: " + hubNode.getSubnet() + "\n" + "NetMask: " + hubNode.getNetmask());

        //lnodeName.setOpacity(0.5);
        lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(node, lnodeName);
        nodeContainer.relocate(hubNode.getPosx(), hubNode.getPosy());

/*
        nodeContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    PopOver popOver = new PopOver(insertHUBpoContent(hubObject, canvas, contextMenu));

                    popOver.show(nodeContainer);
                    popOver.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent e) {
                            hideHubNodeListener(popOver, hubObject, canvas, contextMenu);
                        }
                    });
                }
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.getItems().get(2).setDisable(false);

                    contextMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            String hubName = hubObject.getName();
                            TreeSet<String> interfaces = hubObject.getInfs();
                            for (String inf : interfaces) {
                                Pattern pattern1 = Pattern.compile(PatternConfig.hubInfPattern);
                                Matcher matcher = pattern1.matcher(inf);
                                if (matcher.find()) {
                                    String vmName = matcher.group(1);
                                    String vmEth = matcher.group(2);
                                    PatternConfig.vmMap.get(vmName).removeInf(vmEth);
                                }
                            }

                            PatternConfig.hubMap.remove(hubName);
                            draw(canvas, contextMenu);
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

    private static void createLine(Pane canvas, int startX, int startY, int endX, int endY) {
        Line createLine = new Line();
        createLine.setStartX(startX);
        createLine.setStartY(startY);
        createLine.setEndX(endX);
        createLine.setEndY(endY);
        canvas.getChildren().add(createLine);

    }

    public static void draw(Pane canvas) {
        canvas.getChildren().clear();
        canvas.getChildren().add(createVlanNode());

        int tempPosX = 250;
        int tempPosY = 250;


        for (HubNode hubNode : NodeController.getNodeController().getHubNodes()) {

            String currentHubName = hubNode.getName();
            hubNode.setPosx(tempPosX);
            hubNode.setPosy(tempPosY);
            canvas.getChildren().add(hubInsertNode(hubNode, canvas));

            boolean haveConnections = !hubNode.getVmInterfaceNames().isEmpty();

            if (haveConnections) {
                createLine(canvas, tempPosX + 100, tempPosY + 50, tempPosX + 150, tempPosY + 50);
            }

            /*
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
            */
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

    private static Node createVlanNode() {
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


        Image routerImg = new Image("Images/ROUTER.png");  //Selecting the Location of the router
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