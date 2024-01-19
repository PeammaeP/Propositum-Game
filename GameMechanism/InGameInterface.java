package Project3_136.GameMechanism;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class InGameInterface {
    The2DGamePanel MyGamePanel;
    Font MyFontDisplay , FinishFontDisplay;
    BufferedImage CoinImage,HeartFull,HeartHalf,HeartBlank;
    int MyTile = TheGameConstants.GAME_TILE_SIZE;
    int messageTimeCount = 0;
    public double GamePlayTime;
    public boolean messageOn = false;
    public boolean GameFinished = false;
    Graphics2D MyG2;
    DecimalFormat MyDecimalFormat = new DecimalFormat("#0.00");
    public String message = "";
    public int GameTitleCommand = 0;
    //Set 0 : First Title ( Main Menu ) , Set 1 : Maybe Display the Credits ( Member Group Name )
    public int GameSubTitleScreen = 0;
    public InGameInterface(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
        MyFontDisplay = new Font("Arial",Font.PLAIN,40);
        FinishFontDisplay = new Font("Arial",Font.BOLD,80);

        //set the coin image above on the game
        Coin MyCoin = new Coin(MyGamePanel);
        CoinImage = MyCoin.ObjectImage;

        //set the heart image above on the game
        Heart PlayerHeart = new Heart(MyGamePanel);
        HeartFull = PlayerHeart.ObjectImage;
        HeartHalf = PlayerHeart.ObjectImage2;
        HeartBlank = PlayerHeart.ObjectImage3;
    }
    public void showMessage(String message) {
        this.message = message;
        messageOn = true;
    }
    public void addInterface(Graphics2D MyG2) {
        this.MyG2 = MyG2;
        MyG2.setFont(MyFontDisplay);
        MyG2.setColor(Color.WHITE);

        //Title Game State
        if(MyGamePanel.GameState == MyGamePanel.titleState) {
            //InterfaceTitleScreen();
        }
        //Play Game State
        if(MyGamePanel.GameState == MyGamePanel.Game_Play) {
            //Playing Stuff
            InterfaceGamePlay();
        }
        //Pausing Game State
        if(MyGamePanel.GameState == MyGamePanel.Game_Pause) {
            InterfacePauseScreen();
        }
        //Game Over State
        if(MyGamePanel.GameState == MyGamePanel.Game_Over) {
            //InterfaceGameOver();
            GamePlayTime = 0;
        }
    }
    public void InterfacePlayerLife() {

        int curX = MyTile/2;
        int curY = MyTile*3/2;
        int countedHeart = 0;

        //Max Life
        while(countedHeart < MyGamePanel.MyPlayer.maxLife/2) {
            MyG2.drawImage(HeartBlank,curX,curY,null);
            countedHeart++;
            curX += MyTile; //to display the 3 heart otherwise it's stack in a heart image
        }

        //Reset
        curX = MyTile/2;
        curY = MyTile*3/2;
        countedHeart = 0;

        //Current Life
        while(countedHeart < MyGamePanel.MyPlayer.Life) {
            MyG2.drawImage(HeartHalf,curX,curY,null);
            countedHeart++;
            if(countedHeart < MyGamePanel.MyPlayer.Life) {
                MyG2.drawImage(HeartFull,curX,curY,null);
            }
            countedHeart++;
            curX += MyTile;
        }
    }
    public void InterfacePauseScreen() {
        MyG2.setFont(MyG2.getFont().deriveFont(Font.PLAIN,80F));
        String pause = "PAUSE";
        int myX = CenterTextInX(pause);

        int myY = TheGameConstants.SCREEN_HEIGHT/2;
        MyG2.drawString(pause,myX,myY);
    }
    public int CenterTextInX(String MyText) {
        int length = (int)MyG2.getFontMetrics().getStringBounds(MyText,MyG2).getWidth();
        return TheGameConstants.SCREEN_WIDTH/2 - length/2;
    }
    public void InterfaceGamePlay() {
        if(GameFinished) {
            //Check for pre game finished state, but it doesn't necessary
            String finishedText;
            int finishedTextLen;
            int centerTextX;
            int centerTextY;

            finishedText = "You found the treasure !";
            finishedTextLen = (int)MyG2.getFontMetrics().getStringBounds(finishedText,MyG2).getWidth();

            centerTextX = TheGameConstants.SCREEN_WIDTH/2 - finishedTextLen/2;
            centerTextY = TheGameConstants.SCREEN_HEIGHT/2 - MyTile*3;

            MyG2.drawString(finishedText,centerTextX,centerTextY);

            finishedText = "Total Time is : " + MyDecimalFormat.format(GamePlayTime) + "!";
            finishedTextLen = (int)MyG2.getFontMetrics().getStringBounds(finishedText,MyG2).getWidth();

            centerTextX = TheGameConstants.SCREEN_WIDTH/2 - finishedTextLen/2;
            centerTextY = TheGameConstants.SCREEN_HEIGHT/2 + MyTile*4;

            MyG2.drawString(finishedText,centerTextX,centerTextY);

            MyG2.setFont(FinishFontDisplay);
            MyG2.setColor(Color.YELLOW);
            finishedText = "Congratulations !";
            finishedTextLen = (int)MyG2.getFontMetrics().getStringBounds(finishedText,MyG2).getWidth();

            centerTextX = TheGameConstants.SCREEN_WIDTH/2 - finishedTextLen/2;
            centerTextY = TheGameConstants.SCREEN_HEIGHT/2 + MyTile*2;

            MyG2.drawString(finishedText,centerTextX,centerTextY);

            //stop the gameThreads
            MyGamePanel.MyGameThreads = null;
        }

        else {
            MyG2.drawImage(CoinImage,MyTile/2,MyTile/2,MyTile,MyTile,null);
            MyG2.drawString("= " + MyGamePanel.MyPlayer.PickUpCoin,74,60);
            //message display

            //Player Life Display
            InterfacePlayerLife();

            //Display Game Time on Game Main Screen
            GamePlayTime += (double)1/60;

            MyG2.drawString("Time = " + MyDecimalFormat.format(GamePlayTime),MyTile*11,65);

            if(messageOn) {
                MyG2.setFont(MyG2.getFont().deriveFont(30F));
                MyG2.drawString(message,MyTile/2,MyTile*5);

                messageTimeCount++;

                if(messageTimeCount > 90) {
                    messageTimeCount = 0;
                    messageOn = false;
                }
            }
        }
    }
}
