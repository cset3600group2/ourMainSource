package root.Gui.application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternConfig {
	// Regex used to parse various patterns of the config file
	static final String nodePattern = "(\\w+)\\s(\\w+)\\s\\{(?<=\\{)(.*?)(?=\\})";
	static final String osPattern = "os\\s?:\\s?(\\w+)";
	static final String verPattern = "ver\\s?:\\s?\"(\\d+(?:\\.?)\\d+)\"";
	static final String srcPattern = "src\\s?:\\s?\"((?:\\/\\w+)+)\"";
	static final String ethPattern = "(eth\\d)\\s?:\\s?\"((?:\\d{0,3}\\.?)+)\"";
	static final String subnetPattern = "subnet\\s?:\\s?\"((?:\\d{0,3}\\.?)+)\"";
	static final String netmaskPattern = "netmask\\s?:\\s?\"((?:\\d{0,3}\\.?)+)\"";
	static final String infPattern = "inf\\s?:\\s?(.*)";
	static final String IpPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final String hubInfPattern = "(\\w+).(\\w+?\\d+?.*)";
	// Coordinates for node positions and size
	public static int nodeLength = 100;
	public static int nodeWidth = 100;
	public static int vlanstartPosX = 50;
	public static int vlanstartPosY = 50;
	public static int hubStartPosX = 250;
	public static int hubStartPosY = 50;
	public static int vmStartPosX = 40;
	public static int vmStartPosY = 50;
	// Hash maps containing vm's and hubs
	public static LinkedHashMap<String, VM> vmMap = new LinkedHashMap<String, VM>();
	public static LinkedHashMap<String, HUB> hubMap = new LinkedHashMap<String, HUB>();
	
	public static int getIPClass(String hubNetmask) {
		int ipClass = 0;
		Pattern patt = Pattern.compile(IpPattern);
		Matcher match = patt.matcher(hubNetmask);
		
		if(match.find()) {
			for(int i = 1; i < 5; i++) {
				if(Integer.parseInt(match.group(i).toString()) > 0) {
					ipClass++;
				}
			}
		}
		return ipClass;
	}
	
	public static LinkedHashMap<String, VM> replaceVMKey(LinkedHashMap<String, VM> map, String oldKey, String newKey){
		LinkedHashMap<String, VM> temp = map;
		LinkedHashMap<String, VM> newMap = new LinkedHashMap<String, VM>();
		VM oldValue = temp.get(oldKey);
		for(Map.Entry<String, VM> entry : temp.entrySet()) {
			if(entry.getKey().equals(oldKey)) {
				newMap.put(newKey, oldValue);
			}else {
				newMap.put(entry.getKey(), entry.getValue());
			}
		}
		return newMap;
	}
	
	public static LinkedHashMap<String, HUB> replaceHUBKey(LinkedHashMap<String, HUB> map, String oldKey, String newKey){
		LinkedHashMap<String, HUB> temp = map;
		LinkedHashMap<String, HUB> newMap = new LinkedHashMap<String, HUB>();
		HUB oldValue = temp.get(oldKey);
		for(Map.Entry<String, HUB> entry : temp.entrySet()) {
			if(entry.getKey().equals(oldKey)) {
				newMap.put(newKey, oldValue);
			}else {
				newMap.put(entry.getKey(), entry.getValue());
			}
		}
		return newMap;
	}
	
	public static void clearData() {
		vmMap.clear();
		hubMap.clear();
	}
}
