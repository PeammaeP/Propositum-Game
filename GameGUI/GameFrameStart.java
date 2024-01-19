package Project3_136.GameGUI;

import javax.swing.*;
import Project3_136.MainClass.MySoundEffectControl;

import java.awt.*;

public class GameFrameStart extends JFrame {
    JLayeredPane MyLayeredPane;
    GameMainMenu MyMainMenu;
    GamePreState MyPreState;
    GameCredit MyCredit;
    GameFinishedState MyFinishedState;
    GameLeaderBoard MyLeaderBoardPane;
    MySoundEffectControl MySound;

    public int width = 1008 , height = 720;
    public GameFrameStart()  {
        MySound = new MySoundEffectControl();

        setTitle("PROPOSITUM GAME");

        System.out.println("=============================");
        System.out.println("Currently, Components Frame !");
        System.out.println("=============================");

        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MyLayeredPane = new JLayeredPane();
        MyLayeredPane.setLayout(null);
        MyLayeredPane.setOpaque(false);
        MyLayeredPane.setPreferredSize(new Dimension(width,height));
        getContentPane().add(MyLayeredPane);

        MySound.playMusic();
        addGamePanel();
    }
    private void addGamePanel() {
        // Create Button Pane and Main Game Pane to Main Menu
        MyMainMenu = new GameMainMenu(this, MySound);
        MyCredit = new GameCredit(MySound);
        MyFinishedState = new GameFinishedState(MySound);
        MyPreState = new GamePreState(this,MyFinishedState,MySound);
        MyLeaderBoardPane = new GameLeaderBoard(MySound);

        // Linking Between Page Implementation
        MyMainMenu.getStartButton().LinkingBetweenPane(MyMainMenu, MyPreState); // Link to MyGamePanel
        MyMainMenu.getLeaderBoardButton().LinkingBetweenPane(MyMainMenu,MyLeaderBoardPane);
        MyMainMenu.getCreditButton().LinkingBetweenPane(MyMainMenu, MyCredit);

        MyLeaderBoardPane.getMainMenuButton().LinkingBetweenPane(MyLeaderBoardPane,MyMainMenu);

        MyCredit.getBackButton().LinkingBetweenPane(MyCredit, MyMainMenu);

        MyPreState.getBackButton().LinkingBetweenPane(MyPreState,MyMainMenu);
        MyPreState.getOKButton().LinkingBetweenPane(MyPreState,MyFinishedState);

        MyFinishedState.getConfirmButton().LinkingBetweenPane(MyFinishedState,MyLeaderBoardPane);

        MyLayeredPane.add(MyMainMenu, JLayeredPane.DRAG_LAYER);
        MyLayeredPane.add(MyCredit, JLayeredPane.POPUP_LAYER);
        MyLayeredPane.add(MyPreState,JLayeredPane.POPUP_LAYER);
        MyLayeredPane.add(MyFinishedState,JLayeredPane.POPUP_LAYER);
        MyLayeredPane.add(MyLeaderBoardPane,JLayeredPane.POPUP_LAYER);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
