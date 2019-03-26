package root;

import javafx.collections.ObservableList;

import java.io.*;
import java.util.Iterator;



public class FileController {



    public static String readFile(File inFile) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new FileReader(inFile));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text + System.getProperty("line.separator"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();
    }

    public static void writeFile(File outFile, ObservableList<CharSequence> paragraph) {
        Iterator<CharSequence> iter = paragraph.iterator();
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File(outFile.toString())));
            while (iter.hasNext()) {
                CharSequence seq = iter.next();
                bf.append(seq);
                bf.newLine();
            }
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileOpenConf() {
        //TODO
    }

    public static void fileSaveAsConf() {
        /*TODO
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".cfg", "*.cfg"));
        File selectedFile = fileChooser.showSaveDialog(null);
        controller.MyController.currentFile = selectedFile;
        return selectedFile;
        */

    }
}
