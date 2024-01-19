package Project3_136.MainClass;

import javax.swing.*;
import java.awt.*;

public class GameJCheckBox extends JCheckBox {
    protected String MyBoxPath = "src/main/java/Project3_136/resources/InterfaceGame/MyCheckBoxImage/";
    public GameJCheckBox(String CheckButtonText,boolean isMute) {
        super(CheckButtonText,isMute);
        addComponents();
    }
    public void addComponents() {
        setFont(new Font("Arial", Font.BOLD, 20));
        setOpaque(false);
        setFocusable(false);
        setIcon(new MyImageIcon(MyBoxPath + "Uncheck.png").resize(25,25));
        setSelectedIcon(new MyImageIcon(MyBoxPath + "Check.png").resize(25,25));
    }
}
