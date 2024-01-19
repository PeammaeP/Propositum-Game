package Project1_121;

//6513111 Chutiya Thanaluck
//6513121 Supakorn Thavornvong
//6513123 Anapat Kitsommart
//6513136 Mahannop Thabua

import java.util.*;

class ProjectBookingHotel {
    public static void main(String args[]){
        //Run code
        ReadFile a = new ReadFile();
        ArrayList<Booking> mainArrayList = a.readBookingFile(a.readHotelFile());
        ArrayList<Customer> customerArrayList = new ArrayList<>();
        
        System.out.println("===== Booking Processing =====");
        //Son's code
        Booking [] booking = new Booking[mainArrayList.size()];

        //Assign booking
        for(int i=0;i<mainArrayList.size();i++){
            booking[i] = mainArrayList.get(i);
        }

        for(int i=0;i<mainArrayList.size();i++){
            double totalRoomPrice, totalMealPrice, finalBill, totalCashback,myTotalBill;
            double [] rateRoom = new double[4];

            for(int j=0;j<4;j++){
                rateRoom[j] = mainArrayList.get(i).getRateRoom(j);
            }

            totalRoomPrice = booking[i].calculateTotalRoomPrice(mainArrayList, rateRoom, booking[i].getBookingID());
            booking[i].updateTotalRoomPrice(mainArrayList,totalRoomPrice,booking[i].getBookingID()); // Update total room price
            totalMealPrice = booking[i].calMealPrice(mainArrayList, booking[i].getRateMeal(), booking[i].getBookingID()); // Calculate total meal price
            booking[i].updateTotalMealPrice(mainArrayList,totalMealPrice,booking[i].getBookingID()); // Update total meal price
            myTotalBill = booking[i].calTotalBill(mainArrayList,rateRoom,booking[i].getRateMeal(),booking[i].getBookingID());
            double rm_cashback = booking[i].checkRemainCustomerCashback(customerArrayList,booking[i].getName(), myTotalBill);
            totalCashback = booking[i].checkCustomerCashback(customerArrayList,booking[i].getName(), myTotalBill);
            finalBill = booking[i].calFinalBill(mainArrayList, myTotalBill,  totalCashback, booking[i].getBookingID()); // Calculate final bill
            booking[i].setFinalBill(finalBill); // Update final bill
            booking[i].setCashback((int) (totalRoomPrice*0.05)); // Update cashback for next booking
            double real_cashback = booking[i].updateCashback(customerArrayList,(int) (totalRoomPrice*0.05),booking[i].getName()); // update cashback
            booking[i].print(mainArrayList,totalRoomPrice,totalMealPrice,finalBill,totalCashback,myTotalBill,booking[i].getBookingID(),rm_cashback, real_cashback);
        }

        System.out.println("===== Room Summary =====");
        

        String[] roomName = new String[4];
        int[] roomResv = new int[4];
        double[] roomPrice = new double[4];
        ArrayList<CalculateRoomTotal> roomSummary = new ArrayList<>();
        
        int k=0;
        for(Booking seek : mainArrayList) {
            int objNight[] = seek.getAllRoomNight();
            roomName = seek.getAllRoomName();
            roomPrice = seek.getAllRateRoom();
            for(int j = 0; j < 4; j++){
                roomResv[j] += (objNight[j] * seek.getNights());  
            }
        }
        for(int j = 0; j < 4; j++){                
                roomSummary.add(new CalculateRoomTotal(roomName[j], roomResv[j], roomPrice[j]));
        }
        
        Collections.sort(roomSummary);
        for(CalculateRoomTotal show : roomSummary) show.print();
        
        
        
        
        System.out.println("===== Meal Summary  =====");
        //meal summary code

        //Son's code
        String mealName = null;
        double rateMeal;
        int mealPerson;
        int totalMealSales = 0;
        double totalMealRateSales = 0;
        
        for(int i=0;i<mainArrayList.size();i++){
            mealName = booking[i].getMealName();
            totalMealSales += booking[i].getMealPerson() * booking[i].getNights();
            totalMealRateSales = booking[i].getRateMeal() * totalMealSales;
        }
        System.out.printf("%-20s total sales = %5d heads %,15.2f Baht\n",mealName,totalMealSales,totalMealRateSales);
    }
}