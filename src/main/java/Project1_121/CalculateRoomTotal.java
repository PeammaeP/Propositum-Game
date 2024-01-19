package Project1_121;
        
class CalculateRoomTotal implements Comparable<CalculateRoomTotal>{
    private String roomName ; //Store room name
    private int roomNight; // Store room night
    private double roomPrice; // Store room price
    
    @Override
    public int compareTo(CalculateRoomTotal other){
        if (this.roomNight < other.roomNight) return 1;
        else if(this.roomNight == other.roomNight) return 0;
        else return -1;
    }

    public CalculateRoomTotal(String name, int night, double price) {
       roomName = name;
       roomNight = night;
       roomPrice = price;
    }
    
    public double calculationPrice (double price) { double result = roomNight * price * 1.10 * 1.07; return result; }
    public void print() {
        System.out.printf("%-20s total sales = %5d rooms %,15.2f Baht\n", roomName, roomNight, calculationPrice(roomPrice));
    }

}
