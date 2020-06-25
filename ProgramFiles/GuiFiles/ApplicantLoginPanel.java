package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicantLoginPanel extends CustomizedLoginPanel implements ActionListener {

    private PanelSwitchListener panelSwitchListener;

    ApplicantLoginPanel(){

        super();
        btnLogin.addActionListener(this);
        btnSignUp.addActionListener(this);
        btnBack.addActionListener(this);

    }

    void setPanelSwitchListener(PanelSwitchListener listener){
        this.panelSwitchListener = listener;
    }

    public void actionPerformed(ActionEvent e){

        JButton clicked = (JButton)e.getSource();
        if(clicked == btnLogin){
            if(panelSwitchListener != null)
                panelSwitchListener.panelEmitted("ApplicantHomePanel");
        }
        else if(clicked == btnSignUp){
            if(panelSwitchListener != null)
                panelSwitchListener.panelEmitted("ApplicantSignUp");
        }
        else if(clicked == btnBack){
            if(panelSwitchListener != null)
                panelSwitchListener.panelEmitted("IntroPanel");
        }

    }






}
