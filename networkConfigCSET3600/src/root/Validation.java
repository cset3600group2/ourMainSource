package root;

import java.util.regex.Pattern;

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

    public static boolean isNetworkAddress(String address, String subnetMask)
    {
        int addr = addressToInt(address);
        int mask = addressToInt(subnetMask);
        int networkAddr = extractNetworkAddressInt(addr, mask);

        return (addr == networkAddr);
    }

    public static boolean isValidNetwork(String networkAddress, String subnetMask)
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
}
