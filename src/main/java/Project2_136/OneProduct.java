/*
    Group Member(s):
    Palarp Wasuwat : 6413108
    Kobkit Ruangsuriyakit : 6413211
    Teerapat Phopit : 6413216
 */
package Project2_136;

public class OneProduct {
    private String name;
    private int balance;
    private int failure;

    public OneProduct(String name) {
        this.name = name;
        this.balance = 0;
        this.failure = 0;
    }

    public String getName() { return name; }

    synchronized public void add(int amount) {
        balance += amount;

        System.out.println(String.format("Thread %-4s  >>  add   %3d  %-20s to stock    balance =    %3d",
                Thread.currentThread().getName(), amount, name, balance));
    }

    public void ship(int amount) {
        if ( balance >= amount ) {
            balance -= amount;

            System.out.println(String.format("Thread %-4s  >>  ship  %3d  %-20s             balance =    %3d",
                    Thread.currentThread().getName(), amount, name, balance));
        }
        else {
            System.out.println(String.format("Thread %-4s  >>  ship  %3d  %-20s             balance =    %3d",
                    Thread.currentThread().getName(), 0, name, balance));

            failure += 1;
        }
    }

    public void displayFailure() {
        System.out.println(String.format("Thread %-4s  >>  %-20s failure to ship = %2d days",
                Thread.currentThread().getName(), name, failure));
    }
}
