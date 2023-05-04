package ru.scrin.service.controlers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр класса Robot
            Robot robot = new Robot();

            // Получаем размеры экрана
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // Создаем прямоугольник, который описывает всю область экрана
            Rectangle screenRect = new Rectangle(screenSize);

            // Создаем скриншот экрана
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);

            // Сохраняем скриншот в файл
            ImageIO.write(screenCapture, "jpg", new File("src/screenshot/scr.jpg"));

            System.out.println("Скриншот успешно создан и сохранен в файл screenshot.jpg");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
