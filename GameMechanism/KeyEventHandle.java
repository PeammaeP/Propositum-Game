package Project3_136.GameMechanism;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventHandle implements KeyListener {
    The2DGamePanel MyGamePanel;
    public boolean upPressed, downPressed , leftPressed , rightPressed,enterPressed;
    public KeyEventHandle(The2DGamePanel MyGamePanel) {
        this.MyGamePanel = MyGamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int KeyPressedCode = e.getKeyCode();

        //Game Title
        if(MyGamePanel.GameState == MyGamePanel.titleState) {

            if(MyGamePanel.MyInGameUI.GameSubTitleScreen == 0) {

              if(KeyPressedCode == KeyEvent.VK_W) {
                MyGamePanel.MyInGameUI.GameTitleCommand--;
                if(MyGamePanel.MyInGameUI.GameTitleCommand < 0) {
                    MyGamePanel.MyInGameUI.GameTitleCommand = 3;
                 }
              }
            if(KeyPressedCode == KeyEvent.VK_S) {
                MyGamePanel.MyInGameUI.GameTitleCommand++;
                if(MyGamePanel.MyInGameUI.GameTitleCommand > 3) {
                    MyGamePanel.MyInGameUI.GameTitleCommand = 0;
                }
            }
            if(KeyPressedCode == KeyEvent.VK_ENTER) {
                if(MyGamePanel.MyInGameUI.GameTitleCommand == 0) {
                    MyGamePanel.GameState = MyGamePanel.Game_Play;
                }
                if(MyGamePanel.MyInGameUI.GameTitleCommand == 1) {
                    //Later
                }
                if(MyGamePanel.MyInGameUI.GameTitleCommand == 2) {
                    //Display Member's Name
                    MyGamePanel.MyInGameUI.GameSubTitleScreen = 1;
                }
                if(MyGamePanel.MyInGameUI.GameTitleCommand == 3) {
                    System.exit(0);
                }
              }
            }

            if(MyGamePanel.MyInGameUI.GameSubTitleScreen == 1) {
                if(KeyPressedCode == KeyEvent.VK_ENTER) {
                    if(MyGamePanel.MyInGameUI.GameTitleCommand == 0) {
                        MyGamePanel.MyInGameUI.GameSubTitleScreen = 0;
                    }
                }
            }
        }

        if(MyGamePanel.GameState == MyGamePanel.Game_Over) {
            //gameOverState(KeyPressedCode);
            MyGamePanel.currentMap = 0;
        }

        //Main in Game Part
        switch(KeyPressedCode) {
            case KeyEvent.VK_W :
                upPressed = true;
                break;
            case KeyEvent.VK_A :
                leftPressed = true;
                break;
            case KeyEvent.VK_S :
                downPressed = true;
                break;
            case KeyEvent.VK_D :
                rightPressed = true;
                break;
            case KeyEvent.VK_P :
                if(MyGamePanel.GameState == MyGamePanel.Game_Play) {
                    MyGamePanel.GameState = MyGamePanel.Game_Pause;
                }
                else if(MyGamePanel.GameState == MyGamePanel.Game_Pause) {
                    MyGamePanel.GameState = MyGamePanel.Game_Play;
                }
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = true;
        }
    }
    public void gameOverState(int code) {
        if(code == KeyEvent.VK_W) {
            MyGamePanel.MyInGameUI.GameTitleCommand--;
            if(MyGamePanel.MyInGameUI.GameTitleCommand < 0) {
                MyGamePanel.MyInGameUI.GameTitleCommand = 1;
            }
        }
        if(code == KeyEvent.VK_S) {
            MyGamePanel.MyInGameUI.GameTitleCommand++;
            if(MyGamePanel.MyInGameUI.GameTitleCommand > 1) {
                MyGamePanel.MyInGameUI.GameTitleCommand = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(MyGamePanel.MyInGameUI.GameTitleCommand == 0) {
                MyGamePanel.GameState = MyGamePanel.Game_Play;
                MyGamePanel.Retry();
            }
            else if(MyGamePanel.MyInGameUI.GameTitleCommand == 1) {
                MyGamePanel.GameState = MyGamePanel.titleState;
                MyGamePanel.Restart();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int KeyReleasedCode = e.getKeyCode();
        switch(KeyReleasedCode) {
            case KeyEvent.VK_W :
                upPressed = false;
                break;
            case KeyEvent.VK_A :
                leftPressed = false;
                break;
            case KeyEvent.VK_S :
                downPressed = false;
                break;
            case KeyEvent.VK_D :
                rightPressed = false;
                break;
        }
    }
}
