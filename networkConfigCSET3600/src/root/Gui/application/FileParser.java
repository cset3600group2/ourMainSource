package root.Gui.application;

import java.io.File;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
	public String singleMatcher(String regex, String input) {
		//finds a match and outputs it as a group
		String output = null;
		Pattern patt = Pattern.compile(regex);
		Matcher match = patt.matcher(input);
		if (match.find()) {
			output = match.group(1);
		}
		return output;
	}

	public void ethMatcher(String vm, String regex, String input) {
		//find and match eth interface
		Pattern patt = Pattern.compile(regex);
		Matcher match = patt.matcher(input);
		TreeMap<String, String> interfaces = new TreeMap<String, String>();
		while (true) {
			if (match.find()) {
				interfaces.put(match.group(1), match.group(2));
			} else {
				break;
			}
		}
		PatternConfig.vmMap.get(vm).setInterfaces(interfaces);
	}

	public void infMatcher(String hub, String regex, String input) {
		//find and match inf hub
		Pattern linePatt = Pattern.compile(regex);
		Matcher lineMatch = linePatt.matcher(input);
		if (lineMatch.find()) {
			Pattern innerPatt = Pattern.compile("(\\w+\\.\\w+)");
			Matcher innerMatch = innerPatt.matcher(lineMatch.group(1));
			while (true) {
				if (innerMatch.find()) {
					PatternConfig.hubMap.get(hub).addInf(innerMatch.group(1));
				} else {
					break;
				}
			}
		}
	}

	public FileParser(File selectedFile) {
		String file = null;

		// if the config file being opened has PatternConfig
		// parse it
		if (selectedFile != null) {
			file = FileControls.readFile(selectedFile);
		}

		// parses the file for each entry
		Pattern patt = Pattern.compile(PatternConfig.nodePattern, Pattern.DOTALL);
		Matcher match = patt.matcher(file);
		while (true) {
			// match loop
			if (match.find()) {
				
				if (match.group(1).equals("vm")) {
					// if the node is a vm
					// create vm
					PatternConfig.vmMap.put(match.group(2), new VM());
					// parse and set the vm's name
					PatternConfig.vmMap.get(match.group(2)).setName(match.group(2));
					// parse and set the vm's os
					PatternConfig.vmMap.get(match.group(2)).setOs(singleMatcher(PatternConfig.osPattern, match.group(3)));
					// parse and set the vm's ver
					PatternConfig.vmMap.get(match.group(2))
							.setVer(Double.parseDouble(singleMatcher(PatternConfig.verPattern, match.group(3))));
					// parse and set the vm's src
					PatternConfig.vmMap.get(match.group(2)).setSrc(singleMatcher(PatternConfig.srcPattern, match.group(3)));
					// parse and set the vm's eth(s)
					ethMatcher(match.group(2), PatternConfig.ethPattern, match.group(3));
				
					PatternConfig.vmMap.get(match.group(2)).getInterfaces();
					} else if (match.group(1).equals("hub")) {
					// if the node is a hub
					// create a new hub
					PatternConfig.hubMap.put(match.group(2), new HUB());
					// parse and set the hubs name
					PatternConfig.hubMap.get(match.group(2)).setName(match.group(2));
					// parse and set the hubs subnet
					PatternConfig.hubMap.get(match.group(2)).setSubnet(singleMatcher(PatternConfig.subnetPattern, match.group(3)));
					// parse and set the hubs netmask
					PatternConfig.hubMap.get(match.group(2)).setNetmask(singleMatcher(PatternConfig.netmaskPattern, match.group(3)));
					// parse and set the hubs inf(s)
					infMatcher(match.group(2), PatternConfig.infPattern, match.group(3));

				
					
				}
			} else {
				
				break;
			}
		}
	}
}
