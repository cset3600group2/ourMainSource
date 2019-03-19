package root;

import root.networkobjects.HubNode;
import root.networkobjects.VM;
import root.networkobjects.VMinterfPair;
import root.virtualmachines.HostVM;
import root.virtualmachines.FirewallVM;

import java.util.List;
import java.util.ArrayList;

public class NodeController {//direct gui leverageable controller: carries out back-end for validation of config file generations, deletions, and additions to topology

    private List<VM> currentVms;
    private List<HubNode> currentHubNodes;


    public void addHostVM(VM vm){//triggered on drag and drop from gui
        currentVms.add(vm);
    }
    public List<VM> getCurrentVms(){return this.currentVms;}
    public void removeHostVM(VM vm){
        //remove the pair from HubInterface if applicable
        //to do
        //find index of the passed host from it's list and remove it

    }
    public void addHub(HubNode hubNode){
        currentHubNodes.add(hubNode);
    }
    public List<HubNode> getHubNodes(){
        return this.currentHubNodes;
    }
    public void removeHubNode(int hubIndex){ //removes graphical hub and disconnects the interfaces that the hub was attached to
/*
        //remove gui representation then...
        HubNode thisHub = getHubNodes().get(hubIndex);//disconnect all interfaces that were attached to hub
        List<VMinterfPair> pairs = thisHub.getVMandIntrfcPairs();
        for (int i =0; i<thisHub.getVMandIntrfcPairs().size(); i++){
            pairs.get(i).getIntrfce().setDisconnected();
        }
        currentHubNodes.remove(hubIndex);
        */
    }








}