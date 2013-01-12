/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 12.01.13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Апплет посл-но меняющий картинки
 */
public class ImgChanger extends javax.swing.JApplet
        implements Runnable {

    Image[] picture = new Image[4];
    int totalPictures = 0;
    int current = 0;
    Thread runner;
    int pause = 500;

    public void init() {
        System.out.println(getCodeBase());
        for (int i = 0; i < 4; i++) {
            String imageText = null;
            imageText = "image" + (i+1) + ".gif";
            if (imageText != null) {
                totalPictures++;
                try {
                    URL url = new URL("file:/d:/Java/OOP/kursach/");
                    picture[i] = getImage(url, imageText);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else
                break;
        }
        String pauseText = null;
        pauseText = "300";
        if (pauseText != null) {
            pause = Integer.parseInt(pauseText);
        }
    }

    public void paint(Graphics screen) {
        super.paint(screen);
        Graphics2D screen2D = (Graphics2D) screen;
        if (picture[current] != null)
            screen.drawImage(picture[current], 0, 0, this);
    }

    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (runner == thisThread) {
            repaint();
            current++;
            if (current >= totalPictures)
                current = 0;
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
            }
        }
    }

    public void stop() {
        if (runner != null) {
            runner = null;
        }
    }
}
