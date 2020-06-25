package ProgramFiles.GuiFiles;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CustomizedBackButton extends JButton {

    /**
     * Constructor that creates a white back button based on the given image
     */
    public CustomizedBackButton(){
        super();
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("Back-Button-White.png")));
            setIcon(img);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }


}
