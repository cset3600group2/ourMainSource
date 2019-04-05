package root.xfmlcontrollers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import root.ConfigFile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import root.NodeController;
import root.networkobjects.HubNode;
import root.networkobjects.VM;
import root.networkobjects.VMinterface;

import java.io.*;

public class ApplicationController {


	private static final int NODE_LENGTH = 100;
	private static final int NODE_WIDTH = 100;
	@FXML
    TextArea outputConfig;
    @FXML
    TabPane tabPane;
    @FXML
    Pane canvas;
    @FXML
    AnchorPane anchorPane, mainWindow;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ContextMenu contextMenu;
    @FXML
    MenuItem deleteMenu;
    // These are the toggle button and the VBox container for the form
    public static ToggleButton btnEdit = new ToggleButton("Edit");
    public static Button btnAddInf = new Button("Add Infterface");
    public static File currentFile = null;



    @FXML
	private void openConfigFile() {//Opens a file for the user to generate a fresh NodeController
		FileChooser fileChooser = new FileChooser();//Allows opening and saving files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CFG files (*.cfg)", "*.cfg"); //sets extension filter
		fileChooser.getExtensionFilters().add(extFilter);
		Scene scene = mainWindow.getScene();//grabs the scene from the window that initialized this event,  required for file selector
		if (scene != null) {
			Window window = scene.getWindow();
			if (window != null) {

				File file = fileChooser.showOpenDialog(window);
				if (file != null) {

					String filePath = file.getAbsolutePath();


					NodeController.getNodeController().clear();//wipe all current nodes for new nodes to be generated

					ConfigFile.readFile(filePath);//generates the controller with the config file
					refreshAll();
			}
		}

		}

	}

	@FXML
	private void saveConfigFile() {
		FileChooser fileChooser = new FileChooser();//Allows opening and saving files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CFG files (*.cfg)", "*.cfg"); //sets extension filter
		fileChooser.getExtensionFilters().add(extFilter);
		Scene scene = mainWindow.getScene();//grabs the scene from the window that initialized this event,  required for file selector
		if (scene != null) {
			Window window = scene.getWindow();
			if (window != null) {
				File file = fileChooser.showSaveDialog(window);
				if (file != null) {

					String filePath = file.getAbsolutePath();
					ConfigFile.writeFile(filePath);//generates the controller with the config file
					/*//TODO TO BE REMOVED, ONLY A TEST
					NodeController.getNodeController().addHostVM(new VM("ok", "ok", ips));
					List<String> ips = new ArrayList<String>();
					ips.add("192.168.1.1");//test to see if it's removed
					for (VM vm : NodeController.getNodeController().getCurrentVms()){//test
						System.out.println(vm.getName());

					}
					//TODO TO BE REMOVED END ANCHOR*/
				}
			}

		}
	}
	@FXML
	private void exitProgram() {
		Platform.exit();
	}


	/*@FXML
	private void fileSave() {
	//TODO
	}
	*/
	
	@FXML
	private void tabEditor() {
		ConfigFile.writeOutput(outputConfig);

	}
	
	@FXML
	private void tabGraphical() {
		//TODO
	}
	
	@FXML
	private void insertHub(){
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/root/insertnewhubform.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/root/application.css").toExternalForm());
			Stage formWindow = new Stage();
			formWindow.setScene(scene);
			formWindow.setTitle("Hub Form");
			formWindow.setResizable(false);
			formWindow.setFullScreen(false);


			formWindow.initModality(Modality.WINDOW_MODAL); //forces entry to be done on the form before closing
			formWindow.initOwner(scrollPane.getScene().getWindow());
			formWindow.showAndWait();
			NodeController.getNodeController().refreshHubVMintrfces();
			refreshAll();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@FXML
	private void insertVM(){
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/root/insertnewvmform.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/root/application.css").toExternalForm());
			Stage formWindow = new Stage();
			formWindow.setTitle("VM Form");
			formWindow.setScene(scene);
			formWindow.setFullScreen(false);
			formWindow.setResizable(false);
			formWindow.initModality(Modality.WINDOW_MODAL);//forces entry to be done on the form before closing
			formWindow.initOwner(scrollPane.getScene().getWindow());
			formWindow.showAndWait();
			refreshAll();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void refreshAll(){//refreshes new interfaces on hubs, redraws gui, and reconfigures output
		NodeController.getNodeController().refreshHubVMintrfces();
		draw(canvas, contextMenu);
		ConfigFile.writeOutput(outputConfig);
	}


















	private Node hubInsertNode(HubNode hubNode, Pane canvas , ContextMenu contextMenu) {//returns image of hub to be added to main canvas

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
		hubNode.setCanvasNode(nodeContainer); //for removing

		nodeContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
							refreshAll();
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


	private Node vmInsertNode(VM vmNode, Pane canvas , ContextMenu contextMenu) {
		Image image = new Image("root/Images/VM.png");
		ImagePattern imagePattern = new ImagePattern(image);

		Rectangle node = new Rectangle(NODE_LENGTH, NODE_WIDTH);
		node.setFill(imagePattern);
		String ips = new String();
		int counter = 0;
		for(VMinterface vMinterface:vmNode.getIntrfces()){
			if (counter >= 1) {
				ips += "\n " + "IP: " + vMinterface.getIpAddress();
			}
			else{
				ips = "IP: " + vMinterface.getIpAddress();
			}
			counter++;
		}

		Label lnodeName = new Label("VM: " + vmNode.getName()+ "\n" + ips);

		//lnodeName.setOpacity(0.5);
		lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


		lnodeName.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-font-size: 8; ");


		StackPane nodeContainer = new StackPane();
		nodeContainer.getChildren().addAll(node, lnodeName);
		nodeContainer.relocate(vmNode.getPosx(), vmNode.getPosy());

		nodeContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				NodeController nodeController = NodeController.getNodeController();
				if (event.getButton() == MouseButton.SECONDARY) {
					contextMenu.getItems().get(2).setDisable(false);//allows deletion option of the stack pane object 'nodeContainer'
					contextMenu.getItems().get(2).setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							String vmName = vmNode.getName();
							nodeController.removeVMNode(vmName);
							refreshAll();
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







	public void draw(Pane canvas, ContextMenu contextMenu) {//redraws everything when a form is completed, file is loaded, or node deleted
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
						canvas.getChildren().add(vmInsertNode(currentVM, canvas, contextMenu));

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

	private void createLine(Pane canvas, int startX, int startY, int endX, int endY) {
		Line createLine = new Line();
		createLine.setStartX(startX);
		createLine.setStartY(startY);
		createLine.setEndX(endX);
		createLine.setEndY(endY);
		canvas.getChildren().add(createLine);

	}

	private Node createVlanNode() {//creates the router and it's height
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