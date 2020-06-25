package ProgramFiles.GuiFiles;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ProgramFiles.Exceptions.ApplicationExistsException;
import ProgramFiles.Exceptions.MissingRequirementException;
import ProgramFiles.Main;
import ProgramFiles.Posting;
import ProgramFiles.Applicant;

public class ApplicantHomePostingsPanel extends CustomizedApplicantHomeFunctionalityPanel implements ActionListener, ListSelectionListener {


    private JLabel lblJobs;
    private JLabel lblJobDescription;
    private JList<Posting> jobs;
    private JScrollPane jobsScroller;
    private JScrollPane jobDescriptionScroller;
    private JTextField jobDescription;
    private JButton btnApply;
    private JButton btnShowDescription;
    private JButton btnSearchByTags;
    private Posting currentPosting;
    private DefaultListModel<Posting> dlmPostings;

    /**
     * Constructor that creates layout and buttons aswell as adds listeners to them
     */
    ApplicantHomePostingsPanel(){
        super();
        init();
        createLayout();
        btnApply.addActionListener(this);
        btnShowDescription.addActionListener(this);
        btnSearchByTags.addActionListener(this);
        jobs.getSelectionModel().addListSelectionListener(this);
    }

    /**
     * Initializes the grid layout and adds required lists and buttons
     */
    void init(){
        setLayout(new GridBagLayout());
        // create the list of current jobs and add a scroll bar
        dlmPostings = new DefaultListModel<>();
        jobs = new JList<>(dlmPostings);
        jobs.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jobsScroller = new JScrollPane(jobs);
        // add border to jobDescription
        jobDescription = new JTextField(); jobDescription.setEditable(false);
        jobDescriptionScroller = new JScrollPane(jobDescription);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); jobDescription.setBorder(border);
        //
        lblJobs = new JLabel("Jobs"); makeLabelBold(lblJobs);
        lblJobDescription = new JLabel("Description"); makeLabelBold(lblJobDescription);
        btnApply = new JButton("Apply");
        btnSearchByTags = new JButton("Search By Tags");
        btnShowDescription = new JButton("Show Description");
    }

    /**
     * Creates a grid layout for the panel
     */
    void createLayout(){
        GridBagConstraints gc = new GridBagConstraints();
        // adding lblJobs
        gc.gridx = 0; gc.gridy = 0;
        gc.weighty = 0.05; gc.weightx = 0.3;
        gc.insets = new Insets(0, 0, 5, 0);
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.LINE_START;
        add(lblJobs, gc);
        // adding jobsScroller
        gc.gridy++;
        gc.weighty = 0.85;
        add(jobsScroller, gc);
        // adding btnShowDescription
        gc.gridy++;
        gc.weighty = 0.1;
        gc.insets.bottom = 20;
        add(btnShowDescription, gc);
        // adding btnSearchByTags
        gc.gridy++;
        gc.weighty = 0.1;
        gc.insets.bottom = 50;
        add(btnSearchByTags, gc);
        // adding lblJobDescription
        gc.gridy = 0; gc.gridx++;
        gc.weightx = 0.7; gc.weighty = 0.05;
        gc.insets.left = 10; gc.insets.bottom = 5;
        add(lblJobDescription, gc);
        // adding jobDescription
        gc.gridy++;
        gc.weighty = 0.85;
        add(jobDescriptionScroller, gc);
        // adding btnApply
        gc.gridy++;
        gc.weighty = 0.1;
        gc.insets.bottom = 20;
        add(btnApply, gc);
    }

    /**
     * Updates posting entries in list
     */
    void updatePostingsPanel() {

        dlmPostings.clear();
        for(Posting p: SuperPanel.getStorage().getCompanies().get(0).getAllPostings())
            dlmPostings.addElement(p);

    }

    /**
     * Updates posting entries in list based on specified tags
     *
     * @param tags The tags to filter the postings with and update the list
     */
    void updatePostingsPanelByTags(String[] tags) {
        dlmPostings.removeAllElements();
        for(Posting p: SuperPanel.getStorage().getCompanies().get(0).getAllPostings())
            if (p.hasAllTags(tags)) {
                dlmPostings.addElement(p);
            }
    }

    /**
     * Checks if user clicked a specific button and calls required methods
     *
     * @param e The action performed by the user
     */
    @Override
    public void actionPerformed(ActionEvent e){
        JButton clicked = (JButton)e.getSource();
        if (clicked == btnSearchByTags) {
            new ApplicantTagSearchDialog(this);
        } else {
            if (currentPosting == null)
                return;
            if (clicked == btnShowDescription)
                showDescription(currentPosting);
            else if (clicked == btnApply) {
                apply(currentPosting);
                JOptionPane.showConfirmDialog(Main.mainFrame, "You have applied for this job.", "Application successful", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        getStorage().writeApplicants();
        getStorage().writeCompanies();
    }

    /**
     * Monitors list to see if a posting is selected, sets currentPosting to selected posting
     *
     * @param e The action performed by the user on the list
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if(lsm == jobs.getSelectionModel()) {
            currentPosting = jobs.getSelectedValue();
            System.out.println(currentPosting);
        }
    }

    /**
     * Updates text field to show posting description
     */
    private void showDescription(Posting currentPosting){
        jobDescription.setText(currentPosting.getJobDescription());
    }

    /**
     * Applies the applicant for the specified posting if they meet requirements
     *
     * @param posting The posting the applicant is applying to
     */
    private void apply(Posting posting) {
        try {
            ((Applicant) SuperPanel.getCurrentUser()).apply(posting);
        } catch (ApplicationExistsException e){
            JOptionPane.showMessageDialog(Main.mainFrame, e, "Duplicate application",
                    JOptionPane.ERROR_MESSAGE);
        } catch (MissingRequirementException e) {
            JOptionPane.showMessageDialog(Main.mainFrame, e, "Missing Requirement",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}