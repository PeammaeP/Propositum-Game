package Ex8_136;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    static final String PATH             = "src/main/java/Ex8_136/resources/";
    static final String FILE_BG          = PATH + "wallbackground.jpg";
    static final String FILE_WORM_LEFT   = PATH + "worm_left.png";
    static final String FILE_WORM_RIGHT  = PATH + "worm_right.png";
    static final String FILE_LEAF        = PATH + "leaf.png";
    static final String FILE_SCORPION    = PATH + "scorpion.png";    
    
    static final String FILE_THEME       = PATH + "theme.wav";
    static final String FILE_COIN        = PATH + "coin.wav";
    static final String FILE_KNIFE       = PATH + "knife.wav";
    
    //----- Sizes and locations
    static final int FRAMEWIDTH  = 1200;
    static final int FRAMEHEIGHT = 650;

    static final int WORMWIDTH   = 140;
    static final int WORMHEIGHT  = 120;
    static final int ITEMWIDTH   = 50;
    static final int ITEMHEIGHT  = 60;
}


// Auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}


// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip         clip;
    private FloatControl gainControl;         

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}