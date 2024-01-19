package Project3_136.GameJDialog;

import Project3_136.MainClass.GameJCheckBox;
import Project3_136.MainClass.MyImageIcon;
import Project3_136.MainClass.MySoundEffectControl;

import javax.swing.*;
import java.awt.*;

public class SettingDialog extends MainDialog {
    JLabel MyText,MyMusicText,MyMusicIcon,MySoundIconText,MySoundEffectIcon;
    JSlider MyMusicSlider,MyEffectSlider;
    JPanel MyCenterPane;
    public GameJCheckBox MyMusicMuteBox,MyEffectMuteBox;
    public static MySoundEffectControl MySettingSound;
    public SettingDialog(Frame parentFrame) {
        super(parentFrame);
    }
    @Override
    public void addComponents() {
        //Set the top text with " Setting " Text
        MyText = new JLabel(new MyImageIcon(MyDialogPath + "Setting.png"));
        MyText.setBounds(0,40,width,height);
        MyText.setVerticalAlignment(JLabel.NORTH);

        MyCenterPane = new JPanel(new GridLayout(4,1,0,0));
        MyCenterPane.setOpaque(false);
        MyCenterPane.setBounds(width/4,height/4 - 50,width/2,height/2);

        //Set the center components ( Text Image, JRadioButton )

        //Settings The Music Components
        MyMusicText = new JLabel();
        MyMusicText.setIcon(new MyImageIcon(MyDialogPath + "music.png"));
        MyMusicText.setHorizontalAlignment(JLabel.LEFT);
        MyMusicText.setOpaque(false);

        //Add the MusicIcon
        MyMusicIcon = new JLabel();
        MyMusicIcon.setIcon(new MyImageIcon(MyDialogPath + "soundIcon.png").resize(40,40));
        MyMusicIcon.setLayout(new FlowLayout());
        MyMusicIcon.setOpaque(false);

        //Add the Slider to adjust the Music Volume
        MyMusicSlider = new JSlider(-40,5);
        MyMusicSlider.setPreferredSize(new Dimension(200,80));
        MyMusicSlider.setOpaque(false);
        MyMusicSlider.setValue(Math.round(MySettingSound.getMusicVolume()));
        MyMusicSlider.addChangeListener(e -> MySettingSound.changeMusicVolume(MyMusicSlider.getValue()));

        //add the Box " Mute " Component to adjust the music volume
        MyMusicMuteBox = new GameJCheckBox("",MySettingSound.getMusicMute());
        MyMusicMuteBox.addActionListener(e -> {
            System.out.println("Mute checkbox clicked: " + MyMusicMuteBox.isSelected());
            MySettingSound.setMusicMute(MyMusicMuteBox.isSelected());
        });

        //add it to the MyMusicLabel
        MyMusicIcon.add(MyMusicSlider);
        MyMusicIcon.add(MyMusicMuteBox);
        MyCenterPane.add(MyMusicText);
        MyCenterPane.add(MyMusicIcon);

        //Add the SoundEffect Text
        MySoundIconText = new JLabel();
        MySoundIconText.setIcon(new MyImageIcon(MyDialogPath + "sound.png"));
        MySoundIconText.setHorizontalAlignment(JLabel.LEFT);
        MySoundIconText.setOpaque(false);

        //Add the SoundEffect Icon
        MySoundEffectIcon = new JLabel();
        MySoundEffectIcon.setIcon(new MyImageIcon(MyDialogPath + "soundIcon.png").resize(40,40));
        MySoundEffectIcon.setLayout(new FlowLayout());
        MySoundEffectIcon.setOpaque(false);

        //add the Effect Slider to adjust the effect volume
        MyEffectSlider = new JSlider(-40,5);
        MyEffectSlider.setPreferredSize(new Dimension(200,80));
        MyEffectSlider.setOpaque(false);
        MyEffectSlider.setValue(Math.round(MySettingSound.getSoundVolume()));
        MyEffectSlider.addChangeListener(e -> MySettingSound.changeSoundVolume(MyEffectSlider.getValue()));

        //add the Box " Effect " Component to adjust the effect volume
        MyEffectMuteBox = new GameJCheckBox("",MySettingSound.getSoundMute());
        MyEffectMuteBox.addActionListener(e -> {
            System.out.println("Mute checkbox clicked: " + MyEffectMuteBox.isSelected());
            MySettingSound.setSoundMute(MyEffectMuteBox.isSelected());
        });

        //add it to the MySoundLabel
        MySoundEffectIcon.add(MyEffectSlider);
        MySoundEffectIcon.add(MyEffectMuteBox);
        MyCenterPane.add(MySoundIconText);
        MyCenterPane.add(MySoundEffectIcon);

        //finally, add it to the main pane ( MainDialog )
        MyPane.add(MyCenterPane,JLayeredPane.DRAG_LAYER);

        MyPane.add(MyText,Integer.valueOf(1));
    }
    public static void setSound(MySoundEffectControl Sound) {
        MySettingSound = Sound;
    }
}
