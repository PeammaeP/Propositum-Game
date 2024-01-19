package Project3_136.GameJDialog;

import Project3_136.MainClass.MyImageIcon;

import javax.swing.*;
import java.awt.*;

public class HowToPlayDialog extends MainDialog {
    public HowToPlayDialog(Frame parentFrame) {
        super(parentFrame);
    }
    @Override
    public void addComponents() {
        JLabel HowToPlayLabel = new JLabel(new MyImageIcon("src/main/java/Project3_136/resources/InterfaceGame/PaneImage/HowToPlay.png").resize(750,550));
        HowToPlayLabel.setBounds(width/7,height/7,750,550);
        HowToPlayLabel.setOpaque(false);
        MyPane.add(HowToPlayLabel,Integer.valueOf(1));
    }
}
