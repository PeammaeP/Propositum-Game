package Project3_136.GameMechanism;

import javax.swing.*;
import java.awt.*;

import Project3_136.GameGUI.GameFinishedState;
import Project3_136.MainClass.MySoundEffectControl;

public class The2DGamePanel extends JPanel implements Runnable {
    JFrame currentFrame,parentFrame;
    public final String Difficulty,CharacterSkin;
    private final GameFinishedState MyFinishedState;
    public KeyEventHandle MyKeyEvent = new KeyEventHandle(this);
    public Thread MyGameThreads;
    public GameTilesManage MyGameTilesManage = new GameTilesManage(this);
    MySoundEffectControl ThemeSong = new MySoundEffectControl();
    public InGameInterface MyInGameUI = new InGameInterface(this);
    public GamePlayerClass MyPlayer;
    public GameObject[][] MyGameObject = new GameObject[TheGameConstants.maxMap][20];
    public GameEntity[][] MyMonster = new GameEntity[TheGameConstants.maxMap][30];
    public SetObjectInGame SettingObject = new SetObjectInGame(this);
    public CollisionEvent MyCollisionEvent = new CollisionEvent(this);
    public GameEventHandle MyGameEventHandle = new GameEventHandle(this);
    //Seeing The Current Map
    public int currentMap = 0;
    public int currentScore = 0;
    public int BeginnerThresholdScore = 10000;
    public int IntermediateThresholdScore = 15000;
    public int AdvancedThresholdScore = 20000;
    public int ExtremeThresholdScore = 25000;
    public int InsaneThresholdScore = 30000;
    //GAME STAGE
    public int GameState;
    public int titleState = 0;
    public final int Game_Play = 1;
    public final int Game_Pause = 2;
    public final int Game_Over = 6;

