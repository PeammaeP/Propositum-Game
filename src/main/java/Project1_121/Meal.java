package Project1_121;

class Meal extends Room{
    private String mealType;
    private double mealRate;
    
    public Meal(String mealType, double mealRate){
        this.mealType = mealType;
        this.mealRate = mealRate;
    }
    
    public String getType(){ return mealType;}
    @Override
    public double getRate(){ return mealRate;}
    @Override
    
    public void printData(){
        System.out.printf("%-16s     rate = %,8.2f     rate++ = %,8.2f\n", mealType, mealRate, mealRate);
    }
}
