package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;

public class Chests extends GameObject {
    public Chests(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        ObjectName = "Chest";
        ObjectImage = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.CHEST_OBJECT).resize(MyTile,MyTile));
        ObjectCollision = true;
    }
}
