package Ex5_136;

import java.util.*;
import java.lang.Thread;
import java.io.*;

class RandomThread extends Thread {
    private PrintWriter out; // each thread writes to separate file
    private ArrayList<Integer> values;
    private int numValues, targetSum = 0, currentSum = 0, round = 1;

    public RandomThread(String n, int numValues, int targetSum) {
        super(n);
        this.numValues = numValues;
        this.targetSum = targetSum;
        this.values = new ArrayList<Integer>();
    }

    public void run() {
        String path = "src/main/Java/Ex5_136/";
        String outfile = path + getName() + ".txt";
        File output_file = new File(outfile);

        try {
            if(output_file.exists()) {
                output_file.delete();
            }

            out = new PrintWriter(output_file);

            do {
                Random random = new Random();
                boolean isAllValuesEqual = true;
                values.clear();

                for (int i = 0; i < this.numValues; i++) {
                    int random_values = random.nextInt(10) + 1;
                    values.add(random_values);
                    if (i > 0 && !(values.get(values.size() - 2)).equals(values.get(values.size() - 1))) {
                        isAllValuesEqual = false;
                    }
                }
                Collections.sort(values);

                if(numValues % 2 == 0) {
                    currentSum +=  values.get(values.size()/2 - 1);
                }
                else {
                    currentSum += values.get(values.size()/2);
                }

            String Thread_Output = String.format("Round %3d >> ", round );
                Thread_Output += String.format("Random Values = ");

            for(int i = 0 ; i < values.size() ; i++) {
                Thread_Output += String.format("%5d",values.get(i));
            }

            Thread_Output += String.format("  Current Sum = %5d " , currentSum);

            //Print in the Output File
            out.println(Thread_Output);
            //Increment of Rounding
            round++;
            if(isAllValuesEqual) break;

            }while (!(currentSum >= targetSum));
            out.close();
        }
        catch(Exception e) {
           System.err.print(e);
           System.exit(-1);
        }

        System.out.println(getName() + " finishes in " + round + " rounds");
    }
}

public class Ex5_136 {
    public static void main(String args[]) {

        try {
            Scanner scan = new Scanner(System.in);
            int Input_Rand=0 , Input_Sum=0 , Input_numThreads=0 , Bound_Input_Rand = 10;

            if(Input_Rand <= Bound_Input_Rand) {
                System.out.print("Number of Random Values = ");
                Input_Rand = scan.nextInt();
                System.out.println();
            }

            System.out.print("Target Sum = ");
            Input_Sum = scan.nextInt();
            System.out.println();

            System.out.print("Number of Threads = ");
            Input_numThreads = scan.nextInt();
            System.out.println();

            for(int i=0 ; i < Input_numThreads ; i++) {
              RandomThread SuperThread = new RandomThread("Thread_" + i ,Input_numThreads,Input_Sum);
              SuperThread.start();
            }
        }
        catch(Exception e) {
            System.out.println("Error is occurred !!");
            System.exit(-1);
        }
    }
}
