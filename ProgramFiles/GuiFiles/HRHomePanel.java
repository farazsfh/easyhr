package ProgramFiles.GuiFiles;

import ProgramFiles.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HRHomePanel extends SuperPanel implements ActionListener {
    /**
     * This is the container class for the GUI of an HR user.
     *
     * Attributes:
     *
     * cd: the CardLayout for the workArea panel.
     * workArea: the panel on which different panels are projected for functionality.
     * hrWorkStation: the button taking us to the main work station for HR user
     * hiringStation: the button taking us to the hiring station for HR user.
     * back: a back button logging us out of the HR user account and taking us back to intro.
     */

    private CardLayout cd = new CardLayout();

    private JPanel workArea = new JPanel(cd);
    private HRWorkPanel hrWorkPanel;
    private HRHiringPanel hrHiringPanel;

    private final String[] PANELS = {"WORK STATION", "HIRING STATION"};

    private JButton hrWorkStation = new JButton("Work Station");
    private JButton hiringStation = new JButton("Hiring Station");
    private BlackCustomizedBackButton back = new BlackCustomizedBackButton();

    /**
     * Only Constructor for HRHomePanel
     */
    HRHomePanel(){
        JToolBar options = new JToolBar();
        options.setLayout(new GridLayout(3, 1));
        options.setOrientation(JToolBar.VERTICAL);
        options.add(hrWorkStation);
        options.add(hiringStation);
        options.add(back);
        options.setFloatable(false);

        hrWorkPanel = new HRWorkPanel();
        hrHiringPanel = new HRHiringPanel();

        workArea.add(hrWorkPanel, PANELS[0]);
        workArea.add(hrHiringPanel, PANELS[1]);
        cd.show(workArea, PANELS[0]);

        hrWorkStation.addActionListener(this);
        hiringStation.addActionListener(this);
        back.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridy = 0;
        gc.weighty = 1;
        gc.weightx = 0.5;

        gc.gridx = 0;
        gc.gridwidth = 1;
        add(options, gc);

        gc.gridx = 1;
        gc.gridwidth = 9;
        gc.weightx = 1;
        add(workArea, gc);
    }

    public HRHiringPanel getHrHiringPanel(){return this.hrHiringPanel;}

    /**
     * This is the implemented method to make this class an ActionListener.
     * Give functionality to the buttons.
     * @param e
     * e: the ActionEvent when pressing a button.
     */
    public void actionPerformed(ActionEvent e){
        JButton click = (JButton) e.getSource();
        if (hrWorkStation == click){
            cd.show(workArea, PANELS[0]);
            hrWorkPanel.updateWorkPanel(getStorage().getCompanies().get(0));
        } else if (hiringStation == click) {
            cd.show(workArea, PANELS[1]);
        } else if (back == click){
            getStorage().writeCompanies();
            getStorage().writeApplicants();
            Main.mainFrame.dispose();
            Main.mainFrame = new MainFrame();
        }
    }
}
