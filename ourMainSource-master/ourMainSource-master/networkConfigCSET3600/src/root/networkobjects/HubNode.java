package root.networkobjects;

public class HubNode {
    private String name; //used as parent of record in config file e.g. 'vm Hub1 {...}'

    private String subnet; //the network address itself
    private String netmask; //the network subnet

    public HubNode(String name, String subnet, String netmask){//passed by authenticated gui form

        this.name = name;
        this.subnet = subnet;
        this.netmask = netmask;

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



}
