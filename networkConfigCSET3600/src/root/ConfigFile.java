package root;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


import root.networkobjects.*;

import static root.Validation.isValidInterfacePair;

public class ConfigFile {
    public static final String TAB = "        ";



    public static String writeFile(NodeController controller) {
        String path = ".\\test.cfg";
        String outputText = "";

        //TODO Figure out how the Partial Solution works
        //No idea why it's V2 or the number starts at 21, but this will make it match our example
        String partialSolution = "";
        int partialNumber = 21;

        for (VM vmObj : controller.getCurrentVms()) {
            outputText += "vm " + vmObj.getName() + " {\n" +
                    TAB+"os : " + vmObj.getOs() + "\n" +
                    TAB+"ver : \"" + vmObj.getVer() + "\"\n" +
                    TAB+"src : \"" + vmObj.getSrc() + "\"\n";
            for (VMinterface iface : vmObj.getIntrfces()) {
                outputText += TAB+iface.getIntrfcLabel()+" : \""+iface.getIpAddress()+"\"\n";
                //TODO: Figure out how to match this to a partial solution
            }
            outputText += "}\n\n";
        }
        for (HubNode hubObj : controller.getHubNodes()) {

            //Get inf for the hub
            String infList = "";
            for(VM vmObj : controller.getCurrentVms()) {
                for(VMinterface ifaceVm : vmObj.getIntrfces()) {

                    //Hub doesn't currently have an interface, so we'll make one here
                    HubInterface ifaceHub = new HubInterface();
                    ifaceHub.setNetMask(hubObj.getNetmask());
                    ifaceHub.setSubnet(hubObj.getSubnet());

                    if(isValidInterfacePair(ifaceVm,ifaceHub)) {
                        infList += vmObj.getName() + "." + ifaceVm.getIntrfcLabel() + ",";
                        partialSolution += TAB+"("+vmObj.getName()+"."+ifaceVm.getIntrfcLabel()+" "+"V2.vinf" + partialNumber + "),\n";

                    }
                }
            }
            infList = infList.substring(0,infList.length() - 1); //Remove last comma from list

            outputText += "hub " + hubObj.getName() + " {\n" +
                    TAB+"inf : "+ infList + "\n" +
                    TAB+"subnet : \"" + hubObj.getSubnet() + "\"\n" +
                    TAB+"netmask : \"" + hubObj.getNetmask() + "\"\n";
            outputText += "}\n\n";
            partialNumber++;
        }

        partialSolution = partialSolution.substring(0,partialSolution.length() - 2); //Remove last comma and line break from list
        outputText += "partial_solution {\n"+partialSolution+"\n}";


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

            //Show to console for debugging
            System.out.print(outputText);
            return outputText;
        }
        catch (FileNotFoundException ex) {
            System.out.print(ex);
            return "Error "+ex;
        }
    }
}