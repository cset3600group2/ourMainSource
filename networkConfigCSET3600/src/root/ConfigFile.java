package root;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


import root.networkobjects.*;

public class ConfigFile {
    public static String writeFile(NodeController controller) {
        String path = ".\\test.cfg";
        String outputText = "";
        List<VM> hosts = controller.getCurrentVms();
        for (VM node : hosts) {
            outputText += "vm " + node.getName() + " {\n" +
                    "        os : " + node.getOs() + "\n" +
                    "        ver : \"" + node.getVer() + "\"\n" +
                    "        src : \"" + node.getSrc() + "\"\n" +
                    //TODO: Handle Eth addresses

                    "}\n\n";
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