package Project1_121;

import java.lang.reflect.Array;
import java.util.*;

class Booking extends Customer{
    private int bookingID, nights;
    private int [] roomNight ;
    private int mealPerson;
    private double totalRoomPrice=0,totalMealPrice=0,finalBill=0;
    private double[] rateRoom = {0.0};
    private double rateMeal;
    private String[] roomName = {""};
    private String mealName;
    private ArrayList<Customer> customerArrayList = new ArrayList<>();

    public Booking(int bookingID,String name,int nights,int [] roomNight,int mealPerson,int cashback, ArrayList<Room> hotelData) {
        super(name,cashback);
        this.bookingID = bookingID;
        this.nights = nights;
        this.mealPerson = mealPerson;
        //It should declare the array before attempting to copy and access key and values of it
        this.roomNight = new int[roomNight.length]; //concern of NullPointerException
        for(int i=0;i<roomNight.length;i++) {
            this.roomNight[i] = roomNight[i];
        }

        int round = 0;
        for(Room data: hotelData ) {
            if(data instanceof Meal) {
                this.rateMeal = ((Meal)data).getRate(); // Assign Rate Meal
                this.mealName = ((Meal)data).getType(); // Assign Meal Type
            }
            else{
                if(round == 0){
                    this.rateRoom[0] = data.getRate();
                    this.roomName[0] = data.getName();
                }
                else {
                    this.rateRoom = Arrays.copyOf(this.rateRoom, this.rateRoom.length + 1); //Add 1 more space
                    this.rateRoom[this.rateRoom.length - 1] = data.getRate(); // Assign value to the lastest element of rateRoom
                    this.roomName = Arrays.copyOf(this.roomName, this.roomName.length + 1); //Add 1 more space
                    this.roomName[this.roomName.length - 1] = data.getName(); // Assign value to the lastest element of roomName
                }
            }
            round++; // Increase round by 1
        }
    }

    //Set function
    public void setTotalRoomPrice(double totalRoomPrice) {this.totalRoomPrice = totalRoomPrice; }
    public void setTotalMealPrice(double totalMealPrice) {this.totalMealPrice = totalMealPrice; }
    public void setFinalBill(double finalBill) {this.finalBill = finalBill; }
    public void setCashback(double cashback) {this.cashback = cashback; }

    //Get function
    public int getRoomNight(int i) { return roomNight[i]; }
    public int[] getAllRoomNight() { return roomNight; }
    public int getMealPerson() { return mealPerson; }
    public String getName() { return name; }
    public int getBookingID() { return bookingID; }
    public double getTotalRoomPrice() { return totalRoomPrice; }
    public double getTotalMealPrice() { return totalMealPrice; }
    public double getFinalBill() { return finalBill; }
    public double getCashback() { return cashback; }
    public int getNights() { return nights; }
    public double getRateRoom(int i) { return rateRoom[i]; }
    public double[] getAllRateRoom() { return  rateRoom; }
    public double getRateMeal() { return rateMeal; }
    public String getRoomName(int i) { return roomName[i]; }
    public String[] getAllRoomName() { return roomName; }
    public String getMealName() { return mealName; }

    //3.1 Calculate calculateRoomPrice
    public double calculateTotalRoomPrice(ArrayList<Booking> bookingArrayList, double[] rateRoom,int bookingID) {
        final double vat = 1.07, serviceCharge = 1.10;
        double totalRoomPrice = 0;

        for (int i = 0; i < bookingArrayList.size(); i++) {
            Booking booking = bookingArrayList.get(i);

            // Calculate the total room price for the current booking
            if(bookingArrayList.get(i).getBookingID()==(bookingID)) {
                double bookingRoomPrice = 0;
                for (int j = 0; j < roomNight.length; j++) {
                    bookingRoomPrice = booking.getRoomNight(j) * rateRoom[j] * booking.getNights();
                    totalRoomPrice += bookingRoomPrice;
                }
            }
        }

        totalRoomPrice = (totalRoomPrice * serviceCharge) * vat;

        return totalRoomPrice;
    }


    //Update total room price
    public void updateTotalRoomPrice(ArrayList<Booking> bookingArrayList,double totalRoomPrice,int bookingID) {
        //Set total room price
        for(int i=0;i<bookingArrayList.size();i++) {
            Booking booking = bookingArrayList.get(i);
            if(bookingArrayList.get(i).getBookingID()==(bookingID)) {
                booking.setTotalRoomPrice(totalRoomPrice);
                bookingArrayList.set(i, booking);
            }
        }
    }

    //3.2 Calculate meal price for all night
    public double calMealPrice(ArrayList<Booking> bookingArrayList,double rateMeal,int bookingID) {
        double mealPrice;
        for(int i=0;i<bookingArrayList.size();i++){
            int getID = bookingArrayList.get(i).getBookingID();
            if(getID==bookingID) {
                mealPrice = (bookingArrayList.get(i).getMealPerson() * rateMeal) * bookingArrayList.get(i).getNights();
                return mealPrice;
            }
        }
        return 0;
    }

