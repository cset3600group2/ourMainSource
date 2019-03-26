package root.networkobjects;


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
    public void removeHostVM(VM vm){
        //remove the pair from HubInterface if applicable
        //TODO
        //find index of the passed host from it's list and remove it

    }
    public void addHub(HubNode hubNode){
        currentHubNodes.add(hubNode);
    }
    public List<HubNode> getHubNodes(){
        return this.currentHubNodes;
    }
    public void removeHubNode(int hubIndex){ //TODO
/*T
        //remove gui representation then...
        HubNode thisHub = getHubNodes().get(hubIndex);//disconnect all interfaces that were attached to hub
        List<VMinterfPair> pairs = thisHub.getVMandIntrfcPairs();
        for (int i =0; i<thisHub.getVMandIntrfcPairs().size(); i++){
            pairs.get(i).getIntrfce().setDisconnected();
        }
        currentHubNodes.remove(hubIndex);
        */
    }

    public void clear(){ //wipe the entire list of nodes, used before opening a config file
        this.currentVms.clear();
        this.currentHubNodes.clear();
    }








}