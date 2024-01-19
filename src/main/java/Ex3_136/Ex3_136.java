package Ex3_136;

import java.util.*;
import java.io.*;


class Series implements Comparable<Series> { 
    
     private final String name;
     private final int seasons, episodes; 
     private final String airing;
     
     //Creating Constructor
     public Series(String name, int seasons, int episodes ,String airing) {
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
         if (this.seasons > other.seasons) return -1;
             //if current season is more than next season --> down
         else if (this.seasons < other.seasons) return 1;
             //y - n --> 25 - 13 = 12 --> yes is up
         else if (this.airing.compareToIgnoreCase(other.airing) > 0) return -1;
             //n - y --> 13 - 25 = -12 --> no is down
         else if (this.airing.compareToIgnoreCase(other.airing) < 0) return 1;

             //comparing the episodes which descending sort
         else if (this.episodes > other.episodes) return -1;

         else if (this.episodes < other.episodes) return 1;

             //the first alphabet is up before the last alphabet
         else if (this.name.compareToIgnoreCase(other.name) < 0) return -1;

         else if (this.name.compareToIgnoreCase(other.name) > 0) return 1;

         else return 0;
     }
     public void print_the_series() {
         System.out.printf("%-21s %3d %13s %14d\n",name,seasons,airing,episodes);
     }
};

public class Ex3_136 {
    
    public static void main(String args[]) {
        
        try {
          String path = "src/main/Java/Ex3_136/"; 
          String input_series = path + "series.txt";
          //creating and reading the file path 
          Scanner read_series = new Scanner(new File(input_series));

          ArrayList<Series> The_Series = new ArrayList<>();

          //skip the first line
          if(read_series.hasNextLine()) read_series.nextLine();

          //reading file
          while(read_series.hasNext()) {
              String series_line = read_series.nextLine();

              String [] series_col = series_line.split(",");
              //reading and convert in file
              String series_name = series_col[0].trim();
              int total_seasons = Integer.parseInt(series_col[1].trim());
              int total_episodes = Integer.parseInt(series_col[2].trim());
              String is_airing = series_col[3].trim();
              //create array of series
              The_Series.add(new Series(series_name,total_seasons,total_episodes,is_airing));
          }
          
          //Sorting ArrayList
            Collections.sort(The_Series);
          //printing format
            System.out.printf("%-20s %-13s %-13s %-13s\n","Series","Seasons","Airing","Episodes");
            System.out.println("=".repeat(57));
          //for each print the Series in ArrayList
            for(Series S : The_Series) S.print_the_series();
        }

        catch(Exception e) {
            System.out.println("!!! The Error was occurred !!!");
            System.err.println(e);
            System.exit(-1);
        }
    }
}
