package Ex7_136;

import java.awt.Image;
import javax.swing.ImageIcon;


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    static final String PATH        = "src/main/java/Ex7_136/resources/";
    static final String FILE_BG     = PATH + "background.jpg";
    static final String FILE_CAR    = PATH + "car.png";
    static final String FILE_BIRD   = PATH + "bird.png";
    static final String FILE_HAT    = PATH + "hat.png";    
    static final String FILE_PORTAL = PATH + "portal.png";
    
    //----- Sizes and locations
    static final int FRAMEWIDTH  = 1000;
    static final int FRAMEHEIGHT = 600;
    static final int GROUND_Y    = 350;
    static final int SKY_Y       = 50;
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