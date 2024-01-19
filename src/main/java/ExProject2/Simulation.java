package ExProject2;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class Group {

    private String name, destination;
    private int seat, transaction;
    
    Group(String n, int s) {
        name = n;
        seat = s;
    }

    Group(int t, String n, int s, String d) {
        transaction = t;
        name = n;
        seat = s;
        destination = d;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public int getSeat() {
        return seat;
    }

    public int getTransaction() {
        return transaction;
    }

    public int setSeat(int n) {
        seat = seat - n;
        return seat;
    }

    int zeroSeat() {
        return seat = 0;
    }
}

class Bus {

    private String name;
    private int available;
    ArrayList<Group> Groups = new ArrayList<>();

    Bus(String n, int ms) {
        name = n;
        available = ms;
    }

    public void add(String n, int s) {
        Groups.add(new Group(n, s));
    }
    
    public String getName() {
        return name;
    }

    public int getAvailable() {
        return available;
    }

    public int setAvailable(int n) {
        available = available - n;
        return available;
    }

    public int zeroAvailable() {
        return available = 0;
    }
    
    void print() {
        System.out.printf("\n%s >> %-3s :", Thread.currentThread().getName(), name);
        for (int i = 0; i < Groups.size(); i++) {
            System.out.printf(" %-20s (%2d seats)", Groups.get(i).getName(), Groups.get(i).getSeat());
            
            if(i<Groups.size() - 1) {
                System.out.printf("%s",",");
            }else {
                System.out.printf("%s"," ");
            }
        }
    }
}

class BusLine {

    private String destination;
    private int maxSeat,count;
    ArrayList<Bus> Buses = new ArrayList<>();
    Bus currentBus;
    private static boolean check;

    BusLine(String d, int ms) {
        destination = d;
        maxSeat = ms;
        count = 0;
    }
    
    public int getSize() {
        return Buses.size();
    }
    
    synchronized public static void printCheckpoint(int n, int m) {
        if (!check) {
            System.out.printf("\n%s >> %2d airport-bound have been allocated", Thread.currentThread().getName(), n);
            System.out.printf("\n%s >> %2d city-bound have been allocated\n\n", Thread.currentThread().getName(), m);
            check = true;
        }
    }

    synchronized public void allocateBus(Group currentGroup) {

        while (currentGroup.getSeat() != 0) {

            if (currentBus == null || currentBus.getAvailable() == 0) {
                currentBus = new Bus(destination + Buses.size(), maxSeat);
                Buses.add(currentBus);
            }
            else {
                
                if (currentBus.getAvailable() >= currentGroup.getSeat()) {
                    currentBus.setAvailable(currentGroup.getSeat());
                    currentBus.add(currentGroup.getName(),currentGroup.getSeat());
                    System.out.printf("%s >> Transaction %2d : %-20s (%2d seats) bus %s\n", Thread.currentThread().getName(), 
                                                                                            currentGroup.getTransaction(), 
                                                                                            currentGroup.getName(), 
                                                                                            currentGroup.getSeat(), 
                                                                                            currentBus.getName());
                    currentGroup.zeroSeat();
                } else {
                    currentGroup.setSeat(currentBus.getAvailable());
                    currentBus.add(currentGroup.getName(),currentBus.getAvailable());
                    System.out.printf("%s >> Transaction %2d : %-20s (%2d seats) bus %s\n", Thread.currentThread().getName(), 
                                                                                            currentGroup.getTransaction(), 
                                                                                            currentGroup.getName(), currentBus.getAvailable(), 
                                                                                            currentBus.getName());
                    currentBus.zeroAvailable();
                }
            }    
        }
    }
    
    public void printSummary() {
        if(destination.equals("A")){
            System.out.printf("\n%s >> ===== Airport Bound =====",Thread.currentThread().getName());
            for (int i=0; i<Buses.size() ; i++)     
            {
                Buses.get(i).print();
            }
        }
        else {
            System.out.printf("\n\n%s >> ===== City Bound =====",Thread.currentThread().getName());
            for (int i=0; i<Buses.size() ; i++)     
            {
                Buses.get(i).print();
            }
            
        }
    }
    
}

class TicketCounter extends Thread {

    private final int checkpoint;
    private final ArrayList<BusLine> BusLines;
    private final ArrayList<Group> currentGroup;
    int countA = 1, countC = 1;
    private CyclicBarrier cfinish;

    TicketCounter(String n, int c, ArrayList<BusLine> B, ArrayList<Group> G) {
        super(n);
        checkpoint = c;
        BusLines = B;
        currentGroup = G;
    }
    
    public int getCheckPoint() {
        return checkpoint;
    }

    public void setCyclicBarrier(CyclicBarrier f) {
        cfinish = f;
    }

    public void run() {
        
        for (int i = 0; i < currentGroup.size(); i++) {
            
            if (i+1 != checkpoint) {
                if ("A".equals(currentGroup.get(i).getDestination())) {
                BusLine A = BusLines.get(0);
                A.allocateBus(currentGroup.get(i));
                } else {
                    BusLine C = BusLines.get(1);
                    C.allocateBus(currentGroup.get(i));
                }
            }else {
                try {
                    cfinish.await();                    
                } catch (Exception e) {
                }
                if ("A".equals(currentGroup.get(i).getDestination())) {
                    BusLine A = BusLines.get(0);
                    BusLine.printCheckpoint(BusLines.get(0).getSize(), BusLines.get(1).getSize());
                    A.allocateBus(currentGroup.get(i));
                } else {
                    BusLine C = BusLines.get(1);
                    BusLine.printCheckpoint(BusLines.get(0).getSize(), BusLines.get(1).getSize());
                    C.allocateBus(currentGroup.get(i));
                }
            } 
            
            /*if ("A".equals(currentGroup.get(i).getDestination())) {
                BusLine A = (BusLine) BusLines.get(0);
                A.allocateBus(currentGroup.get(i));
                //countA++;
            } else {
                BusLine C = (BusLine) BusLines.get(1);
                C.allocateBus(currentGroup.get(i));
                //countC++;
            }
            if (i+1 == checkpoint - 1) {
                try {
                    if (cfinish.getNumberWaiting() == 2) {
                        System.out.printf("\n%s >> %2d airport-bound have been allocated", Thread.currentThread().getName(), BusLines.get(0).count());
                        System.out.printf("\n%s >> %2d city-bound have been allocated\n\n", Thread.currentThread().getName(), BusLines.get(1).count());
                    }
                     cfinish.await();
                } catch (Exception e) {
                    System.err.println(Thread.currentThread().getName() + e);
                }
            }
            */
            
            
        }

    }
}

public class Simulation {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println(Thread.currentThread().getName() + " >> Enter max seats =");
        int maxSeats = scan.nextInt();
        
        System.out.println(Thread.currentThread().getName() + " >> Enter checkpoint =");
        int checkpoint = scan.nextInt();
        
        System.err.print("\n");

        ArrayList<BusLine> BusLines = new ArrayList<>();
        BusLines.add(new BusLine("A", maxSeats));
        BusLines.add(new BusLine("C", maxSeats));

        CyclicBarrier finish = new CyclicBarrier(3);
        
        ArrayList<TicketCounter> AL = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ArrayList<Group> currentGroup = new ArrayList<>();
            try (Scanner key = new Scanner(new File("T" + i + ".txt"));) {
                
                while (key.hasNext()) {
                    String[] buf = key.nextLine().split(",");
                    int transaction = Integer.parseInt(buf[0].trim());
                    String name = buf[1].trim();
                    int passenger = Integer.parseInt(buf[2].trim());
                    String destination = buf[3].trim();

                    Group G = new Group(transaction, name, passenger, destination);
                    currentGroup.add(G);
                }
            } catch (FileNotFoundException e) {
                System.err.println(e + "\n");
            } catch (Exception e) {
                System.out.println(e);
            }
            TicketCounter T = new TicketCounter("T" + i, checkpoint, BusLines, currentGroup);
            T.setCyclicBarrier(finish);
            AL.add(T);
            T.start();
        }
        
        try {
            for (int i = 0; i < 3; i++) {
                AL.get(i).join();
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        
        for (BusLine j : BusLines) {
            j.printSummary();
        }
    }
}
