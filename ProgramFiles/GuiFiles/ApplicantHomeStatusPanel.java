package ProgramFiles.GuiFiles;

import ProgramFiles.GuiFiles.SuperPanel;
import ProgramFiles.Posting;
import ProgramFiles.UserApplication;
import ProgramFiles.Applicant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicantHomeStatusPanel extends CustomizedApplicantHomeFunctionalityPanel implements ActionListener, ListSelectionListener {


    private JLabel lblAppliedJobs;
    private JLabel lblStatus;
    private JLabel appliedJobStatus;
    private JList<UserApplication> appliedJobs;
    private JScrollPane appliedJobsScroller;
    private JButton btnShowStatus;
    private JButton btnRemoveApplication;
    private UserApplication currentApplication;
    private DefaultListModel<UserApplication> dlmApplications;

    /**
     * Constructor that creates the panel with the required buttons for an applicant and places action listeners
     * on all buttons
     */
    ApplicantHomeStatusPanel(){
        super();
        init();
        createLayout();
        btnShowStatus.addActionListener(this);
        btnRemoveApplication.addActionListener(this);
        appliedJobs.getSelectionModel().addListSelectionListener(this);
    }

    /**
     * Initializes the grid layout and adds required lists and buttons
     */
    void init(){
        lblAppliedJobs = new JLabel("Applied Jobs"); makeLabelBold(lblAppliedJobs);
        lblStatus = new JLabel("Status"); makeLabelBold(lblStatus);
        // create the list of applied jobs and add scrollbar
        dlmApplications = new DefaultListModel<>();
        appliedJobs = new JList<>(dlmApplications);
        appliedJobs.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appliedJobsScroller = new JScrollPane(appliedJobs);
        //
        appliedJobStatus = new JLabel();
        btnShowStatus = new JButton("Show Status");
        btnRemoveApplication = new JButton("Remove Application");
    }

    /**
     * Creates a grid layout for the panel
     */
    void createLayout(){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        // add lblAppliedJobs
        gc.gridx = 0; gc.gridy = 0;
        gc.weighty = 0.1; gc.weightx = 0.7;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.BOTH;
        add(lblAppliedJobs, gc);
        // add appliedJobsScroller
        gc.gridy++;
        gc.weighty = 0.6;
        add(appliedJobsScroller, gc);
        // add btnShowStatus
        gc.gridy++;
        gc.weighty = 0.3;
        add(btnShowStatus, gc);
        // add lblStatus
        gc.gridx++; gc.gridy = 0;
        gc.weighty = 0.1; gc.weightx = 0.3;
        add(lblStatus, gc);
        // add appliedJobStatus
        gc.gridy++;
        gc.weighty = 0.5;
        add(appliedJobStatus, gc);
        // add btnRemoveApplication
        gc.gridy++;
        gc.weighty = 0.4;
        add(btnRemoveApplication, gc);
    }

    /**
     * Checks if user clicked a specific button and calls required methods
     *
     * @param e The action performed by the user
     */
    public void actionPerformed(ActionEvent e){
        JButton clicked = (JButton)e.getSource();
        if(clicked == btnRemoveApplication){
            if(currentApplication != null){
                ((Applicant)getCurrentUser()).withdraw(currentApplication);
                dlmApplications.removeElement(currentApplication);
            }
        }
        else if(clicked == btnShowStatus){
            if(currentApplication != null){

                appliedJobStatus.setText(currentApplication.getLetter());
            }
        }
        getStorage().writeApplicants();
        getStorage().writeCompanies();
    }

    /**
     * Monitors list to see if a posting is selected, sets currentApplication to selected posting
     *
     * @param e The action performed by the user on the list
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if(lsm == appliedJobs.getSelectionModel()) {
            currentApplication = appliedJobs.getSelectedValue();
            UserApplication system = getSystemApplication();
            if (system !=null)
                currentApplication = system;
        }
    }

    /**
     * Gets the application made by the user saved in the disk
     *
     * @return Returns a UserApplication if found, or null if not
     */
    private UserApplication getSystemApplication(){
        for (Posting p : SuperPanel.getStorage().getCompanies().get(0).getAllPostings()){
            for (UserApplication application: p.getAllApplications()){
                if (application.getapplicant().getUserName().equals(currentApplication.getapplicant().getUserName()))
                    return application;
            }
        }
        return null;
    }

    /**
     * Updates application lists
     */
    void updateStatusPanel() {
        dlmApplications.clear();
        for (UserApplication application : ((Applicant) SuperPanel.getCurrentUser()).getApplicationsSubmissionLive()) {
            if (application.getClosingDate() == null) {
                dlmApplications.addElement(application);
            }
        }
        for (UserApplication application : ((Applicant) SuperPanel.getCurrentUser()).getApplicationsInterviewLive()) {
            if (application.getClosingDate() == null) {
                dlmApplications.addElement(application);
            }
        }
    }
}
