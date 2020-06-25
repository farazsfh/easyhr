package ProgramFiles.GuiFiles;

import ProgramFiles.GuiFiles.SuperPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

abstract class CustomizedApplicantHomeFunctionalityPanel extends SuperPanel {

    /**
     * Sets dimensions of panel
     */
    CustomizedApplicantHomeFunctionalityPanel(){
        setPreferredSize(new Dimension(530, 480));
    }

    /**
     * Makes JLabel text bold
     *
     * @param label Label to make bold
     */
    void makeLabelBold(JLabel label){
        Font lblFont = label.getFont();
        label.setFont(lblFont.deriveFont(lblFont.getStyle() ^ Font.BOLD));
    }

    abstract void init();
    abstract void createLayout();

}
