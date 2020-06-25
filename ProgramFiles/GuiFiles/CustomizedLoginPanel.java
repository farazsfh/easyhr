package ProgramFiles.GuiFiles;

import javax.swing.*;
import java.awt.*;

public class CustomizedLoginPanel extends SuperPanel {

    JButton btnLogin;
    JButton btnSignUp;
    CustomizedBackButton btnBack;
    private JTextField fldUsername;
    private JPasswordField fldPassword;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel pnlColored;

    /**
     * Constructor that creates the layout and adds all buttons to the panel
     */
    public CustomizedLoginPanel(){
        init();
        createLayout();
    }

    /**
     * Initializes the colour and buttons of the panel
     */
    private void init(){
        // set the color of panel
        setBackground(new Color(0, 153, 255));
        //
        btnLogin = new JButton("Login");
        btnSignUp = new JButton("Sign Up");
        btnBack = new CustomizedBackButton();
        fldUsername = new JTextField(10);
        fldPassword = new JPasswordField(10);
        lblUsername = new JLabel("Username: ");
        lblPassword = new JLabel("Password: ");
        pnlColored = new JPanel();
        pnlColored.setPreferredSize(new Dimension(400, 240));

    }

    /**
     * Creates a spring layout for the panel
     */
    private void createLayout(){
        // set the layout
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        // put constraints
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pnlColored, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, pnlColored, 0, SpringLayout.VERTICAL_CENTER, this);

        layout.putConstraint(SpringLayout.WEST, btnBack, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, btnBack, 0, SpringLayout.SOUTH, this);
        // create the pnlColored layout
        createPnlColoredLayout();
        // add the components
        add(pnlColored);
        add(btnBack);
    }

    /**
     * Creates a separate grid, colored layout for this panel
     */
    private void createPnlColoredLayout(){
        // create constrains
        GridBagConstraints gc = new GridBagConstraints();
        pnlColored.setLayout(new GridBagLayout());
        // add lblUsername
        gc.gridy = 0; gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        pnlColored.add(lblUsername, gc);
        // add lblPassword
        gc.gridy++;
        pnlColored.add(lblPassword, gc);
        // add btnSignUp
        gc.gridy++;
        gc.anchor = GridBagConstraints.CENTER;
        pnlColored.add(btnSignUp, gc);
        // add fldUsername
        gc.gridx = 1; gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        pnlColored.add(fldUsername, gc);
        // add fldPassword
        gc.gridy++;
        pnlColored.add(fldPassword, gc);
        // add btnLogin
        gc.gridy++;
        gc.anchor = GridBagConstraints.CENTER;
        pnlColored.add(btnLogin, gc);

    }

    String getUsername(){ return fldUsername.getText(); }
    String getPassword(){ return String.valueOf(fldPassword.getPassword()); }

}
