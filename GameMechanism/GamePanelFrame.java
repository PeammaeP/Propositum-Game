package Project3_136.GameMechanism;

import Project3_136.GameGUI.GameFinishedState;
import Project3_136.MainClass.MySoundEffectControl;

import javax.swing.*;
import java.awt.*;

public class GamePanelFrame extends JFrame {
    JFrame MyFrame;
    //JPanel contentpane;
    public static MySoundEffectControl Sound;
    String MyGameDifficulty,CharacterSkin;
    GameFinishedState MyGameFinishedState;
    The2DGamePanel MyGamePanel;
    public GamePanelFrame(JFrame MyFrame, MySoundEffectControl Sound, GameFinishedState MyGameFinishedState, String CharacterSkin,String MyGameDifficulty) {
        this.MyFrame = MyFrame;
        this.MyGameFinishedState = MyGameFinishedState;
        this.MyGameDifficulty = MyGameDifficulty;
        this.CharacterSkin = CharacterSkin;
        GamePanelFrame.Sound = Sound;

        setTitle("PROPOSITUM MAIN GAME");

        System.out.println("=============================");
        System.out.println("Currently in Main Game Frame ==>");
        System.out.println("=============================");

        MyGamePanel = new The2DGamePanel(this, MyFrame, MyGameFinishedState, MyGameDifficulty,CharacterSkin);
        MyGamePanel.addObjectComponent();
        MyGamePanel.GameThreads();
        getContentPane().add(MyGamePanel);

        pack();

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
