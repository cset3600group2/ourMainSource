package root;

import root.networkobjects.HubNode;
import root.networkobjects.VMinterfPair;
import root.virtualmachines.HostVM;
import root.virtualmachines.FirewallVM;

import java.util.List;
import java.util.ArrayList;

public class NodeController {//direct gui leverageable controller: carries out back-end for validation of config file generations, deletions, and additions to topology

    private List<HostVM> currentHostVms = new ArrayList<HostVM>();
    private List<FirewallVM> currentFWVms = new ArrayList<FirewallVM>();
    private List<HubNode> currentHubNodes = new ArrayList<HubNode>();

    public void addHostVM(){//triggered on drag and drop from gui
        currentHostVms.add(new HostVM());
    }
    //CONSIDER ADDING AN INDEX TO THE CONSTRUCTOR FOR IDENTIFICATION TO CORRELATE GUI PIC WITH BACKENED OBJ
    //CONSIDER ADDING AN INDEX TO THE CONSTRUCTOR FOR IDENTIFICATION TO CORRELATE GUI PIC WITH BACKENED OBJ
    //CONSIDER ADDING AN INDEX TO THE CONSTRUCTOR FOR IDENTIFICATION TO CORRELATE GUI PIC WITH BACKENED OBJ
    //CONSIDER ADDING AN INDEX TO THE CONSTRUCTOR FOR IDENTIFICATION TO CORRELATE GUI PIC WITH BACKENED OBJ
    //CONSIDER ADDING AN INDEX TO THE CONSTRUCTOR FOR IDENTIFICATION TO CORRELATE GUI PIC WITH BACKENED OBJ


    public void addFireWallVM(){//triggered on drag and drop from gui
        currentFWVms.add(new FirewallVM());
    }
    public void addHub(String name, String subnet, String netmask){
        currentHubNodes.add(new HubNode(name, subnet, netmask));
    }
    public void removeHostVM(int hostVMindex){
        //remove gui representation then...
        //remove the pair from HubInterface if applicable
        currentHostVms.remove(hostVMindex);
    }
    public void removeFirewallVM(int fwVMindex){
        //remove gui representation then...
        //remove the pair from HubInterface if applicable
        //currentFWVms.remove(fwVMindex);
    }
    public void removeHubNode(int hubIndex){ //removes graphical hub and disconnects the interfaces that the hub was attached to

        // FIXME: This is outdated logic and will need to be refactored.
        //remove gui representation then...
        // HubNode thisHub = getHubNodes().get(hubIndex);//disconnect all interfaces that were attached to hub
        // List<VMinterfPair> pairs = thisHub.getVMandIntrfcPairs();
        // for (int i =0; i<thisHub.getVMandIntrfcPairs().size(); i++){
        //    pairs.get(i).getIntrfce().setDisconnected();
        // }
        //currentHubNodes.remove(hubIndex);
    }
    public List<FirewallVM> getCurrentFWVms(){
        return this.currentFWVms;
    }
    public List<HostVM> getCurrentHostVms(){
        return this.currentHostVms;
    }
    public List<HubNode> getHubNodes(){
        return this.currentHubNodes;
    }


    public void validateAll(List<HostVM> currentHostVms, List<FirewallVM> currentFWVms){//for config file generation, ensures every device is connect by a hub
       /*
        //check the isConnected() on each of the interfafces COMPLETE LATER
        */
    }


}