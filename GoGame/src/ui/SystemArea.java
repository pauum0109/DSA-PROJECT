/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Handles the system announcement between 2 players
*/
package ui;

import menu.Menu;
import control.Object;
import control.Client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.FontManager;
import menu.MenuConstants;
import control.Constants;

public class SystemArea extends JPanel implements ActionListener {
    private String agreeStr    = "Agree";
    private String disagreeStr = "Disagree";

    private JLabel upLabel;    
    private JTextArea textArea;
    private JScrollPane textPane;
    private JButton agreeButton, disagreeButton;

    private Client goNetClient;
    private GoBoard goBoard;
    private GoConfig goConfig;
    private String answerWhat = null;

    public SystemArea(String labelStr, int xNum, int yNum, 
        Client goNetClient, GoBoard goBoard, GoConfig goConfig) {
        this.goNetClient = goNetClient;
        this.goBoard     = goBoard;
        this.goConfig    = goConfig; 
        
        upLabel = new JLabel(labelStr);
        upLabel.setFont(FontManager.labelFont);

        textArea = new JTextArea(xNum, yNum);
        textArea.setMargin(new Insets(5,5,5,5));
        textArea.setEditable(false);
        textArea.setFont(FontManager.systemAreaFont);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.gray);
        textArea.setSelectionColor(Color.gray);  //To remove some undesirable effect.
        textArea.setSelectedTextColor(Color.black);  //To remove some undesirable effect.
        textPane = new JScrollPane(textArea);

        agreeButton  = new JButton(agreeStr);
        agreeButton.setFont(FontManager.buttonFont);
        agreeButton.setActionCommand(agreeStr);
        agreeButton.setEnabled(false);
       
        disagreeButton = new JButton(disagreeStr);
        disagreeButton.setFont(FontManager.buttonFont);
        disagreeButton.setActionCommand(disagreeStr);
        disagreeButton.setEnabled(false);

        agreeButton.addActionListener(this);
        disagreeButton.addActionListener(this);

        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agreeButton);  buttonPanel.add(new JPanel());  buttonPanel.add(disagreeButton);
        add(upLabel, BorderLayout.NORTH);
        add(textPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(agreeStr)) {
            Object sentObject = new Object(Constants.ANSWER, Constants.AGREE, answerWhat);
            goNetClient.sendToServer(sentObject);

            setButtonActive(false);
            if (sentObject.answerWhat.equals(Constants.COUNTSTR)) {
                String str = "Please wait your partner to mark the dead buttons.";
                append(str);
            } else {
                agree(sentObject);

                if (sentObject.answerWhat.equals(Constants.CONTINUESTR)) {
                    Menu.setMIStatus(MenuConstants.NEWGAMESTR, false);
                    Menu.setMIStatus(MenuConstants.CONTINUESTR, false);
                    Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
                    Menu.setMIStatus(MenuConstants.COUNTSTR, false);

                    String str = "Please wait your partner to open the file.";
                    append(str);            
                }
            }
        }
        else if (e.getActionCommand().equals(disagreeStr)) {
            Menu.restoreNetMenus();

            Object sentObject = new Object(Constants.ANSWER, Constants.DISAGREE, answerWhat);
            goNetClient.sendToServer(sentObject);

            setButtonActive(false);
        }
    }

    public void agree(Object netObject) {  //being used for two parts
        Menu.restoreNetMenus();  // Pay attention to this line's position in this method.

        if (netObject.answerWhat.equals(Constants.NEWGAMESTR)) {
            Menu.setMIStatus(MenuConstants.NEWGAMESTR, false);
            Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
            goConfig.setButtonActive(true);
            goBoard.reset();
        }
        else if (netObject.answerWhat.equals(Constants.CONTINUESTR)) {
            Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
            goBoard.reset();
        }
        else if (netObject.answerWhat.equals(Constants.BACKGOSTR)) {
            int i = goBoard.oneStepBack();
            if (i == 0) {  // No buttons in the board 
                Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
                Menu.setMIStatus(MenuConstants.SAVEASSTR, false);
            }
        }
        else if (netObject.answerWhat.equals(Constants.COUNTSTR)) {
            /* Do nothing special here */
        }
    }

    public void disagree(Object netObject) {  //only being used by the other part
        Menu.restoreNetMenus();
       
        //GoNetConstants.NEWGAMESTR, CONTINUESTR, BACKGOSTR, COUNTSTR
        String str = "Sorry, your partner don't agree to " + netObject.answerWhat + ".";
        append(str);
    }

    public void setAnswerWhat(String answerWhat) {
        this.answerWhat = answerWhat; 
    }

    public void setNetClient(Client goNetClient) {
        this.goNetClient = goNetClient;
    }

    public void setButtonActive(boolean isActive) {
        agreeButton.setEnabled(isActive);
        disagreeButton.setEnabled(isActive);
    }

    public void append(String str) {
        textArea.setEditable(true);
        textArea.selectAll();
        textArea.replaceSelection(str);
        textArea.setEditable(false);
    }
}