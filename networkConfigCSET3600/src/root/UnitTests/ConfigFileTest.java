package root.UnitTests;

import root.ConfigFile;
import root.NodeController;
import root.networkobjects.*;
import java.util.Arrays;

import static root.Validation.isValidInterfacePair;


public class ConfigFileTest {

    //Check a controller to ensure it has the right number of hubs and VMs
    //Print debugging info on error
    private static void checkController(NodeController controller, int expectedHubs, int expectedVms, String debugText) {
        if(controller.getCurrentVms().size() != expectedVms) {
            System.out.print(debugText + "\n");
            System.out.print("Incorrect number of VMs found.\n");
            for (VM vmObj : controller.getCurrentVms()) {
                System.out.print(vmObj.getName()+"\n");
            }
        }
        if(controller.getHubNodes().size() != expectedHubs) {
            System.out.print(debugText + "\n");
            System.out.print("Incorrect number of Hubs found.\n");
            for (HubNode hubObj : controller.getHubNodes()) {
                System.out.print(hubObj.getName()+"\n");
            }
        }
    }
    public static void main(String[] args) {
        String sampleConfig = "vm Gemmini {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.10.3\"\n}\n\nvm Nfs {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.10.2\"\n}\n\nvm Intfw {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.20.2\"\n        eth1 : \"192.168.10.1\"\n}\n\nvm Extfw {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.40.1\"\n        eth1 : \"192.168.30.1\"\n        eth2 : \"192.168.20.1\"\n}\n\nvm Dmz {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.30.1\"\n}\n\nvm DefaultGW {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.40.2\"\n}\n\nhub hub1 {\n        inf : Gemmini.eth0, Nfs.eth0, Intfw.eth1\n        subnet : \"192.168.10.0\"\n        netmask : \"255.255.255.0\"\n}\n\nhub hub2 {\n        inf : Intfw.eth0, Extfw.eth2\n        subnet : \"192.168.20.0\"\n        netmask : \"255.255.255.0\"\n}\n\nhub hub3 {\n        inf : Extfw.eth1, Dmz.eth0\n        subnet : \"192.168.30.0\"\n        netmask : \"255.255.255.0\"\n}\n\nhub hub4 {\n        inf : Extfw.eth0, DefaultGW.eth0\n        subnet : \"192.168.40.0\"\n        netmask : \"255.255.255.0\"\n}";
        NodeController controller = NodeController.getNodeController();

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

        //Add interfaces to all of our hubs
        for (HubNode hubObj : controller.getHubNodes()) {
            for (VM vmObj : controller.getCurrentVms()) {
                for (VMinterface iface : vmObj.getIntrfces()) {
                    if (isValidInterfacePair(iface, hubObj)) {
                        hubObj.addVmInterfaceName(vmObj.getName() + "." + iface.getIntrfcLabel());
                    }
                }
            }
        }

        //Make sure they were all added correctly
        checkController(controller,4,6,"Initial Setup");

        //Write our config to a file
        String fileContents = ConfigFile.writeFile(".\\test.cfg");
        //System.out.print(fileContents);

        //Read the config we just wrote
        ConfigFile.readFile(".\\test.cfg");
        checkController(controller,4,6,"Reading written file");

        //Write it again to make sure it's still good
        String fileContents2 = ConfigFile.writeFile(".\\test.cfg");
        //System.out.print(fileContents2);

        //Make sure that our second file matches the first
        if(!fileContents.equals(fileContents2)) {
            System.out.print("Generating file from read file doesn't match original");
            System.out.print("\nFirst START\n"+fileContents.trim()+"\nEND\n");
            System.out.print("\nSecond START\n"+fileContents2.trim()+"\nEND\n");
        }

        //Compare against a known config, print debugging info if failed
        if(!sampleConfig.trim().equals(fileContents.trim())) {
            System.out.print("Generated file does not match sample");
            System.out.print("\nSample START\n"+sampleConfig.trim()+"\nEND\n");
            System.out.print("\nGenerated START\n"+fileContents.trim()+"\nEND\n");
        }
    }


}