package root;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import root.networkobjects.*;
import static root.Validation.isValidInterfacePair;

public class ConfigFile {
    public static final String TAB = "        ";

    public static String writeFile(NodeController controller, String path) {
        String outputText = "";

        String partialSolution = "";
        int partialNumber = 21;

        for (VM vmObj : controller.getCurrentVms()) {
            outputText += "vm " + vmObj.getName() + " {\n" +
                    TAB+"os : " + vmObj.getOs() + "\n" +
                    TAB+"ver : \"" + vmObj.getVer() + "\"\n" +
                    TAB+"src : \"" + vmObj.getSrc() + "\"\n";
            for (VMinterface iface : vmObj.getIntrfces()) {
                outputText += TAB+iface.getIntrfcLabel()+" : \""+iface.getIpAddress()+"\"\n";
            }
            outputText += "}\n\n";
        }

        for (HubNode hubObj : controller.getHubNodes()) {
            //Get inf for the hub
            String infList = "";
            for(VM vmObj : controller.getCurrentVms()) {
                for(VMinterface ifaceVm : vmObj.getIntrfces()) {
                    if(isValidInterfacePair(ifaceVm,hubObj)) {
                        infList += vmObj.getName() + "." + ifaceVm.getIntrfcLabel() + ",";
                        partialSolution += TAB+"("+vmObj.getName()+"."+ifaceVm.getIntrfcLabel()+" "+"V2.vinf" + partialNumber + "),\n";
                    }
                }
            }
            if(!infList.equals("")) {
                infList = infList.substring(0, infList.length() - 1); //Remove last comma from list
            }

            outputText += "hub " + hubObj.getName() + " {\n" +
                    TAB+"inf : "+ infList + "\n" +
                    TAB+"subnet : \"" + hubObj.getSubnet() + "\"\n" +
                    TAB+"netmask : \"" + hubObj.getNetmask() + "\"\n";
            outputText += "}\n\n";
            partialNumber++;
        }

        if(!partialSolution.equals("")) {
            partialSolution = partialSolution.substring(0, partialSolution.length() - 2); //Remove last comma and line break from list
            outputText += "partial_solution {\n" + partialSolution + "\n}";
        }

        //Write the string we just created to a file
        try {
            //Make directory
            File file = new File(path);
            file.getParentFile().mkdirs();

            //Write the file
            PrintWriter out = new PrintWriter(path);
            out.write(outputText);
            out.flush();
            out.close();

            return outputText;
        }
        catch (FileNotFoundException ex) {
            System.err.println(ex);
            return "Error "+ex;
        }
    }

    public static NodeController readFile(String path) {
        NodeController controller = new NodeController();
        String fileLine;
        String nodeType="", nodeName="", nodeSubnet="", nodeNetmask="", nodeOs="",attributeType="", attributeVal="";
        List<String> nodeIp = new ArrayList<String>();


        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(path));
            while ((fileLine = fileReader.readLine()) != null) {
                fileLine = fileLine.trim();
                if(fileLine.contains("{")) { //We've got a new network object
                    nodeType = fileLine.split(" ")[0].trim();
                    nodeName = fileLine.split(" ")[1];
                    continue;
                }
                if(fileLine.contains(" : ")) {
                    attributeType = fileLine.split(" : ")[0];
                    attributeVal = fileLine.split(" : ")[1].replace("\"","");
                    if(attributeType.contains("eth")) {
                        nodeIp.add(attributeVal);
                    }
                    if(attributeType.equals("subnet")) {
                        nodeSubnet = attributeVal;
                    }
                    if(attributeType.equals("netmask")) {
                        nodeNetmask = attributeVal;
                    }
                    if(attributeType.equals("os")) {
                        nodeOs = attributeVal;
                    }
                }
                if(fileLine.contains("}")) { //Write the object and cleanup
                    if(nodeType.equals("hub")) {
                        controller.addHub(new HubNode(nodeName,nodeSubnet,nodeNetmask));
                    }
                    if(nodeType.equals("vm")) {
                        controller.addHostVM(new VM(nodeName,nodeOs,nodeIp));
                    }
                    nodeIp = new ArrayList<String>();
                    nodeName = "";
                    nodeSubnet = "";
                    nodeNetmask = "";
                    nodeOs="";
                    nodeType="";
                }

            }
        }
        catch(IOException e){
            System.err.println(e);
        }
        return controller;
    }
}