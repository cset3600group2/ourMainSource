package root.networkobjects;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class VM {
    private String name; //vm alias i.e. host1
    private String os;
    private String ver;
    private String src;

    private List<VMinterface> intrfces = new List<VMinterface>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<VMinterface> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(VMinterface vMinterface) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends VMinterface> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends VMinterface> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public VMinterface get(int index) {
            return null;
        }

        @Override
        public VMinterface set(int index, VMinterface element) {
            return null;
        }

        @Override
        public void add(int index, VMinterface element) {

        }

        @Override
        public VMinterface remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<VMinterface> listIterator() {
            return null;
        }

        @Override
        public ListIterator<VMinterface> listIterator(int index) {
            return null;
        }

        @Override
        public List<VMinterface> subList(int fromIndex, int toIndex) {
            return null;
        }
    };//contains a list of interface labels and their ip addresses


    VM(String name, String os, List<String> ipAddresses) {//passed from gui form

        for (String ipAddress : ipAddresses) { //for each valid ip address submitted in the form, add an interface
            String intrfcLabel = "eth" + ipAddress.indexOf(ipAddress);
            this.intrfces.add(new VMinterface(intrfcLabel, ipAddress));
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


