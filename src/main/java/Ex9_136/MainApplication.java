
//6513136 Mahannop Thabua
package Ex9_136;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

class Series implements Comparable<Series> {
    private String name;
    private int seasons, episodes;
    private String airing;
    private double result; // dummy variable to keep calculation result
    public Series(String na, int se, int ep, String air) {
        name = na;
        seasons = se;
        episodes = ep;
        airing = air;
    }
    public void setName(String na) { name = na; }
    public void setSeasons(int se) { seasons = se; }
    public void setEpisodes(int ep) { episodes = ep; }
    public void setAiring(String air) { airing = air; }
    public void setResult(double res) { result = res; }
    public String getName() { return name; }
    public int getSeasons() { return seasons; }
    public int getEpisodes() { return episodes; }
    public String getAiring() { return airing; }
    public double getResult() { return result; }

    public int compareTo(Series other) {
        int x = Double.compare(other.result, this.result); // decreasing order
        if (x != 0) return x;
        else return this.name.compareToIgnoreCase(other.name); // alphabetical order
    }
}

public class MainApplication {
    public static void main(String[] args) {

        try {
            String Path = "src/main/java/Ex9_136/";
            String MySeriesFilePath = Path + "series.txt";

            Scanner MySeriesFileRead = new Scanner(new File(MySeriesFilePath));
            Scanner MyGeneralScan = new Scanner(System.in);

            ArrayList<Series> MySeriesList = new ArrayList<>();

            //skipping the first line
            if (MySeriesFileRead.hasNextLine()) MySeriesFileRead.nextLine();

            while (MySeriesFileRead.hasNext()) {

                String MySeriesReadLine = MySeriesFileRead.nextLine();

                String[] MySeries_Column = MySeriesReadLine.split(",");

                String MySeriesName = MySeries_Column[0].trim();
                int MySeriesSeasons = Integer.parseInt(MySeries_Column[1].trim());
                int MySeriesEpisodes = Integer.parseInt(MySeries_Column[2].trim());
                String MySeriesAiring = MySeries_Column[3].trim();

                //Creating The ArrayList in the constructor
                MySeriesList.add(new Series(MySeriesName, MySeriesSeasons, MySeriesEpisodes, MySeriesAiring));
            }
            //closing the file
            MySeriesFileRead.close();

            System.out.println("=== Stop-airing series, sorted by seasons ===");

            //Convert the ArrayList into the Stream
            Stream<Series> MySeriesStream = MySeriesList.stream();

            //Using the Stream Processing to sort the seasons
            MySeriesStream.filter(arg -> Objects.equals(arg.getAiring(), "no"))
                    .sorted(Comparator.comparing(Series::getSeasons).thenComparing(Series::compareTo))
                    .forEach(arg -> System.out.printf("%-24s  seasons = %2d%n", arg.getName().toLowerCase(), arg.getSeasons()));
            System.out.println();

            /*
            Note : The Method Reference in .sorted, it can be written in form ;

            MySeriesStream.sorted((series1, series2) -> {
                   int result = Integer.compare(series1.getSeasons(), series2.getSeasons());
                     if (result == 0) {
                        // If seasons are equal, use the natural order defined by compareTo
                        result = series1.compareTo(series2);
                     }
                    return result;
            });
             */

            //Receive the Minimum Episodes
            System.out.print("Enter Minimum of Episodes : ");
            int MyMinThreshold = MyGeneralScan.nextInt();
            MyGeneralScan.close();

            System.out.println();

            System.out.printf("=== All Series With >= %d episodes, sorted by episodes per season ===\n",MyMinThreshold);

            Predicate<Series> MySeasonPredicate = arg -> (arg.getEpisodes() >= MyMinThreshold);

            MySeriesList.stream().filter(MySeasonPredicate)
                   .sorted((series1,series2) -> {
                   double episodes_per_seasons1 = (double)series1.getEpisodes()/series1.getSeasons();
                   double episodes_per_seasons2 = (double)series2.getEpisodes()/series2.getSeasons();
                   int result = Double.compare(episodes_per_seasons2,episodes_per_seasons1);
                   if(result == 0) {
                     result = series1.compareTo(series2);
                   }
                 return result; })
            .forEach(arg -> System.out.printf("%20s      episodes = %-10d episodes per season = %-5.2f\n"
            ,arg.getName().toUpperCase()
            ,arg.getEpisodes()
            ,(double) arg.getEpisodes() / arg.getSeasons()));

            //counting the episodes which have been sorted.
            int total_series = (int) MySeriesList.stream()
                            .filter(MySeasonPredicate)
                            .mapToInt(arg -> arg.getEpisodes())
                            .summaryStatistics().getCount();

            System.out.printf("\nNumber of Series          = %d\n",total_series);

            //print the average of episodes per seasons by map function
            double avg_episodes_per_seasons = MySeriesList.stream().filter(MySeasonPredicate)
                            .mapToDouble(arg -> (double)arg.getEpisodes()/arg.getSeasons())
                            .summaryStatistics().getAverage();
            System.out.printf("Avg. episodes per seasons = %.2f\n\n\n",avg_episodes_per_seasons);

            //print the still-airing with < 400 episodes
            System.out.printf("=== Still-airing series with < %d episodes, sorted by episodes ===\n",MyMinThreshold);

            MySeriesList.stream().filter(MySeasonPredicate.negate())
                    .filter(arg -> Objects.equals(arg.getAiring(),"yes"))
                    .sorted((series1,series2) -> {
                        int episodes1 = series1.getEpisodes();
                        int episodes2 = series2.getEpisodes();
                        int result = Double.compare(episodes2,episodes1);
                        if(result == 0) {
                            result = series1.compareTo(series2);
                        }
                        return result;
                    })
                    .forEach(arg -> System.out.printf("%20s      episodes = %-10d\n"
                    ,arg.getName().toUpperCase()
                    ,arg.getEpisodes()));

            int total_series_negate = (int)MySeriesList.stream()
                            .filter(MySeasonPredicate.negate())
                            .filter(arg -> Objects.equals(arg.getAiring(),"yes"))
                            .mapToInt(arg -> arg.getEpisodes())
                            .summaryStatistics().getCount();

            System.out.printf("\nNumber of series = %-5d\n",total_series_negate);

            int total_episodes_negate = (int)MySeriesList.stream()
                    .filter(MySeasonPredicate.negate())
                    .filter(arg -> Objects.equals(arg.getAiring(),"yes"))
                    .mapToInt(arg -> arg.getEpisodes())
                    .summaryStatistics().getSum();

            System.out.printf("Total episodes   = %-,5d",total_episodes_negate);

        } catch (Exception e) {
            System.err.print("Error has occurred !");
            e.printStackTrace();
        }
    }
}

