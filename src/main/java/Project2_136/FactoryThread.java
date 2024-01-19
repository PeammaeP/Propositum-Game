/*
    Group Member(s):
    Palarp Wasuwat : 6413108
    Kobkit Ruangsuriyakit : 6413211
    Teerapat Phopit : 6413216
 */
package Project2_136;

import java.util.ArrayList;
import java.util.Random;

public class FactoryThread extends Thread {
    private ArrayList<OneProduct> products;
    private ArrayList<Integer> productionCapacities;

    public FactoryThread(String name, ArrayList<OneProduct> products, ArrayList<Integer> productionCapacities) {
        super(name);
        this.products = products;
        this.productionCapacities = productionCapacities;
    }

    public ArrayList<OneProduct> getProducts() {
        return products;
    }

    public ArrayList<Integer> getProductionCapacities() {
        return productionCapacities;
    }

    public void displayProductionCapacities() {
        System.out.printf("Thread %-4s  >>  daily capacity of %-4s >>  ", Thread.currentThread().getName(), super.getName());
        for (int i = 0; i < products.size(); i++) System.out.printf("%3d %-20s", productionCapacities.get(i), products.get(i).getName());
        System.out.println();
    }

    @Override
    public void run() {
        Random random = new Random();
        random.setSeed(Math.abs((Thread.currentThread().getName().hashCode() + productionCapacities.hashCode()) % random.nextLong(10000)));

        for (int i = 0; i < products.size(); i++) {
            try {
                Thread.sleep(random.nextInt((products.size()-i) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            products.get(i).add(productionCapacities.get(i));
        }
    }
}
