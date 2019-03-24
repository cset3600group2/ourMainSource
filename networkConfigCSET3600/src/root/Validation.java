package root;

import java.util.regex.Pattern;


//import root.Gui.application.VM; not used: other derivation of vm
import root.networkobjects.VM;
import root.networkobjects.HubNode;
import root.networkobjects.VMinterface;
import root.networkobjects.HubInterface;

public class Validation {
    private static final Pattern ipv4Pattern =
            Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static int addressToInt(String address) {
        int value = 0;

        String[] array = address.split("\\.");

        for (int i = 0; i <= 3; i++) {
            long octet = Integer.parseInt(array[3 - i]);

            value |= (octet << (i * 8));
        }

        return value;
    }

    private static int extractNetworkAddressInt(int address, int mask)
    {
        // Extract the network address by ANDing.
        return (address & mask);
    }

    private static boolean isIPv4(String address)
    {
        return ipv4Pattern.matcher(address).matches();
    }

    private static boolean isSubnetMask(String subnetMask)
    {
        // Reverse the order of bits to cancel out trailing 0s.
        long mask = Integer.reverse((addressToInt(subnetMask)));

        // All 0s and all (32) 1s address is invalid.
        if ((mask == 0) || (mask == 0xFFFFFFFF))
            return false;

        // If the address contains any 0s at this point it is invalid.
        while (mask != 0) {
            if ((mask & 1) == 0)
                return false;
            mask >>= 1;
        }

        return true;
    }

    public static boolean isNetworkAddress(String address, String subnetMask)//for hubs ensures a network address is added
    {
        int addr = addressToInt(address);
        int mask = addressToInt(subnetMask);
        int networkAddr = extractNetworkAddressInt(addr, mask);

        return (addr == networkAddr);
    }

    public static boolean isValidNetwork(String networkAddress, String subnetMask)//for hubs
    {
        // First check if supplied values are valid in IPv4.
        if (!isIPv4(networkAddress)) {
            System.err.println("Network address not in IPv4 format: " + networkAddress);

            return false;
        }
        if (!isIPv4(subnetMask)) {
            System.err.println("Subnet mask not in IPv4 format: " + subnetMask);

            return false;
        }

        // Check that we have a valid subnet mask
        if (!isSubnetMask(subnetMask)) {
            System.err.println("Subnet mask is not a valid value: " + subnetMask);

            return false;
        }

        // Check if we were passed a real network address
        if (!isNetworkAddress(networkAddress, subnetMask)) {
            System.err.println("Address supplied is not a valid network address: " + networkAddress);

            return false;
        }

        return true;
    }

    public static boolean isValidInterfacePair(VMinterface vmIface, HubInterface hubIface)
    {
        // First check that the VM interface has a valid IPv4 address.
        if (!isIPv4(vmIface.getIpAddress())) {
            System.err.println("Invalid VM address assigned to interface " + vmIface.getIntrfcLabel() + ": " +
                    vmIface.getIpAddress());

            return false;
        }

        // Extract network addresses from VM and Hub interfaces.
        int vmAddr = addressToInt(vmIface.getIpAddress());
        int hubMask = addressToInt(hubIface.getNetMask());
        int hubSubnet = addressToInt(hubIface.getSubnet());
        int vmNetAddr = extractNetworkAddressInt(vmAddr, hubMask);
        int hubNetAddr = extractNetworkAddressInt(hubSubnet, hubMask);

        // Check that the VM is connecting to a Hub in the same subnet.
        if (vmNetAddr != hubNetAddr) {
            System.err.println("VM interface address " + vmIface.getIpAddress() + " and Hub interface subnet " +
                    hubIface.getSubnet() + " with mask " + hubIface.getNetMask() + " do not match.");

            return false;
        }

        return true;
    }









    //added source

    public static boolean checkName(String name) {//checks if name is taken
        boolean valid = true;
        try {
            if(name.trim().isEmpty()) {
                valid = false;
            }else {
                //check every hub
                for(int i = 0; i<(Main.nodeController.getHubNodes().size()); i++) {
                    HubNode currentHub = Main.nodeController.getHubNodes().get(i);
                    if(currentHub.getName().toLowerCase().equals(name.trim().toLowerCase())) {
                        valid = false;
                    }
                }
                //check every vm
                for(int i = 0; i<(Main.nodeController.getCurrentVms().size()); i++) {
                    VM currentVM = Main.nodeController.getCurrentVms().get(i);
                    if(currentVM.getName().toLowerCase().equals(name.trim().toLowerCase())) {
                        valid = false;
                    }
                }
            }
        }catch(NullPointerException e) {
            System.out.println("Please enter a String");
            valid = false;
        }
        return valid;
    }

