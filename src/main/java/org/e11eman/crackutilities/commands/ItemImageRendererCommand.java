package org.e11eman.crackutilities.commands;

import org.e11eman.crackutilities.utilities.ImageUtilities;
import org.e11eman.crackutilities.utilities.rendering.ItemScreen;
import org.e11eman.crackutilities.utilities.rendering.Screen;
import org.e11eman.crackutilities.utilities.toolclasses.Command;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ItemImageRendererCommand extends Command {
    public Robot robot;

    public ItemImageRendererCommand() {
        super("IImageRenderer", "Lets you render images on the item you are currently holdings tooltip", "\nIImageRenderer <URL> <width> <height> <optionalChar>\nIImageRenderer screenshot <width> <height> <optionalChar>");
    }

    {
        try {
            System.setProperty("java.awt.headless", "false");
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(ArrayList<String> arguments) {
        int width = Integer.parseInt(arguments.get(1));
        int height = Integer.parseInt(arguments.get(2));
        BufferedImage processimage;
        BufferedImage resizedimage;
        ItemScreen image;

        if (arguments.size() > 3) {
            image = new ItemScreen(width, height, arguments.get(3));
        } else {
            image = new ItemScreen(width, height, "█");
        }

        try {
            if (arguments.get(0).equalsIgnoreCase("screenshot")) {
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

                BufferedImage capture = robot.createScreenCapture(screenRect);
                BufferedImage resized = ImageUtilities.resize(capture, width, height);

                int[] pixels = resized.getRGB(0, 0, resized.getWidth(), resized.getHeight(), null, 0, resized.getWidth());
                for (int y = 0; y < resized.getHeight(); y++) {
                    for (int x = 0; x < resized.getWidth(); x++) {
                        int pixel = pixels[y * resized.getWidth() + x];
                        int red = (pixel >> 16) & 255;
                        int green = (pixel >> 8) & 255;
                        int blue = pixel & 255;

                        image.screen[x][y] = String.format("%02x%02x%02x", red, green, blue);
                    }
                }
            } else if (arguments.get(0).startsWith("http")) {
                processimage = ImageUtilities.urlToImage(new URL(arguments.get(0)));
                resizedimage = ImageUtilities.resize(processimage, width, height);

                for(int y = 0; y < resizedimage.getHeight(); y++) {
                    for(int x = 0; x < resizedimage.getWidth(); x++) {
                        int RGBA = resizedimage.getRGB(x, y);
                        int red = (RGBA >> 16) & 255;
                        int green = (RGBA >> 8) & 255;
                        int blue = RGBA & 255;

                        image.screen[x][y] = String.format("#%02x%02x%02x", red, green, blue);
                    }
                }
            }
            image.draw();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
