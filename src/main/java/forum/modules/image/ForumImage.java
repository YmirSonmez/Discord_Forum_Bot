package forum.modules.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ForumImage {

    public static BufferedImage fromURL(String stringURL){
        BufferedImage img;
        try {
            URLConnection url = new URL(stringURL).openConnection();
            url.setRequestProperty("User-Agent", "forum bot");
            img = ImageIO.read(url.getInputStream());
        } catch (IOException e) {
            img = null;
        }
        return img;
    }





}
