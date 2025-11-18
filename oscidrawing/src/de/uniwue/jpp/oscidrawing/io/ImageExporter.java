package de.uniwue.jpp.oscidrawing.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageExporter {
    public static boolean writeToPNG(String pathWithoutSuffix, BufferedImage img) {
        try{
            return ImageIO.write(img,"png",new File(pathWithoutSuffix+".png"));
        }catch (IOException e){
            return false;

        }

    }
}
