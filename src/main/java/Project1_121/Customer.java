package Project1_121;

//Class Customer
class Customer {
    protected String name;
    protected double cashback=0;
    protected int bookingID;

    //Get function
    public String getName() { return name; }
    public double getCashback() { return cashback; }
//    public int getBookingID() { return bookingID; }

    //Set function
    public void setCashback(double cashback) { this.cashback = cashback; }

    //Constructor
    public Customer(String name,double cashback) {
        this.name = name;
        this.cashback = cashback;
//        this.bookingID = bookingID;
    }
}
