package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;
public class Boots extends GameObject {
    public Boots(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        ObjectName = "Boots";
        ObjectImage = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.BOOTS_OBJECT).resize(MyTile,MyTile));
    }
}
