package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterviewerLoginPanel extends CustomizedLoginPanel implements ActionListener {
    /**
     * The login panel for an HR user.
     *
     * Attributes:
     *
     * panelSwitchListener: the PanelSwitchListener to switch between panels.
     */

    private PanelSwitchListener panelSwitchListener;

    /**
     * Only Constructor for InterviewerLoginPanel.
     */
    InterviewerLoginPanel(){
        super();
        btnLogin.addActionListener(this);
        btnSignUp.addActionListener(this);
        btnBack.addActionListener(this);
    }

    /**
     * Setter method for a PanelSwitchListener.
     * @param listener
     * listener: the PanelSwitchListener to be set.
     */
    void setPanelSwitchListener(PanelSwitchListener listener){
        this.panelSwitchListener = listener;
    }

    /**
     * The method called whenever a selection in the lists changes values.
     * Makes this class an ListSelectionListener.
     * @param e
     * e: The ListSelectionEvent whenever a new value is clicked on.
     */
    public void actionPerformed(ActionEvent e){

        JButton clicked = (JButton)e.getSource();
        if(clicked == btnLogin){
            if(panelSwitchListener != null)
                panelSwitchListener.panelEmitted("InterviewerHomePanel");
        }
        else if(clicked == btnSignUp){
            if(panelSwitchListener != null)
                panelSwitchListener.panelEmitted("InterviewerSignUp");
        }
        else if(clicked == btnBack){
            if(panelSwitchListener != null)
                panelSwitchListener.panelEmitted("CompanyIntroPanel");
        }

    }
}
