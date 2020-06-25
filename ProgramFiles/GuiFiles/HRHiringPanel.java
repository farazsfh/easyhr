package ProgramFiles.GuiFiles;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import ProgramFiles.*;

public class HRHiringPanel extends SuperPanel implements ActionListener, ListSelectionListener {
    /**
     * GUI for the hiring station for an HR user
     */

    private HR currentUser;
    private DefaultListModel<UserApplication> recommendedApplicationsDLM = new DefaultListModel<>();
    private DefaultListModel<Posting> postingsDLM = new DefaultListModel<>();
    private JList<UserApplication> recommendedApplications = new JList<>(recommendedApplicationsDLM);
    private JList<Posting> postings = new JList<>(postingsDLM);
    private JScrollPane recommended = new JScrollPane(recommendedApplications);
    private JScrollPane postingsPane = new JScrollPane(postings);
    private JButton hire = new JButton("Hire selected");
    private JButton addPosting = new JButton("Add posting");
    private JButton startNextRound = new JButton("Begin next round/Start interviews");
    private UserApplication currentApplication;
    private Posting currentPosting;

    /**
     * Only Constructor for HRHiringPanel.
     */
    HRHiringPanel(){

        init();
        createLayout();
        hire.addActionListener(this);
        addPosting.addActionListener(this);
        startNextRound.addActionListener(this);
        postings.getSelectionModel().addListSelectionListener(this);
        recommendedApplications.getSelectionModel().addListSelectionListener(this);
    }

    public void setCurrentUser(HR currentUser){
        this.currentUser = currentUser;
        for (Posting posting: currentUser.getAllPosting())
            postingsDLM.addElement(posting);
    }

    private void init(){
        setBackground(Color.white);
        setVisible(true);
        TitledBorder border = new TitledBorder("Hiring for a position");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);
        // add borders for recommended and postings pane
        recommended.setBorder(new TitledBorder("Recommended applications"));
        postingsPane.setBorder(new TitledBorder("All postings"));
        // set the selection mode of the JLists to single selection
        postings.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recommendedApplications.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void createLayout(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // add postingsPane
        c.gridx = 0; c.gridy = 0;
        c.weightx = 1; c.weighty = 1;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        add(postingsPane, c);
        // add recommended JList
        c.gridx = 3;
        c.gridwidth = 1;
        add(recommended, c);
        // add addPosting
        c.gridx = 0; c.gridy = 1;
        c.weighty = 0.3;
        add(addPosting, c);
        // add hire
        c.gridwidth = 2;
        c.gridx++;
        add(startNextRound, c);

        c.gridwidth = 1;
        c.gridx+=2;
        add(hire, c);
    }

    /**
     * This is the implemented method to make this class an ActionListener.
     * Give functionality to the buttons.
     * @param e
     * e: the ActionEvent when pressing a button.
     */
    public void actionPerformed(ActionEvent e){
        JButton click = (JButton) e.getSource();
        if (click == addPosting){
            HRPostingDialog hrPostingDialog = new HRPostingDialog(this);
        } else if (click == hire){
            if (currentPosting != null && currentApplication != null){
                hire(currentPosting, currentApplication);
                toggleHiring();
                JOptionPane.showConfirmDialog(Main.mainFrame, "This job is hired for.");
            }
        } else if (click == startNextRound){
            if (currentPosting != null){
                currentUser.StartNextRoundInterview(currentPosting.getID());
                toggleHiring();
                JOptionPane.showConfirmDialog(Main.mainFrame, "Entering round " + currentPosting.getCurrentRound());
            }
        }
        getStorage().writeApplicants();
        getStorage().writeCompanies();
    }

    private void toggleHiring(){
        if (currentPosting.getFilledStatus()){
            this.hire.setEnabled(false);
            this.startNextRound.setEnabled(false);
        } else if (currentPosting.getRemainApplications().isEmpty() && currentPosting.getCurrentRound() > 0) {
            this.hire.setEnabled(false);
            this.startNextRound.setEnabled(false);
        }else if (currentPosting.getCurrentRound() < 4){
            this.hire.setEnabled(false);
            this.startNextRound.setEnabled(true);
        } else {
            this.hire.setEnabled(true);
            this.startNextRound.setEnabled(false);
        }
    }

    /**
     * The method called whenever a selection in the lists changes values.
     * Makes this class an ListSelectionListener.
     * @param e
     * e: The ListSelectionEvent whenever a new value is clicked on.
     */
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if (lsm == recommendedApplications.getSelectionModel()){
            currentApplication = recommendedApplications.getSelectedValue();
        }
        if (lsm == postings.getSelectionModel()){
            currentPosting = postings.getSelectedValue();
            updateRecommended(currentPosting);
            toggleHiring();
        }
    }

    /**
     * helper method to add a posting to the company.
     * @param created
     * created: the posting to be added.
     */
    void addPosting(Posting created){
        try {
            getCompany().addPosting(created);
            postingsDLM.addElement(created);
            SuperPanel.getStorage().writeCompanies();
        } catch (NullPointerException e){
            System.out.println("Parsing error");
        }
    }

    private Company getCompany(){
        return getStorage().getCompanies().get(0);
    }

    private void updateRecommended(Posting posting) {
        recommendedApplicationsDLM.clear();
        if (posting.getFilledStatus()){
            recommendedApplicationsDLM.addElement(posting.getHiredApplication());
        } else {
            for (UserApplication application : posting.getRemainApplications()) {
                if (application.getCurrentround() >= 4)
                    recommendedApplicationsDLM.addElement(application);
            }
        }
    }

    private void hire(Posting posting, UserApplication application) {
        posting.hire(application);
    }

}
