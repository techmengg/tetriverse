/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package csculminatingprojecttetris;

/**
 *
 * @author techmengg
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 The ImageLoader class provides functionality to load image files from a specified path.
 The loadImage() method uses the ImageIO class to read the image file and returns it as a BufferedImage object.
 If an IOException occurs during the loading process, it prints the stack trace and terminates the program.
 The method can be accessed using the class name ImageLoader.loadImage().
*/

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            //return ImageIO.read(new File("data" + path));
            return ImageIO.read(new File(path));

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;

    }
}
