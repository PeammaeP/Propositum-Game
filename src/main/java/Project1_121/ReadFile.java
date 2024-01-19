package Project1_121;

import java.awt.desktop.OpenFilesHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class ReadFile {
    private Scanner input;
    private String itemType;

    // Method stand for hotel file reading
    public ArrayList<Room> readHotelFile(){
        final double vat = 1.07, serviceCharge = 1.10;
        boolean FoundFile =  false;
        ArrayList<Room> hotelData = new ArrayList<>();

        // Set and declare filePath(hotel.txt)
        String path,fileName, filePath;
        path = "src/main/Java/Project1_121/";
        fileName = "hotel.txt";

        // Scanner
        input = new Scanner(System.in);

        //Read data from file
        while(!FoundFile){
            filePath = path + fileName;
            try( Scanner fileScan = new Scanner(new File(filePath)))
            {
                FoundFile = true; // Found the file
                System.out.println("Read hotel data from file " + filePath + "\n");
                for( int i = 0; fileScan.hasNext(); i++){
                    String line = fileScan.nextLine();
                    String [] col = line.split(",");
                    String itemType = col[0].trim(); //Set and trim type
                    String itemName = col[1].trim(); //Set and trim room's / meal's name
                    double rate = Double.parseDouble(col[2].trim());// Set rate of room / meal
                    double ratepp;
                    if(itemType.equals("R"))ratepp = (rate * serviceCharge) * vat;// Set rate including VAT and Service charge
                    else ratepp = rate;

                    //Add data to ArrayList: hotelData
                    if(itemType.equals("R")) hotelData.add(new Room(itemName, rate, ratepp));
                    else hotelData.add(new Meal(itemName, rate));
                }
                //Show read data from ArrayList: hotelData
                for(Room data: hotelData ) if(data instanceof Meal) ((Meal)data).printData(); else data.printData();
                System.out.println();
            }
            catch(Exception e)
            {
                System.out.println("[ERROR] " + e);
                System.out.println("Enter file name for hotel data =");
                fileName = input.next();
            }
        }
        return hotelData;
    }

    // Method stand for booking file reading
    public ArrayList<Booking> readBookingFile(ArrayList<Room> inData){
        boolean FoundFile =  false;
        ArrayList<Booking> bookingData = new ArrayList<>();
        // Set and declare filePath(bookings.txt)
        String path,fileName, filePath;
        path = "src/main/Java/Project1_121/";
        fileName = "bookings.txt";

        // Scanner
        input = new Scanner(System.in);

        //Read data from file
        while(!FoundFile){
            filePath = path + fileName;
            try( Scanner fileScan = new Scanner(new File(filePath)))
            {
                FoundFile = true; // Found the file
                input.close(); // Close scanner and System.in
                System.out.println("Read booking data from file " + filePath + "\n");
                for( int i = 0; fileScan.hasNext(); i++){
                    int bkID, bkNight, bkMeal; // Booking ID, Number of Night, and Number of Meal
                    String bkName; // Customer's name
                    int[] bkRoom = new int[4]; //Array of room-booking number
                    String line = null;

                    try{
                        line = fileScan.nextLine();
                        String [] col = line.split(",");
                        bkID = Integer.parseInt(col[0].trim()); //Set and trim Booking ID
                        bkName = col[1].trim(); //Set and trim Customer's name
                        bkNight = Integer.parseInt(col[2].trim()); //Set and trim Number of Night
                        if(bkNight<0) throw new OtherException("For input string: " + "\"" + bkNight + "\"");
                        bkRoom[0] = Integer.parseInt(col[3].trim()); //Set and trim Number of Single Room
                        if(bkRoom[0]<0) throw new OtherException("For input string: " + "\"" + bkRoom[0] + "\"");
                        bkRoom[1] = Integer.parseInt(col[4].trim()); //Set and trim Number of Twin Room
                        if(bkRoom[1]<0) throw new OtherException("For input string: " + "\"" + bkRoom[1] + "\"");
                        bkRoom[2] = Integer.parseInt(col[5].trim()); //Set and trim Number of Triple Room
                        if(bkRoom[2]<0) throw new OtherException("For input string: " + "\"" + bkRoom[2] + "\"");
                        bkRoom[3] = Integer.parseInt(col[6].trim()); //Set and trim Number of Single Dorm Room
                        if(bkRoom[3]<0) throw new OtherException("For input string: " + "\"" + bkRoom[3] + "\"");
                        bkMeal = Integer.parseInt(col[7].trim()); //Set and trim Number of Meal
                        if(bkMeal<0) throw new OtherException("For input string: " + "\"" + bkMeal + "\"");

                        //Add data to ArrayList: bookingData
                        bookingData.add(new Booking(bkID,bkName,bkNight, bkRoom,bkMeal,0, inData));
                    }
                    catch(Exception e){
                        System.err.println("Error occured" + e);
                        System.err.println("[" + line + "]  -->  skip this booking");
                        continue; // Skip that line
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("[ERROR] " + e);
                System.out.println("Enter file name for booking data =");
                fileName = input.next();
            }
        }
        return bookingData;
    }
}