package root.UnitTests;

import root.Validation;
import root.networkobjects.VM;
import root.networkobjects.HubNode;
import root.networkobjects.VMinterface;

import java.util.ArrayList;
import java.util.List;

public class ValidationTest {
    public static void main(String[] args) {
        boolean test = false;

        // Create VM A instance for 192.168.0.0/24 subnet
        List<String> VmAIpAddresses = new ArrayList<String>();
        VmAIpAddresses.add("192.168.0.2");
        VmAIpAddresses.add("192.168.0.3");
        VM vmA = new VM("TestVM(A)", "LINUX", VmAIpAddresses);

        // Create VM B instance for 131.183.10.0/16 subnet
        List<String> VmBIpAddresses = new ArrayList<String>();
        VmBIpAddresses.add("131.183.10.2");
        VmBIpAddresses.add("131.183.10.3");
        VM vmB = new VM("TestVM(B)", "WINDOWS", VmBIpAddresses);

        // Create test Hub instance
        HubNode hubNode = new HubNode("TestHub0", "192.168.0.0", "255.255.255.0");

        //
        // Test VM interface to Hub matching
        //

        // Scenario: VM A is in the correct subnet for the Hub.
        System.out.println(
                "ValidationTest 0: Valiation.isValidInterfacePair(), (expected result: all succeed), scenario: matching subnets");
        for (VMinterface vmIface : vmA.getIntrfces()) {
            if (Validation.isValidInterfacePair(vmIface, hubNode)) {
                System.out.println("ValidationTest 0: Valiation.isValidInterfacePair(): SUCCESS on "
                        + vmA.getName() + ": " + vmIface.getIntrfcLabel() + ":" + vmIface.getIpAddress());
                test = true;
            } else {
                System.err.println("ValidationTest 0: Valiation.isValidInterfacePair(): FAILURE on "
                        + vmA.getName() + ": " + vmIface.getIntrfcLabel() + ":" + vmIface.getIpAddress());
                test = false;
                break;
            }
        }

        if (test == false)
            System.err.println("ValidationTest 0 failed (expected result: all succeed).");
        else
            System.out.println("ValidationTest 0 succeeded. (expected result: all succeed)");

        // Scenario: VM B is not in the correct subnet for the hub.
        test = false;
        System.out.println(
                "ValidationTest 1: Valiation.isValidInterfacePair(), (expected result: failure), scenario: non-matching subnets");
        for (VMinterface vmIface : vmB.getIntrfces()) {
            if (Validation.isValidInterfacePair(vmIface, hubNode)) {
                System.out.println("ValidationTest 1: Valiation.isValidInterfacePair(): SUCCESS on "
                        + vmB.getName() + ": " + vmIface.getIntrfcLabel() + ":" + vmIface.getIpAddress());
                test = true;
            } else {
                System.err.println("ValidationTest 1: Valiation.isValidInterfacePair(): FAILURE on "
                        + vmB.getName() + ": " + vmIface.getIntrfcLabel() + ":" + vmIface.getIpAddress());
                if (test != true)
                    test = false;
            }
        }

        if (test == true)
            System.err.println("ValidationTest 1 failed. (expected: failure).");
        else
            System.out.println("ValidationTest 1 succeeded. (expected: failure)");

        //
        // Test network address validation
        //

        // Scenario A: is a valid network
        test = false;
        System.out.println("ValidationTest 2: Valiation.isValidNetwork(), (expected: succeed), scenario: valid network");
        test = Validation.isValidNetwork("192.168.0.0", "255.255.0.0");

        if (test == true)
            System.out.println("ValidationTest 2 succeeded. (expected: succeed)");
        else
            System.err.println("ValidationTest 2 failed. (expected: succeed)");

        // Scenario B: is an invalid network (out of range IP address)
        test = false;
        System.out.println("ValidationTest 3: Valiation.isValidNetwork(), (expected: failure), scenario: our of range network");
        test = Validation.isValidNetwork("192.168.256.0", "255.255.0.0");

        if (test == true)
            System.err.println("ValidationTest 3 failed. (expected: failed)");
        else
            System.out.println("ValidationTest 3 succeeded. (expected: failed)");

        // Scenario C: is an invalid network (out of range IP address)
        test = false;
        System.out.println("ValidationTest 4: Valiation.isValidNetwork(), (expected: failure), scenario: out of range subnet");
        test = Validation.isValidNetwork("192.168.0.0", "255.256.0.0");

        if (test == true)
            System.err.println("ValidationTest 4 failed. (expected: failed)");
        else
            System.out.println("ValidationTest 4 succeeded. (expected: failed)");
    }

}