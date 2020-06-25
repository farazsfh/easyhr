package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.*;

class ApplicantHomePanel extends SuperPanel {

    private JLayeredPane paneApplicant;
    private ApplicantHomeMyAccountPanel myAccountPanel;
    private ApplicantHomePostingsPanel postingsPanel;
    private ApplicantHomeStatusPanel statusPanel;

    private ApplicantHomePanelButtonsPanel applicantHomePanelButtonsPanel;


    private static final String APPLICANT_MY_ACCOUNT = "ApplicantHomeMyAccountPanel";
    private static final String APPLICANT_POSTINGS = "ApplicantHomePostingsPanel";
    private static final String APPLICANT_STATUS = "ApplicantHomeStatusPanel";

    /**
     * Constructor that creates the applicant home panel and calls for switches to the appropriate panel
     */
    ApplicantHomePanel(){

        init();
        createLayout();

        applicantHomePanelButtonsPanel.setPanelSwitchListener(new PanelSwitchListener() {
            @Override
            public void panelEmitted(String panel) {
                switch(panel){
                    case APPLICANT_MY_ACCOUNT:
                        myAccountPanel.updateFields();
                        paneApplicant.setVisible(true);
                        switchPanel(myAccountPanel, APPLICANT_MY_ACCOUNT);
                        break;
                    case APPLICANT_POSTINGS:
                        postingsPanel.updatePostingsPanel();
                        paneApplicant.setVisible(true);
                        switchPanel(postingsPanel, APPLICANT_POSTINGS);
                        break;
                    case APPLICANT_STATUS:
                        statusPanel.updateStatusPanel();
                        paneApplicant.setVisible(true);
                        switchPanel(statusPanel, APPLICANT_STATUS);
                        break;
                }
            }
        });


    }

    /**
     * Initializes the various applicant panels and sets the layout of the pane
     */
    private void init(){
        paneApplicant = new JLayeredPane();
        paneApplicant.setLayout(new CardLayout());
        paneApplicant.setVisible(false);
        applicantHomePanelButtonsPanel = new ApplicantHomePanelButtonsPanel();
        myAccountPanel = new ApplicantHomeMyAccountPanel();
        postingsPanel = new ApplicantHomePostingsPanel();
        statusPanel = new ApplicantHomeStatusPanel();
    }

    /**
     * Sets up spring layout for this panel to use
     */
    private void createLayout(){
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        // put constraints - applicantHomeButtonsPanel
        layout.putConstraint(SpringLayout.WEST, applicantHomePanelButtonsPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, applicantHomePanelButtonsPanel, 0, SpringLayout.NORTH, this);
        // put constrains - paneApplicant
        layout.putConstraint(SpringLayout.WEST, paneApplicant, 104, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, paneApplicant, 0, SpringLayout.NORTH, this);
        // add to ApplicantHomePanel
        add(applicantHomePanelButtonsPanel);
        add(paneApplicant);
        // add to paneApplicant
        paneApplicant.add(myAccountPanel, APPLICANT_MY_ACCOUNT);
        paneApplicant.add(postingsPanel, APPLICANT_POSTINGS);
        paneApplicant.add(statusPanel, APPLICANT_STATUS);
    }

    /**
     * Switches current view to specified panel
     * @param panel The JPanel to be switched to
     * @param description A description of the panel that is being switched to
     */
    private void switchPanel(JPanel panel, String description){
        paneApplicant.removeAll();
        paneApplicant.add(panel, description);
        paneApplicant.repaint();
        paneApplicant.revalidate();
        if (panel.equals(statusPanel))
            statusPanel.updateStatusPanel();
    }

}
