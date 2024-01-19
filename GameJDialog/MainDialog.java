package Project3_136.GameJDialog;

import Project3_136.MainClass.GameButton;
import Project3_136.MainClass.MyImageIcon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public abstract class MainDialog extends JDialog implements ActionListener {
    protected JLayeredPane MyPane;
    protected JPanel MyBackGroundPanel;
    protected String MyDialogPath = "src/main/java/Project3_136/resources/InterfaceGame/DialogFrame/";
    protected String MyPanePath = "src/main/java/Project3_136/resources/InterfaceGame/PaneImage/";
    GameButton CloseButton;
    ImageIcon MainPane,SubPane;

    public int width = 1008, height = 720;
    public MainDialog(Frame parentFrame) {
        //Set the Dialog passing JFrame
        super(parentFrame,true);
        setUndecorated(true);

        //Move it to the center of screen
        Point MyPoint = parentFrame.getLocation();
        setLocation(MyPoint.x+10,MyPoint.y+10);

        //Set the pane
        MyPane = new JLayeredPane();
        MyPane.setLayout(null);
        MyPane.setPreferredSize(new Dimension(width,height));
        getContentPane().add(MyPane);

        addGeneralComponents();
        addComponents();
        pack();
    }
    public void addGeneralComponents() {
        //Set pane ( background ) components
        MainPane = new MyImageIcon(MyPanePath + "background.png").resize(width,height);
        SubPane = new MyImageIcon(MyPanePath + "SubPopBG.png");

        MyBackGroundPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(MainPane.getImage(), 0, 0, null);
            }
        };

        MyBackGroundPanel.setLayout(new GridBagLayout());
        MyBackGroundPanel.setOpaque(false);
        MyBackGroundPanel.setBounds(0,0,width,height);

        JLabel MySubPaneLabel = new JLabel(SubPane);
        MyBackGroundPanel.add(MySubPaneLabel,new GridBagConstraints());

        MyPane.add(MyBackGroundPanel,Integer.valueOf(0));

        //Setting the close button
        CloseButton = new GameButton("Close",true);
        CloseButton.setBounds(width-50,0,50,50);
        CloseButton.setOpaque(false);
        CloseButton.setBorderPainted(false);
        CloseButton.setContentAreaFilled(false);
        CloseButton.addActionListener(this);

        MyPane.add(CloseButton,Integer.valueOf(2));
    }

    @Override
    public void actionPerformed(ActionEvent e) { dispose(); }
    abstract public void addComponents();

    public void run() { this.setVisible(true); }
}
