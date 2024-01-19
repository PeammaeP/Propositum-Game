//Read file Prototype
//Version 1.0.4
//Time 11 Nov 2023 @ 16.29
//Using Monitor

//6513111 Chutiya Thanaluck
//6513121 Supakorn Thavornvong
//6513123 Anapat Kitsommart
//6513136 Mahannop Thabua

package Project2_121;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

class Material{
    //    • Variable to keep material balance
    private ArrayList<Material> materialArrayList;
    private int materialBalance;
    private String materialName;

    //Constructor
    public Material (String materialName,int materialBalance){
        //Structure : [{button, 0}]
        this.materialBalance = materialBalance;
        this.materialName = materialName;
    }

    //Set function
    public void setMaterialBalance(int materialBalance){
        this.materialBalance = materialBalance;
    }

    //Get function
    public String getName(){
        return materialName;
    }
    public int getMaterialBalance(){
        return materialBalance;
    }

//    • Methods to add materials (by suppliers and factories) and retrieve materials (by factories)
//    • All suppliers and factories must access the same set of material objects
}

class SupplierThread extends Thread{
    private String name;
    private ArrayList<Integer> data;
    private ArrayList<Material> materialArrayList;
    private int day;
    private CyclicBarrier myFinish, globalFinish;

    //Constructor
    public SupplierThread(String inName, ArrayList<Integer> inData, ArrayList<Material> materialArrayList, int inDay)   {
        //Structure : [{Supplier1, {100,100}}]
        super(inName);
        name = inName;
        data = inData;
        day = inDay;

        this.materialArrayList = materialArrayList;
    }

    public void print(ArrayList<Material> material) {
        System.out.printf("%-10s >> %-10s daily supply rates = ", Thread.currentThread().getName(), name);
        for( int i = 0; i< material.size(); i++){
            System.out.printf( "%3d %-10s ",data.get(i), material.get(i).getName());
        } System.out.println();
    }
//    3.1 Wait until one thread (main, SupplierThread, or FactoryThread) prints day number
//    3.2 Add materials and update material balances. The amount to add depends = its daily supply rates. Print thread activities as in the demo

    //Set function
    public void setBarrier(CyclicBarrier c, int mode) { switch(mode) {case 0 -> globalFinish = c; case 1 -> myFinish = c;}}

    synchronized public void newDayMaterial(ArrayList<SupplierThread> supplierThreadArrayList,int index){
        for(int i=0;i<supplierThreadArrayList.size();i++){
            Material material = materialArrayList.get(i);
            SupplierThread supplierThread = supplierThreadArrayList.get(index);
            material.setMaterialBalance(material.getMaterialBalance() + supplierThread.data.get(i));
            
            String name = materialArrayList.get(i).getName();
            int balance = materialArrayList.get(i).getMaterialBalance();
            System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Put",data.get(i),name,"balance",balance,name);
        }
    }
    
    synchronized public void newDayMaterial(){
        for(int i=0;i<materialArrayList.size();i++){
            Material material = materialArrayList.get(i);
            material.setMaterialBalance(material.getMaterialBalance() + data.get(i));
                  
            String name = materialArrayList.get(i).getName();
            int balance = materialArrayList.get(i).getMaterialBalance();
            System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Put",data.get(i),name,"balance",balance,name);
        }
    }
    
