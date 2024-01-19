package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;

public class Coin extends GameObject {
    public Coin(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        ObjectName = "Coin";
        ObjectImage = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.COIN_OBJECT).resize(MyTile,MyTile));
    }
}
