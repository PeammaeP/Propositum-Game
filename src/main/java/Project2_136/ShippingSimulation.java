package Project2_136;

import java.util.ArrayList;

public class ShippingSimulation {
    public static void main(String[] args) {
        ShippingSimulation shippingSimulation = new ShippingSimulation();

        // Read data from config.txt
        final String PATH = "src/main/java/Project2_136/";
        final String CONFIGFILE = "config.txt";
        Config config = new Config(PATH, CONFIGFILE);

        // Display config data
        System.out.println();
        config.displayConfigData();

        // Create ArrayList of OneProducts (shared by all threads) and ArrayList of FactoryThreads, then start FactoryThreads
        for (int day = 1; day <= config.getDayOfSimulation(); day++) {
            System.out.println(String.format("Theard %-4s  >>  --------------------------------------------------------------------------------------------", Thread.currentThread().getName()));
            System.out.println(String.format("Theard %-4s  >>  Day %d", Thread.currentThread().getName(), day));

            shippingSimulation.oneDayProcess(config);
        }

        // After completing all days of simulation, report number of shipping failures for each product
        System.out.println(String.format("Theard %-4s  >>  --------------------------------------------------------------------------------------------", Thread.currentThread().getName()));
        System.out.println(String.format("Thread %-4s  >>  Shipping summary", Thread.currentThread().getName()));
        config.getProducts().forEach(x -> x.displayFailure());
    }

    private void oneDayProcess(Config config) {
        ArrayList<FactoryThread> factoryThreads_one = new ArrayList<>();
        config.getFactoryThreads().forEach(x -> factoryThreads_one.add(new FactoryThread(
                x.getName(),
                x.getProducts(),
                x.getProductionCapacities())));
        factoryThreads_one.forEach(x -> x.start());

        factoryThreads_one.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        for (int i = 0; i < config.getProducts().size(); i++) {
            config.getProducts().get(i).ship(config.getDailyShipping().get(i));
        }
    }
}
