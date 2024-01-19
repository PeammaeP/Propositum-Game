package Ex4_136;

import java.util.Scanner;
import java.lang.String;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

class AnythingElseException extends Exception {
    public AnythingElseException(String message) { super(message); }
};

class Series implements Comparable<Series> {

    private final String name;
    private final int seasons, episodes;
    private final String airing;

    //Creating Constructor
    public Series(String name, int seasons, int episodes ,String airing) throws AnythingElseException {
        this.name = name;
        this.seasons = seasons;
        this.episodes = episodes;
        this.airing = airing;
    }

    @Override
    public int compareTo(Series other) {
     /*
     2.2) Sort Series objects in decreasing order of seasons
     2.3) If seasons are equal, place active series (still airing) before non-active series
     2.4) Among active series with the same number of seasons, sort them in decreasing order of episodes
     , then by name (if episodes are equal)
     2.5) Among non-active series with the same number of seasons,
     sort them in decreasing order of episodes, then by name (if episodes are equal) */

        //if current season is more than next season --> up
        if(this.seasons > other.seasons) return -1;
            //if current season is more than next season --> down
        else if(this.seasons < other.seasons) return 1;
            //y - n --> 25 - 13 = 12 --> yes is up
        else if(this.airing.compareToIgnoreCase(other.airing) > 0) return -1;
            //n - y --> 13 - 25 = -12 --> no is down
        else if(this.airing.compareToIgnoreCase(other.airing) < 0) return 1;

            //comparing the episodes which descending sort
        else if(this.episodes > other.episodes) return -1;

        else if(this.episodes < other.episodes) return 1;

            //the first alphabet is up before the last alphabet
        else if(this.name.compareToIgnoreCase(other.name) < 0) return -1;

        else if(this.name.compareToIgnoreCase(other.name) > 0) return 1;

        else return 0;
    }

    public void print_the_series() {
        System.out.printf("%-21s %3d %13s %14d\n",name,seasons,airing,episodes);
    }
};

class TheInputSeries {
    private String path , fileName;

    private Scanner series_scan;

    private ArrayList<Series> The_Series = new ArrayList<>();

    public TheInputSeries(String path,String fileName,Scanner series_scan) {
        this.path = path;
        this.fileName = fileName;
        this.series_scan = series_scan;
    }

    public void SeriesExecute(String Series_line, ArrayList<Series> The_Series) {

        try{

            String[] series_col = Series_line.split(",");

                //reading and convert in file
                String series_name = series_col[0].trim();
                int total_seasons = Integer.parseInt(series_col[1].trim());
                if (total_seasons < 0) throw new AnythingElseException("For input: " + "\"" + total_seasons + "\"");
                //if error has occurred in seasons < 0 then throw exception
                int total_episodes = Integer.parseInt(series_col[2].trim());
                if (total_episodes < 0) throw new AnythingElseException("For input : " + "\"" + total_episodes + "\"");
                //if error has occurred in episodes < 0 then throw exception
                String is_airing = series_col[3].trim();
                if (!(is_airing.equalsIgnoreCase("yes") || is_airing.equalsIgnoreCase("no")))
                    throw new AnythingElseException("For input: " + "\"" + is_airing + "\"");
                //if error has occurred in is_airing then throw exception
                /* it can be groups of conditions here -->
                * if(total_seasons < 0 || total_episodes < 0 || !(is_airing.equalsIgnoreCase("yes") || !(is_airing.equalsIgnoreCase("no")))
                * throw new AnythingElseException("For input: " + "\"" + is_airing + "\""); */
                //adding the All_Series Array into the ArrayList
                The_Series.add(new Series(series_name, total_seasons, total_episodes, is_airing));

        }
        catch(RuntimeException | AnythingElseException e) {
            System.out.print("Error Occurred --> ");
            System.out.println(e);
            System.out.println(Series_line);
            System.out.println();
        }
    }

    public void try_to_open_correct_file() {

        boolean is_open_correct = false;

        while (!is_open_correct) {

            try (Scanner file_scan = new Scanner(new File(path + fileName))) {
                is_open_correct = true;
                //Skipping Read The First Line
                if(file_scan.hasNextLine()) file_scan.nextLine();
                //Keeping Read Next Line
                while (file_scan.hasNext()) {
                    SeriesExecute(file_scan.nextLine(),The_Series);
                }

            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.print("Please Input The Correct File Name --> ");
                fileName = series_scan.next();
                System.out.println();
            }
        }

        Collections.sort(The_Series);
        System.out.printf("%-20s %-13s %-13s %-13s\n","Series","Seasons","Airing","Episodes");
        System.out.println("=".repeat(57));
        for(Series S : The_Series) S.print_the_series();
    }
};

public class Ex4_136 {

    public static void main(String args[]) {

        String path = "src/main/Java/Ex4_136/";
        String input_series_error = "series_error.txt";
        Scanner Series_Scan = new Scanner(System.in);
        TheInputSeries Series = new TheInputSeries(path,input_series_error,Series_Scan);
        Series.try_to_open_correct_file();
    }
}