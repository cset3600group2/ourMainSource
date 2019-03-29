package root.networkobjects;

import java.util.ArrayList;
import java.util.List;

public class HubNode {

    private int posx;
    private int posy;
    private String name; //used as parent of record in config file e.g. 'vm Hub1 {...}
    private String subnet; //the network address itself
    private String netmask; //the network subnet
    private List<String> vmInterfaceNames;

    public HubNode(String name, String subnet, String netmask){//passed by authenticated gui form

        this.name = name;
        this.subnet = subnet;
        this.netmask = netmask;
        vmInterfaceNames = new ArrayList<String>();
    }


    public String getName(){
        return this.name;
    }
    public void setName(){
        this.name = name;
    }
    public void setSubnet(String subnet){
        this.subnet = subnet;
    }
    public String getSubnet(){
        return this.subnet;
    }
    public void setNetmask(String netmask)
    {
        this.netmask = netmask;
    }
    public String getNetmask(){
        return this.netmask;
    }
    public void setPosx(int posx) {
        this.posx = posx;
    }
    public int getPosx(){
        return this.posx;
    }
    public void setPosy(int posy){
        this.posy = posy;
    }

    public int getPosy() {
        return this.posy;
    }

    public List<String> getVmInterfaceNames()
    {
        return this.vmInterfaceNames;
    }

    public void addVmInterfaceName(String name)
    {
        this.vmInterfaceNames.add(name);
    }
}
