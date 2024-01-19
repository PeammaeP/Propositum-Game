package Lab_Ch12;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class w12_3_StreamInt 
{
    public static void main(String[] args) 
    {   
        int [] myArray = {1, -9, 2, 8, -3, 7, 4, 6, -5};  
            
        ArrayList<Integer> myList = new ArrayList<>();
        for(int i : myArray) myList.add(i*11);
           
        IntConsumer printLambda_primitive    = arg -> System.out.printf("%d  ", arg);
        Consumer<Integer> printLambda_object = arg -> System.out.printf("%d  ", arg);

        // (1) If it doesn't give the ArrayList, it can be declared in the type of intStream.
        //IntStream primitiveStream    = IntStream.of(1, -9, 2, 8, -3, 7, 4, 6, -5);  
        //Stream<Integer> objectStream = Stream.of(11, -99, 22, 88, -33, 77, 44, 66, -55);

        //Assign the ArrayList myArray to IntStream
        IntStream primitiveStream    = IntStream.of(myArray);        // stream of primitive int
        Stream<Integer> objectStream = myList.stream();              // stream of Integer objects

        System.out.println("Original list");
        primitiveStream.forEach( printLambda_primitive ); System.out.println();
        objectStream.forEach( printLambda_object );

        System.out.print("\n\n");


        // (2) Stream from previous pipeline is terminated; so create a new one
        primitiveStream = IntStream.of(myArray); 
        objectStream    = myList.stream(); 
        System.out.println("Positive & sorted list");
        primitiveStream.filter( arg -> arg > 0 )                 // filter positives
                       .sorted()                                 // sort
                       .forEach( printLambda_primitive );        // print each element
        System.out.print("\n");


        objectStream.filter( arg -> arg > 0 )                 
                    .sorted()                                 
                    .forEach( printLambda_object );        
        System.out.print("\n\n");        


        // (3)
        primitiveStream  = IntStream.of(myArray);
        int [] oddResult = primitiveStream.filter( arg -> arg%2 !=0 )           // filter odds
                                          .toArray();                           // array of primitives
        System.out.println("Odd list");
        IntStream.of(oddResult).forEach( printLambda_primitive );
        double avg = IntStream.of(oddResult).summaryStatistics().getAverage();  
        System.out.printf("\nAverage of odds = %.2f \n\n", avg);


        // (4)
        objectStream = myList.stream(); 
        List<Integer> evenResult = objectStream.filter( arg -> arg%2 == 0 )     // filter evens
                                               .toList();                       // list of objects
        System.out.println("Even list");
        evenResult.stream().forEach( printLambda_object );
        int sumsquare = evenResult.stream()
                                  .reduce( 0, (arg1, arg2) -> arg1 = arg1 + arg2*arg2 );
                                //.reduce( 1000, (arg1, arg2) -> arg1 = arg1 + arg2*arg2 );
        System.out.printf("\nSum of even-square = %d \n\n", sumsquare);


        // (5)
        primitiveStream  = IntStream.of(myArray); 
        int [] absResult = primitiveStream.map(arg -> Math.abs(arg) )          // absolute
                                          .toArray();
        System.out.println("Absolute list");
        IntStream.of(absResult).forEach( printLambda_primitive );        
        int product = IntStream.of(absResult).reduce( 1, (arg1, arg2) -> arg1 = arg1 * arg2 );
        System.out.printf("\nProduct of absolutes = %,d \n\n", product);
    }
}
