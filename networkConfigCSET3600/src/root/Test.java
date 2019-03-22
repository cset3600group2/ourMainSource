package root;

import root.networkobjects.*;

public class Test  {
    public static void main(String[] args) {
        NodeController controller = new NodeController();

        //Set up host VMs
        for(int i = 0;i < 5; i++) {

            // controller.addHostVM(new VM()); FIXME
            controller.getCurrentVms().get(i).setName("Blah-"+i);
            controller.getCurrentVms().get(i).setVer("1.0");
            controller.getCurrentVms().get(i).setOs("Windows");
            controller.getCurrentVms().get(i).setSrc("/src/test");
        }

        //Set up Firewalls
        for(int i = 0;i < 5; i++) {
            // controller.addHostVM(new VM()); FIXME
        }

        //Set up Hubs
        for(int i = 0;i < 5; i++) {
            controller.addHub(new HubNode("Hub-"+i, "192.168.150."+i, "255.255.255.0"));
        }


        ConfigFile.writeFile(controller);
    }

}