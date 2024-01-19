package Project3_136.GameMechanism;

import Project3_136.MainClass.MySoundEffectControl;

public class GameEventHandle {
    The2DGamePanel MyGamePanel;
    GameEventRectangle[][][] MyEventRectangle;
    public int tileSize = TheGameConstants.GAME_TILE_SIZE;
    int previousEventX , previousEventY;
    boolean canTouchEvent = true;
    private final MySoundEffectControl effectSound;
    public GameEventHandle(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
        //EffectSound = new MySoundEffectControl[10];
        effectSound = new MySoundEffectControl();

        MyEventRectangle = new GameEventRectangle[TheGameConstants.maxMap][TheGameConstants.MAX_WORLD_COLUMN][TheGameConstants.MAX_WORLD_ROW];

        int col = 0 , row = 0, map = 0;
        while(map < TheGameConstants.maxMap && col < TheGameConstants.MAX_WORLD_COLUMN && row < TheGameConstants.MAX_WORLD_ROW) {

            MyEventRectangle[map][col][row] = new GameEventRectangle();
            //set the default value to area of the point of each event
            MyEventRectangle[map][col][row].x = 23;
            MyEventRectangle[map][col][row].y = 23;
            MyEventRectangle[map][col][row].width = 2;
            MyEventRectangle[map][col][row].height = 2;
            MyEventRectangle[map][col][row].eventDefaultX = MyEventRectangle[map][col][row].x;
            MyEventRectangle[map][col][row].eventDefaultY = MyEventRectangle[map][col][row].y;

            col++;

            if(col == TheGameConstants.MAX_WORLD_COLUMN) {
                col = 0;
                row++;
                if(row == TheGameConstants.MAX_WORLD_ROW) {
                    row = 0;
                    map++;
                }
            }
        }
    }
    public void hasTheEvent() {
        //Check the event if the player character is more than 1 tile away from the last event
        int myDistanceX = Math.abs(MyGamePanel.MyPlayer.curX - previousEventX);
        int myDistanceY = Math.abs(MyGamePanel.MyPlayer.curY - previousEventY);
        int myDistance = Math.max(myDistanceX,myDistanceY);

        if(myDistance > TheGameConstants.GAME_TILE_SIZE) {
            canTouchEvent = true;
        }

        if(canTouchEvent) {
            //if player walk and hit the direction col = 27, row = 16 and currentDirection = Right
            if(isHittingEvent(1,27,16,"RIGHT")) {
                Teleport(0,23,23);
                System.out.println("===== Downloading =====");
                System.out.println("Back to Map 1 !");
            }
            else if(isHittingEvent(0,23,12,"UP")) {
                HealingPool(MyGamePanel.Game_Play);
            }
            else if(isHittingEvent(0,10,39,"ANY")) {
                System.out.println("===== Downloading =====");
                System.out.println("Warp to Map 2 !");
                Teleport(1,23,23);
            }
        }
    }
    public void Teleport(int map, int col, int row) {
       MyGamePanel.currentMap = map;
       MyGamePanel.MyPlayer.curX = tileSize * col;
       MyGamePanel.MyPlayer.curY = tileSize * row;
       previousEventX = MyGamePanel.MyPlayer.curX;
       previousEventY = MyGamePanel.MyPlayer.curY;
       canTouchEvent = false;
    }
    public void damageOccurred(int GameState) {
        MyGamePanel.GameState = GameState;
        MyGamePanel.MyPlayer.Life -= 1;
        canTouchEvent = false;
        setBreakEffectSound();
    }
    public void HealingPool(int GameState) {
        if(MyGamePanel.MyKeyEvent.enterPressed) {
            MyGamePanel.GameState = GameState;
            MyGamePanel.MyPlayer.Life = MyGamePanel.MyPlayer.maxLife;
            setCoinEffectSound();
        }
        MyGamePanel.MyKeyEvent.enterPressed = false;
    }
    public boolean isHittingEvent(int map,int col,int row,String requiredDirection) {
        boolean isHit = false;

        //the same method as collisionEvent
        if(map == MyGamePanel.currentMap) {
        //getting the player's current solidArea position
        MyGamePanel.MyPlayer.MySolidArea.x = MyGamePanel.MyPlayer.curX + MyGamePanel.MyPlayer.MySolidArea.x;
        MyGamePanel.MyPlayer.MySolidArea.y = MyGamePanel.MyPlayer.curY + MyGamePanel.MyPlayer.MySolidArea.y;
        //getting evenRectangle's solidArea position
        MyEventRectangle[map][col][row].x = col*tileSize + MyEventRectangle[map][col][row].x;
        MyEventRectangle[map][col][row].y = row*tileSize + MyEventRectangle[map][col][row].y;

        //Checking the player's if solidArea of player colliding with eventRectangle's solidArea
        if(MyGamePanel.MyPlayer.MySolidArea.intersects(MyEventRectangle[map][col][row]) && !MyEventRectangle[map][col][row].isEventDone) {
            if(MyGamePanel.MyPlayer.Direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("ANY")) {
                isHit = true;

                previousEventX = MyGamePanel.MyPlayer.curX;
                previousEventY = MyGamePanel.MyPlayer.curY;
            }
        }

        //After checking the collision, reset the solidArea of Player's
        MyGamePanel.MyPlayer.MySolidArea.x = MyGamePanel.MyPlayer.EntitySolidAreaDefaultX;
        MyGamePanel.MyPlayer.MySolidArea.y = MyGamePanel.MyPlayer.EntitySolidAreaDefaultY;
        MyEventRectangle[map][col][row].x = MyEventRectangle[map][col][row].eventDefaultX;
        MyEventRectangle[map][col][row].y = MyEventRectangle[map][col][row].eventDefaultY;
        }
        return isHit;
    }
    public void setCoinEffectSound() {
        System.out.println("Sound Mute Status: " + effectSound.getSoundMute());
        if (!effectSound.getSoundMute()) {
            effectSound.playSoundEffect(effectSound.CoinSFX);
        }
    }
    public void setBreakEffectSound() {
        System.out.println("Sound Mute Status: " + effectSound.getSoundMute());
        if (!effectSound.getSoundMute()) {
            effectSound.playSoundEffect(effectSound.BreakSFX);
        }
    }
}
