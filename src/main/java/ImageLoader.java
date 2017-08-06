import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author Yakiv Tymoshenko
 * @since 06-Aug-17
 */
public class ImageLoader {

    public static BufferedImage loadImage(String fileName) {
        BufferedImage image = null;
        try {
            URL file = ImageLoader.class.getResource(fileName);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
