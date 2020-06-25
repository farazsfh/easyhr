package ProgramFiles.GuiFiles;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ProgramFiles.*;

public class HRWorkPanel extends SuperPanel implements ActionListener, ListSelectionListener {
    /**
     * The work panel for an HR user on which an HR user assigns Interviewers applications
     * to interview.
     */
    private DefaultListModel<Interviewer> interviewsDLM = new DefaultListModel<>();
    private DefaultListModel<UserApplication> applicationsDLM = new DefaultListModel<>();

    private JList<Interviewer> interviewers = new JList<>(interviewsDLM);
    private JList<UserApplication> applications = new JList<>(applicationsDLM);

    private JButton addPostingsToInterviewer = new JButton("Add applications to Interviewer");

    private Interviewer currentInterviewer;
    private UserApplication currentApplication;

    /**
     * Only Constructor for HRWorkPanel.
     */
    HRWorkPanel(){
        addPostingsToInterviewer.addActionListener(this);

        JScrollPane interviewerScroll = new JScrollPane(interviewers);
        JScrollPane applicationScroll = new JScrollPane(applications);

        interviewerScroll.setBorder(new TitledBorder("Interviewers"));
        applicationScroll.setBorder(new TitledBorder("Applications"));

        interviewers.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        applications.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        interviewers.getSelectionModel().addListSelectionListener(this);
        applications.getSelectionModel().addListSelectionListener(this);

        updateWorkPanel(getStorage().getCompanies().get(0));

        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Add applications for Interviewers"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.gridy = 0;

        gc.gridx = 0;
        add(interviewerScroll, gc);

        gc.gridx = 1;
        add(applicationScroll, gc);

        gc.gridy = 1;

        gc.gridx = 0;
        gc.weighty = 0.3;
        add(addPostingsToInterviewer, gc);

    }

    /**
     * This is the implemented method to make this class an ActionListener.
     * Give functionality to the buttons.
     * @param e
     * e: the ActionEvent when pressing a button.
     */
    public void actionPerformed(ActionEvent e){
        JButton click = (JButton) e.getSource();
        if (click == addPostingsToInterviewer){
            if (currentInterviewer != null && currentApplication != null){
                submitForInterview(currentInterviewer, currentApplication);
                applicationsDLM.removeElement(currentApplication);
            }
        }
        getStorage().writeApplicants();
        getStorage().writeCompanies();
    }

    /**
     * The method called whenever a selection in the lists changes values.
     * Makes this class an ListSelectionListener.
     * @param e
     * e: The ListSelectionEvent whenever a new value is clicked on.
     */
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if (lsm == applications.getSelectionModel()){
            currentApplication = applications.getSelectedValue();
        } if (lsm == interviewers.getSelectionModel()) {
            currentInterviewer = interviewers.getSelectedValue();
        }
    }

    /**
     * Helper method to update lists in after pressing 'Add postings to Interviewer'.
     * @param company
     * company: the Company object to which the changes are being made.
     */
    public void updateWorkPanel(Company company) {
        interviewsDLM.clear();
        applicationsDLM.clear();
        for (Interviewer interviewer : company.getAllInterviewers()) {
            interviewsDLM.addElement(interviewer);
        }
        for (Posting posting : company.getAllPostings()) {
            System.out.println(posting.getRemainApplications());
            if (!posting.getPostStatus()) {
                for (UserApplication application : posting.getRemainApplications()) {
                    if (!application.getReviewstatus() && !application.getHirestatus())
                        applicationsDLM.addElement(application);
                }
            }
        }
    }

    /**
     * Helper method to add an application for an interviewer to review.
     * @param interviewer
     * interviewer: the Inteviewer to which the application is to be added.
     * @param application
     * application: the application to be added.
     */
    private void submitForInterview(Interviewer interviewer, UserApplication application) {
        interviewer.AddApplication(application);
        application.setReviewstatus(true);
        SuperPanel.getStorage().writeCompanies();
    }

}
