package ProgramFiles.GuiFiles;

import ProgramFiles.Main;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterviewerHomePanel extends SuperPanel implements ActionListener {
    /**
     * This is the container class for the GUI of an Interviewer user.
     *
     * Attributes:
     *
     * cd: the CardLayout for the workArea panel.
     * workStation: the panel on which different panels are projected for functionality.
     * toWorkPanel: the button taking us to the main work station for Interviewer user
     * back: a back button logging us out of the interviewer user account and taking us back to intro.
     */

    private CardLayout cd = new CardLayout();

    private JPanel workStation = new JPanel(cd);
    private InterviewerWorkPanel interviewerWorkPanel = new InterviewerWorkPanel();

    private JButton toWorkPanel = new JButton("Work Station");
    private BlackCustomizedBackButton back = new BlackCustomizedBackButton();

    private final String WORK_PANEL = "interviewerWorkPanel";

    /**
     * Only Constructor for InterviewerHomePanel
     */
    InterviewerHomePanel(){

        setBackground(Color.white);
        setVisible(true);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        TitledBorder border = new TitledBorder("Interviewer Home Page");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        setBorder(border);

        toWorkPanel.addActionListener(this);
        back.addActionListener(this);

        JToolBar homeToolBar = new JToolBar();

        homeToolBar.setOrientation(JToolBar.VERTICAL);
        homeToolBar.setFloatable(false);
        homeToolBar.setLayout(new GridLayout(2, 1));
        homeToolBar.add(toWorkPanel, 0);
        homeToolBar.add(back, 1);

        workStation.add(interviewerWorkPanel, WORK_PANEL);

        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;

        c.weightx = 0.2;
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridwidth = 1;
        add(homeToolBar, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridwidth = 10;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(workStation, c);
    }

    /**
     * This is the implemented method to make this class an ActionListener.
     * Give functionality to the buttons.
     * @param e
     * e: the ActionEvent when pressing a button.
     */
    public void actionPerformed(ActionEvent e){
        JButton clicked = (JButton)e.getSource();
        if (clicked == toWorkPanel){
            interviewerWorkPanel.updateWorkPanel();
            cd.show(workStation, WORK_PANEL);
        } else if (clicked == back){
            getStorage().writeCompanies();
            getStorage().writeApplicants();
            Main.mainFrame.dispose();
            Main.mainFrame = new MainFrame();
        }
    }
}
