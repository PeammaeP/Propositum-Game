package Lab_Ch12;

import java.util.function.*;

class Pixel
{
    private int    xpos, ypos;
    private String color;
    
    public Pixel(int x, int y, String c)    { xpos = x; ypos = y; color = c; }
    
    public int getX()            { return xpos; }
    public int getY()            { return ypos; }
    public String getColor()     { return color; }
    public String getAll()       
    { return String.format("(%d, %d, %s)", xpos, ypos, color); }
    
    public boolean isNegativeX()            { return this.xpos < 0; }
    public boolean isNegativeY()            { return this.ypos < 0; }   
    public boolean isHigher(Pixel other)    { return this.ypos < other.ypos; }
    public boolean isLeftOf(Pixel other)    { return this.xpos < other.xpos; }
}

// ----- (2) Functional interface for lambda
interface OnePixel      { boolean test(Pixel p); }
interface TwoPixels     { boolean test(Pixel p1, Pixel p2); }
interface Transform     { Pixel apply(Pixel p); }

////////////////////////////////////////////////////////////////////////////////////////
public class w12_1_SimpleLambda 
{     
    public static void main(String[] args) {
        w12_1_SimpleLambda mainApp = new w12_1_SimpleLambda();
        //mainApp.OOPStyle();
        //mainApp.LambdaStyleOne();
        //mainApp.LambdaStyleTwo();
        //mainApp.LambdaStyleThree();
        mainApp.LambdaStyleOthers();
        
        System.out.println();
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void OOPStyle()
    {
        Pixel p1 = new Pixel(-222, 333, "blue");
        Pixel p2 = new Pixel(100, 100, "blue");
        Pixel p3 = new Pixel(200, 50, "red"); 
    
        // ----- (1) Traditional OOP
        System.out.println("\n\n----- Traditional OOP style -----\n");
        boolean answer;
        
        answer = p1.isNegativeX(); 
        System.out.printf("Negative X in %s ? %b \n", p1.getAll(), answer);
        
        answer = p2.isHigher(p3);  
        System.out.printf("%s higher than %s ? %b \n", p2.getAll(), p3.getAll(), answer);
        
        answer = p2.isLeftOf(p3);  
        System.out.printf("%s left of %s ? %b \n", p2.getAll(), p3.getAll(), answer);        
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void LambdaStyleOne()
    {
        Pixel p1 = new Pixel(-222, 333, "blue");
        Pixel p2 = new Pixel(100, 100, "blue");
        Pixel p3 = new Pixel(200, 50, "red"); 

        // ----- (2) Lambda : Types of parameters can be omitted
        //                    Implicit return for expression body
        System.out.println("\n\n----- Lambda style (predicate 1 argument) -----\n");        
        //OnePixel  lambda1;
        Predicate<Pixel> lambda1;
        
        lambda1 = arg -> arg.getX() < 0;              // don't need () for 1 param
        System.out.printf("Implementation 1 --> negative X in %s ? %b \n", 
                          p1.getAll(), lambda1.test(p1));
        
        lambda1 = (arg) -> (arg.getY() % 2) == 0;
        System.out.printf("Implementation 2 --> even Y in %s ? %b \n", 
                          p1.getAll(), lambda1.test(p1));
        
        lambda1 = (Pixel arg) -> arg.getColor().equals("red");
        System.out.printf("Implementation 3 --> is %s red ? %b \n", 
                          p1.getAll(), lambda1.test(p1));
        
        lambda1 = arg -> {  // statement body
                            int sum = arg.getX() + arg.getY();
                            return (sum % 2 > 0);
                         };
        System.out.printf("Implementation 4 --> positive sum in %s ? %b \n", 
                          p1.getAll(), lambda1.test(p1));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void LambdaStyleTwo()
    {
        Pixel p1 = new Pixel(-222, 333, "blue");
        Pixel p2 = new Pixel(100, 100, "blue");
        Pixel p3 = new Pixel(200, 50, "red");   
        
        System.out.println("\n\n----- Lambda style (predicate 2 arguments) -----\n");                
        TwoPixels lambda2;  
        //BiPredicate<Pixel, Pixel> lambda2; 
        
        lambda2 = (arg1, arg2) -> arg1.getY() < arg2.getY();       
        System.out.printf("Implementation 1 --> %s higher than %s ? %b \n", 
                          p2.getAll(), p3.getAll(), lambda2.test(p2, p3));
        
        lambda2 = (arg1, arg2) -> arg1.getX() < arg2.getX();
        System.out.printf("Implementation 2 --> %s left of %s ? %b \n", 
                          p2.getAll(), p3.getAll(), lambda2.test(p2, p3));
        
        lambda2 = (arg1, arg2) -> arg1.getColor().equals(arg2.getColor());
        System.out.printf("Implementation 3 --> same color for %s and %s ? %b \n", 
                          p2.getAll(), p3.getAll(), lambda2.test(p2, p3));           
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void LambdaStyleThree()
    {
        Pixel p1 = new Pixel(-222, 333, "red");
        Pixel p2 = new Pixel(100, 100, "blue");
        Pixel p3 = new Pixel(200, 50, "red");   

        // ------ (3) local variables used by lambda
        System.out.println("\n\n----- Lambda style (function + local vars) -----\n");  
        final int scale = 2;        // explicitly final
        int shift = 10;             // effectively final
        //Transform lambda3;
        //Function<Pixel, Pixel> lambda3;       // param & return type are the same
        UnaryOperator<Pixel> lambda3;         // so, this can be used instead
        
        lambda3 = arg -> {
                            int x = arg.getX() * scale;
                            int y = arg.getY() * scale;
                            return new Pixel(x, y, arg.getColor());
                         };
        System.out.printf("Implementation 1 --> scale %s to %s \n", 
                          p2.getAll(), lambda3.apply(p2).getAll());
        
        lambda3 = arg -> new Pixel( arg.getX()+shift, arg.getY()+shift, arg.getColor() );
        System.out.printf("Implementation 2 --> shift %s to %s \n", 
                          p2.getAll(), lambda3.apply(p2).getAll());
        
        // shift = 100;
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static void LambdaStyleOthers()
    {
        Pixel p1 = new Pixel(-222, 333, "red");
        Pixel p2 = new Pixel(100, 100, "blue");
        Pixel p3 = new Pixel(200, 50, "red");   

        System.out.println("\n\n----- Lambda style (others) -----\n");  
        
        int shift = 100;

        BiFunction<Pixel, Integer, Pixel> lambda4;          // params = Pixel, Integer        
        lambda4 = (arg1, arg2) -> new Pixel( arg1.getX()+arg2, arg1.getY()+arg2, arg1.getColor());
        System.out.printf("Function 2 arguments    --> shift %s to %s \n", 
                          p2.getAll(), lambda4.apply(p2, shift).getAll());
        
        BinaryOperator<Pixel> lambda5;                     // all params + return type are the same
        lambda5 = (arg1, arg2) -> new Pixel( arg1.getX()+arg2.getX(), arg1.getY()+arg2.getY(), "black" );
        System.out.printf("Operator 2 arguments    --> %s + %s = %s \n", 
                          p2.getAll(), p3.getAll(), lambda5.apply(p2, p3).getAll());
        
        lambda5 = (arg1, arg2) -> new Pixel( arg1.getX()-arg2.getX(), arg1.getY()-arg2.getY(), "white" );
        System.out.printf("Operator 2 arguments    --> %s - %s = %s \n", 
                          p2.getAll(), p3.getAll(), lambda5.apply(p2, p3).getAll());
        
        ToIntFunction<Pixel> lambda6;
        lambda6 = arg -> arg.getX() * arg.getY();
        System.out.printf("Function returns int    --> product xy of %s = %d \n", 
                          p2.getAll(), lambda6.applyAsInt(p2));     // return value is already int
        
        Consumer<Pixel> lambda7;
        lambda7 = arg -> System.out.printf("Consumer (returns void) --> color = %s \n", arg.getColor());
        lambda7.accept(p2);
        
        Supplier<Pixel> lambda8;
        lambda8 = () -> new Pixel(0, 0, "black");
        System.out.printf("Supplier returns Pixel  --> %s \n", lambda8.get().getAll());
    }
}
