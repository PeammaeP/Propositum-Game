package Project3_136.MainClass;

import java.io.*;
import java.util.*;

public class WriteScoreClass {
    public String FilePath = "src/main/java/Project3_136/resources/ScoreFile/";
    private String ScoreFile = FilePath + "MyScore.csv";
    protected Scanner MyKeyboardScan;
    private String[][] GameData;
    private final int THRESHOLD_ROWS = 4;
    private int countingRows;
    protected boolean file_open = false;
    //creating the files by the constructor
    public WriteScoreClass() throws IOException {
        MyKeyboardScan = new Scanner(System.in);

        while(!file_open) {
            try(FileReader ignored = new FileReader(ScoreFile)) {
                file_open = true;
            }
            catch(FileNotFoundException e) {
               System.err.print(e);
               System.out.print("Enter Your File Again : ");
               ScoreFile = FilePath + MyKeyboardScan.next();
            }
        }
        //reading the file by buffered files
        try(BufferedReader MyBufferReader = new BufferedReader(new FileReader(ScoreFile))) {
            while(MyBufferReader.readLine() != null) {
                countingRows++;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        GameData = new String[countingRows][THRESHOLD_ROWS];
    }
    public void FileWriting() {
        try(BufferedWriter MyBufferedWriter = new BufferedWriter(new FileWriter(ScoreFile,false))) {
            PrintWriter MyPrintWriter = new PrintWriter(MyBufferedWriter);
            for(int i=0 ; i < countingRows ; i++) {
                MyPrintWriter.println(GameData[i][1] + "," + GameData[i][2] + "," + GameData[i][3]);
                MyPrintWriter.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void FileReading() {
        int rowsTemp = 0;
        String line;

        try(BufferedReader MyReaderfile = new BufferedReader(new FileReader(ScoreFile));){
            while ((line=MyReaderfile.readLine()) != null) {
                String[] MyBufferString = line.split(",");

                if (MyBufferString.length >= 3) {
                    GameData[rowsTemp][0] = String.valueOf(rowsTemp + 1);
                    GameData[rowsTemp][1] = MyBufferString[0];
                    GameData[rowsTemp][2] = MyBufferString[1];
                    GameData[rowsTemp][3] = MyBufferString[2];
                } else {
                    // Handle the case where the array does not have enough elements.
                    // You can print an error message or take appropriate action.
                    System.err.println("Invalid data format in line: " + line);
                    break;
                }
                rowsTemp++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void scoreSorting(String[][] myData) {
        int ranking = 1;
        int myColumn = 4;

        Arrays.sort(myData, (MyObject1, MyObject2) -> {
            String score1 = MyObject1[myColumn - 2];
            String score2 = MyObject2[myColumn - 2];

            if (score1 == null && score2 == null) {
                return 0;
            } else if (score1 == null) {
                return 1;
            } else if (score2 == null) {
                return -1;
            }
            return Double.compare(Double.parseDouble(score2), Double.parseDouble(score1));
        });

        for (int i = 0; i < countingRows; i++) {
            for (int j = 0; j < myColumn; j++) {
                if (j == 0) {
                    myData[i][j] = String.valueOf(ranking);
                    ranking++;
                }
            }
        }
    }

    public void RowIncreasing() {
        countingRows++;
        GameData = new String[countingRows][THRESHOLD_ROWS];
    }
    public void SettingData(int rows,String Username,double score,String DifficultySelected) {
        GameData[rows][0] = String.valueOf(rows+1);
        GameData[rows][1] = Username;
        GameData[rows][2] = String.valueOf(score);
        GameData[rows][3] = DifficultySelected;
    }
    public int getCountingRows() { return countingRows; }
    public String[][] getGameData() {
        return GameData;
    }
}
