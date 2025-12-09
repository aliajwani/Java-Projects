package my_package;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A custom JLabel class that displays and manages pet sprite images.
 * This class extends JLabel to provide specialized functionality for displaying pet sprites
 * with proper scaling and image management.
 * @author danielantal
 */
public class PetSpriteLabel extends JLabel {
    private ImageIcon originalIcon;
    private ImageIcon scaledIcon;
    private Timer timer;

    /**
     * Constructs a new PetSpriteLabel with the specified image path.
     * @param imagePath The path to the image file to display
     */
    public PetSpriteLabel(String imagePath) {
        originalIcon = new ImageIcon(imagePath);
        scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        setIcon(scaledIcon);
        setText("");
        timer = new Timer(100, e -> repaint());
        timer.start();
    }

    /**
     * Updates the pet's image with a new sprite.
     * @param imagePath The path to the new image file
     */
    public void setPetImage(String imagePath) {
        originalIcon = new ImageIcon(imagePath);
        scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        setIcon(scaledIcon);
        repaint();
    }
}
