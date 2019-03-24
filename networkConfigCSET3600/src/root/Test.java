package root;

import root.networkobjects.*;
import java.util.List;
import java.util.Arrays;

public class Test  {
    public static void main(String[] args) {
        NodeController controller = new NodeController();

        //Set up host VMs
        controller.addHostVM(new VM("Gemmini","LINUX",Arrays.asList("192.168.10.3")));
        controller.addHostVM(new VM("Nfs","LINUX",Arrays.asList("192.168.10.2")));
        controller.addHostVM(new VM("Intfw","LINUX",Arrays.asList("192.168.20.2","192.168.10.1")));
        controller.addHostVM(new VM("Extfw","LINUX",Arrays.asList("192.168.40.1","192.168.30.1","192.168.20.1")));
        controller.addHostVM(new VM("Dmz","LINUX",Arrays.asList("192.168.30.1")));
        controller.addHostVM(new VM("DefaultGW","LINUX",Arrays.asList("192.168.40.2")));

        //Set up Hubs
        controller.addHub(new HubNode("hub1", "192.168.10.0", "255.255.255.0"));
        controller.addHub(new HubNode("hub2", "192.168.20.0", "255.255.255.0"));
        controller.addHub(new HubNode("hub3", "192.168.30.0", "255.255.255.0"));
        controller.addHub(new HubNode("hub4", "192.168.40.0", "255.255.255.0"));



        ConfigFile.writeFile(controller);
    }

}