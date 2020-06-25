package ProgramFiles.GuiFiles;

import ProgramFiles.IncrementIDGenerator;
import ProgramFiles.Posting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HRPostingDialog extends JDialog implements ActionListener {
    /**
     * A dialog class which is called as a pop up dialog to ask for input
     * to add a Posting.
     */

    private JTextField postingID = new JTextField();
    private JTextField postingTitle = new JTextField();
    private JTextArea postingDescription = new JTextArea();
    private JTextArea postingRequiredSkills = new JTextArea();
    private JTextArea postingTags = new JTextArea();
    private JCheckBox resumeRequired = new JCheckBox();
    private JCheckBox coverLetterRequired = new JCheckBox();

    private SpinnerModel numberEditor = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
    private JSpinner numHireSpinner = new JSpinner(numberEditor);

    private DatePicker datePicker = new DatePicker();

    private JButton cancel = new JButton("Cancel");
    private JButton addPosting = new JButton("Add");

    private HRHiringPanel hrHiringPanel;

    /**
     * Only Constructor for HRPostingDialog.
     */
    HRPostingDialog(HRHiringPanel hrHiringPanel){
        super();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.hrHiringPanel = hrHiringPanel;
        hrHiringPanel.getRootPane().setVisible(false);

        JPanel inputArea =  new JPanel();
        JPanel checkBoxes = new JPanel(new GridLayout(1, 2));
        JPanel buttons = new JPanel(new GridLayout(1, 2));

        buttons.add(cancel);
        buttons.add(addPosting);
        checkBoxes.add(resumeRequired);
        checkBoxes.add(coverLetterRequired);
        cancel.addActionListener(this);
        addPosting.addActionListener(this);

        postingTitle.setColumns(20);

        ((JSpinner.DefaultEditor) numHireSpinner.getEditor()).getTextField().setEditable(false);

        GridLayout gridLayout = new GridLayout(14, 1);
        gridLayout.setVgap(20);
        inputArea.setLayout(gridLayout);
        setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int height = screenSize.height * 7 / 10;
        int width = screenSize.width / 4;

        setSize(new Dimension(width, height));
        setResizable(true);

        postingDescription.setLineWrap(true);
        JScrollPane description = new JScrollPane(postingDescription);

        postingRequiredSkills.setLineWrap(true);
        JScrollPane requiredSkills = new JScrollPane(postingRequiredSkills);

        postingTags.setLineWrap(true);
        JScrollPane tags = new JScrollPane(postingTags);

        inputArea.add(new JLabel("Enter the Posting title*"));
        inputArea.add(postingTitle);
        inputArea.add(new JLabel("Enter the posting description*"));
        inputArea.add(description);
        inputArea.add(new JLabel("Enter the posting required skills*"));
        inputArea.add(requiredSkills);
        inputArea.add(new JLabel("Enter the posting tags (seperated by commas)"));
        inputArea.add(tags);
        inputArea.add(new JLabel("Resume required? Cover letter required?"));
        inputArea.add(checkBoxes);
        inputArea.add(new JLabel("Enter posting end date"));
        inputArea.add(datePicker);
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
        Posting created;
        JButton clicked = (JButton) e.getSource();
        if (clicked == cancel){
            this.dispose();
            hrHiringPanel.getRootPane().setVisible(true);
        } else if (clicked == addPosting){
            this.dispose();
            created = createPosting();
            this.hrHiringPanel.addPosting(created);
            hrHiringPanel.getRootPane().setVisible(true);
        }
    }

    /**
     * Returns a posting based off of the user input.
     * @return Posting
     */
    public Posting createPosting(){
        String description = postingDescription.getText() + "\n\nRequired Skills:\n" + postingRequiredSkills.getText() +
                "\n\nTags: " + postingTags.getText() + "\n\nDeadline: " + datePicker.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String[] tags = postingTags.getText().replaceAll("\\s+","").split(",");

        return new Posting(
                (new IncrementIDGenerator()).getNewID(),
                postingTitle.getText(),
                description,
                LocalDate.now(),
                datePicker.getDate(),
                LocalDate.now(),
                resumeRequired.isSelected(),
                coverLetterRequired.isSelected(),
                tags);
    }
}
