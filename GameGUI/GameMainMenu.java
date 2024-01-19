package Project3_136.GameGUI;

import Project3_136.GameJDialog.HowToPlayDialog;
import Project3_136.GameJDialog.SettingDialog;
import Project3_136.MainClass.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GameMainMenu extends GameLayeredPane {
    JFrame MyMenuFrame;
    JPanel MyMenuPanel,MyIconPanel;
    JLabel MyIcon;
    GameButton StartButton,LeaderBoardButton,CreditButton,SettingButton,HowToPlayButton,QuitButton;
    public GameMainMenu(JFrame MyMenuFrame, MySoundEffectControl MySound) {
        super(MySound);
        this.MyMenuFrame = MyMenuFrame;
    }
    @Override
    public void addComponent() {
        setVisible(true);
        addBackgroundImage("background.png");

        addMenuButtonComponents();
        addIconComponents();
    }
    public void addMenuButtonComponents() {
        MyMenuPanel = new JPanel();
        MyMenuPanel.setBounds(0, 50, width, height);
        MyMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, width, 15));
        MyMenuPanel.setOpaque(false);

        MyIcon = new JLabel();
        MyIcon.setIcon(new MyImageIcon(MyBackgroundPath + "jack_o_lantern.png").resize(200,150));

        StartButton = new GameButton("Start", true);
        StartButton.setSoundControl(MySound);

        LeaderBoardButton = new GameButton("Leader",true);
        LeaderBoardButton.setSoundControl(MySound);

        CreditButton = new GameButton("Credits", true);
        CreditButton.setSoundControl(MySound);

        //anonymous class for the quit button
        QuitButton = new GameButton("Exit",true) {
            @Override
            public void mouseClicked(MouseEvent e) {
                MySound.playSoundEffect(MySound.SoundClick);
                System.exit(0);
            }
        };

        MyMenuPanel.add(MyIcon);
        MyMenuPanel.add(StartButton);
        MyMenuPanel.add(LeaderBoardButton);
        MyMenuPanel.add(CreditButton);
        MyMenuPanel.add(QuitButton);

        add(MyMenuPanel,Integer.valueOf(1));
    }

    public void addIconComponents() {
        MyIconPanel = new JPanel();
        MyIconPanel.setBounds(width-200,15,100,100);
        MyIconPanel.setLayout(new GridLayout(1,2,10,20));
        MyIconPanel.setOpaque(false);

        HowToPlayButton = new GameButton("HowToPlay",false,200,200) {
            @Override
            public void mouseClicked(MouseEvent e) {
                MySound.playSoundEffect(MySound.SoundClick);
                new HowToPlayDialog(MyMenuFrame).run();
            }
        };

        SettingButton = new GameButton("Settings",false,200,200) {
            @Override
            public void mouseClicked(MouseEvent e) {
                MySound.playSoundEffect(MySound.SoundClick);
                SettingDialog.setSound(MySound);
                new SettingDialog(MyMenuFrame).run();
            }
        };

        //Adding Icon Pane to the Panel
        MyIconPanel.add(HowToPlayButton);
        MyIconPanel.add(SettingButton);

        //Add IconPanel to the JFrame
        add(MyIconPanel,Integer.valueOf(2));
    }
    public GameButton getStartButton() {
        return StartButton;
    }
    public GameButton getLeaderBoardButton() { return LeaderBoardButton; }
    public GameButton getCreditButton() {
        return CreditButton;
    }
}
