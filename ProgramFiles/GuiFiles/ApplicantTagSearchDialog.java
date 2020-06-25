package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicantTagSearchDialog extends JDialog implements ActionListener {

    private JTextField inputTags = new JTextField();

    private JButton search = new JButton("Search");
    private JButton cancel = new JButton("Cancel");

    private ApplicantHomePostingsPanel applicantHomePostingsPanel;

    /**
     * Only Constructor for ApplicantTagSearchDialog.
     */
    ApplicantTagSearchDialog(ApplicantHomePostingsPanel applicantHomePostingsPanel){
        super();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.applicantHomePostingsPanel = applicantHomePostingsPanel;
        applicantHomePostingsPanel.getRootPane().setVisible(false);

        JPanel inputArea =  new JPanel();
        JPanel buttons = new JPanel(new GridLayout(1, 2));

        buttons.add(search);
        buttons.add(cancel);
        search.addActionListener(this);
        cancel.addActionListener(this);

        GridLayout gridLayout = new GridLayout(1, 1);
        gridLayout.setVgap(20);
        inputArea.setLayout(gridLayout);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = screenSize.height / 10;
        int width = screenSize.width / 2;

        setSize(new Dimension(width, height));
        setResizable(false);

        inputArea.add(new JLabel("Input tags to search for (seperated by commas)"));
        inputArea.add(inputTags);
        inputArea.add(buttons);
        add(inputArea);
    }

    /**
     * This is the implemented method to make this class an ActionListener.
     * Give functionality to the buttons.
     * @param e
     * e: the ActionEvent when pressing a button.
     */
    public void actionPerformed(ActionEvent e){
        String[] cleanedInputTags = inputTags.getText().replaceAll("\\s+","").split(",");
        JButton clicked = (JButton) e.getSource();
        if (clicked == cancel){
            this.dispose();
            applicantHomePostingsPanel.getRootPane().setVisible(true);
        } else if (clicked == search){
            this.dispose();
            this.applicantHomePostingsPanel.updatePostingsPanelByTags(cleanedInputTags);
            applicantHomePostingsPanel.getRootPane().setVisible(true);
        }
    }
}
