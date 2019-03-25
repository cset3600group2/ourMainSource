package root.networkobjects;


public class VMinterface { //Holds interface label and its ip address
    private String intrfcLabel; //i.e. eth0
    private String ipAddress;



    public VMinterface(String intrfcLabel, String ipAddress){
        this.intrfcLabel = intrfcLabel;
        this.ipAddress = ipAddress;

    }

    //add method for checking if they're on same subnet to add to pair

    //add method for removing pair when object is removed
    public void setIntrfclabel(String intrfcLabel){
        this.intrfcLabel = intrfcLabel;

    }
    public String getIntrfcLabel(){
        return this.intrfcLabel;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }
    public String getIpAddress(){
        return this.ipAddress;
    }



}