    public The2DGamePanel(JFrame currentFrame,JFrame parentFrame,GameFinishedState MyFinishedState,String Difficulty,String CharacterSkin) {
        this.currentFrame = currentFrame;
        this.parentFrame = parentFrame;
        this.Difficulty = Difficulty;
        this.MyFinishedState = MyFinishedState;
        this.CharacterSkin = CharacterSkin;

        MyPlayer = new GamePlayerClass(this,MyKeyEvent,CharacterSkin);
        setPreferredSize(new Dimension(TheGameConstants.SCREEN_WIDTH,TheGameConstants.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(MyKeyEvent);
        setFocusable(true);
        add(MyGameTilesManage);

        System.out.println("Player's Skin is : " + CharacterSkin);
        GameState = Game_Play;
    }
    public void setDifficulty() {
        switch(Difficulty) {
            case("Beginner") :
                setMonsterSpeed(120,2);
                currentScore = BeginnerThresholdScore;
                break;
            case("Intermediate") :
                setMonsterSpeed(100,3);
                currentScore = IntermediateThresholdScore;
                break;
            case("Advanced") :
                setMonsterSpeed(80,4);
                currentScore = AdvancedThresholdScore;
                break;
            case("Extreme") :
                setMonsterSpeed(60,5);
                currentScore = ExtremeThresholdScore;
                break;
            case("Insane") :
                setMonsterSpeed(40,6);
                currentScore = InsaneThresholdScore;
                break;
        }
    }
    public void addObjectComponent() {
        SettingObject.SetMonster();
        SettingObject.SetObjectGame();
    }
    public void GameThreads() {
        MyGameThreads = new Thread(this);
        MyGameThreads.start();
    }
    public void Retry() {
        MyPlayer.setDefaultPlayerStats();
        MyPlayer.setDefaultPositions();
    }
    public void Restart() {
        MyPlayer.setDefaultPlayerStats();
        MyPlayer.setDefaultPositions();
    }
    @Override
    public void run() { //Creating The Game Loop by using Delta Time Accumulation ( better than Thread Sleep )
        double drawInterval = (double) 1000000000 / TheGameConstants.GAME_FPS; //allocated time 0.0166 seconds
        double delta_time = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        //Display The FPS
        long Timer = 0;
        int drawCount = 0;

        while (MyGameThreads != null) {
            currentTime = System.nanoTime();
            delta_time += (currentTime - lastTime) / drawInterval;
            Timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta_time >= 1) {
                updateGameEvent();
                setDifficulty();
                repaint(); //call the paintComponent Method
                delta_time--;
                //increment drawCount when has updated and repainted
                drawCount++;
            }

            long sleepTime = Math.max(0, (long) (lastTime + drawInterval - System.nanoTime()) / 1000000000);

            try {
                Thread.sleep(sleepTime);  // Sleep to control frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Timer >= 1000000000) {
                drawCount = 0;
                Timer = 0;
            }
        }
    }
    public void setMonsterSpeed(int interval,double speed) {
        for (int i = 0; i < MyMonster[1].length; i++) {
            if (MyMonster[currentMap][i] != null) {
                MyMonster[currentMap][i].speed = speed;
                MyMonster[currentMap][i].getRandomDirection(interval, MyMonster[currentMap][i].speed);
            }
        }
    }
    public void updateGameEvent() {
        if(GameState == Game_Play) {
            MyPlayer.updatePlayerEvents();

            for(int i=0;i < MyMonster[1].length;i++) {
                if(MyMonster[currentMap][i] != null) {
                    MyMonster[currentMap][i].EntityUpdate();
                }
            }
            if(MyPlayer.Life <= 0) {
                handleGameOver();
                System.out.println("Player's Remaining Life : " + MyPlayer.Life);
                System.out.println("=============================");
                currentScore *= 0.5;
                //Minimum 20 seconds in Game
                if (!(MyInGameUI.GamePlayTime >= 20)) {
                    currentScore = 0;
                }
                MyFinishedState.setScore(currentScore,MyInGameUI.GamePlayTime);
                MyFinishedState.setDifficulty(Difficulty);
            }
            if(MyInGameUI.GameFinished) {
                handleGameWin();
                System.out.println("=============================");
                System.out.println("Player's Found The Treasure !");
                System.out.println("Player Win !");
                System.out.println("=============================");
                MyFinishedState.setScore(currentScore,MyInGameUI.GamePlayTime);
                MyFinishedState.setDifficulty(Difficulty);
            }
        }
        if(GameState == Game_Pause) {
            ThemeSong.setMusicMute(true);
        }
    }
    private void handleGameOver() {
         removeAll();

         currentFrame.setVisible(false);
         parentFrame.setVisible(true);

         MyFinishedState.addBackgroundImage("Over.png");
    }
    private void handleGameWin() {
         removeAll();

         currentFrame.setVisible(false);
         parentFrame.setVisible(true);

         MyFinishedState.addBackgroundImage("Victory.png");
    }

    public void paintComponent(Graphics MyGraphics) {
        super.paintComponent(MyGraphics);

        Graphics2D MyG2 = (Graphics2D)MyGraphics;

        //Creating Title Screen
        if(GameState == titleState) {
            MyInGameUI.addInterface(MyG2);
        }
        else {
            //Draw Tiles
            MyGameTilesManage.drawTileImage(MyG2);
            MyPlayer.drawPlayerImage(MyG2);
            //Adding the Entity
            for(int i=0; i < MyGameObject[1].length ; i++) {
                if(MyGameObject[currentMap][i] != null) {
                    MyGameObject[currentMap][i].drawGameObject(MyG2,this);
                }
            }
            for(int i=0; i < MyMonster[1].length ; i++) {
                if(MyMonster[currentMap][i] != null) {
                    MyMonster[currentMap][i].drawGameEntity(MyG2);
                }
            }
            //Adding the Interface in Main Game
            MyInGameUI.addInterface(MyG2);
            MyG2.dispose();
      }
    }
}

