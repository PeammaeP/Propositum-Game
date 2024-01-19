package Project2_136;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Config {
    private final String PATH;
    private final String CONFIGFILE;
    private int dayOfSimulation;
    private ArrayList<OneProduct> products;
    private ArrayList<Integer> dailyShipping;
    private ArrayList<FactoryThread> factoryThreads;

    public Config(final String path, final String configFile) {
        this.PATH = path;
        this.CONFIGFILE = configFile;

        products = new ArrayList<>();
        dailyShipping = new ArrayList<>();
        factoryThreads = new ArrayList<>();

        readConfigFile();
    }

    //------------------------------------- getter / setter ----------------------------------------------

    public ArrayList<OneProduct> getProducts() {
        return products;
    }

    public ArrayList<FactoryThread> getFactoryThreads() {
        return factoryThreads;
    }

    public ArrayList<Integer> getDailyShipping() {
        return dailyShipping;
    }

    public int getDayOfSimulation() {
        return dayOfSimulation;
    }

    // endregion

    // region other class methods

    // region file processing

    /**
     * Generate string of file path which in the 'Path' directory.
     *
     * @param fileName File name which in the following path. This must contain file extension.
     * @return String of full file path which corresponds to file's directory.
     */
    private String FullFilePath(String fileName) {
        try {
            System.out.println(String.format("Thread %-4s  >>  Read File From: %s", Thread.currentThread().getName(), PATH + fileName));
            if (!new File(PATH + fileName).exists())
                throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println(String.format("Thread %-4s  >>  File: %s is not found", Thread.currentThread().getName(), PATH + fileName));
            System.out.println(String.format("Thread %-4s  >>  %s", Thread.currentThread().getName(), e));
            System.out.println();

            System.out.printf("Thread %-4s  >>  Enter new file name = ", Thread.currentThread().getName());
            Scanner scanner = new Scanner(System.in);
            return FullFilePath(scanner.next());
        }

        return PATH + fileName;
    }

    private void readConfigFile() {
        try (Scanner fp = new Scanner(new File(FullFilePath(CONFIGFILE)))) {
            //line 1
            dayOfSimulation = Integer.parseInt(fp.nextLine());

            //line 2
            String lines = fp.nextLine();
            String[] buf = lines.split(",");
            for (String product: buf) {
                products.add(new OneProduct(product.trim()));
            }

            //line 3
            lines = fp.nextLine();
            buf = lines.split(",");
            for(int i = 1; i < buf.length; i++) {
                dailyShipping.add(Integer.parseInt(buf[i].trim()));
            }

            //other lines
            while (fp.hasNext()) {
                ArrayList<Integer> productionCapacities = new ArrayList<>();

                lines = fp.nextLine();
                buf = lines.split(",");
                String threadName = buf[0].trim();
                for(int i = 1; i < buf.length; i++) {
                    productionCapacities.add(Integer.parseInt(buf[i].trim()));
                }

                factoryThreads.add(new FactoryThread(threadName, products, productionCapacities));
            }
        } catch (FileNotFoundException ignore) {}
    }

    // endregion

    public void displayConfigData() {
        System.out.println(String.format("Thread %-4s  >>  days of simulation = %d", Thread.currentThread().getName(), dayOfSimulation));

        System.out.printf("Thread %-4s  >>  daily shipping         >>  ", Thread.currentThread().getName());
        for (int i = 0; i < products.size(); i++) System.out.printf("%3d %-20s", dailyShipping.get(i), products.get(i).getName());
        System.out.println();

        factoryThreads.forEach(x -> x.displayProductionCapacities());
    }

    // endregion
}
