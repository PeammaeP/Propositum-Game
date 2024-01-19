package Project3_136.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageIconToBufferedImageConverter {
    public static BufferedImage convertToBufferedImage(ImageIcon MyImageIcon) {
        // Get the Image from the ImageIcon
        Image image = MyImageIcon.getImage();

        // Create a BufferedImage and paint the Image onto it
        BufferedImage bufferedImage = new BufferedImage(
                MyImageIcon.getIconWidth(),
                MyImageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }
}
