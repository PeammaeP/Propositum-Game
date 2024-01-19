package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;

public class WoodDoor extends GameObject {
    public WoodDoor(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        ObjectName = "WoodDoor";
        ObjectImage = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.WOOD_DOOR_OBJECT).resize(MyTile,MyTile));
        ObjectCollision = true;
    }
}
