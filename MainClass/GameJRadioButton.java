package Project3_136.MainClass;

import javax.swing.*;
import java.awt.*;

public class GameJRadioButton extends JRadioButton {
    public GameJRadioButton(String Name) {
        super(Name);
        setActionCommand(Name);
        addComponent();
    }
    public void addComponent() {
        setFont(new Font("Arial",Font.BOLD,18));
        setOpaque(false);
        setFocusable(false);

        String radioPath = "src/main/java/Project3_136/resources/InterfaceGame/MyCheckBoxImage/";

        setIcon(new MyImageIcon(radioPath + "radioUncheck.png").resize(30,30));
        setSelectedIcon(new MyImageIcon(radioPath + "radioCheck.png").resize(30,30));
    }
}
