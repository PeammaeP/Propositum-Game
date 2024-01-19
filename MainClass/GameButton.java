package Project3_136.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class GameButton extends JButton implements MouseListener {
    public final String PathNormalButton = "src/main/java/Project3_136/resources/InterfaceGame/GameButtonImage/normal/";
    public final String PathHoverButton = "src/main/java/Project3_136/resources/InterfaceGame/GameButtonImage/hover/";
    public final String PathPressButton = "src/main/java/Project3_136/resources/InterfaceGame/GameButtonImage/press/";
    public String ButtonName;
    public boolean buttonAnimation;
    GameLayeredPane currentPane, destinationPane;
    MyImageIcon normalButtonImage,hoverButtonImage,pressButtonImage;
    MySoundEffectControl Sound;

    //The Button constructor for the default button
    public GameButton(String ButtonName,boolean buttonAnimation) {
        super(ButtonName);
        this.ButtonName = ButtonName;
        this.buttonAnimation = buttonAnimation;
        ButtonComponents();
    }

    //The Button constructor for the small image button
    public GameButton(String ButtonName,boolean buttonAnimation,int width,int length) {
        this.ButtonName = ButtonName;
        this.buttonAnimation = buttonAnimation;
        setPreferredSize(new Dimension(width,length));
        ButtonComponents();
    }

    public void ButtonComponents() {
        if(buttonAnimation) {
            hoverButtonImage = new MyImageIcon(PathHoverButton + ButtonName + ".png");
            setSelectedIcon(hoverButtonImage);

            pressButtonImage = new MyImageIcon(PathPressButton + ButtonName + ".png");
            setPressedIcon(pressButtonImage);
        }
        if(Objects.equals(ButtonName, "Settings") || Objects.equals(ButtonName,"HowToPlay")) {
            normalButtonImage = new MyImageIcon(PathNormalButton + ButtonName + ".png").resize(60,60);
        }
        else if(Objects.equals(ButtonName, "Close")) {
            normalButtonImage = new MyImageIcon(PathNormalButton + ButtonName + ".png").resize(40,40);
        }
        else { normalButtonImage = new MyImageIcon(PathNormalButton + ButtonName + ".png"); }

        setIcon(normalButtonImage);
        setText("");

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
        //Adding the MouseListener to the Button
        addMouseListener(this);
    }
    public void addButtonComponents() { Sound.playSoundEffect(Sound.SoundClick); }
    public void LinkingBetweenPane(GameLayeredPane MyCurrentPane,GameLayeredPane MyDestinationPane) {
        currentPane = MyCurrentPane;
        destinationPane = MyDestinationPane;
    }
    public void setSoundControl(MySoundEffectControl sound) { Sound = sound; }
    @Override
    public void mouseClicked(MouseEvent e) {
        addButtonComponents();
        currentPane.setVisible(false);
        destinationPane.setVisible(true);
    }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { setSelected(true); }
    @Override
    public void mouseExited(MouseEvent e) { setSelected(false); }
}
