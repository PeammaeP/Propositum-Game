package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;

public class KirbyMonster extends GameEntity {
    public KirbyMonster(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        Direction = "DOWN";
        speed = 2;
        setKirbyImage();
    }
    public void SetEntityAction() {
        /*if(getTileDistanceInCenter(MyGamePanel.MyPlayer) < 10) {
            MonsterTrackingPlayer(100);
        }*/
        checkAttackInRange(30,tileSize*10,tileSize*10);
    }
    public void setKirbyImage() {
        upAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_UP_1).resize(tileSize,tileSize));
        upAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_UP_2).resize(tileSize,tileSize));
        downAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_DOWN_1).resize(tileSize,tileSize));
        downAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_DOWN_2).resize(tileSize,tileSize));
        leftAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_LEFT_1).resize(tileSize,tileSize));
        leftAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_LEFT_2).resize(tileSize,tileSize));
        rightAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_RIGHT_1).resize(tileSize,tileSize));
        rightAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.KIRBY_RIGHT_2).resize(tileSize,tileSize));
    }
}
