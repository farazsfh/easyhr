package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroPanel extends SuperPanel implements ActionListener{


    private JButton btnCompany;
    private JButton btnApplicant;

    private PanelSwitchListener panelSwitchListener;

    /**
     * Creates a spring layout and adds all buttons to this panel
     */
    IntroPanel(){

        setBackground(new Color(0, 153, 255));

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        btnCompany = new JButton("Company");
        btnApplicant = new JButton("Applicant");



        btnCompany.setPreferredSize(new Dimension(100, 50));
        btnApplicant.setPreferredSize(new Dimension(100, 50));

        btnApplicant.addActionListener(this);
        btnCompany.addActionListener(this);

        layout.putConstraint(SpringLayout.WEST, btnCompany, 270 , SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, btnCompany, 155, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.NORTH, btnApplicant, 20, SpringLayout.SOUTH, btnCompany);
        layout.putConstraint(SpringLayout.WEST, btnApplicant, 0, SpringLayout.WEST, btnCompany);
        
        add(btnApplicant);
        add(btnCompany);

    }

    /**
     * Changes the current active PanelSwitchListener
     *
     * @param  listener The PanelSwitchListener to switch to
     */
    void setPanelSwitchListener(PanelSwitchListener listener){
        this.panelSwitchListener = listener;
    }

    /**
     * Checks if user clicked a specific button and calls required methods
     *
     * @param e The action performed by the user
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        if(clicked == btnCompany) {
            if (panelSwitchListener != null)
                panelSwitchListener.panelEmitted("CompanyIntroPanel");
        }
        else if(clicked == btnApplicant) {
            if (panelSwitchListener != null)
                panelSwitchListener.panelEmitted("ApplicantLoginPanel");
        }

    }
}
