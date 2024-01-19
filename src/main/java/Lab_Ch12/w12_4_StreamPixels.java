package Lab_Ch12;

// Use class Pixel from w12_1_SimpleLambda.java

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class w12_4_StreamPixels
{
    public static void main(String[] args) {
        ArrayList<Pixel> myList = new ArrayList<>();
        myList.add( new Pixel(11, 11, "red") );
        myList.add( new Pixel(10, 20, "blue") );
        myList.add( new Pixel(50, 20, "blue") );
        myList.add( new Pixel(55, 10, "red") );
        myList.add( new Pixel(50, 60, "red") );

        Stream<Pixel> mystream = myList.stream();
        /*
        Stream<Pixel> mystream = Stream.of(new Pixel(11, 11, "red"),
                                           new Pixel(10, 20, "blue"), 
                                           new Pixel(50, 20, "blue"),
                                           new Pixel(55, 10, "red"),
                                           new Pixel(50, 60, "red"));
        */

        // (1) print all pixels
        System.out.println("===== Original pixels =====");
        Consumer<Pixel> printLambda = arg -> System.out.println(arg.getAll());
        mystream.forEach( printLambda );
        System.out.println();
        
        
        final int shift = 100;
        java.util.Scanner scan = new Scanner(System.in);
        mystream = myList.stream();
        // (2.1) feed red pixel to the next operation
        // (2.2) shift pixel
        // (2.3) print pixel
        System.out.println("===== Shift red pixels =====");
        Function<Pixel, Pixel> shiftLambda = arg -> new Pixel( arg.getX()+shift, 
                                                               arg.getY()+shift, 
                                                               arg.getColor() );
        mystream.filter( arg -> arg.getColor().equals("red") )
                .map( shiftLambda  )
                .forEach( printLambda );
        System.out.println();
        
        
        mystream = myList.stream();
        // (3) scale blue pixels
        System.out.println("===== Scale blue pixels =====");

        Function<Pixel, Pixel> scaleLambda = arg -> {
            System.out.println("Scale blue by = ");
            int scale = scan.nextInt();
            return new Pixel( arg.getX()*scale, arg.getY()*scale, arg.getColor() );
        };

        mystream.filter( arg -> arg.getColor().equals("blue") )
                .map( scaleLambda )
                .forEach( printLambda );        
        System.out.println();
    }
}