    //run method
    public void run(){
        int x = -1; 
        Random rand = new Random();
        //System.out.printf("%-10s >> Thread is running\n",Thread.currentThread().getName());
        for(int today = 1; today <= day; today++){
            int tsleep = rand.nextInt(101) + 500; //500 - 600
            try { x = globalFinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            //System.out.printf("++++++++++ " + this.getName() + " global finishes, x = %d at day = %d ++++++++++\n", x, today);
            //System.out.printf("%-10s >> Thread is sleeping for %d ms\n",Thread.currentThread().getName(),tsleep);
            try{this.sleep(tsleep);} catch(Exception e) {}; // To ensure that thread will not print before main thread access System.out
            newDayMaterial();
            try { x = myFinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            //System.out.printf("++++++++++ " + this.getName() + " finishes, x = %d at day = %d ++++++++++\n", x, today);
        }
        //System.out.printf("%-10s >> Thread is ending\n",Thread.currentThread().getName());
    }
}

class FactoryThread extends Thread implements Comparable<FactoryThread>{
    private String name;
    private String product;
    private int lotNum;
    private int use;
    private int day;
    private ArrayList<Integer> data = new ArrayList<>();
    private ArrayList<Material> myMaterial = new ArrayList();
    private ArrayList<Material> materialArrayList;
    private CyclicBarrier globalFinish, materialFinish, myFinish;

    public FactoryThread(String inName, String inProduct,int usage, ArrayList<Integer> inData, ArrayList<Material> inMaterial,int inDay){
        //Structure : [{Factory1, Handbags, 10, {2,6}, {{button, 0}, {zipper, 0}}}]
        super(inName);
        name = inName;
        product = inProduct;
        lotNum = 0;
        use = usage;
        day = inDay;
        this.materialArrayList = inMaterial;

        //Deep Copy
        for(Material in : inMaterial) myMaterial.add(new Material(in.getName(),in.getMaterialBalance()));

        // Desigen for deducting item from material, the index of int-value corresponds to material so get(i) at the same position will be good for calculation ex. materialarr.get(i).setval(materialarr.get(i).getIntdata - data.get(i))
        // Structure : [20,60]
        for(int in : inData) data.add(usage*in);
    }
    
    //CompareTo
        public int compareTo(FactoryThread other) {
        // lotNum in DECREASING
        if(this.lotNum > other.lotNum)        { return -1; }// Put to before
        else if(this.lotNum < other.lotNum)   { return 1; } // Put to after
        else                                  { return 0; } // Equal
    }
    
    //Set function
    public void setBarrier(CyclicBarrier c, int mode) {
        switch(mode) {
            case 0 -> globalFinish = c;
            case 1 -> myFinish = c;
            case 2 -> materialFinish = c;
        }
    }
    //Get function
    public int getData(int index){
        for(int i=0;i<data.size();i++){
            if(i==index) return data.get(i);
        }
        return 0;
    }
    public String getProduct() { return product; }
    public int getLot() { return lotNum; }
    
    //Print function
    public void print(ArrayList<Material> material) {
        System.out.printf("%-10s >> %-10s daily use    rates = ", Thread.currentThread().getName(),name);
        for(int i = 0; i< material.size(); i++){
            System.out.printf( "%3d %-10s ",data.get(i), material.get(i).getName());
        }
        System.out.printf("producing  %s %s", use, product);
        System.out.println();
    }
//    4.1 Wait until one thread (can be main, SupplierThread, or FactoryThread) prints day number and all SupplierThreads finish adding materials
//    4.2 Report materials it is holding from yesterday. All FactoryThreads must wait until all of them finish printing reports, then proceed to 4.3
//    4.3 Retrieve remaining materials to produce 1 lot of products and update material balances. The amount to retrieve = its daily requirement rates. For example, if Factory 1 is already holding 20 buttons, it only needs to get 60 zippers today. If the material balance is not enough, it will take as much as it can. For example, if there are only 50 zippers, it will still take them. Print thread activities as in the demo
//    4.4 Wait until all FactoryThreads finish retrieving materials, then check whether it can make 1 full lot of products by using the materials it has from 4.2 + 4.3
//        • If 1 full lot can be made ➔ update lot count and clear all materials (i.e. they are all used up)
//        • If 1 full lot can’t be made ➔ keep only materials it has in full amount for tomorrow. Return materials it doesn’t have in full amount (i.e. updating material balance)
//        • Example 1: if Factory 1 has 20 buttons + 60 zippers, it will make 1 lot of handbags, hence holding 0 buttons + 0 zippers for tomorrow
//        • Example 2: if Factory 1 has 20 buttons + 50 zippers, it can’t make handbags today. It will keep 20 buttons and return 50 zippers, hence holding 20 buttons + 0 zippers for tomorrow
//        • Print thread activities as in the demo

    //4.2 Report materials holding
    public void printMaterialHolding(ArrayList<FactoryThread> factoryThreadArrayList,int index){
        FactoryThread FTA = factoryThreadArrayList.get(index);
        System.out.printf("%-10s >> %-10s ",Thread.currentThread().getName(), "Holding");
        for(int j=0;j<myMaterial.size();j++){
            Material MM = myMaterial.get(j);
            System.out.printf("%3d %-15s",MM.getMaterialBalance(),MM.getName());
        }
        System.out.println();
    }
    
    public void printMaterialHolding(){
        synchronized(System.out){
        System.out.printf("%-10s >> %-10s ",Thread.currentThread().getName(), "Holding");
        for(int j=0;j<myMaterial.size();j++){
            Material MM = myMaterial.get(j);
            System.out.printf("%3d %-15s",MM.getMaterialBalance(),MM.getName());
        }
        System.out.println();
        }
    }

    //4.3 Add factory material
    synchronized public void factoryAddMaterial(ArrayList<FactoryThread> factoryThreadArrayList,int index){                        //Add material to myMaterial
        FactoryThread FTA = factoryThreadArrayList.get(index);
        for(int i=0;i<FTA.materialArrayList.size();i++){
            Material MM = FTA.myMaterial.get(i);
            Material MAL = FTA.materialArrayList.get(i);
            int materialFromYesterday = MM.getMaterialBalance();

            if(day == 0 && data.get(i) <= MAL.getMaterialBalance()){
                MM.setMaterialBalance(data.get(i));
                MAL.setMaterialBalance(MAL.getMaterialBalance() - data.get(i));
                System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Get",MM.getMaterialBalance(),MM.getName(),"balance",MAL.getMaterialBalance(),MM.getName());
            }
//            else if(day == 0 && data.get(i) > MAL.getMaterialBalance()){                                              //Same use as else
//                MM.setMaterialBalance(MAL.getMaterialBalance());
//                MAL.setMaterialBalance(0);
//            }
            else if(day != 0 && (data.get(i) - materialFromYesterday) <= MAL.getMaterialBalance()){
                MM.setMaterialBalance(data.get(i));
                MAL.setMaterialBalance(MAL.getMaterialBalance() - (data.get(i) - materialFromYesterday));
                System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Get",MM.getMaterialBalance(),MM.getName(),"balance",MAL.getMaterialBalance(),MM.getName());
            }
//            else if(day != 0 && (data.get(i) - materialFromYesterday) > MAL.getMaterialBalance()){                    //Same use as else
//                MM.setMaterialBalance(MAL.getMaterialBalance());
//                MAL.setMaterialBalance(0);
//            }
            else{
                MM.setMaterialBalance(MAL.getMaterialBalance());
                MAL.setMaterialBalance(0);
                System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Get",MM.getMaterialBalance(),MM.getName(),"balance",MAL.getMaterialBalance(),MM.getName());
            }
        }
    }
    
    synchronized public void factoryMaterialAdd(){
        for(int i=0;i<myMaterial.size();i++){
            Material MM = myMaterial.get(i);
            Material MAL = materialArrayList.get(i);
            int materialFromYesterday = MM.getMaterialBalance();

            if(day == 0 && data.get(i) <= MAL.getMaterialBalance()){
                MM.setMaterialBalance(data.get(i));
                MAL.setMaterialBalance(MAL.getMaterialBalance() - data.get(i));
                System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Get",MM.getMaterialBalance(),MM.getName(),"balance",MAL.getMaterialBalance(),MM.getName());
            }
//            else if(day == 0 && data.get(i) > MAL.getMaterialBalance()){                                              //Same use as else
//                MM.setMaterialBalance(MAL.getMaterialBalance());
//                MAL.setMaterialBalance(0);
//            }
            else if(day != 0 && (data.get(i) - materialFromYesterday) <= MAL.getMaterialBalance()){
                MM.setMaterialBalance(data.get(i));
                MAL.setMaterialBalance(MAL.getMaterialBalance() - (data.get(i) - materialFromYesterday));
                System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Get",MM.getMaterialBalance(),MM.getName(),"balance",MAL.getMaterialBalance(),MM.getName());
            }
//            else if(day != 0 && (data.get(i) - materialFromYesterday) > MAL.getMaterialBalance()){                    //Same use as else
//                MM.setMaterialBalance(MAL.getMaterialBalance());
//                MAL.setMaterialBalance(0);
//            }
            else{
                MM.setMaterialBalance(MAL.getMaterialBalance());
                MAL.setMaterialBalance(0);
                System.out.printf("%-10s >> %-10s %3d %-15s %15s = %5d %8s\n",Thread.currentThread().getName(),"Get",MM.getMaterialBalance(),MM.getName(),"balance",MAL.getMaterialBalance(),MM.getName());
            }
        }
    }

    //4.4 Produce
    synchronized public void factoryProduce(ArrayList<FactoryThread> factoryThreadArrayList,int index){
        FactoryThread FTA = factoryThreadArrayList.get(index);
        int canMake = 0;
        ArrayList<Integer> notEnoughMaterial = new ArrayList<>();

        for(int i=0;i<FTA.myMaterial.size();i++){
            Material MM = FTA.myMaterial.get(i);
            if(FTA.data.get(i) == MM.getMaterialBalance()){
                canMake++;
            }
            else{
                notEnoughMaterial.add(i);
                //System.out.printf("[i = %d] %-10s >> %-10s %3d/%3d %-15s\n",i,Thread.currentThread().getName(),"NOT enought for",MM.getMaterialBalance(),data.get(i),MM.getName());
            }
        }
//        System.out.println(name);
        if(canMake == FTA.materialArrayList.size()){
            FTA.lotNum++;
            for(int i=0;i<FTA.materialArrayList.size();i++){
                FTA.myMaterial.get(i).setMaterialBalance(0);
            }
            System.out.printf("%-10s >> %-10s production succeeds, lot %4d\n",Thread.currentThread().getName(),FTA.getProduct(),FTA.lotNum);
        }
        else{
            //for(int read: notEnoughMaterial) System.out.printf("%-10s >> not enough in index = %d\n",Thread.currentThread().getName(),read);
            System.out.printf("%-10s >> %-10s production fails\n",Thread.currentThread().getName(),getProduct());
            for(int read: notEnoughMaterial) {
                Material MM = myMaterial.get(read);
                Material MAL = materialArrayList.get(read);
                if(MM.getMaterialBalance() > 0){
                    System.out.printf("%-10s >> %-10s %3d %-15s ",Thread.currentThread().getName(),"Put",MM.getMaterialBalance(),MM.getName());
                    MAL.setMaterialBalance(MAL.getMaterialBalance() + MM.getMaterialBalance());
                    MM.setMaterialBalance(0);
                    System.out.printf("%15s = %5d %8s\n","balance",MAL.getMaterialBalance(),MM.getName());
                }
            }
        }
    }
    
    synchronized public void factoryProduce(){
        int canMake = 0;
        ArrayList<Integer> notEnoughMaterial = new ArrayList<>();

        for(int i=0;i<myMaterial.size();i++){
            Material MM = myMaterial.get(i);
            if(data.get(i) == MM.getMaterialBalance()){
                canMake++;
            }
            else{
                notEnoughMaterial.add(i);
                //System.out.printf("[i = %d] %-10s >> %-10s %3d/%3d %-15s\n",i,Thread.currentThread().getName(),"NOT enought for",MM.getMaterialBalance(),data.get(i),MM.getName());
            }
        }
//        System.out.println(name);
        synchronized(System.out){
        if(canMake == materialArrayList.size()){
            lotNum++;
            for(int i=0;i<materialArrayList.size();i++){
                myMaterial.get(i).setMaterialBalance(0);
            }
            System.out.printf("%-10s >> %-10s production succeeds, lot %4d\n",Thread.currentThread().getName(),getProduct(),lotNum);
        }
        else{
            //for(int read: notEnoughMaterial) System.out.printf("%-10s >> not enough in index = %d\n",Thread.currentThread().getName(),read);
            System.out.printf("%-10s >> %-10s production fails\n",Thread.currentThread().getName(),getProduct());
            for(int read: notEnoughMaterial) {
                Material MM = myMaterial.get(read);
                Material MAL = materialArrayList.get(read);
                if(MM.getMaterialBalance() > 0){
                    System.out.printf("%-10s >> %-10s %3d %-15s ",Thread.currentThread().getName(),"Put",MM.getMaterialBalance(),MM.getName());
                    MAL.setMaterialBalance(MAL.getMaterialBalance() + MM.getMaterialBalance());
                    MM.setMaterialBalance(0);
                    System.out.printf("%15s = %5d %8s\n","balance",MAL.getMaterialBalance(),MM.getName());
                }
            }
        }
        }
    }    
    
    //run method
    @Override
    public void run(){
        int x = -1, y = -1; 
        //System.out.printf("%-10s >> Thread is running\n",Thread.currentThread().getName());
        for(int today = 1; today <= day; today++){
            try { x = globalFinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            //System.out.printf("++++++++++ " + this.getName() + " global finishes, x = %d at day = %d ++++++++++\n", x, today);            
            
            try { x = materialFinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            //System.out.printf("++++++++++ " + this.getName() + " material finishes, x = %d at day = %d ++++++++++\n", x, today);
            
            printMaterialHolding();
            try { x = myFinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            //System.out.printf("++++++++++ " + this.getName() + " my(1) finishes, x = %d at day = %d ++++++++++\n", x, today);
            
            factoryMaterialAdd();
            try { x = myFinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            //System.out.printf("++++++++++ " + this.getName() + " my(2) finishes, x = %d at day = %d ++++++++++\n", x, today);
            
            factoryProduce();
        }
        //System.out.printf("%-10s >> Thread is ending\n",Thread.currentThread().getName());
    }

}


public class NewMain {
    public static void main(String[] args) {
//        5.1 Read data from config.txt
        //Declare Variable
        Scanner input = new Scanner(System.in);
        boolean fileFound = false;
        int readDay = 0;
        int today = 1;
        //Structure Material: [{button, 0}, {zipper, 0}]
        ArrayList<Material> readMaterial = new ArrayList<>();
        
        ArrayList<Integer> readData;
        
        //Structure SupplierThread: [{Supplier1, {100,100}}, {Supplier2, {50,50}}]
        ArrayList<SupplierThread> readSupplier = new ArrayList<>();
        
        //Structure FactoryThread: [{Factory1, Handbags, 10, {2,6}}, {Factory2, Jackets, 20, {8,1}}, {Factory3, Pants, 40, {3,3}}]
        ArrayList<FactoryThread> readFactory = new ArrayList<>();
        
        // Set and declare filePath(config.txt)
        String path,fileName, filePath;
        path = "src/main/Java/Project2_121/";
        fileName = "config.txt";

        // Implementation
        while(!fileFound){
            filePath = path + fileName;
            try( Scanner fileScan = new Scanner(new File(filePath)))
            {
                fileFound = true; // Found the file
                input.close(); // Close Scanner and System.in
                System.out.printf("%-10s >> Read configs data from %s\n" ,Thread.currentThread().getName(),filePath);
                for( int i = 0; fileScan.hasNext(); i++){
                    //Reading text by row
                    String line = fileScan.nextLine();
                    String [] col = line.split(",");
                    readData = new ArrayList<Integer>();
                    for(int test = 1; test < 1; test++) System.out.println("Hey!!"+ test);
                    for( int j = 1; j < col.length; j++ ){
                        switch(col[0]){
                            //Row - Day
                            case "D" : readDay  = Integer.parseInt(col[j].trim()); break;
                            //Row - Materials
                            case "M" : readMaterial.add( new Material(col[j].trim(), 0) ); break;
                            //Row - Suppliers
                            case "S" : if(j > 1)readData.add(Integer.parseInt(col[j].trim()));
                                if( j == col.length - 1) {
                                    readSupplier.add(new SupplierThread(col[1].trim(),readData,readMaterial,readDay));
                                }
                                break;
                            //Row - Factories
                            case "F" : if(j > 3)readData.add(Integer.parseInt(col[j].trim()));
                                if( j == col.length - 1) readFactory.add(new FactoryThread(col[1].trim(),col[2].trim(),Integer.parseInt(col[3].trim()),readData, readMaterial,readDay));
                                break;
                            default : /*System.out.println("default");*/ break;
                        }
                    }
                }

                //Print in main
                System.out.println();
                //Simulation days
                System.out.printf("%-10s >> simulation days = %d\n" ,Thread.currentThread().getName(),readDay);

                //Materials
                //System.out.print("Materials >> " );
                //for(String read : readMaterial) System.out.print(read + " "); System.out.println();

                //Suppliers
                //System.out.print("Suppliers >> \n");
                for(SupplierThread read : readSupplier) read.print(readMaterial);

                //Factories
                //System.out.print("Factories >> \n");
                for(FactoryThread read : readFactory) read.print(readMaterial);

                //Test Loop day(2 days)
/*
                for(int k=0;k<2;k++){
                    //Day Suppliers
                    for(int i=0;i<readMaterial.size();i++){
                        readSupplier.get(i).newDayMaterial(readSupplier,i);
                    }

                    //Day Factory
                    for(int i=0;i<readFactory.size();i++){
                        readFactory.get(i).printMaterialHolding(readFactory,i);
                    }

                    for(int i=0;i<readFactory.size();i++){
                        readFactory.get(i).factoryAddMaterial(readFactory,i);
                    }

                    for(int i=0;i<readFactory.size();i++){
                        readFactory.get(i).factoryProduce(readFactory,i);
                    }
                    System.out.println("-------------------------------------------");
                }
*/
                //Materials
                /*
                System.out.print("Materials >> " );
                for(Material read : readMaterial) System.out.printf("\n Main material is %s value = %d", read.getName(), read.getMaterialBalance());
                */

//        5.2 Create Materials, SupplierThreads, FactoryThreads. Start all threads.
//            You are recommended to use ArrayLists to keep Materials, SupplierThreads, FactoryThreads for flexibility
            //Create CyclicBarrier
            CyclicBarrier sfinish = new CyclicBarrier(readSupplier.size() + readFactory.size()); // Size = 5
            CyclicBarrier ffinish = new CyclicBarrier(readFactory.size());  // Size = 3
            CyclicBarrier Gfinish = new CyclicBarrier(6);
            
            //Suppliers
            for(SupplierThread read : readSupplier) {
                read.setBarrier(Gfinish,0);
                read.setBarrier(sfinish,1);
                read.start();
            }

            //Factories
            for(FactoryThread read : readFactory) {
                read.setBarrier(Gfinish,0);
                read.setBarrier(ffinish,1);
                read.setBarrier(sfinish,2);
                read.start();
            }

            while(today <= readDay){
                int x = -1;
                
                if(today == 1){
                    System.out.printf("\n%-10s >> -----------------------------------------------------------\n",Thread.currentThread().getName());
                    if(today <= readDay) System.out.printf("%-10s >> Day %d\n" ,Thread.currentThread().getName(), today);
                    today++;
                
                    try { x = Gfinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
                    //System.out.printf("++++++++++ " + Thread.currentThread().getName() + " global finishes, x = %d ++++++++++\n", x);
                }
                else{
                    try { x = Gfinish.await(); } catch (Exception e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
                    //System.out.printf("++++++++++ " + Thread.currentThread().getName() + " global finishes, x = %d ++++++++++\n", x);
                    
                    System.out.printf("\n%-10s >> -----------------------------------------------------------\n",Thread.currentThread().getName());
                    if(today <= readDay) System.out.printf("%-10s >> Day %d\n" ,Thread.currentThread().getName(), today);
                    today++;
                }
            }


//        5.3 After all threads complete all days of simulation, let main thread report number of lots made by all FactoryThreads,
//            sorted in decreasing order of lots. If lot counts are equal, use alphabetical sorting of product names.
            for(SupplierThread read: readSupplier) {
                // Wait until all suppierThread terminated
                try{  read.join(); } 
                catch (InterruptedException e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            }
            for(FactoryThread read: readFactory) {
                // Wait until all factoryThread terminated
                try{  read.join(); } 
                catch (InterruptedException e) { System.out.printf("%-10s >> Error : %s\n",Thread.currentThread().getName(),e); }
            }
            
            //Summary
            System.out.printf("\n%-10s >> -----------------------------------------------------------\n",Thread.currentThread().getName());
            System.out.printf("%-10s >> Summary\n" ,Thread.currentThread().getName());
            Collections.sort(readFactory);
            for(FactoryThread read: readFactory) {
                System.out.printf("%-10s >> Total %-12s = %5d lots\n" ,Thread.currentThread().getName(),read.getProduct(), read.getLot());
            }
            }
                catch(FileNotFoundException e){
                    System.out.println("[ERROR] " + e);
                    System.out.println("Enter file name =");
                    fileName = input.next();
                }
        }
    }

}