    /* TO BE ANALYZED



    //make sure ip is not taken also make sure its in the range of 255.255.255.255
    public static boolean checkIp(String ip) {
        boolean valid = true;
        try {
            //check to make sure the ip is in the range of 255.255.255.255
            Pattern ipPattern = Pattern.compile(PatternConfig.IpPattern);
            Matcher matcher = ipPattern.matcher(ip.trim());

            if(matcher.find()) {
                //if the ip is at least with the range of 255.255.255.255
                //make sure it's not a hub subnet
                for(Map.Entry<String, HUB> hubEntry : PatternConfig.hubMap.entrySet()) {
                    HUB currentHub = hubEntry.getValue();
                    if(currentHub.getSubnet().equals(ip.trim())) {
                        valid = false;
                    }
                }
                //make sure it's not a vm interface
                for(Map.Entry<String, VM> vmEntry : PatternConfig.vmMap.entrySet()) {
                    VM currentVM = vmEntry.getValue();
                    for(Map.Entry<String, String> infEntry: currentVM.getInterfaces().entrySet()) {
                        String currentIp = infEntry.getValue();
                        if(currentIp.equals(ip.trim())) {
                            valid = false;
                        }
                    }
                }
            }else {
                valid = false;
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }
    //make sure netmask is in the range of 255.255.255.255
    public static boolean checkNetmask(String netmask) {
        boolean valid = true;
        try {
            Pattern ipPattern = Pattern.compile(PatternConfig.IpPattern);
            Matcher matcher = ipPattern.matcher(netmask.trim());

            if(!matcher.find()) {
                valid = false;
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }
    //make sure ver is a double
    public static boolean checkVer(String ver) {
        boolean valid = true;
        try {
            Double.parseDouble(ver.trim());
        }catch(NumberFormatException e) {
            System.out.println("Not an integer");
            valid = false;
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }
    //make sure src is valid
    public static boolean checkSrc(String src) {
        boolean valid;
        try {
            if(!src.trim().equals("/srv/VMLibrary/JeOS")) {
                valid = false;
            }else {
                valid = true;
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }
    //make sure Os field is not empty
    public static boolean checkOs(String os) {
        boolean valid = false;
        try {
            List<String> validOs = Arrays.asList("LINUX", "WINDOW", "UNIX");
            for(String item : validOs) {
                if(item.equals(os.trim())) {
                    valid = true;
                }
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }
    //make sure vlan field is not empty
    public static boolean checkVlan(String vlan) {
        boolean valid = true;
        try {
            if(vlan.trim().isEmpty()) {
                valid = false;
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }

    public static boolean checkHubInf(String inf) {
        boolean valid = false;
        try {
            //check for it's format
            Pattern pat = Pattern.compile(PatternConfig.hubInfPattern);
            Matcher matcher = pat.matcher(inf.trim());
            if(matcher.find()) {
                //check the vm object appointed to the given inf name
                VM vmObject = PatternConfig.vmMap.get(matcher.group(1));
                if(vmObject.getInterfaces().containsKey(matcher.group(2))) {
                    //the number of octets for hub interface to match vm ip
                    valid = true;
                }
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }

    public static boolean checkSubnetting(String hubNetmask, String hubInf, String hubSubnet) {
        boolean valid = true;
        try {
            int ipClass = PatternConfig.getIPClass(hubNetmask);
            Pattern hubInfPat = Pattern.compile(PatternConfig.hubInfPattern);
            Matcher hubInfMatcher = hubInfPat.matcher(hubInf);

            Pattern ipPat = Pattern.compile(PatternConfig.IpPattern);
            if(hubInfMatcher.find()) {
                String vmInf = PatternConfig.vmMap.get(hubInfMatcher.group(1)).getInterfaces().get(hubInfMatcher.group(2));

                Matcher hubSubnetMatcher = ipPat.matcher(hubSubnet);
                Matcher vmInfMatcher = ipPat.matcher(vmInf);

                if(hubSubnetMatcher.find() && vmInfMatcher.find()) {
                    for(int i = 1; i <= ipClass; i++) {
                        if(!hubSubnetMatcher.group(i).equals(vmInfMatcher.group(i))) {
                            valid = false;
                        }
                    }
                }
            }
        }catch(NullPointerException e) {
            valid = false;
        }
        return valid;
    }
      */

}
