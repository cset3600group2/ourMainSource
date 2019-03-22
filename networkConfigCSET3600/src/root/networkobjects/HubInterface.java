package root.networkobjects;

import root.networkobjects.VM;
import root.virtualmachines.VMInterface;

public class HubInterface extends VMInterface {
    private String subnet; //must follow constraints of a subnet mask
    private String netMask;


    public void setSubnet(String subnet){
        this.subnet = subnet;
    }
    public String getSubnet(){
        return this.subnet;
    }
    public void setNetMask(String netMask){
        this.netMask = netMask;
    }
    public String getNetMask(){
        return  this.netMask;
    }
    public static void connectInterface(){//connects a VM interface to itself if the subnet matches
        return;
    }
}
