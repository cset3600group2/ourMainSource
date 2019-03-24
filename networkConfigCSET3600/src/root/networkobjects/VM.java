package root.networkobjects;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class VM {
    private String name; //vm alias i.e. host1
    private String os;
    private String ver;
    private String src;

    private List<VMinterface> intrfces = new ArrayList<VMinterface>();//contains a list of interface labels and their ip addresses


    public VM(String name, String os, List<String> ipAddresses) {//passed from gui form

        this.name = name;
        this.os = os;

        int ethNumber = 0;
        for (String ipAddress : ipAddresses) { //for each valid ip address submitted in the form, add an interface
            String intrfcLabel = "eth" + ethNumber;
            this.intrfces.add(new VMinterface(intrfcLabel, ipAddress));
            ethNumber++;
        }

        //same version and src set for every vm: dependent on OS
        switch (os) {
            case "WINDOWS":
                this.setOs("WINDOWS");
                break;
            case "LINUX":
                this.setOs("LINUX");
                break;
        }
        if (this.getOs() == "WINDOWS") {
            this.setVer("7.0");
            this.setSrc("/srv/VMLibrary/win7");
        } else {
            this.setVer("7.3");
            this.setSrc("/srv/VMLibrary/JeOS");
        }
    }


    public void addVMinterfPair(String intrfcLabel, String ipAddress) { //adds another interface, triggered from gui form event

        this.intrfces.add(new VMinterface(intrfcLabel, ipAddress));
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    } // this.name = "windowsorlinuxHost"; TO BE CHANGED based on gui action event

    public String getVer() {
        return this.ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public List<VMinterface> getIntrfces(){
        return this.intrfces;
    }



}


