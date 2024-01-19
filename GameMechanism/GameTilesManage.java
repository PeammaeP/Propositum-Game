package Project3_136.GameMechanism;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;
public class GameTilesManage extends JLabel {
    The2DGamePanel MyGamePanel;
    Scanner MapScan;
    public GameTiles[] tile;
    public int[][][] mapTileCount;
    int tileSize = TheGameConstants.GAME_TILE_SIZE;
    int maxWorldCol = TheGameConstants.MAX_WORLD_COLUMN;
    int maxWorldRow = TheGameConstants.MAX_WORLD_ROW;
    int maxMap = TheGameConstants.maxMap;
    public GameTilesManage(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
        tile = new GameTiles[50];
        mapTileCount = new int[maxMap][maxWorldCol][maxWorldRow];
        setTileImage();
        //Loading The First Map ( @1 Map )
        MyMapLoading(TheGameMap.PATH_WORLD_MAP_3,0);
        //Loading The Second Map ( @02 Map )
        MyMapLoading(TheGameMap.PATH_WORLD_MAP_2,1);
    }
    public void MyMapLoading(String FILE_PATH, int myMap) {
        try {
            int read_col = 0;
            int read_row = 0;
            MapScan = new Scanner(new File(FILE_PATH));

            while (MapScan.hasNext() && read_col < maxWorldCol && read_row < maxWorldRow) {
                String TextLine = MapScan.nextLine();
                while (read_col < maxWorldCol) {
                    String[] num = TextLine.split(" ");
                    int MapNumber = Integer.parseInt(num[read_col]);
                    mapTileCount[myMap][read_col][read_row] = MapNumber;
                    read_col++;
                }
                if (read_col == maxWorldCol) {
                    read_col = 0;
                    read_row++;
                }
            }
            MapScan.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public void drawTileImage(Graphics2D MyGraphics) {

        int worldCol=0, worldRow=0;

        while(worldCol < maxWorldCol && worldRow < maxWorldRow) {

            int tileNum = mapTileCount[MyGamePanel.currentMap][worldCol][worldRow];
            int worldX = worldCol * tileSize; //both worldX , worldY are the player position on the game
            int worldY = worldRow * tileSize;
            //interact with the camera position --> partially displaying the current player position
            //both screenX , screenY are the drawing position of the tiles
            int screenX = worldX - MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.screenX;
            int screenY = worldY - MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.screenY;

            if(worldX + tileSize > MyGamePanel.MyPlayer.curX - MyGamePanel.MyPlayer.screenX &&
                    worldX - tileSize < MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.screenX &&
                    worldY + tileSize > MyGamePanel.MyPlayer.curY - MyGamePanel.MyPlayer.screenY &&
                    worldY - tileSize < MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.screenY) {
                MyGraphics.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
            }

            worldCol++;

            if(worldCol == maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
    public void setTileImage() {
        /// PLACEHOLDER
        tile[0] = new GameTiles();
        tile[0].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[1] = new GameTiles();
        tile[1].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[2] = new GameTiles();
        tile[2].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[3] = new GameTiles();
        tile[3].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[4] = new GameTiles();
        tile[4].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[5] = new GameTiles();
        tile[5].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[6] = new GameTiles();
        tile[6].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[7] = new GameTiles();
        tile[7].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[8] = new GameTiles();
        tile[8].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[9] = new GameTiles();
        tile[9].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        ///////
        tile[10] = new GameTiles();
        tile[10].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_00).resize(tileSize,tileSize));
        tile[11] = new GameTiles();
        tile[11].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.GRASS_01).resize(tileSize,tileSize));
        tile[12] = new GameTiles();
        tile[12].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_00).resize(tileSize,tileSize));
        tile[12].isCollision = true;
        tile[13] = new GameTiles();
        tile[13].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_01).resize(tileSize,tileSize));
        tile[13].isCollision = true;
        tile[14] = new GameTiles();
        tile[14].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_02).resize(tileSize,tileSize));
        tile[14].isCollision = true;
        tile[15] = new GameTiles();
        tile[15].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_03).resize(tileSize,tileSize));
        tile[15].isCollision = true;
        tile[16] = new GameTiles();
        tile[16].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_04).resize(tileSize,tileSize));
        tile[16].isCollision = true;
        tile[17] = new GameTiles();
        tile[17].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_05).resize(tileSize,tileSize));
        tile[17].isCollision = true;
        tile[18] = new GameTiles();
        tile[18].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_06).resize(tileSize,tileSize));
        tile[18].isCollision = true;
        tile[19] = new GameTiles();
        tile[19].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_07).resize(tileSize,tileSize));
        tile[19].isCollision = true;
        tile[20] = new GameTiles();
        tile[20].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_08).resize(tileSize,tileSize));
        tile[20].isCollision = true;
        tile[21] = new GameTiles();
        tile[21].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_09).resize(tileSize,tileSize));
        tile[21].isCollision = true;
        tile[22] = new GameTiles();
        tile[22].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_10).resize(tileSize,tileSize));
        tile[22].isCollision = true;
        tile[23] = new GameTiles();
        tile[23].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_11).resize(tileSize,tileSize));
        tile[23].isCollision = true;
        tile[24] = new GameTiles();
        tile[24].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_12).resize(tileSize,tileSize));
        tile[24].isCollision = true;
        tile[25] = new GameTiles();
        tile[25].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WATER_13).resize(tileSize,tileSize));
        tile[25].isCollision = true;
        tile[26] = new GameTiles();
        tile[26].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_00).resize(tileSize,tileSize));
        tile[27] = new GameTiles();
        tile[27].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_01).resize(tileSize,tileSize));
        tile[28] = new GameTiles();
        tile[28].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_02).resize(tileSize,tileSize));
        tile[29] = new GameTiles();
        tile[29].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_03).resize(tileSize,tileSize));
        tile[30] = new GameTiles();
        tile[30].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_04).resize(tileSize,tileSize));
        tile[31] = new GameTiles();
        tile[31].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_05).resize(tileSize,tileSize));
        tile[32] = new GameTiles();
        tile[32].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_06).resize(tileSize,tileSize));
        tile[33] = new GameTiles();
        tile[33].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_07).resize(tileSize,tileSize));
        tile[34] = new GameTiles();
        tile[34].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_08).resize(tileSize,tileSize));
        tile[35] = new GameTiles();
        tile[35].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_09).resize(tileSize,tileSize));
        tile[36] = new GameTiles();
        tile[36].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_10).resize(tileSize,tileSize));
        tile[37] = new GameTiles();
        tile[37].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_11).resize(tileSize,tileSize));
        tile[38] = new GameTiles();
        tile[38].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.ROAD_12).resize(tileSize,tileSize));
        tile[39] = new GameTiles();
        tile[39].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.EARTH).resize(tileSize,tileSize));
        tile[40] = new GameTiles();
        tile[40].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.WALL).resize(tileSize,tileSize));
        tile[40].isCollision = true;
        tile[41] = new GameTiles();
        tile[41].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.TREE).resize(tileSize,tileSize));
        tile[41].isCollision = true;
        tile[42] = new GameTiles();
        tile[42].image = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(TheGameTiles.HUT).resize(tileSize,tileSize));
    }
}
