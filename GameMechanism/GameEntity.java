package Project3_136.GameMechanism;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class GameEntity extends JLabel {
    The2DGamePanel MyGamePanel;
    public int curX,curY;
    public double speed;
    public String EntityName;
    public BufferedImage upAct1,upAct2,downAct1,downAct2,leftAct1,leftAct2,rightAct1,rightAct2;
    public String Direction;
    public int tileSize = TheGameConstants.GAME_TILE_SIZE;
    public Rectangle MySolidArea = new Rectangle(0,0,48,48);
    public int EntitySolidAreaDefaultX = 0;
    public int EntitySolidAreaDefaultY = 0;
    //set the Collision of each Entity ( Player , Monster , NPC )
    public boolean CollisionFlag = false;
    //Character Life Remaining ( Status )
    public int maxLife;
    public int Life;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionLookCounter;
    public boolean invincible = false;
    public int invincibleCounted = 0;
    public GameEntity(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
    }
    public void SetEntityAction() { }
    public void EntityUpdate() {

        SetEntityAction();
        CollisionFlag = false;

        MyGamePanel.MyCollisionEvent.checkCollisionOnEntity(this);
        MyGamePanel.MyCollisionEvent.checkCollisionOnObject(this,false);
        MyGamePanel.MyCollisionEvent.checkCollisionPlayerOnEntity(this,MyGamePanel.MyMonster);
        boolean contactPlayer = MyGamePanel.MyCollisionEvent.checkCollisionEntityOnPlayer(this);

        if(contactPlayer) {
           if(!MyGamePanel.MyPlayer.invincible) {
             MyGamePanel.MyPlayer.Life -= 1;
             MyGamePanel.MyPlayer.invincible = true;
             MyGamePanel.MyPlayer.setBreakEffectSound();
            }
        }

        if(!CollisionFlag) {
            switch(Direction) {
                case "UP" : curY -= (int)this.speed; break;
                case "DOWN" : curY += (int)this.speed; break;
                case "LEFT" : curX -= (int)this.speed; break;
                case "RIGHT" : curX += (int)this.speed; break;
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
    public int getCenterX() {
        return curX + leftAct1.getWidth()/2;
    }
    public int getCenterY() {
        return curY + upAct1.getHeight()/2;
    }
    public int getDistanceX(GameEntity target) {
        return Math.abs(curX - target.curX);
    }
    public int getDistanceY(GameEntity target) {
        return Math.abs(curY - target.curY);
    }
    public int getDistanceXinCenter(GameEntity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }
    public int getDistanceYinCenter(GameEntity target) {
        return Math.abs(getCenterY() - target.getCenterY());
    }
    public int getTileDistanceInCenter(GameEntity target) {
        return getDistanceXinCenter(target) + getDistanceYinCenter(target);
    }
    /*public void MonsterTrackingPlayer(int interval) {
          actionLookCounter++;

          if(actionLookCounter > interval) {
               if(getDistanceXinCenter(MyGamePanel.MyPlayer) > getDistanceYinCenter(MyGamePanel.MyPlayer)) {
                       if(MyGamePanel.MyPlayer.getCenterX() < getCenterX()) {
                           Direction = "LEFT";
                       }
                       else Direction = "RIGHT";
               }
               else if(getDistanceXinCenter(MyGamePanel.MyPlayer) < getDistanceYinCenter(MyGamePanel.MyPlayer)) {
                   if(MyGamePanel.MyPlayer.getCenterY() < getCenterY()) {
                       Direction = "UP";
                   }
                   else Direction = "DOWN";
               }
              actionLookCounter = 0;
          }
    }*/
    public void checkAttackInRange(int rate, int vertical,int horizontal) {
         boolean targetInRange = false;
         int curX = getDistanceXinCenter(MyGamePanel.MyPlayer);
         int curY = getDistanceYinCenter(MyGamePanel.MyPlayer);

         switch(Direction) {
             case "UP":
                 if(MyGamePanel.MyPlayer.getCenterY() < getCenterY() && curY < vertical && curX < horizontal) {
                     targetInRange = true;
                 }
                 break;
             case "DOWN":
                 if(MyGamePanel.MyPlayer.getCenterY() > getCenterY() && curY < vertical && curX < horizontal) {
                     targetInRange = true;
                 }
                 break;
             case "LEFT":
                 if(MyGamePanel.MyPlayer.getCenterX() < getCenterX() && curY < vertical && curX < horizontal) {
                     targetInRange = true;
                 }
                 break;
             case "RIGHT":
                 if(MyGamePanel.MyPlayer.getCenterX() > getCenterX() && curY < vertical && curX < horizontal) {
                     targetInRange = true;
                 }
                 break;
         }
         if(targetInRange) {
             int i = new Random().nextInt(rate);
             if(i == 0) {
                 spriteNum = 1;
                 spriteCounter = 0;
             }
         }
    }
    public void getRandomDirection(int interval, double monsterSpeed) {
        actionLookCounter++;

        // Calculate the adjusted interval based on monster speed
        int adjustedInterval = (int) (interval / monsterSpeed);

        if (actionLookCounter > adjustedInterval) {
            Random MyRandom = new Random();
            int counter = MyRandom.nextInt(100) + 1;

            if (counter <= 25) {
                Direction = "UP";
            } else if (counter <= 50) {
                Direction = "DOWN";
            } else if (counter <= 75) {
                Direction = "LEFT";
            } else {
                Direction = "RIGHT";
            }
            actionLookCounter = 0;
        }
    }

    public void drawGameEntity(Graphics2D MyG2) {
        BufferedImage EntityImage = null;

        int screenX = curX - MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.screenX;
        int screenY = curY - MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.screenY;

        if(curX + tileSize > MyGamePanel.MyPlayer.curX - MyGamePanel.MyPlayer.screenX &&
                curX - tileSize < MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.screenX &&
                curY + tileSize > MyGamePanel.MyPlayer.curY - MyGamePanel.MyPlayer.screenY &&
                curY - tileSize < MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.screenY) {

           switch(Direction) {
            case "UP" :
                if(spriteNum == 1) {
                    EntityImage = upAct1;
                }
                else if(spriteNum == 2) {
                    EntityImage = upAct2;
                }
                break;
            case "DOWN" :
                if(spriteNum == 1) {
                    EntityImage = downAct1;
                }
                else if(spriteNum == 2) {
                    EntityImage = downAct2;
                }
                break;
            case "LEFT" :
                if(spriteNum == 1) {
                    EntityImage = leftAct1;
                }
                else if(spriteNum == 2) {
                    EntityImage = leftAct2;
                }
                break;
            case "RIGHT" :
                if(spriteNum == 1) {
                    EntityImage = rightAct1;
                }
                else if(spriteNum == 2) {
                    EntityImage = rightAct2;
                }
                break;
          }
            MyG2.drawImage(EntityImage, screenX, screenY, tileSize, tileSize, null);
        }
    }
}
