package Project3_136.MainClass;

import javax.swing.*;
import java.awt.*;

public abstract class GameLayeredPane extends JLayeredPane {
    JLabel MyBackgroundPane;
    protected int width = 1008 , height = 720, tileSize = 48;
    protected String MyBackgroundPath = "src/main/java/Project3_136/resources/InterfaceGame/TitleScreen/";
    protected String MyComponentPreGamePath = "src/main/java/Project3_136/resources/InterfaceGame/componentImage/preGame/";
    protected String MyComponentFinalGamePath = "src/main/java/Project3_136/resources/InterfaceGame/componentImage/gameover/";
    protected String MyComponentCreditsPath = "src/main/java/Project3_136/resources/InterfaceGame/componentImage/credits/";
    protected String MyPlayerSkinPath = "src/main/java/Project3_136/resources/Player/";
    protected MySoundEffectControl MySound;
    public GameLayeredPane(MySoundEffectControl MySound) {

        this.MySound = MySound;

        setLayout(null);
        setOpaque(true);

        Rectangle myLayeredRectangle = new Rectangle(width, height);
        setBounds(myLayeredRectangle);

        setVisible(false);

        MyBackgroundPane = new JLabel();
        MyBackgroundPane.setOpaque(false);
        MyBackgroundPane.setVerticalAlignment(JLabel.NORTH);
        MyBackgroundPane.setHorizontalAlignment(JLabel.LEFT);
        MyBackgroundPane.setBounds(0,0,width, height);

        //set it as the first layered and label of the game and doesn't appear
        add(MyBackgroundPane,Integer.valueOf(0));

        addGeneralComponent();
        addComponent();
    }
    public void addBackgroundImage(String currentPath) {
        MyImageIcon BackgroundImage = new MyImageIcon(MyBackgroundPath + currentPath).resize(width,height);
        MyBackgroundPane.setIcon(BackgroundImage);
    }
    public void addGeneralComponent() {};
    abstract public void addComponent();
}
