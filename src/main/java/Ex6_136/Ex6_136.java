//6513136 Mahannop Thabua
package Ex6_136;

import java.util.*;
import java.util.concurrent.*;

class FactoryThread extends Thread
{
    private Material            sharedMaterial;    // all threads compete for sharedMaterial
    private Product             myProduct;         // each thread updates its myProduct      
    private Exchanger<Product>  exchanger;         
    private int                 orderCount = 1;
    private CyclicBarrier myBarrier;

    public FactoryThread(String n, Material ma, Product pd,CyclicBarrier myBarrier)
    { 
        super(n); 
        sharedMaterial = ma;
        myProduct      = pd;
        this.myBarrier = myBarrier; //using to wait the 2 threads in main
    }
    public void setExchanger(Exchanger<Product> ex)     { exchanger = ex; }
    
    public void run() {
        try {
            // (1) Print ID + current balance of myProduct it has to update
            System.out.printf("%s >> will update %s , balance = %d\n",Thread.currentThread().getName(),myProduct.ID,myProduct.balance);

            //Waiting the others threads --> If step 1 successfully then do next thread
            myBarrier.await();

            // (2) Loop for 3 orders
            //  - Get random number of materials (e.g. n) --> call sharedMaterial.use()
            //  - Use n materials to create n products    --> call myProduct.create(n)
            for(int i = orderCount ; i <= 6 ; i++) {

                if(i == 4) {
                    // (3) Exchange myProduct with another FactoryThread
                    try{
                        this.myProduct = exchanger.exchange(this.myProduct);
                        System.out.printf("%s >> will update %s , balance = %d\n",Thread.currentThread().getName(),myProduct.ID,myProduct.balance);
                        myBarrier.await(); //Waiting for the exchange thread successfully
                    }
                    catch(InterruptedException e) {}
                }
                // (4) Repeat step (1) to print new myProduct
                int myMaterials = sharedMaterial.use(i);
                // (5) Repeat step (2) for orders 4-6 which update new myProduct
                myProduct.create(myMaterials,i);
            }
        }
        catch(Exception e) { }

    }
}

abstract class Item {
    protected String ID;
    protected int balance;
    protected Random random;
    
    //public Item()      {}
    public Item(String id, int b) {
        ID = id;
        balance = b; 
        random = new Random();        
    }
    public void print() {
        System.out.printf("final balance of %4s = %d\n",this.ID,this.balance);
    }
}

class Material extends Item {
    public Material(String id, int b)   {
        super(id, b);
    }
    synchronized public int use(int myOrderCount) {
        int n;
        // (1) Random n items from 1-10, but make sure that n must not exceed balance
            do {
                n = 1 + random.nextInt(10);
            } while (n > this.balance);
            // (2) Update Material's balance, decreasing it by n
            this.balance -= n;

            // (3) Report thread's activity + balance (see example output)
            System.out.printf("%s >> order %d   - %2d %s    balance of %s = %d\n", Thread.currentThread().getName(), myOrderCount, n, this.ID, this.ID, this.balance);

        // (4) Make thread sleep for some random delay
        int delay = (int)(Math.random()*123);
        try{Thread.sleep(delay); } catch(InterruptedException e) { }

        return n;
    }
}

class Product extends Item { 
    public Product(String id, int b)   { super(id, b); }
    
    public void create(int n,int myOrderCount) //need to make the orderCount for the sake of for loop to make sure that what order is currently working ?
    {
        // (1) Update Product's balance, increasing it by n
        this.balance += n; //this the balance value from class Item that can be used !

        // (2) Report thread's activity + balance (see example output)
        System.out.printf("%s >> order %d   + %2d %s    balance of %s = %d\n",Thread.currentThread().getName(),myOrderCount,n,this.ID,this.ID,this.balance);

        // (3) Make thread sleep for some random delay
        int delay = (int)(Math.random()*123);
        try{Thread.sleep(delay); } catch(InterruptedException e) { }
    }
}

public class Ex6_136 {

    public static void main(String[] args) {
        Ex6_136 mainApp = new Ex6_136();
        mainApp.runSimulation();
    }

    public void runSimulation()
    {
        Material sharedMaterial = new Material("material    ", 100);
        Product [] myProducts   = { new Product("product[ 0 ]", 0), new Product("product[ 1 ]", 0) };
            
        Exchanger<Product> exchanger = new Exchanger<>();
        
        FactoryThread [] allThreads = new FactoryThread[2];

        CyclicBarrier Barrier = new CyclicBarrier(2);

        for(int i=0; i < allThreads.length; i++)
        {
            // Threads see the same Material but different Product objects
            allThreads[i] = new FactoryThread("Factory[ "+i+" ]", sharedMaterial, myProducts[i],Barrier);
            allThreads[i].setExchanger(exchanger);
        }

        // (1) Start all FactoryThreads
        for(Thread MyFactory : allThreads) MyFactory.start();

        try {
            allThreads[0].join();
            allThreads[1].join();
        } catch (InterruptedException e) {
            System.err.print("Got Interrupted");
        } //Time_waiting State ( don't use the processor ) if don't have it --> it can print in any part of code !

        // (2) After all FactoryThreads complete their work, print final balance of Material and all Products

        System.out.printf("\n%s >> ",Thread.currentThread().getName());
        sharedMaterial.print();

        for(Product p : myProducts) {
            System.out.printf("%s >> ",Thread.currentThread().getName());
            p.print();
        }

    }
}
