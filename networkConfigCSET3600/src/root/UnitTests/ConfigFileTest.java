package root.UnitTests;

import root.ConfigFile;
import root.NodeController;
import root.networkobjects.*;
import java.util.Arrays;


public class ConfigFileTest {
    public static void main(String[] args) {
        String sampleConfig = "vm Gemmini {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.10.3\"\n}\n\nvm Nfs {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.10.2\"\n}\n\nvm Intfw {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.20.2\"\n        eth1 : \"192.168.10.1\"\n}\n\nvm Extfw {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.40.1\"\n        eth1 : \"192.168.30.1\"\n        eth2 : \"192.168.20.1\"\n}\n\nvm Dmz {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.30.1\"\n}\n\nvm DefaultGW {\n        os : LINUX\n        ver : \"7.3\"\n        src : \"/srv/VMLibrary/JeOS\"\n        eth0 : \"192.168.40.2\"\n}\n\nhub hub1 {\n        inf : Gemmini.eth0,Nfs.eth0,Intfw.eth1\n        subnet : \"192.168.10.0\"\n        netmask : \"255.255.255.0\"\n}\n\nhub hub2 {\n        inf : Intfw.eth0,Extfw.eth2\n        subnet : \"192.168.20.0\"\n        netmask : \"255.255.255.0\"\n}\n\nhub hub3 {\n        inf : Extfw.eth1,Dmz.eth0\n        subnet : \"192.168.30.0\"\n        netmask : \"255.255.255.0\"\n}\n\nhub hub4 {\n        inf : Extfw.eth0,DefaultGW.eth0\n        subnet : \"192.168.40.0\"\n        netmask : \"255.255.255.0\"\n}\n\npartial_solution {\n        (Gemmini.eth0 V2.vinf21),\n        (Nfs.eth0 V2.vinf21),\n        (Intfw.eth1 V2.vinf21),\n        (Intfw.eth0 V2.vinf22),\n        (Extfw.eth2 V2.vinf22),\n        (Extfw.eth1 V2.vinf23),\n        (Dmz.eth0 V2.vinf23),\n        (Extfw.eth0 V2.vinf24),\n        (DefaultGW.eth0 V2.vinf24)\n}";
        NodeController controller = NodeController.getNodeController();

        //Set up host VMs
        controller.addHostVM(new VM("Gemmini","LINUX",Arrays.asList("192.168.10.3")));
        controller.addHostVM(new VM("Nfs","LINUX",Arrays.asList("192.168.10.2")));
        controller.addHostVM(new VM("Intfw","LINUX",Arrays.asList("192.168.20.2","192.168.10.1")));
        controller.addHostVM(new VM("Extfw","LINUX",Arrays.asList("192.168.40.1","192.168.30.1","192.168.20.1")));
        controller.addHostVM(new VM("Dmz","LINUX",Arrays.asList("192.168.30.1")));
        controller.addHostVM(new VM("DefaultGW","LINUX",Arrays.asList("192.168.40.2")));

        //Make sure they were all added correctly
        if(controller.getCurrentVms().size() != 6) {
            System.out.print("Error adding VMs");
        }

        //Set up Hubs
        controller.addHub(new HubNode("hub1", "192.168.10.0", "255.255.255.0"));
        controller.addHub(new HubNode("hub2", "192.168.20.0", "255.255.255.0"));
        controller.addHub(new HubNode("hub3", "192.168.30.0", "255.255.255.0"));
        controller.addHub(new HubNode("hub4", "192.168.40.0", "255.255.255.0"));

        //Make sure they were all added correctly
        if(controller.getHubNodes().size() != 4) {
            System.out.print("Error adding hubs");
        }

        //Write our config to a file
        String fileContents = ConfigFile.writeFile(".\\test.cfg");
        //System.out.print(fileContents);

        //Read the config we just wrote
        ConfigFile.readFile(".\\test.cfg");
        //Write it again to make sure it's still good
        String fileContents2 = ConfigFile.writeFile(".\\test.cfg");
        //System.out.print(fileContents2);

        //Make sure that our second file matches the first
        if(!fileContents.equals(fileContents2)) {
            System.out.print("Generating file from read file doesn't match original");
        }

        //Compare against a known config
        if(!sampleConfig.trim().equals(fileContents.trim())) {
            System.out.print("Generated file does not match sample");
        }
    }


}