package root.Gui.application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.scene.control.TextArea;

public class FileWriter {
	public static void writeFile(TextArea textEditor) {
		textEditor.clear();
		for(Map.Entry<String, VM> entry : PatternConfig.vmMap.entrySet()) {
			textEditor.appendText(vmWriter(entry.getValue()));
		}
		
		for(Map.Entry<String, HUB> entry : PatternConfig.hubMap.entrySet()) {
			textEditor.appendText(hubWriter(entry.getValue()));
		}
		
		if(!PatternConfig.hubMap.isEmpty()) {
			textEditor.appendText(solutionWriter(PatternConfig.hubMap));
		}
	}
	
	private static String hubWriter(HUB hubObject) {
		//this method handle the formatting of a single hub object
		String objectText = "hub " + hubObject.getName() + " {\n" + 
								"\tinf : " + hubInf(hubObject.getInfs()) + "\n" +
								"\tsubnet : \"" + hubObject.getSubnet() + "\"\n" +
								"\tnetmask : \"" + hubObject.getNetmask() + "\"\n" + 
								"}\n\n"; 
		return objectText;
	}
	
	private static String hubInf(TreeSet<String> hubInf) {
		//handles the formatting of the inf values
		String inf = "";
		for(String item : hubInf) {
			if(item.equals(hubInf.last())) {
				inf += item;
			}else {
				inf += item + ", ";
			}
		}
		return inf;
	}
	
	private static String vmWriter(VM vmObject) {
		//this method handle the formatting of a single hub object
		String objectText = "vm " + vmObject.getName() + " {\n" + 
								"\tos : " + vmObject.getOs() + "\n" +
								"\tver : " + "\"" + vmObject.getVer().toString() + "\"" + "\n" +
								"\tsrc : \"" + vmObject.getSrc() + "\"\n" +
								vmInf(vmObject.getInterfaces()) +
								"}\n\n"; 
		return objectText;
	}
	
	private static String vmInf(TreeMap<String, String> vmInf) {
		String inf = "";
		for(Map.Entry<String, String> item : vmInf.entrySet()) {
			inf += "\t" + item.getKey() + " : \"" + item.getValue() + "\"\n";
		}
		return inf;
	}
	
	private static String solutionWriter(LinkedHashMap<String,HUB> hubMap) {
		String solution = "partial_solution {\n";
		int vinfIndex = 21;
		for(Map.Entry<String, HUB> hubEntry : hubMap.entrySet()) {
			HUB currentHub = hubEntry.getValue();
			for(String inf : currentHub.getInfs()) {
				if(inf.equals(currentHub.getInfs().last())) {
					solution += "(" + inf + " v2.inf" + vinfIndex + ")\n";
				}else {
					solution += "(" + inf + " v2.inf" + vinfIndex + "),\n";
				}
			}
			vinfIndex++;
		}
		solution += "}";
		return solution;
	}
	
	
}
