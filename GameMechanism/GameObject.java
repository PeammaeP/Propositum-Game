package Project3_136.GameMechanism;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject extends JLabel{
    //building the SolidArea of object to frame the object
    The2DGamePanel MyGamePanel;
    public Rectangle ObjectSolidArea = new Rectangle(0,0,48,48);
    public int ObjectX,ObjectY;
    public String ObjectName;
    public BufferedImage ObjectImage,ObjectImage2,ObjectImage3;
    public boolean ObjectCollision = false;
    public int ObjectSolidAreaDefaultX = 0;
    public int ObjectSolidAreaDefaultY = 0;
    int MyTile = TheGameConstants.GAME_TILE_SIZE;
    public GameObject(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
    }
    public void drawGameObject(Graphics2D MyG2, The2DGamePanel MyGamePanel) {

        int screenX = ObjectX - MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.screenX;
        int screenY = ObjectY - MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.screenY;

        if(ObjectX + MyTile > MyGamePanel.MyPlayer.curX - MyGamePanel.MyPlayer.screenX &&
                ObjectX - MyTile < MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.screenX &&
                ObjectY + MyTile > MyGamePanel.MyPlayer.curY - MyGamePanel.MyPlayer.screenY &&
                ObjectY - MyTile < MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.screenY) {
           MyG2.drawImage(ObjectImage, screenX, screenY, MyTile, MyTile, null);
        }
    }
}
