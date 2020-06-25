package ProgramFiles.GuiFiles;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BlackCustomizedBackButton extends JButton {

    /**
     * Constructor that creates a black back button based on the given image
     */
    public BlackCustomizedBackButton(){
        super();
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("Back-Button.png")));
            setIcon(img);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
