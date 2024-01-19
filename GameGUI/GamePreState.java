package Project3_136.GameGUI;

import Project3_136.GameMechanism.GamePanelFrame;
import Project3_136.MainClass.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GamePreState extends GameLayeredPane implements ActionListener {
    protected GameFinishedState MyGameFinishedState;
    public JFrame MyFrame;
    public JLabel SkinText,DifficultyLabelText,SkinDisplayLabel,JCheckButtonLabel;
    private JComboBox<String> GameDifficulty;
    private ButtonGroup MyButtonGroup;
    protected GameJRadioButton MyRadioButtonSkin1,MyRadioButtonSkin2,MyRadioButtonSkin3;
    public JPanel MyButtonMenu,OptionPane;
    private GameButton OKButton , BackButton;
    protected String CharacterSkin;
    public GamePreState(JFrame MyFrame, GameFinishedState MyGameFinishedState, MySoundEffectControl MySound) {
        super(MySound);
        this.MyFrame = MyFrame;
        this.MyGameFinishedState = MyGameFinishedState;
        CharacterSkin = "RedBoy";
    }
    @Override
    public void addGeneralComponent() {
        //Adding Background Image
        addBackgroundImage("background_red.png");
        //Setting the button pane
        MyButtonMenu = new JPanel();
        MyButtonMenu.setOpaque(false);
        MyButtonMenu.setBounds(20,height-100,width-40,70);
        MyButtonMenu.setLayout(new BorderLayout());

        //Setting each button in game
        //Setting Back Button
        BackButton = new GameButton("Back",true);
        BackButton.setSoundControl(MySound);
        //Setting OK Button
        OKButton = new GameButton("Ok",true) {
            @Override
            public void addButtonComponents() {
                MySound.playSoundEffect(MySound.SoundClick);
                MyFrame.setVisible(false);
                String MyDifficulty = Objects.requireNonNull(GameDifficulty.getSelectedItem()).toString();
                System.out.println("=============================");
                System.out.println("Player is playing the game with difficulty : " + MyDifficulty);
                System.out.println("Game Frame Loading ...");
                System.out.println("=============================");
                Thread MyGameThread = new Thread(()-> new GamePanelFrame(MyFrame,MySound,MyGameFinishedState,CharacterSkin,MyDifficulty));
                MyGameThread.start();
            }
        };
        OKButton.setSoundControl(MySound);

        MyButtonMenu.add(BackButton,BorderLayout.WEST);
        MyButtonMenu.add(OKButton,BorderLayout.EAST);

        add(MyButtonMenu,JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void addComponent() {
        addOtherComponents();
        SkinDisplayLabel = new JLabel(new MyImageIcon(MyPlayerSkinPath + "RedWalkingSprites/boy_down_1.png").resize(200,240));
        SkinDisplayLabel.setBounds(100,65,width/2,height/2);
        add(SkinDisplayLabel,JLayeredPane.DRAG_LAYER);
    }
    public void addOtherComponents() {
        SkinText = new JLabel();
        SkinText.setIcon(new MyImageIcon(MyComponentPreGamePath + "Skin.png"));
        SkinText.setHorizontalAlignment(JLabel.LEFT);
        SkinText.setOpaque(false);

        // Set the JLabel of the JCheckButton
        JCheckButtonLabel = new JLabel();
        JCheckButtonLabel.setLayout(new BoxLayout(JCheckButtonLabel, BoxLayout.Y_AXIS));
        JCheckButtonLabel.setOpaque(false);
        JCheckButtonLabel.setPreferredSize(new Dimension(width, 20));
        JCheckButtonLabel.setHorizontalAlignment(JLabel.LEADING);

        // Set the JCheckButtons
        MyRadioButtonSkin1 = new GameJRadioButton("RedBoy");
        MyRadioButtonSkin1.setForeground(Color.lightGray);
        MyRadioButtonSkin1.setSelected(true);
        MyRadioButtonSkin1.addActionListener(this);

        MyRadioButtonSkin2 = new GameJRadioButton("HelShinKi");
        MyRadioButtonSkin2.setForeground(Color.lightGray);
        MyRadioButtonSkin2.addActionListener(this);

        MyRadioButtonSkin3 = new GameJRadioButton("BlueFire");
        MyRadioButtonSkin3.setForeground(Color.lightGray);
        MyRadioButtonSkin3.addActionListener(this);

        JCheckButtonLabel.add(MyRadioButtonSkin1);
        JCheckButtonLabel.add(MyRadioButtonSkin2);
        JCheckButtonLabel.add(MyRadioButtonSkin3);

        MyButtonGroup = new ButtonGroup();
        MyButtonGroup.add(MyRadioButtonSkin1);
        MyButtonGroup.add(MyRadioButtonSkin2);
        MyButtonGroup.add(MyRadioButtonSkin3);

        // Set the optionPane
        OptionPane = new JPanel(new GridLayout(4,2));
        OptionPane.setOpaque(false);
        OptionPane.setBounds(width/2 + 125, height / 3 - 100, width / 2 - 50, height - 150);

        // Set the All Components

        // Set the Difficulty Text Label
        DifficultyLabelText = new JLabel();
        DifficultyLabelText.setLayout(new FlowLayout(FlowLayout.LEFT,10,40));
        DifficultyLabelText.setOpaque(false);
        DifficultyLabelText.setIcon(new MyImageIcon(MyComponentPreGamePath + "mode.png"));
        DifficultyLabelText.setVerticalAlignment(JLabel.NORTH);

        // Set the Difficulty JComboBox
        String[] Difficulty = {"Beginner", "Intermediate", "Advanced", "Extreme", "Insane"};
        GameDifficulty = new JComboBox<>(Difficulty);
        GameDifficulty.setPreferredSize(new Dimension(200, 50));
        GameDifficulty.setFont(new Font("Arial", Font.BOLD, 18));

        ((JLabel)GameDifficulty.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        ((FlowLayout) DifficultyLabelText.getLayout()).setHgap(10); // Adjust the gap width as needed
        DifficultyLabelText.add(GameDifficulty);

        OptionPane.add(SkinText);
        OptionPane.add(JCheckButtonLabel);
        OptionPane.add(DifficultyLabelText,BorderLayout.SOUTH);

        add(OptionPane, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CharacterSkin = MyButtonGroup.getSelection().getActionCommand();

        if(Objects.equals(CharacterSkin, "RedBoy")) {
            SkinDisplayLabel.setIcon(new MyImageIcon(MyPlayerSkinPath + "RedWalkingSprites/boy_down_1.png").resize(200,240));
        }
        else if(Objects.equals(CharacterSkin, "HelShinKi")) {
            SkinDisplayLabel.setIcon(new MyImageIcon(MyPlayerSkinPath + "GreenWalkingSprites/boy_down_1.png").resize(200,240));
        }
        else if(Objects.equals(CharacterSkin, "BlueFire")) {
            SkinDisplayLabel.setIcon(new MyImageIcon(MyPlayerSkinPath + "BlueWalkingSprites/boy_down_1.png").resize(200,240));
        }
    }
    public GameButton getOKButton() {
        return OKButton;
    }
    public GameButton getBackButton() {
        return BackButton;
    }
}
