package Project3_136.GameGUI;

import Project3_136.MainClass.*;

import javax.swing.*;
import java.awt.*;

public class GameCredit extends GameLayeredPane {
    JLabel MyLabelText,MyName1,MyName2,MyName3,MyName4;
    JPanel MyButtonPanel,MyNamePanel;
    GameButton BackButton;
    public GameCredit(MySoundEffectControl soundEffect) {
        super(soundEffect);
    }
    @Override
    public void addComponent() {
        //Creating Text Label from JLabel
        MyLabelText = new JLabel(new MyImageIcon(MyComponentCreditsPath + "Credits.png"));
        MyLabelText.setOpaque(false);
        MyLabelText.setBounds(0,20,width,height);
        MyLabelText.setHorizontalAlignment(JLabel.CENTER);
        MyLabelText.setVerticalAlignment(JLabel.TOP);
        add(MyLabelText,JLayeredPane.POPUP_LAYER);

        //MySubBackground = new JLabel(new MyImageIcon(MyComponentsPanePath + "SubPopBG.png"));

        //Creating Each Member Name Panel
        MyNamePanel = new JPanel();
        MyNamePanel.setBounds(175,225,width,height);
        MyNamePanel.setLayout(new FlowLayout(FlowLayout.LEADING,50,25));
        MyNamePanel.setOpaque(false);

        MyName1 = new JLabel(new MyImageIcon(MyComponentCreditsPath + "chutiya.png"));
        MyName1.setOpaque(false);
        MyNamePanel.add(MyName1);

        MyName2 = new JLabel(new MyImageIcon(MyComponentCreditsPath + "supakorn.png"));
        MyName2.setOpaque(false);
        MyNamePanel.add(MyName2);

        MyName3 = new JLabel(new MyImageIcon(MyComponentCreditsPath + "anapat.png"));
        MyNamePanel.add(MyName3);

        MyName4 = new JLabel(new MyImageIcon(MyComponentCreditsPath + "mahannop.png"));
        MyNamePanel.add(MyName4);

        add(MyNamePanel,JLayeredPane.DRAG_LAYER);

        //Creating JPanel for Button Pane
        MyButtonPanel = new JPanel();
        MyButtonPanel.setOpaque(false);
        MyButtonPanel.setBounds(20,height-100,width-40,70);
        MyButtonPanel.setLayout(new BorderLayout());

        //Adding BackButton
        BackButton = new GameButton("Back",true);
        BackButton.setSoundControl(MySound);

        //Add ButtonPanel to the Credit Pane
        MyButtonPanel.add(BackButton,BorderLayout.WEST);

        add(MyButtonPanel,JLayeredPane.DRAG_LAYER);
        addBackgroundImage("background.png");
    }
    public GameButton getBackButton() {
        return BackButton;
    }
}
