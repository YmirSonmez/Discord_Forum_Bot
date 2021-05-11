package forum.modules.image;

import forum.database.DataBase;
import forum.Main;
import net.dv8tion.jda.api.entities.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ThreadImage {
    private final User writer;
    private final User admin;
    private final String threadTitle;
    private final String threadCategory;
    private final String templatePath;
    private BufferedImage scaledBI;
    private Graphics2D g;

    public ThreadImage(User writer,User admin, String threadTitle,String threadCategory) {
        this.writer=writer;
        this.admin=admin;
        this.threadTitle = threadTitle;
        this.threadCategory=threadCategory;
        this.templatePath = Main.path+"resources/konuarkaplan" + new Random().nextInt(10)+".png";
    }

    public File getFile(){
        getGraphics2D();
        drawStrings();
        drawProfileImage();
        g.dispose();
        File file = new File(Main.path+"oldfiles/Konu.png");
        try {
            ImageIO.write(scaledBI,"png",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void getGraphics2D(){
        Graphics2D g;
        try {
            Image image = new ImageIcon(this.templatePath).getImage();
            BufferedImage bufferedImage = ImageIO.read(new File(this.templatePath));
            this.scaledBI = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            g = this.scaledBI.createGraphics();
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHints(rh);
            g.setComposite(AlphaComposite.Src);
            g.drawImage(image, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        } catch (IOException e) {
            e.printStackTrace();
            g=null;
        }
        this.g= g;
    }

    private void drawProfileImage(){
        BufferedImage profileImage = new ProfileImage(this.writer).getBufferedImage();
        this.g.setClip(new Ellipse2D.Float(60, 33, profileImage.getWidth()+5, profileImage.getHeight()+5));
        this.g.drawImage(profileImage, 62, 37,profileImage.getWidth(), profileImage.getHeight(), null);
        this.g.setStroke(new BasicStroke(7));
        this.g.setColor(new Color(94, 94, 94));
        this.g.setClip(new Rectangle2D.Float(0,0,this.scaledBI.getWidth(),this.scaledBI.getHeight()));
        this.g.draw(new Ellipse2D.Double(60,33,profileImage.getWidth()+5, profileImage.getHeight()+5));
        GradientPaint paint = new GradientPaint(0,0,new Color(0xFFFDF449, true),20,250,new Color(0xFF0900));
        this.g.setPaint(paint);
        this.g.drawArc(60,33,profileImage.getWidth()+5, profileImage.getHeight()+5,0,getAngel(10000));
    }
    private void drawStrings(){
        this.g.setFont(new Font("SansSerif ", Font.BOLD, 30));
        this.g.drawString(this.threadTitle,230,225);
        this.g.drawString(this.threadCategory,230,303);
        this.g.drawString(this.writer.getName(),205,75);
        this.g.drawString("XP: "+ DataBase.getMember(this.writer.getId()).getXp(),205,105);
        this.g.drawString("KONU: "+ DataBase.getMember(this.writer.getId()).getThreadCount(),205,135);
        this.g.setColor(Color.GRAY);
        this.g.drawString(this.admin.getName(),890,390);
    }
    private int getAngel(int max){
        if(DataBase.getMember(this.writer.getId()).getXp()>max){
            return 360;
        }else{
            float xp = DataBase.getMember(this.writer.getId()).getXp();
            float angel = (xp/max)*360;
            return (int) angel;
        }
    }

}