    //Update total meal price
    public void updateTotalMealPrice(ArrayList<Booking> bookingArrayList,double totalMealPrice,int bookingID) {
        for(int i=0;i<bookingArrayList.size();i++) {
            Booking booking = bookingArrayList.get(i);
            if(bookingArrayList.get(i).getBookingID()==(bookingID)) {
                booking.setTotalMealPrice(totalMealPrice);
                bookingArrayList.set(i, booking);
            }
        }
    }


    //3.3 Calculate total bill
    public double calTotalBill(ArrayList<Booking> bookingArrayList,double [] rateRoom, double rateMeal,int bookingID) {
        return calculateTotalRoomPrice(bookingArrayList,rateRoom,bookingID) + calMealPrice(bookingArrayList,rateMeal,bookingID);
    }

    //3.4 Check customer's cashback
    public double checkCustomerCashback(ArrayList<Customer> customerArrayList,String name,double totalBill) {
        int maxCashback = (int) (totalBill/2);
            for (int i = customerArrayList.size() - 2; i >= 0; i--) {
                if (customerArrayList.get(i).getName().equalsIgnoreCase(name)) {
                    double customerCashback = customerArrayList.get(i).getCashback();
                    if (customerCashback > maxCashback) {
                        customerCashback -= maxCashback;
                        Customer customer = customerArrayList.get(i);
                        customer.setCashback(customerCashback);
                        customerArrayList.set(i, customer);
                        return maxCashback;
                    } else {
                        updateCustomerCashback(customerArrayList, name, 0);
                        return customerCashback;
                    }
                }
            }
        customerArrayList.add(new Customer(name, 0));
        return 0;
    }

    //Update customer cashback
    public void updateCustomerCashback(ArrayList<Customer> inCustomerArrayList,String name, double customerCashback){
        for(int i=0;i<inCustomerArrayList.size();i++) {
            Customer customer = inCustomerArrayList.get(i);
            if(inCustomerArrayList.get(i).getName().equalsIgnoreCase(name)) {
                customer.setCashback(customerCashback);
                inCustomerArrayList.set(i, customer);
            }
        }
    }
    
    public double checkRemainCustomerCashback(ArrayList<Customer> customerArrayList,String name,double totalBill) {
        for (int i = customerArrayList.size() - 2; i >= 0; i--) {
            if(customerArrayList.get(i).getName().equalsIgnoreCase(name)){
            return customerArrayList.get(i).getCashback();
            }
        }
        return 0;
    }
    //3.5 Calculate final bill
    public double calFinalBill(ArrayList<Booking> bookingArrayList,double totalBill,double cashbackRedeem,int bookingID){
        double finalBill = totalBill - cashbackRedeem;

        //Set final bill
        for(int i=0;i<bookingArrayList.size();i++) {
            Booking booking = bookingArrayList.get(i);
            if(bookingArrayList.get(i).getBookingID()==(bookingID)) {
                booking.setFinalBill(finalBill);
                bookingArrayList.set(i, booking);
            }
        }

        return finalBill;
    }

    //3.6 Calculate customer's cashback
    public double updateCashback(ArrayList<Customer> inCustomerArrayList,int inCashback,String name) {
        int updateCashback = inCashback;
        for (int i = 0; i < inCustomerArrayList.size(); i++) {
            Customer customer = inCustomerArrayList.get(i);
            if (inCustomerArrayList.get(i).getName().equalsIgnoreCase(name)) {
                updateCashback += inCustomerArrayList.get(i).getCashback();
                customer.setCashback(updateCashback);
                inCustomerArrayList.set(i, customer);
                return updateCashback;
            }
        }
        return updateCashback;
    }

    //3.7 Print
    public void print(ArrayList<Booking> bookingArrayList, double totalRoomPrice,double totalMealPrice,double finalBill,double totalCashback,double myTotalBill,int bookingID, double remainCashback, double finalCashback) {

        for (int i = 0; i < bookingArrayList.size(); i++) {
            if(bookingArrayList.get(i).getBookingID()==bookingID) {
                Booking booking = bookingArrayList.get(i);

                System.out.printf("Booking %2d , %s, %2d nights    ", booking.getBookingID(), booking.getName(), nights);
                System.out.printf(">> %-15s(%2d)   %-15s(%2d)   %-15s(%2d)   %-15s(%2d)   %-10s(%2d) \n", roomName[0], roomNight[0], roomName[1], roomNight[1], roomName[2], roomNight[2], roomName[3], roomNight[3], mealName, mealPerson);
                System.out.printf("Available Cashback = %-,11.0f ", remainCashback);
                System.out.printf(">> %-20s = %,10.2f     with service charge and VAT\n","Total room price++", totalRoomPrice);
                System.out.printf("                                 >> %-20s = %,10.2f\n","Total meal price", totalMealPrice);
                System.out.printf("                                 >> %-20s = %,10.2f     redeem = %,4.0f\n","Total bill", myTotalBill, totalCashback);
                System.out.printf("                                 >> %-20s = %,10.2f     cashback for next booking = %,4.0f \n\n","Final bill", finalBill, finalCashback);


            }
        }
    }
}