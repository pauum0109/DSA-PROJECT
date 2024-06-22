package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageManager {
    public static Image load(String name, Component cmp) {
        Image img = null;
        String filePath = System.getProperty("user.dir") +
                System.getProperty("file.separator") + "data" +
                System.getProperty("file.separator") + "images" +
                System.getProperty("file.separator") + name;

        try {
            File imgFile = new File(filePath);
            if (!imgFile.exists()) {
                System.out.println("Error: Image file " + filePath + " does not exist.");
                return null;
            }
            img = ImageIO.read(imgFile);
            if (img == null) {
                System.out.println("Error: Could not load image " + name);
            }
        } catch (IOException ex) {
            System.out.println("Error: IOException while loading image " + name);
            ex.printStackTrace();
        }

        return img;
    }
}