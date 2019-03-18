package root;
import root.virtualmachines.*;
import root.networkobjects.*;

public class Test  {
    public static void main(String[] args) {
        NodeController controller = new NodeController();

        //Set up host VMs
        for(int i = 0;i < 5; i++) {
            controller.addHostVM();
            controller.getCurrentHostVms().get(i).setName("Blah-"+i);
            controller.getCurrentHostVms().get(i).setVer("1.0");
            controller.getCurrentHostVms().get(i).setOs("Windows");
            controller.getCurrentHostVms().get(i).setSrc("/src/test");
        }

        //Set up Firewalls
        for(int i = 0;i < 5; i++) {
            controller.addFireWallVM();
        }

        //Set up Hubs
        for(int i = 0;i < 5; i++) {
            controller.addHub("Hub-"+i, "192.168.150."+i, "255.255.255.0");
        }


        ConfigFile.writeFile(controller);
    }

}