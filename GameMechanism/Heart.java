package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;
public class Heart extends GameObject{
    public Heart(The2DGamePanel MyGamePanel) {
      super(MyGamePanel);
      ObjectName = "Heart";
      ObjectImage = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.HEART_FULL).resize(MyTile,MyTile));
      ObjectImage2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.HEART_HALF).resize(MyTile,MyTile));
      ObjectImage3 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(MyObjectGame.HEART_BLANK).resize(MyTile,MyTile));
    }
}
