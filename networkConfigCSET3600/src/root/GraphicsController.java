package root;

import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import root.networkobjects.NodeController;



import java.util.Map;


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

public class GraphicsController {//
    //create an object of SingleObject
    private static GraphicsController graphicsController = new GraphicsController();
    public NodeController nodeController = NodeController.getNodeController();//used to keep track of where to position vms

    //make the constructor private so that this class cannot be
    //instantiated
    private GraphicsController(){}



    private static Node hubInsertNode(HUB hubObject, Pane canvas, ContextMenu contextMenu) {
        Image image = new Image("Images/HUB.png");
        ImagePattern imagePattern = new ImagePattern(image);

        Rectangle node = new Rectangle(PatternConfig.nodeLength, PatternConfig.nodeWidth);
        node.setFill(imagePattern);

        ImageView imageView = new ImageView(image);

        Label lnodeName = new Label("HUB: " + hubObject.getName()+ "\n" + "IP: " + hubObject.getSubnet() + "\n" + "NetMask: " + hubObject.getNetmask());

        //lnodeName.setOpacity(0.5);
        lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(node, lnodeName);
        nodeContainer.relocate(hubObject.getPosX(), hubObject.getPosY());


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

        return nodeContainer;
    }

    private static Node VMInsert(VM vmObject, Pane canvas, ContextMenu contextMenu) {
        //Selecting the Location of the image needed
        Image image = new Image("Images/VM.png");
        ImagePattern imagePattern = new ImagePattern(image);

        // each hub is represented by a blue rectangle
        Rectangle node = new Rectangle(PatternConfig.nodeLength, PatternConfig.nodeWidth);
        node.setFill(imagePattern);

        //ImageView imageView = new ImageView(image);

        // the hub name in the rectangle
        ImageView imageView = new ImageView(image);

        Label lnodeName = new Label("HUB: " + vmObject.getName()+ "\n" + "IP: " + vmObject.getInterfaces()+ "\n" + vmObject.getOs());

        //lnodeName.setOpacity(0.5);
        lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(node, lnodeName);
        nodeContainer.relocate(vmObject.getPosX(), vmObject.getPosY());


        nodeContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    PopOver popOver = new PopOver(vmInsertPO(vmObject, canvas, contextMenu));
                    popOver.setDetachable(false);
                    popOver.show(nodeContainer);
                    popOver.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent e) {
                            vmNodeHidingListener(popOver, vmObject, canvas, contextMenu);
                        }
                    });
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println(PatternConfig.vmMap);
                    // menu
                    contextMenu.getItems().get(2).setDisable(false);
                    // select object to delete
                    contextMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            System.out.println("Delete is clicked");
                            String vmName = vmObject.getName();
                            LinkedHashMap<String, HUB> tempHubMap = PatternConfig.hubMap;
                            // find hubs
                            for (Map.Entry<String, HUB> hubEntry : tempHubMap.entrySet()) {
                                TreeSet<String> hubInf = tempHubMap.get(hubEntry.getValue().getName()).getInfs();
                                Pattern pat = Pattern.compile(PatternConfig.hubInfPattern);
                                boolean deleteInf = false;
                                ArrayList<String> toRemove = new ArrayList<String>();
                                // find hub interfaces
                                for (String inf : hubInf) {
                                    Matcher matcher = pat.matcher(inf);
                                    if (matcher.find()) {
                                        if (matcher.group(1).toLowerCase().equals(vmName
                                                .toLowerCase())) {
                                            toRemove.add(inf);
                                            deleteInf = true;
                                        }
                                    }
                                }
                                System.out.println(toRemove);
                                if (deleteInf == true) {
                                    for (String item : toRemove) {
                                        PatternConfig.hubMap.get(hubEntry.getValue().getName()).removeInf(item);
                                    }
                                }
                            }
                            // delete vm
                            PatternConfig.vmMap.remove(vmName);
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
        return nodeContainer;
    }

    private static Node createVlanNode(String nodeLabel, Pane canvas, ContextMenu contextMenu) {
        int infNumber = 0;

        if (PatternConfig.hubMap.size() == 0) {
            infNumber =1;
        } else {
            for (Map.Entry<String, HUB> entry : PatternConfig.hubMap.entrySet()) {
                if (entry.getValue().getInfs().size() == 0) {
                    infNumber += 1;
                } else {
                    infNumber += entry.getValue().getInfs().size();
                }
            }
        }

        int vlanHeight = ((infNumber * 100) + ((infNumber -1) * 50));
        //Selecting the Location of the image needed
        Image image = new Image("Images/ROUTER.png");
        ImagePattern imagePattern = new ImagePattern(image);



        StackPane imageContainer = new StackPane();
        // each hub is represented by a blue rectangle
        //imageContainer.addAll(new Rectangle(64, 48, Color.CORNFLOWERBLUE), image); //new Rectangle(PatternConfig.nodeLength, PatternConfig.nodeWidth);

        //node.setFill(imagePattern);

        ImageView imageView = new ImageView(image);
        Rectangle node = new Rectangle(PatternConfig.nodeWidth, vlanHeight);
        Label lnodeName = new Label(nodeLabel);
        node.setFill(Color.PURPLE);

        StackPane nodeContainer = new StackPane();
        nodeContainer.getChildren().addAll(node, imageView);
        nodeContainer.relocate(PatternConfig.vlanstartPosX, PatternConfig.vlanstartPosY);

        return nodeContainer;
    }

    private static void insertObjectRow(String labelText, String textFormText, Integer size, VBox content, boolean addToEnd) {
        HBox formRow = new HBox(size);
        formRow.getStyleClass().add("popover-form");
        Label rowLabel = new Label(labelText);
        rowLabel.getStyleClass().add("popover-form-label");
        TextField rowTF = new TextField();
        rowTF.getStyleClass().add("popover-form-textfield-inactive");
        rowTF.setText(textFormText);
        rowTF.setEditable(false);


        if (addToEnd) {
            formRow.getChildren().addAll(rowLabel, rowTF);
            content.getChildren().add(content.getChildren().size() - 1, formRow);
        } else {
            formRow.getChildren().addAll(rowLabel, rowTF);
            content.getChildren().add(formRow);
        }
    }

    private static void createHeader(String labelText, ToggleButton btn, Integer size, VBox content) {
        HBox headerRow = new HBox(size);
        Label lname = new Label(labelText);
        lname.getStyleClass().add("popover-label");
        btn.setPrefSize(50, 10);

        btn.setSelected(false);
        headerRow.getChildren().addAll(lname, btn);
        content.getChildren().add(headerRow);
    }

    public static void draw(Pane canvas, ContextMenu contextMenu) {
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

    private static void createLine(Pane canvas, int startX, int startY, int endX, int endY) {
        Line createLine = new Line();
        createLine.setStartX(startX);
        createLine.setStartY(startY);
        createLine.setEndX(endX);
        createLine.setEndY(endY);
        canvas.getChildren().add(createLine);

    }

    private static void vmBtnListener(VBox content, VM vmObject, Pane canvas, ContextMenu contextMenu) {

        MyController.btnEdit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getButton() == MouseButton.PRIMARY) {
                    for (Node node : content.getChildren()) {

                        if (node instanceof HBox) {
                            ObservableList<Node> childNode = ((HBox) node).getChildren();
                            for (int i = 0; i < childNode.size(); i++) {
                                if (childNode.get(i) instanceof TextField) {
                                    ((TextField) childNode.get(i)).editableProperty()
                                            .bindBidirectional(MyController.btnEdit.selectedProperty());
                                    if (MyController.btnEdit.isSelected()) {

                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .remove("popover-form-textfield-inactive");
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .add("popover-form-textfield-active");
                                    } else if (!MyController.btnEdit.isSelected()) {
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .remove("popover-form-textfield-active");
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .add("popover-form-textfield-inactive");
                                    }
                                }

                                if (childNode.get(i) instanceof Button) {
                                    ((Button) childNode.get(i)).disableProperty()
                                            .bindBidirectional(MyController.btnEdit.selectedProperty());
                                }
                            }
                        }
                    }
                    draw(canvas, contextMenu);
                }

            }
        });
    }

    private static void vmNodeHidingListener(PopOver popover, VM vmObject, Pane canvas, ContextMenu contextMenu) {
        try {

            VBox contentPane = (VBox) popover.getContentNode();

            HBox headerRow = (HBox) contentPane.getChildren().get(0);

            ToggleButton toggleBtn = (ToggleButton) headerRow.getChildren().get(1);

            VM oldVM = vmObject;


            if (!toggleBtn.isSelected()) {
                VM newVmObject = new VM();
                TreeMap<String, String> newInterfaces = new TreeMap<String, String>();
                for (Node row : contentPane.getChildren()) {
                    if (row instanceof HBox) {
                        ObservableList<Node> childNode = ((HBox) row).getChildren();
                        for (int i = 0; i < childNode.size(); i++) {
                            if (childNode.get(i) instanceof Label) {
                                if (((Label) childNode.get(i)).getText().matches("Name.*")) {
                                    String vmName = ((TextField) childNode.get(i + 1)).getText();

                                    if (!oldVM.getName().equals(vmName)) {

                                        if (valueChecker.checkName(vmName)) {
                                            newVmObject.setName(vmName);
                                        } else {

                                            creatAlert(((Label) childNode.get(i)).getText(), "VM");
                                            newVmObject.setName(oldVM.getName());
                                        }
                                    } else {

                                        newVmObject.setName(oldVM.getName());
                                    }

                                } else if (((Label) childNode.get(i)).getText().matches("OS.*")) {
                                    String vmOs = ((TextField) childNode.get(i + 1)).getText();
                                    if (valueChecker.checkOs(vmOs)) {
                                        newVmObject.setOs(vmOs);
                                    } else {
                                        creatAlert(((Label) childNode.get(i)).getText(), "VM");
                                        newVmObject.setOs(oldVM.getOs());
                                    }
                                } else if (((Label) childNode.get(i)).getText().matches("Ver.*")) {
                                    String vmVer = ((TextField) childNode.get(i + 1)).getText();
                                    if (valueChecker.checkVer(vmVer)) {
                                        newVmObject.setVer(Double.parseDouble(vmVer));
                                    } else {
                                        creatAlert(((Label) childNode.get(i)).getText(), "VM");
                                        newVmObject.setVer(oldVM.getVer());
                                    }
                                } else if (((Label) childNode.get(i)).getText().matches("Src.*")) {
                                    String vmSrc = ((TextField) childNode.get(i + 1)).getText();
                                    if (valueChecker.checkSrc(vmSrc)) {
                                        newVmObject.setSrc(vmSrc);
                                    } else {
                                        creatAlert(((Label) childNode.get(i)).getText(), "VM");
                                        newVmObject.setSrc(oldVM.getSrc());
                                    }
                                } else if (((Label) childNode.get(i)).getText().matches("(\\w+?).(\\w+?\\d+?.*)")) {
                                    String ipLabel = ((Label) childNode.get(i)).getText();
                                    String vmIp = ((TextField) childNode.get(i + 1)).getText().trim();

                                    if (oldVM.getInterfaces().containsKey(ipLabel)) {

                                        if (!oldVM.getInterfaces().get(ipLabel).equals(vmIp)) {

                                            if (valueChecker.checkIp(vmIp)) {
                                                newInterfaces.put(ipLabel, vmIp);
                                            } else {
                                                //creatAlert(ipLabel, "VM");
                                                newInterfaces.put(ipLabel, oldVM.getInterfaces().get(ipLabel));
                                            }
                                        } else {
                                            // if ip value didn't change just
                                            // set to old value
                                            newInterfaces.put(ipLabel, oldVM.getInterfaces().get(ipLabel));
                                        }
                                    } else {
                                        // if that eth# doesn't exist yet
                                        // check it and set it
                                        // only set the new eth# if the new ip
                                        // value is valid
                                        if (!vmIp.isEmpty()) {
                                            if (valueChecker.checkIp(vmIp)) {
                                                newInterfaces.put(ipLabel, vmIp);
                                            } else {
                                                // tell user there was an error
                                                // and don't insert the new eth#
                                                // interface
                                                creatAlert(ipLabel, "VM");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                newVmObject.setInterfaces(newInterfaces);
                PatternConfig.vmMap.replace(oldVM.getName(), newVmObject);
                // here we don't want to simply delete old entry because of the
                // coordinates
                // so we update the key to a different key if they change the VM
                // Object name
                LinkedHashMap<String, VM> updatedMap = PatternConfig.replaceVMKey(PatternConfig.vmMap, oldVM.getName(),
                        newVmObject.getName());
                PatternConfig.vmMap = updatedMap;
                draw(canvas, contextMenu);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Something went Wrong");
        }
    }

    private static void hubBtnEventListener(VBox content, HUB hubObject, Pane canvas, ContextMenu contextMenu) {
        // Add ability to go into edit mode for the Textfield
        MyController.btnEdit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Query the popover setup schema to eventually find the
                // Textfields and set the editable property based off the
                // btnEdit
                if (event.getButton() == MouseButton.PRIMARY) {
                    // inf is used to update the hub's interfaces
                    // TreeSet<String> inf = new TreeSet<String>();
                    for (Node node : content.getChildren()) {
                        // System.out.println(node);
                        if (node instanceof HBox) {
                            ObservableList<Node> childNode = ((HBox) node).getChildren();
                            for (int i = 0; i < childNode.size(); i++) {
                                if (childNode.get(i) instanceof TextField) {
                                    ((TextField) childNode.get(i)).editableProperty()
                                            .bindBidirectional(MyController.btnEdit.selectedProperty());
                                    if (MyController.btnEdit.isSelected()) {
                                        System.out.println("Edit Mode");
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .remove("popover-form-textfield-inactive");
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .add("popover-form-textfield-active");
                                    } else if (!MyController.btnEdit.isSelected()) {
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .remove("popover-form-textfield-active");
                                        ((TextField) childNode.get(i)).getStyleClass()
                                                .add("popover-form-textfield-inactive");
                                    }
                                }
                                if (childNode.get(i) instanceof Button) {
                                    ((Button) childNode.get(i)).disableProperty()
                                            .bindBidirectional(MyController.btnEdit.selectedProperty());
                                }
                            }
                        }
                    }
                    draw(canvas, contextMenu);
                }
            }
        });
    }

    private static void hideHubNodeListener(PopOver popover, HUB hubObject, Pane canvas, ContextMenu contextMenu) {
        try {
            // eveything in the popover
            VBox contentPane = (VBox) popover.getContentNode();
            // find the header row
            HBox headerRow = (HBox) contentPane.getChildren().get(0);
            // find the toggle button
            ToggleButton toggleBtn = (ToggleButton) headerRow.getChildren().get(1);
            // the old key used to find the correlating vmObject
            HUB oldHub = hubObject;

            // only update with it's not in edit mode
            if (!toggleBtn.isSelected()) {
                HUB newHubObject = new HUB();
                TreeSet<String> newInterfaces = new TreeSet<String>();
                for (Node row : contentPane.getChildren()) {
                    if (row instanceof HBox) {
                        ObservableList<Node> childNode = ((HBox) row).getChildren();
                        for (int i = 0; i < childNode.size(); i++) {
                            if (childNode.get(i) instanceof Label) {
                                if (((Label) childNode.get(i)).getText().matches("Name.*")) {
                                    String hubName = ((TextField) childNode.get(i + 1)).getText();
                                    // only check name if it's different than
                                    // the current one
                                    if (!oldHub.getName().equals(hubName)) {
                                        // make sure name input is a valid name
                                        if (valueChecker.checkName(hubName)) {
                                            newHubObject.setName(hubName);
                                        } else {
                                            // if input is not valid, warn user
                                            // and keep the old one
                                            creatAlert(((Label) childNode.get(i)).getText(), "HUB");
                                            newHubObject.setName(oldHub.getName());
                                        }
                                    } else {
                                        // set it to old name if it did not
                                        // change
                                        newHubObject.setName(oldHub.getName());
                                    }
                                    // same general idea goes for the rest of
                                    // the labels
                                    // if input is different
                                    // then test it's validation before making
                                    // changes
                                    // if it's not valid then warn the user
                                    // else just set it to the old one
                                } else if (((Label) childNode.get(i)).getText().matches("Subnet.*")) {
                                    String hubSubnet = ((TextField) childNode.get(i + 1)).getText();
                                    if (!oldHub.getSubnet().equals(hubSubnet)) {
                                        if (valueChecker.checkIp(hubSubnet)) {
                                            newHubObject.setSubnet(hubSubnet);
                                        } else {
                                            creatAlert(((Label) childNode.get(i)).getText(), "HUB");
                                            newHubObject.setSubnet(oldHub.getSubnet());
                                        }
                                    } else {
                                        newHubObject.setSubnet(oldHub.getSubnet());
                                    }
                                } else if (((Label) childNode.get(i)).getText().matches("Netmask.*")) {
                                    String hubNetmask = ((TextField) childNode.get(i + 1)).getText();
                                    if (valueChecker.checkNetmask(hubNetmask)) {
                                        newHubObject.setNetmask(hubNetmask);
                                    } else {
                                        creatAlert(((Label) childNode.get(i)).getText(), "HUB");
                                        newHubObject.setNetmask(oldHub.getNetmask());
                                    }
                                } else if (((Label) childNode.get(i)).getText().matches("Inf.*")) {
                                    String infValue = ((TextField) childNode.get(i + 1)).getText();
                                    // if field is not empty
                                    if (!infValue.isEmpty()) {
                                        // if the interface is not in the old
                                        // ones
                                        if (!oldHub.getInfs().contains(infValue)) {
                                            String hubNetmask = null;
                                            if(!hubObject.getSubnet().equals(hubNetmask)) {
                                                hubNetmask = newHubObject.getNetmask();
                                            }else {
                                                hubNetmask = oldHub.getNetmask();
                                            }
                                            // if new inf value is valid then
                                            // insert the new one
                                            if (valueChecker.checkHubInf(infValue) && valueChecker.checkSubnetting(newHubObject.getNetmask(), infValue, newHubObject.getSubnet())) {
                                                newInterfaces.add(infValue);
                                            } else {
                                                creatAlert("Inf.", "HUB");
                                            }
                                        } else {
                                            newInterfaces.add(infValue);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                newHubObject.setInfs(newInterfaces);
                PatternConfig.hubMap.replace(oldHub.getName(), newHubObject);
                // here we don't want to simply delete old entry because of the
                // coordinates
                // so we update the key to a different key if they change the
                // Hub Object name
                LinkedHashMap<String, HUB> updatedMap = PatternConfig.replaceHUBKey(PatternConfig.hubMap, oldHub.getName(),
                        newHubObject.getName());
                PatternConfig.hubMap = updatedMap;
                draw(canvas, contextMenu);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Something went horriably wrong");
        }
    }

    private static VBox insertHUBpoContent(HUB hubObject, Pane canvas, ContextMenu contextMenu) {
        // this content container is everything that is going to go on the
        // PopOver
        VBox content = new VBox(5);
        content.getStyleClass().add("popover-content");
        content.setId("contentPane");
        // the first row contains the big label and the toggle button
        createHeader(hubObject.getName(), MyController.btnEdit, 25, content);

        // just a line separator
        Separator hr = new Separator(Orientation.HORIZONTAL);
        hr.minWidth(Control.USE_COMPUTED_SIZE);
        content.getChildren().add(hr);

        // the first row of the form (Hub name)
        insertObjectRow("Name: ", hubObject.getName(), 15, content, false);

        // Here is the same layout as row 1 (Hub subnet)
        insertObjectRow("Subnet:", hubObject.getSubnet(), 15, content, false);

        // Row 3 (Hub netmask)
        insertObjectRow("Netmask:", hubObject.getNetmask(), 15, content, false);

        // this will dynamically add rows to the formPane base on the # of inf
        // entries
        for (String inf : hubObject.getInfs()) {
            insertObjectRow("Inf:", inf, 15, content, false);

        }
        hubBtnEventListener(content, hubObject, canvas, contextMenu);
        insertButtons("Add Interfaces", content, "hub");
        return content;
    }

    private static VBox vmInsertPO(VM vmObject, Pane canvas, ContextMenu contextMenu) {
        // this content container is everything that is going to go on the
        // PopOver
        VBox content = new VBox(5);
        content.getStyleClass().add("popover-content");
        content.setId("contentPane");
        // the first row contains the big label and the toggle button
        createHeader(vmObject.getName(), MyController.btnEdit, 25, content);

        // just a line separator
        Separator hr = new Separator(Orientation.HORIZONTAL);
        hr.minWidth(Control.USE_COMPUTED_SIZE);
        content.getChildren().add(hr);

        // the first row of the form (VM name)
        // each row of the form contains a label and a Textfield
        insertObjectRow("Name: ", vmObject.getName(), 15, content, false);

        insertObjectRow("OS:", vmObject.getOs(), 15, content, false);

        // Row 3 (VM ver)
        insertObjectRow("Ver:", vmObject.getVer().toString(), 15, content, false);

        // Row 4 (VM src)
        insertObjectRow("Src:", vmObject.getSrc(), 15, content, false);

        for (Map.Entry<String, String> entry : vmObject.getInterfaces().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            insertObjectRow(key, value, 15, content, false);
        }
        vmBtnListener(content, vmObject, canvas, contextMenu);
        insertButtons("Add Interfaces", content, "vm");
        return content;
    }

    private static void insertButtons(String btnLabel, VBox content, String type) {
        // add a row with a button to popovers
        HBox btnRow = new HBox();
        btnRow.setId("btnRow");
        btnRow.getStyleClass().add("popover-form-buttonRow");
        Button button = new Button(btnLabel);
        button.getStyleClass().add("popover-form-button-enable");
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    ObservableList<Node> popOverContent = content.getChildren();
                    String ethRegex = "(\\w+?)(\\d+?.*)";
                    Pattern ethPattern = Pattern.compile(ethRegex);
                    String nextInterfaceLabel = "";
                    // determine what is the index tagged at the last eht# on
                    // the form
                    for (Node node : popOverContent) {
                        if (node instanceof HBox) {
                            for (Node innerNode : ((HBox) node).getChildren()) {
                                if (innerNode instanceof Label) {
                                    String nodeLabel = ((Label) innerNode).getText();
                                    if (nodeLabel.matches(ethRegex) && type.trim().toLowerCase().equals("vm")) {
                                        Matcher matcher = ethPattern.matcher(nodeLabel);
                                        if (matcher.find()) {
                                            nextInterfaceLabel = "eth"
                                                    + String.valueOf(Integer.parseInt(matcher.group(2)) + 1);
                                        } else {
                                            nextInterfaceLabel = "eth0";
                                        }
                                    } else if (!nodeLabel.matches(ethRegex) && type.trim().equals("vm")) {
                                        nextInterfaceLabel = "eth0";
                                    } else if (type.trim().toLowerCase().equals("hub")) {
                                        nextInterfaceLabel = "Inf:";
                                    }
                                }
                            }
                        }

                    }
                    insertObjectRow(nextInterfaceLabel, "", 15, content, true);
                }
            }
        });
        btnRow.getChildren().add(button);
        content.getChildren().add(btnRow);
    }

    public static void interfaceAdd(String labelText, VBox content) {
        // this one is used for adding rows in the Insert New VM Form
        HBox formRow = new HBox();
        formRow.getStyleClass().add("vmform-infRow");
        Label rowLabel = new Label(labelText);
        rowLabel.getStyleClass().add("vmform-label");
        TextField rowTF = new TextField();
        formRow.getChildren().addAll(rowLabel, rowTF);
        content.getChildren().add(formRow);
    }

    private static void creatAlert(String input, String type) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(input + " Input Error");
        alert.setHeaderText("Interface Error");
        alert.setContentText("Please check over your '" + input + "' input parameter. The " + type + " " + input
                + " will not be saved.");
        alert.show();
    }


}
