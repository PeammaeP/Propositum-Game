package Project3_136.GameMechanism;

import Project3_136.MainClass.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GamePlayerClass extends GameEntity {
    public The2DGamePanel PlayerGamePanel;
    public KeyEventHandle PlayerKeyEvent;
    public final int maxScreenX = TheGameConstants.SCREEN_WIDTH;
    public final int maxScreenY = TheGameConstants.SCREEN_HEIGHT;
    public int screenX , screenY;
    public int collisionArea = tileSize - TheGameConstants.ORIGIN_TILE_SIZE;
    public int collisionX = TheGameConstants.ORIGIN_TILE_SIZE/2;
    public int collisionY = TheGameConstants.ORIGIN_TILE_SIZE;
    public int PickUpCoin = 0;
    String CharacterSkin;
    public GamePlayerClass(The2DGamePanel PlayerGamePanel, KeyEventHandle PlayerKeyEvent,String CharacterSkin) {
        super(PlayerGamePanel);
        this.PlayerGamePanel = PlayerGamePanel;
        this.PlayerKeyEvent = PlayerKeyEvent;
        this.CharacterSkin = CharacterSkin;

        screenX = maxScreenX/2 - tileSize/2; //set into the center position
        screenY = maxScreenY/2 - tileSize/2; //set into the center position

        MySolidArea = new Rectangle(collisionX,collisionY,collisionArea,collisionArea);

        //set the default solid area because we want the change the collisionX and collisionY in further
        EntitySolidAreaDefaultX = MySolidArea.x;
        EntitySolidAreaDefaultY = MySolidArea.y;

        setDefaultPlayerStats();
        setPlayerImageWalk();
    }
    public void setDefaultPlayerStats() {
        curX = 23 * tileSize;
        curY = 21 * tileSize;
        Direction = "UP";
        speed = 4;
        maxLife = 6;
        Life = maxLife;
        setBounds(curX,curY,tileSize,tileSize);
    }
    public void setDefaultPositions() {
        curX = 23 * tileSize;
        curY = 21 * tileSize;
        Direction = "UP";
        setBounds(curX,curY,tileSize,tileSize);
    }
    public void updatePlayerEvents() {

        if(PlayerKeyEvent.upPressed || PlayerKeyEvent.downPressed
          || PlayerKeyEvent.leftPressed || PlayerKeyEvent.rightPressed) {

        if(PlayerKeyEvent.upPressed) {
            this.Direction = "UP";
        }
        else if(PlayerKeyEvent.downPressed) {
            this.Direction = "DOWN";
        }
        else if(PlayerKeyEvent.leftPressed) {
            this.Direction = "LEFT";
        }
        else {
            this.Direction = "RIGHT";
        }

        setBounds(curX,curY,tileSize,tileSize);
        //check the collision event
        CollisionFlag = false;
        //check the current frame of the game such that has collision ?
        PlayerGamePanel.MyCollisionEvent.checkCollisionOnEntity(this);


        //check the object collision
        int ObjectIndex = PlayerGamePanel.MyCollisionEvent.checkCollisionOnObject(this,true);
        //The player pick up the object called method after collision
        pickUpObject(ObjectIndex);

        //check Monster collision
        int MonsterIndex = PlayerGamePanel.MyCollisionEvent.checkCollisionPlayerOnEntity(this,MyGamePanel.MyMonster);
        collidingMonster(MonsterIndex);

        //check the player event
        PlayerGamePanel.MyGameEventHandle.hasTheEvent();
        PlayerGamePanel.MyKeyEvent.enterPressed = false;

        //if collisionFlag is false, Player can move
        if(!CollisionFlag) {
            switch(Direction) {
                case "UP" : curY -= (int) speed; break;
                case "DOWN" : curY += (int) speed; break;
                case "LEFT" : curX -= (int) speed; break;
                case "RIGHT" : curX += (int) speed; break;
            }
        }

        spriteCounter++;

        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
           }
        }

        if(invincible) {
            invincibleCounted++;
            if(invincibleCounted > 60) {
                invincible = false;
                invincibleCounted = 0;
            }
        }

        if(Life <= 0) {
            MyGamePanel.GameState = MyGamePanel.Game_Over;
            System.out.println("=============================");
            System.out.println("Player has died ! : ");
            System.out.println("Go to the Components Frame ==> ");
            System.out.println("=============================");
        }
    }
    public void collidingMonster(int index) {
        if(index != 555) {
            if(!invincible) {
                Life -= 1;
                invincible = true;
                setBreakEffectSound();
            }
        }
    }
    public void pickUpObject(int index) {

       if(index != 555) {
           String ObjectName = PlayerGamePanel.MyGameObject[PlayerGamePanel.currentMap][index].ObjectName;

           switch(ObjectName) {
               case "Coin":
                   setCoinEffectSound();
                   PickUpCoin++;
                   PlayerGamePanel.MyGameObject[PlayerGamePanel.currentMap][index] = null;
                   PlayerGamePanel.MyInGameUI.showMessage("You Got a Coin !!");
                   break;
               case "WoodDoor":
                   if(PickUpCoin > 0) {
                       setBreakEffectSound();
                       PlayerGamePanel.MyGameObject[PlayerGamePanel.currentMap][index] = null;
                       PickUpCoin--;
                       PlayerGamePanel.MyInGameUI.showMessage("You Opened The Door !!");
                   }
                   else {
                       PlayerGamePanel.MyInGameUI.showMessage("Need The Coin to Open The Door !");
                   }
                   break;
               case "Boots":
                   setCoinEffectSound();
                   speed += 1;
                   PlayerGamePanel.MyGameObject[PlayerGamePanel.currentMap][index] = null;
                   PlayerGamePanel.MyInGameUI.showMessage("You got the Boots !!");
                   break;
               case "PotionRed":
                   setCoinEffectSound();
                   if(Life <= 4) {
                       Life += 2;
                   }
                   PlayerGamePanel.MyGameObject[PlayerGamePanel.currentMap][index] = null;
                   PlayerGamePanel.MyInGameUI.showMessage("Recovery !!");
                   break;
               case "Chest":
                   PlayerGamePanel.MyInGameUI.GameFinished = true;
                   break;
           }
       }
    }
    public void drawPlayerImage(Graphics2D MyG2) {

       BufferedImage PlayerImage = null;

        if (PlayerGamePanel.GameState == PlayerGamePanel.Game_Play) {

            switch (Direction) {
                case "UP":
                    if (spriteNum == 1) {
                        PlayerImage = upAct1;
                    } else if (spriteNum == 2) {
                        PlayerImage = upAct2;
                    }
                    break;

                case "DOWN":
                    if (spriteNum == 1) {
                        PlayerImage = downAct1;
                    } else if (spriteNum == 2) {
                        PlayerImage = downAct2;
                    }
                    break;

                case "LEFT":
                    if (spriteNum == 1) {
                        PlayerImage = leftAct1;
                    } else if (spriteNum == 2) {
                        PlayerImage = leftAct2;
                    }
                    break;

                case "RIGHT":
                    if (spriteNum == 1) {
                        PlayerImage = rightAct1;
                    } else if (spriteNum == 2) {
                        PlayerImage = rightAct2;
                    }
                    break;
            }
            MyG2.drawImage(PlayerImage, screenX, screenY, tileSize, tileSize, null);
        }
    }

    public void setPlayerImageWalk() {
        if(Objects.equals(MyGamePanel.CharacterSkin,"HelShinKi")) {
           upAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_UP1).resize(tileSize,tileSize));
           upAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_UP2).resize(tileSize,tileSize));
           downAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_DOWN1).resize(tileSize,tileSize));
           downAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_DOWN2).resize(tileSize,tileSize));
           leftAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_LEFT1).resize(tileSize,tileSize));
           leftAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_LEFT2).resize(tileSize,tileSize));
           rightAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_RIGHT1).resize(tileSize,tileSize));
           rightAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinGreen.PATH_PLAYER_WALK_RIGHT2).resize(tileSize,tileSize));
       }
       else if(Objects.equals(MyGamePanel.CharacterSkin,"BlueFire")) {
           upAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_UP1).resize(tileSize,tileSize));
           upAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_UP2).resize(tileSize,tileSize));
           downAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_DOWN1).resize(tileSize,tileSize));
           downAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_DOWN2).resize(tileSize,tileSize));
           leftAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_LEFT1).resize(tileSize,tileSize));
           leftAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_LEFT2).resize(tileSize,tileSize));
           rightAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_RIGHT1).resize(tileSize,tileSize));
           rightAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinBlue.PATH_PLAYER_WALK_RIGHT2).resize(tileSize,tileSize));
       }
       else {
            upAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_UP1).resize(tileSize,tileSize));
            upAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_UP2).resize(tileSize,tileSize));
            downAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_DOWN1).resize(tileSize,tileSize));
            downAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_DOWN2).resize(tileSize,tileSize));
            leftAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_LEFT1).resize(tileSize,tileSize));
            leftAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_LEFT2).resize(tileSize,tileSize));
            rightAct1 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_RIGHT1).resize(tileSize,tileSize));
            rightAct2 = ImageIconToBufferedImageConverter.convertToBufferedImage(new MyImageIcon(PlayerSkinRed.PATH_PLAYER_WALK_RIGHT2).resize(tileSize,tileSize));
        }
    }

    public void setCoinEffectSound() {
        if (!GamePanelFrame.Sound.getSoundMute()) {
            GamePanelFrame.Sound.playSoundEffect(GamePanelFrame.Sound.CoinSFX);
        }
    }

    public void setBreakEffectSound() {
        if (!GamePanelFrame.Sound.getSoundMute()) {
            GamePanelFrame.Sound.playSoundEffect(GamePanelFrame.Sound.BreakSFX);
        }
    }
}
