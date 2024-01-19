package Project3_136.GameMechanism;

import java.util.ArrayList;

public class CollisionEvent {
     The2DGamePanel MyGamePanel;
     int MyTile = TheGameConstants.GAME_TILE_SIZE;
     public CollisionEvent(The2DGamePanel MyGamePanel) {
         this.MyGamePanel = MyGamePanel;
     }

     public void checkCollisionOnEntity(GameEntity MyEntity) {
          //calculate the entity collision area on object by currentLocation of entity + fix solid area in each axis
          //Because It was initialized at 0 then we have to assign it following terms ;
          int entityOnLeftX = MyEntity.curX + MyEntity.MySolidArea.x;
          int entityOnRightX = MyEntity.curX + MyEntity.MySolidArea.x + MyEntity.MySolidArea.width;
          int entityOnTopY = MyEntity.curY + MyEntity.MySolidArea.y;
          int entityOnBottomY = MyEntity.curY + MyEntity.MySolidArea.y + MyEntity.MySolidArea.height;

          //make it in row and column as a unit of each entity
          int entityLeftCol = entityOnLeftX/MyTile;
          int entityRightCol = entityOnRightX/MyTile;
          int entityTopRow = entityOnTopY/MyTile;
          int entityBottomRow = entityOnBottomY/MyTile;

          //calculate the area of collision
          int collisionTile_1 , collisionTile_2;

          switch(MyEntity.Direction) {
               case "UP" :
                    //predict the direction of entity
                    entityTopRow = (int) ((entityOnTopY - MyEntity.speed)/MyTile);
                    //get the current location matrix of entity
                    collisionTile_1 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityLeftCol][entityTopRow];
                    collisionTile_2 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityRightCol][entityTopRow];
                    //If Entity has the collision event of entity , set the flag collision event of each entity equal true
                    if(MyGamePanel.MyGameTilesManage.tile[collisionTile_1].isCollision ||
                       MyGamePanel.MyGameTilesManage.tile[collisionTile_2].isCollision) {
                         MyEntity.CollisionFlag = true;
                    }
                    break;
               case "DOWN":
                    //predict the direction of entity
                    entityBottomRow = (int) ((entityOnBottomY + MyEntity.speed)/MyTile);
                    //get the current location matrix of entity
                    collisionTile_1 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityLeftCol][entityBottomRow];
                    collisionTile_2 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityRightCol][entityBottomRow];
                    //If Entity has the collision event of entity , set the flag collision event of each entity equal true
                    if(MyGamePanel.MyGameTilesManage.tile[collisionTile_1].isCollision ||
                       MyGamePanel.MyGameTilesManage.tile[collisionTile_2].isCollision) {
                         MyEntity.CollisionFlag = true;
                    }
                    break;
               case "LEFT":
                    //predict the direction of entity
                    entityLeftCol = (int) ((entityOnLeftX - MyEntity.speed)/MyTile);
                    //get the current location matrix of entity
                    collisionTile_1 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityLeftCol][entityTopRow];
                    collisionTile_2 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityLeftCol][entityBottomRow];
                    //If Entity has the collision event of entity , set the flag collision event of each entity equal true
                    if(MyGamePanel.MyGameTilesManage.tile[collisionTile_1].isCollision ||
                            MyGamePanel.MyGameTilesManage.tile[collisionTile_2].isCollision) {
                         MyEntity.CollisionFlag = true;
                    }
                    break;
               case "RIGHT":
                    //predict the direction of entity
                    entityRightCol = (int) ((entityOnRightX + MyEntity.speed)/MyTile);
                    //get the current location matrix of entity
                    collisionTile_1 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityRightCol][entityTopRow];
                    collisionTile_2 = MyGamePanel.MyGameTilesManage.mapTileCount[MyGamePanel.currentMap][entityRightCol][entityBottomRow];
                    //If Entity has the collision event of entity , set the flag collision event of each entity equal true
                    if(MyGamePanel.MyGameTilesManage.tile[collisionTile_1].isCollision ||
                            MyGamePanel.MyGameTilesManage.tile[collisionTile_2].isCollision) {
                         MyEntity.CollisionFlag = true;
                    }
                    break;
          }
     }
     public int checkCollisionOnObject(GameEntity MyEntity, boolean MyPlayer) {

          int index = 555;

          for(int i=0; i < MyGamePanel.MyGameObject[1].length ; i++) {

               if(MyGamePanel.MyGameObject[MyGamePanel.currentMap][i] != null) {
                    //Getting the Entity's solid area position
                    MyEntity.MySolidArea.x =  MyEntity.curX + MyEntity.MySolidArea.x;
                    MyEntity.MySolidArea.y = MyEntity.curY + MyEntity.MySolidArea.y;

                    //Getting the Object's solid area position
                    MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea.x
                            = MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectX
                            + MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea.x;
                    MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea.y
                            = MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectY
                            + MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea.y;

                    switch(MyEntity.Direction) {
                         case "UP" :
                              MyEntity.MySolidArea.y -= (int) MyEntity.speed;
                              break;
                         case "DOWN" :
                              MyEntity.MySolidArea.y += (int) MyEntity.speed;
                              break;
                         case "LEFT" :
                              MyEntity.MySolidArea.x -= (int) MyEntity.speed;
                              break;
                         case "RIGHT" :
                              MyEntity.MySolidArea.x += (int) MyEntity.speed;
                              break;
                    }
                    //if player solidArea intersects with the Object Solid Area
                    if(MyEntity.MySolidArea.intersects(MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea)) {
                         //if the object is solid ( true ), then on the player's collision : true
                         if(MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectCollision) {
                              MyEntity.CollisionFlag = true;
                         }
                         //check this is the player or not, if not it can't pick up the object e.g. Monster, Boss
                         if(MyPlayer) {
                              index = i;
                         }
                    }
                    //set the default value
                    MyEntity.MySolidArea.x= MyEntity.EntitySolidAreaDefaultX;
                    MyEntity.MySolidArea.y = MyEntity.EntitySolidAreaDefaultY;
                    MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea.x = MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidAreaDefaultX;
                    MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidArea.y = MyGamePanel.MyGameObject[MyGamePanel.currentMap][i].ObjectSolidAreaDefaultY;
               }
          }
          return index;
     }
     public int checkCollisionPlayerOnEntity(GameEntity MyEntity, GameEntity[][] MyTarget) {
          int index = 555;

          for(int i=0; i < MyTarget[1].length ; i++) {

              if(MyTarget[MyGamePanel.currentMap][i] != null) {
                    //Getting the Entity's solid area position
                 MyEntity.MySolidArea.x = MyEntity.curX + MyEntity.MySolidArea.x;
                 MyEntity.MySolidArea.y = MyEntity.curY + MyEntity.MySolidArea.y;

                    //Getting the Object's solid area position
                 MyTarget[MyGamePanel.currentMap][i].MySolidArea.x = MyTarget[MyGamePanel.currentMap][i].curX + MyTarget[MyGamePanel.currentMap][i].MySolidArea.x;
                 MyTarget[MyGamePanel.currentMap][i].MySolidArea.y = MyTarget[MyGamePanel.currentMap][i].curY + MyTarget[MyGamePanel.currentMap][i].MySolidArea.y;

                    switch(MyEntity.Direction) {
                         case "UP" :
                              MyEntity.MySolidArea.y -= (int) MyEntity.speed;
                              break;
                         case "DOWN" :
                              MyEntity.MySolidArea.y += (int) MyEntity.speed;
                              break;
                         case "LEFT" :
                              MyEntity.MySolidArea.x -= (int) MyEntity.speed;
                              break;
                         case "RIGHT" :
                              MyEntity.MySolidArea.x += (int) MyEntity.speed;
                              break;
                    }

                    if(MyEntity.MySolidArea.intersects(MyTarget[MyGamePanel.currentMap][i].MySolidArea)) {
                           if(MyTarget[MyGamePanel.currentMap][i] != MyEntity) {
                             MyEntity.CollisionFlag = true;
                             index = i;
                         }
                    }
                    //set the default value
                    MyEntity.MySolidArea.x = MyEntity.EntitySolidAreaDefaultX;
                    MyEntity.MySolidArea.y = MyEntity.EntitySolidAreaDefaultY;
                    MyTarget[MyGamePanel.currentMap][i].MySolidArea.x = MyTarget[MyGamePanel.currentMap][i].EntitySolidAreaDefaultX;
                    MyTarget[MyGamePanel.currentMap][i].MySolidArea.y = MyTarget[MyGamePanel.currentMap][i].EntitySolidAreaDefaultY;
               }
          }
          return index;
     }
     public boolean checkCollisionEntityOnPlayer(GameEntity MyEntity) {

        boolean contactPlayer = false;

        MyEntity.MySolidArea.x = MyEntity.curX + MyEntity.MySolidArea.x;
        MyEntity.MySolidArea.y = MyEntity.curY + MyEntity.MySolidArea.y;

        MyGamePanel.MyPlayer.MySolidArea.x = MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.MySolidArea.x;
        MyGamePanel.MyPlayer.MySolidArea.y = MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.MySolidArea.y;

             switch(MyEntity.Direction) {
                  case "UP":
                  MyEntity.MySolidArea.y -= (int) MyEntity.speed;
                  break;
                  case "DOWN":
                  MyEntity.MySolidArea.y += (int) MyEntity.speed;
                  break;
                  case "LEFT":
                  MyEntity.MySolidArea.x -= (int) MyEntity.speed;
                  break;
                  case "RIGHT":
                  MyEntity.MySolidArea.x += (int) MyEntity.speed;
                  break;
             }

          //if player solidArea intersects with the Object Solid Area
          if(MyEntity.MySolidArea.intersects(MyGamePanel.MyPlayer.MySolidArea)) {
               MyEntity.CollisionFlag = true;
               contactPlayer = true;
          }

          //set the default value
          MyEntity.MySolidArea.x= MyEntity.EntitySolidAreaDefaultX;
          MyEntity.MySolidArea.y = MyEntity.EntitySolidAreaDefaultY;
          MyGamePanel.MyPlayer.MySolidArea.x = MyGamePanel.MyPlayer.EntitySolidAreaDefaultX;
          MyGamePanel.MyPlayer.MySolidArea.y = MyGamePanel.MyPlayer.EntitySolidAreaDefaultY;

          return contactPlayer;
     }
}


