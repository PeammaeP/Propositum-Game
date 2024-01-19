package Project3_136.MainClass;

import javax.swing.*;
import java.awt.*;

public class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String Filename)  { super(Filename); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
        Image Old_img = this.getImage();
        Image New_img = Old_img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(New_img);
    }
}