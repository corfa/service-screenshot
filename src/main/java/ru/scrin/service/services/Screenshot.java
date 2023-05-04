package ru.scrin.service.services;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Component
public class Screenshot {
    static {

        System.setProperty("java.awt.headless", "false");
    }
    public void makeSreen(){
        try {
            Robot robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRect = new Rectangle(screenSize);
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            ImageIO.write(screenCapture, "jpg", new File("src/screenshot/scr.jpg"));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
