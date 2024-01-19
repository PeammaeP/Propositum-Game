package Project1_121;

class Room {
    private String roomName;
    private double roomRate, roomRatepp;
    
    //For Meal
    public Room(){}
    
    //For Room
    public Room(String roomName, double roomRate, double roomRatepp){
        this.roomName = roomName;
        this.roomRate = roomRate;
        this.roomRatepp = roomRatepp;
    }
    
    public String getName(){ return roomName;}
    public double getRate(){ return roomRate;}
                  
    public void printData(){
        System.out.printf("%-16s     rate = %,8.2f     rate++ = %,8.2f\n", roomName, roomRate, roomRatepp);
    }
}
