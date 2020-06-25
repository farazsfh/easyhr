package ProgramFiles.GuiFiles;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ProgramFiles.UserApplication;
import ProgramFiles.Interviewer;

public class InterviewerWorkPanel extends SuperPanel implements ListSelectionListener, ActionListener {
    /**
     * The GUI for the work panel of an Interviewer user in which the user recommends
     * or doesn't recommend an application.
     */

    private DefaultListModel<UserApplication> dlm = new DefaultListModel<>();
    private DefaultListModel<UserApplication> dlm2 = new DefaultListModel<>();
    private DefaultListModel<UserApplication> dlm3 = new DefaultListModel<>();

    private JList<UserApplication> applications = new JList<>(dlm);
    private JList<UserApplication> recommended = new JList<>(dlm2);
    private JList<UserApplication> notRecommended = new JList<>(dlm3);

    private UserApplication selection = null;
    private DefaultListModel<UserApplication> current = null;

    private JButton recommend = new JButton("Recommend");
    private JButton notRecommend = new JButton("Don't Recommend");
    private JButton neutralize = new JButton("Reverse recommendation");
    private JButton submit = new JButton("Submit");

    /**
     * Only Constructor for InterviewerWorkPanel.
     */
    InterviewerWorkPanel() {
        setBackground(Color.white);
        setVisible(true);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        TitledBorder border = new TitledBorder("Choose whether you recommend these applications or not:");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);

        recommend.addActionListener(this);
        neutralize.addActionListener(this);
        notRecommend.addActionListener(this);
        submit.addActionListener(this);

        applications.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recommended.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notRecommended.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        applications.getSelectionModel().addListSelectionListener(this);
        recommended.getSelectionModel().addListSelectionListener(this);
        notRecommended.getSelectionModel().addListSelectionListener(this);

        JPanel[] listPanels = {new JPanel(), new JPanel(), new JPanel()};

        for (JPanel j: listPanels){
            j.setLayout(new GridLayout(1, 1));}
        listPanels[0].setBorder(new TitledBorder("Yet to interview"));
        listPanels[1].setBorder(new TitledBorder("Recommended"));
        listPanels[2].setBorder(new TitledBorder("Not Recommended"));
        listPanels[0].add(new JScrollPane(applications));
        listPanels[1].add(new JScrollPane(recommended));
        listPanels[2].add(new JScrollPane(notRecommended));

        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 1;
        c.ipady = 1;

        c.gridx = 0;
        add(listPanels[0], c);

        c.gridx = 1;
        add(listPanels[1], c);

        c.gridx = 2;
        add(listPanels[2], c);

        c.gridy = 1;
        c.weightx = 0.4;
        c.weighty = 0.4;

        c.gridx = 0;
        add(neutralize, c);

        c.gridx = 1;
        add(recommend, c);

        c.gridx = 2;
        add(notRecommend, c);

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        add(submit, c);
    }

    /**
     * This is the implemented method to make this class an ActionListener.
     * Give functionality to the buttons.
     * @param e
     * e: the ActionEvent when pressing a button.
     */
    public void actionPerformed(ActionEvent e){
        JButton j = (JButton)e.getSource();

        if (selection != null) {
            UserApplication app = selection;
            current.removeElement(selection);
            if (j == recommend) {
                dlm2.addElement(app);
            } else if (j == neutralize) {
                dlm.addElement(app);
            } else if (j == notRecommend) {
                dlm3.addElement(app);
            }
        }
        else if (j == submit){
            submit();
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
        if (lsm == applications.getSelectionModel()){
            recommended.clearSelection();
            notRecommended.clearSelection();
            selection = applications.getSelectedValue();
            current = dlm;
        } if (lsm == recommended.getSelectionModel()){
            notRecommended.clearSelection();
            applications.clearSelection();
            selection = recommended.getSelectedValue();
            current = dlm2;
        } if (lsm == notRecommended.getSelectionModel()){
            recommended.clearSelection();
            applications.clearSelection();
            selection = notRecommended.getSelectedValue();
            current = dlm3;
        }
        System.out.println("valueChanged: " + current);
    }

    /**
     * Helper method to update lists in after pressing 'submit'.
     */
    void updateWorkPanel() {

        dlm.clear();

        for (UserApplication app : ((Interviewer) SuperPanel.getCurrentUser()).getCurrentApplications()) {
            dlm.addElement(app);
        }

    }

    private void submit() {
        System.out.println("submitting");
        for (int i = 0; i < dlm2.getSize(); i++) {
            ((Interviewer) SuperPanel.getCurrentUser()).updateApplicant((dlm2.get(i)), true);
        }
        for (int i = 0; i < dlm3.getSize(); i++) {
            ((Interviewer) SuperPanel.getCurrentUser()).updateApplicant((dlm3.get(i)), false);
        }
        getStorage().writeApplicants();
        getStorage().writeCompanies();
        dlm2.removeAllElements();
        dlm3.removeAllElements();
    }
}
