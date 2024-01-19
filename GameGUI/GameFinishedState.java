package Project3_136.GameGUI;

import Project3_136.MainClass.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFinishedState extends GameLayeredPane {
    public String Difficulty;
    public int score,final_score;
    public double currentTime;
    public WriteScoreClass MyScoreFile;
    GameButton ConfirmButton;
    JTextField TextNameFieldArea;
    JLabel MyScoreDisplay,MyScoreIcon,NameIcon;
    JPanel MyNamePanel, MyScorePanel, MyComponentsPanel, MyButtonPanel;
    public GameFinishedState(MySoundEffectControl MySound) {
        super(MySound);
    }
    @Override
    public void addComponent() {
        try {
            MyScoreFile = new WriteScoreClass();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Setting the Components Panel
        MyComponentsPanel = new JPanel(new GridLayout(1,2,50,10));
        MyComponentsPanel.setOpaque(false);
        MyComponentsPanel.setBounds(200,300,width,height-450);

        //Setting the Name Panel
        MyNamePanel = new JPanel(new GridLayout(2,1,0,50));
        //MyNamePanel = new JPanel(new BorderLayout());
        MyNamePanel.setOpaque(false);

        //Setting the Score Panel
        MyScorePanel = new JPanel(new GridLayout(2,1,0,50));
        MyScorePanel.setOpaque(false);

        //Setting the Name Icon
        NameIcon = new JLabel(new MyImageIcon(MyComponentFinalGamePath + "Name.png"));
        NameIcon.setHorizontalAlignment(JLabel.LEFT);

        //Setting the TextNameArea from User
        TextNameFieldArea = new JTextField();
        TextNameFieldArea.setFont(new Font("Arial",Font.PLAIN,16));
        TextNameFieldArea.setBounds(100,height-150,width-40,70);
        TextNameFieldArea.setText("Field Your Name");

        MyNamePanel.add(NameIcon);
        MyNamePanel.add(TextNameFieldArea);

        //Setting the Score to User
        MyScoreIcon = new JLabel(new MyImageIcon(MyComponentFinalGamePath + "Score.png"));
        MyScoreIcon.setHorizontalAlignment(JLabel.LEFT);

        MyScoreDisplay = new JLabel();
        MyScoreDisplay.setFont(new Font("Arial",Font.BOLD,20));
        MyScoreDisplay.setHorizontalAlignment(JLabel.LEFT);
        MyScoreDisplay.setForeground(Color.WHITE);

        MyScorePanel.add(MyScoreIcon);
        MyScorePanel.add(MyScoreDisplay);

        MyComponentsPanel.add(MyNamePanel);
        MyComponentsPanel.add(MyScorePanel);

        add(MyComponentsPanel,JLayeredPane.DRAG_LAYER);

        //Setting the Button Panel
        MyButtonPanel = new JPanel();
        MyButtonPanel.setOpaque(false);
        MyButtonPanel.setBounds(20,height-100,width-40,70);
        MyButtonPanel.setLayout(new BorderLayout());
        //Anonymous Class for the Button
        ConfirmButton = new GameButton("Confirm",true) {
            @Override
            public void addButtonComponents() {
                MySound.playSoundEffect(MySound.SoundClick);
                MyScoreFile.RowIncreasing();
                MyScoreFile.FileReading();
                MyScoreFile.SettingData(MyScoreFile.getCountingRows()-1,TextNameFieldArea.getText(),final_score,Difficulty);
                MyScoreFile.scoreSorting(MyScoreFile.getGameData());
                MyScoreFile.FileWriting();
                GameLeaderBoard.updateGameTable();
            }
        };
        ConfirmButton.setSoundControl(MySound);

        MyButtonPanel.add(ConfirmButton);
        add(MyButtonPanel,JLayeredPane.DRAG_LAYER);
    }
    public GameButton getConfirmButton() {
        return ConfirmButton;
    }
    public void setDifficulty(String Difficulty) {
        this.Difficulty = Difficulty;
    }
    public void setScore(int score,double currentTime) {
        this.score = score;
        this.currentTime = currentTime;
        final_score = (int)Math.ceil(score/currentTime);
        MyScoreDisplay.setText(String.valueOf(final_score));
    }
}
