package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;

public class PotionRed extends GameObject {
    public PotionRed(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        ObjectName = "PotionRed";
        ObjectImage = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.POTION_OBJECT).resize(MyTile,MyTile));
    }
}
