package root;

import org.w3c.dom.Node;
import root.networkobjects.HubNode;
import root.networkobjects.VM;
import root.networkobjects.VMinterface;


import java.util.List;
import java.util.ArrayList;

public class NodeController {//direct gui leverageable controller: carries out back-end for validation of config file generations, deletions, and additions to topology

    private List<VM> currentVms = new ArrayList<VM>();
    private List<HubNode> currentHubNodes = new ArrayList<HubNode>();


    //create an object of SingleObject
    private static NodeController nodeController = new NodeController();

    //make the constructor private so that this class cannot be
    //instantiated
    private NodeController(){}

    //Get the only object available
    public static NodeController getNodeController(){
        return nodeController;
    }
    public void addHostVM(VM vm){//triggered on drag and drop from gui
        currentVms.add(vm);
    }
    public List<VM> getCurrentVms(){return this.currentVms;}
    public void removeVMNode(String vmName){
        VM vm = null;
        for (VM vminCtrler: currentVms) {
            if (vminCtrler.getName().equals(vmName)) {
                vm = vminCtrler;
            }

        }
        if (vm!= null){
            currentVms.remove(vm);
        }

        //remove the pair from HubInterface if applicable
        //TODO
        //find index of the passed host from it's list and remove it

    }
    public void addHub(HubNode hubNode){
        currentHubNodes.add(hubNode);
    }
    public List<HubNode> getHubNodes(){
        return currentHubNodes;
    }
    public void removeHubNode(String hubName){
        HubNode hub = null;
       for (HubNode hubinCtrler: currentHubNodes) {
            if (hubinCtrler.getName().equals(hubName)) {
                hub = hubinCtrler;
                System.out.println(hub.getName());
            }

        }
       if (hub!= null){
           currentHubNodes.remove(hub);
        }


    }

    public void clear(){ //wipe the entire list of nodes, used before opening a config file
        this.currentVms.clear();
        this.currentHubNodes.clear();
    }
    public void refreshHubVMintrfces(){
        for (HubNode hubNode : NodeController.getNodeController().getHubNodes()) {
            hubNode.clearVMInterfaces();
            for(VM vm: NodeController.getNodeController().getCurrentVms()) {

                for (VMinterface iface : vm.getIntrfces()) {
                    if (Validation.isValidInterfacePair(iface, hubNode))
                        hubNode.addVmInterfaceName(vm.getName() + "." + iface.getIntrfcLabel());
                }
            }
        }
    }








}