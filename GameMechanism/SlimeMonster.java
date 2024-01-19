package Project3_136.GameMechanism;

import Project3_136.MainClass.ImageIconToBufferedImageConverter;
import Project3_136.MainClass.MyImageIcon;

public class SlimeMonster extends GameEntity {
    public SlimeMonster(The2DGamePanel MyGamePanel) {
        super(MyGamePanel);
        this.MyGamePanel = MyGamePanel;
        EntityName = "Slime";
        Direction = "DOWN";
        speed = 2;
        maxLife = 4;
        Life = maxLife;
        //Creating the solid area of the monster
        MySolidArea.x = 3;
        MySolidArea.y = 18;
        MySolidArea.width = 42;
        MySolidArea.height = 30;
        EntitySolidAreaDefaultX = MySolidArea.x;
        EntitySolidAreaDefaultY = MySolidArea.y;
        setSlimeImage();
    }
    public void setSlimeImage() {
        upAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_1).resize(tileSize,tileSize));
        upAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_2).resize(tileSize,tileSize));
        downAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_1).resize(tileSize,tileSize));
        downAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_2).resize(tileSize,tileSize));
        leftAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_1).resize(tileSize,tileSize));
        leftAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_2).resize(tileSize,tileSize));
        rightAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_1).resize(tileSize,tileSize));
        rightAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(GameMonster.SLIME_DOWN_2).resize(tileSize,tileSize));
    }
    public void SetEntityAction() {
        /*if(getTileDistanceInCenter(MyGamePanel.MyPlayer) < 10) {
            MonsterTrackingPlayer(60);
        }*/
        checkAttackInRange(30,tileSize*10,tileSize*10);
    }
}
