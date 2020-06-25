package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class DatePicker extends JPanel {
    /**
     * A GUI class which helps users pick a date.
     */

    private SpinnerModel dayEditor = new SpinnerNumberModel(LocalDateTime.now().getDayOfMonth(), 1, 31, 1);
    private SpinnerModel monthEditor = new SpinnerNumberModel(LocalDateTime.now().getMonthValue(), 1, 12, 1);
    private SpinnerModel yearEditor = new SpinnerNumberModel(
            LocalDate.now().getYear(),
            LocalDate.now().getYear(),
            LocalDate.MAX.getYear(),
            1);
    private JSpinner daySpinner = new JSpinner(dayEditor);
    private JSpinner monthSpinner = new JSpinner(monthEditor);
    private JSpinner yearSpinner = new JSpinner(yearEditor);

    /**
     * Constructor that creates the layout of the DatePicker and adds fields for day/month/year
     */
    DatePicker(){
        super();


        setLayout(new GridLayout(1, 3));

        ((JSpinner.DefaultEditor) daySpinner.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) monthSpinner.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) yearSpinner.getEditor()).getTextField().setEditable(false);

        add(daySpinner);
        add(monthSpinner);
        add(yearSpinner);
    }

    /**
     * Returns a LocalDate object from the inputs by the user.
     *
     * @return LocalDate
     */
    LocalDate getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return simpleDateFormat.parse(daySpinner.getValue().toString() + "-"
                    + monthSpinner.getValue().toString() + "-"
                    + yearSpinner.getValue().toString()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e){
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
