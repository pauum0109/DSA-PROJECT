/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Handles user actions through event listeners, and manages the game flow and network interactions
for the Go game application.
*/
package main;

import menu.Menu;
import ui.GoGameConfig;
import ui.WaitWindow;
import ui.GoConnection;
import ui.GoInform;
import ui.GoInterface;
import ui.BackgroundPanel;
import control.Object;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.FontManager;
import menu.MenuConstants;
import util.StringConfig;
import control.Constants;
import ui.MainGamePanel;
import util.ImageManager;

public class Client extends JFrame implements ActionListener {
    /** 
     *  Optional LookAndFeel's Names:
     *    static String metalClassName = "javax.swing.plaf.metal.MetalLookAndFeel";
     *    static String motifClassName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
     *    static final String windowsClassName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
     */

    //MenuBar
    private JMenuBar goMB;
    
    //the handler of the contentPane
    private BackgroundPanel backgroundPanel;
    private MainGamePanel mainGamePanel;
    private GoInterface goInterface = null; //Is what in contentPane;

    //the handlers of the initial JComponents and related variables
    private static boolean shouldAnimating = false;
    private static boolean isWindowIconified = false;

    //Network Connection, Informing Type Selection and Useful Words Configuration Dialogs
    private GoConnection goConnection = null;
    private WaitWindow goWaitWindow = null;
    private GoInform goInform = null;
    private GoGameConfig goConfig = null;

    //The filechooser created for later usage.
    private JFileChooser goFileChooser = null;
    private File goFile = null;

    Client() throws InterruptedException {
        super("Go Game");

        Image icon = ImageManager.load("go.png", this);
        if (icon != null) {
            setIconImage(icon);
        }
        
        Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dSize;
        int x, y;

        dSize = new Dimension(380, 200);
        setSize(dSize);
        x = (dScreen.width - dSize.width) / 2;
        y = (dScreen.height - dSize.height) / 2;
        setLocation(x, y);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //add Window Listener
        addWindowListener(new WindowAdapter() {
            public void windowIconified(WindowEvent e) {
                isWindowIconified = true;
                if (goInterface != null) {
                    goConnection.getNetClient().sendToServer(new Object(Constants.CONFIRM, Constants.ICONFIEDSTR));
                }
            }
            public void windowDeiconified(WindowEvent e) {
                isWindowIconified = false;
                if (goInterface != null) {
                    goConnection.getNetClient().sendToServer(new Object(Constants.CONFIRM, Constants.DEICONFIEDSTR));
                }
            }
            public void windowClosing(WindowEvent e) {
                goExit();
            }
        });

        JOptionPane.setRootFrame((Frame)this);

        Dimension labelSize = new Dimension(380, 20);
        JLabel progressLabel = new JLabel("Running, please wait...");
        progressLabel.setFont(FontManager.labelFont);
        progressLabel.setAlignmentX(CENTER_ALIGNMENT);
        progressLabel.setMaximumSize(labelSize);
        progressLabel.setPreferredSize(labelSize);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressLabel.setLabelFor(progressBar);
        progressBar.setAlignmentX(CENTER_ALIGNMENT);
        progressBar.setMinimum(0);
        progressBar.setMaximum(14); 
        progressBar.setValue(0);
        progressBar.getAccessibleContext().setAccessibleName("Project is being set up...");

        JPanel progressPanel = new JPanel() {
            public Insets getInsets() {
                return new Insets(40, 30, 20, 30);
            }
        };
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
        progressPanel.add(progressLabel);
        progressPanel.add(Box.createRigidArea(new Dimension(1,20)));
        progressPanel.add(progressBar);
        setContentPane(progressPanel);
        setVisible(true);

        goWaitWindow = new WaitWindow((Frame)this);
        goWaitWindow.pack();
        dSize = goWaitWindow.getSize();
        x = (dScreen.width - dSize.width) / 2;
        y = (dScreen.height - dSize.height) / 2;
        System.out.print(dScreen.width + " " + dScreen.height);
        goWaitWindow.setLocation(x, y); 
        Thread.sleep(100);

        progressBar.setValue(progressBar.getValue() + 1);

        /* 
         * new GoConnection, GoInform, GoHelpHowTo, GoHelpAboutGo, GoGameConfig
         */
        //new goConnection
        progressLabel.setText("Loading...");
        goConnection = new GoConnection((Frame)this, "Connect to a Server", true, goWaitWindow);
        dSize = new Dimension(440, 210);
        goConnection.setSize(dSize);
        goConnection.setResizable(false);
        x = (dScreen.width - dSize.width) / 2;
        y = (dScreen.height - dSize.height) / 2;
        goConnection.setLocation(x, y);
        progressBar.setValue(progressBar.getValue() + 2);

        //new goInform
        progressLabel.setText("Loading...");
        goInform = new GoInform((Frame)this, "How to notify me", true);
        dSize = new Dimension(460, 520);
        goInform.setSize(dSize);
        goInform.setResizable(false);
        x = (dScreen.width - dSize.width) / 2;
        y = (dScreen.height - dSize.height) / 2;
        goInform.setLocation(x, y);
        progressBar.setValue(progressBar.getValue() + 3);

        progressLabel.setText("Loading...");
        progressBar.setValue(progressBar.getValue() + 3);

        goConfig = new GoGameConfig((Frame)this, "Useful Words Configuration", true);
        dSize = new Dimension(360, 300);
        goConfig.setSize(dSize);
        x = (dScreen.width - dSize.width) / 2;
        y = (dScreen.height - dSize.height) / 2;
        goConfig.setLocation(x, y);

        Thread.sleep(100);
        progressBar.setValue(progressBar.getValue() + 1);

        
        backgroundPanel = new BackgroundPanel("bg.png",this);
        backgroundPanel.setLayout(new BorderLayout());
        
        mainGamePanel = new MainGamePanel();

        //Create the MenuBar, Menus and contentPane.
        progressLabel.setText("Create menu and background...");
        goMB = new JMenuBar();
        new Menu(goMB, this);
        setJMenuBar(goMB);
        mainGamePanel.setBackground(Color.white);
        Thread.sleep(200);
        progressBar.setValue(progressBar.getValue() + 1);

        getContentPane().removeAll();
        setContentPane(backgroundPanel);
        dSize = new Dimension(830, 630);
        x = (dScreen.width - dSize.width) / 2;
        y = (dScreen.height - dSize.height) / 2;
        setSize(dSize);
        setResizable(false); 
        setLocation(x, y);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        validate();
        repaint();
    }

    public void actionPerformed(ActionEvent evt) {
        
        //Game Menu Operations
        if (evt.getActionCommand().equals(MenuConstants.NEWGAMESTR)) {
            switchToMainGamePanel();
            if ((!goInterface.isChanged()) ||
                ((goInterface.isChanged()) && (goOptionConfirm("Confirmation of New a Game")))) {
                if (!Menu.getMIStatus(MenuConstants.NEWGAMESTR)) { //has been disabled already
                    showClashInformation();
                } else {
                    networkAsking(Constants.NEWGAMESTR);
                }
            }
        }
        else if (evt.getActionCommand().equals(MenuConstants.CONTINUESTR)) {
            switchToMainGamePanel();
            if ((!goInterface.isChanged()) ||
                ((goInterface.isChanged()) && (goOptionConfirm("Confirmation of Continue a Former Game")))) {
                if (!Menu.getMIStatus(MenuConstants.CONTINUESTR)) { //has been disabled already
                    showClashInformation();
                } else {
                    networkAsking(Constants.CONTINUESTR);
                }
            }
        }
        else if (evt.getActionCommand().equals(MenuConstants.SAVESTR)) {
            switchToMainGamePanel();
            if ((goFile != null) || ((goFile = goInterface.getFile()) != null)) goInterface.save(goFile);
            else goSaveAs();
        }
        else if (evt.getActionCommand().equals(MenuConstants.SAVEASSTR)) {
            switchToMainGamePanel();
            goSaveAs();      
        }
        else if (evt.getActionCommand().equals(MenuConstants.EXITSTR)) {
            switchToMainGamePanel();
            goExit();    
        }
        //Edit Menu Operations
        else if (evt.getActionCommand().equals(MenuConstants.BACKGOSTR)) {
            switchToMainGamePanel();
            networkAsking(Constants.BACKGOSTR);
        }
        else if (evt.getActionCommand().equals(MenuConstants.COUNTSTR)) {
            switchToMainGamePanel();
            int returnVal = JOptionPane.showConfirmDialog(this, StringConfig.askForCountStrings, "Count Confirmation", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (returnVal == JOptionPane.YES_OPTION) {
                if (!Menu.getMIStatus(MenuConstants.COUNTSTR)) { //has been disabled already
                    showClashInformation();
                } else {
                    networkAsking(Constants.COUNTSTR);
                }
            }
        }
        else if (evt.getActionCommand().equals(MenuConstants.REFRESHSTR)) {
            switchToMainGamePanel();
            mainGamePanel.repaint();
        }
        //Server Menu Operations
        else if (evt.getActionCommand().equals(MenuConstants.CONNECTSTR)) {
            goConnect();
            switchToMainGamePanel();
        }
        //Tools Menu Operations
        else if (evt.getActionCommand().equals(MenuConstants.INFORMSTR)) {
            goConnection.getNetClient().sendToServer(new Object(Constants.CONFIRM, Constants.INFORMTOCOMESTR));

            Menu.setMIStatus(MenuConstants.INFORMSTR, false);

            String str = "The system is now trying to notify your friend according to his or her preferred way ... ";
            goInterface.getSystemArea().append(str);
        }
        else if (evt.getActionCommand().equals(MenuConstants.INFORMTYPESTR)) {
            //Pop up a dialog to let the user choose his preferred informing method.
            goInform.setVisible(true);
        }
        else if (evt.getActionCommand().equals(MenuConstants.ENDINFORMSTR)) {
            Menu.setMIStatus(MenuConstants.ENDINFORMSTR, false);

            //Try to interrupt the calling.
            goInterface.endInform();

            //Inform the other player that you are here now.
            goConnection.getNetClient().sendToServer(new Object(Constants.CONFIRM, Constants.INFORMCOMINGSTR));

            String str = "The system has told your friend that you are here now.";
            goInterface.getSystemArea().append(str);
        }
        else if (evt.getActionCommand().equals(MenuConstants.EDITWORDSSTR)) {
            goConfig.setVisible(true);
        }
    }

    private void switchToMainGamePanel() {
        setContentPane(mainGamePanel);
        revalidate();
        repaint();
    }
    
    private void showClashInformation() {
        String[] messages = {"You have chosen a network operation which is similar as that ",
                             "of your partner and this operation has been canceled by the ",
                             "system. But no problem and just answer your partner please! "};
        JOptionPane.showMessageDialog(this, messages, "Similar Network Operation Request Clash", 
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    private void networkAsking(String askingWhat) {
        Menu.disableNetMenus();

        goConnection.getNetClient().sendToServer(new Object(Constants.ASKING, askingWhat));

        goInterface.getSystemArea().append(StringConfig.queryString);
    }

    private void goConnect() {
        goConnection.setVisible(true);

        if (goConnection.isConnectButtonSelected) {
            Thread runner = new Thread() {
                public void run() {
                    goWaitWindow.setVisible(true);

                    if (goConnection.isNetConnected()) {
                        if (goInterface == null) {
                            Menu.setMIStatus(MenuConstants.NEWGAMESTR, true);
                            Menu.setMIStatus(MenuConstants.CONTINUESTR, true);
                            Menu.setMIStatus(MenuConstants.INFORMSTR, true);
                            Menu.setMIStatus(MenuConstants.CONNECTSTR, false);
                            Menu.setMIStatus(MenuConstants.EDITWORDSSTR, false);

                            shouldAnimating = false;
                            setResizable(true);
                        
                            goInterface = new GoInterface(Client.this, mainGamePanel, goConnection.getNetClient());
                            goConnection.getAcceptThread().setAccepter(goInterface);
                            goInterface.getGoTalkArea().setEditable(true);
                            goFileChooser = goInterface.getFileChooser();
                            goInterface.setGoInform(goInform);
                            mainGamePanel.revalidate();
                            mainGamePanel.repaint();
                        }
                        else { //reconnect to the server
                            goInterface.restoreNetworkOperation();
                            goInterface.setNetClient(goConnection.getNetClient());
                            goConnection.getAcceptThread().setAccepter(goInterface);

                            //tell the partner
                            goConnection.getNetClient().sendToServer(new Object(Constants.CONFIRM, Constants.HENETRESTOREDSTR));

                            //tell the user
                            goInterface.isMySocketError = false;
                            String str = "You have connected to the server successfully and the network " + 
                                         "related operation has been restored.";
                            goInterface.getSystemArea().append(str); 
                        }
                    }
                }
            };    
            runner.start();    
        }
    }

    private boolean goOptionConfirm(String title) {
        int returnVal = JOptionPane.showConfirmDialog(this, StringConfig.askForSaveString, title, 
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (returnVal == JOptionPane.YES_OPTION) {
            if ((goFile != null) || ((goFile = goInterface.getFile()) != null)) goInterface.save(goFile);
            else goSaveAs();
        }
        else if (returnVal == JOptionPane.NO_OPTION) {
            //Just return true
        }
        else if (returnVal == JOptionPane.CANCEL_OPTION) {
            return false;
        }
        return true;
    }

    private void goSaveAs() {
        int returnVal = goFileChooser.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            goFile = goFileChooser.getSelectedFile();
            goInterface.save(goFile);
        }
    }

    private void goExit() {
        if (goInterface == null) System.exit(0);

        if ((!goInterface.isChanged()) ||
            ((goInterface.isChanged()) && (goOptionConfirm("Confirmation of Exit")))) {
            if ((goConnection != null) && (goConnection.isNetConnected())) {
                control.Client netClient = goConnection.getNetClient();
                if (!GoInterface.isMySocketError) {
                    if ((!GoInterface.isOtherPartExit) || (!GoInterface.isHeSocketError)) {
                        netClient.sendToServer(new Object(Constants.CONFIRM, Constants.EXITSTR));
                    }
                }
            }  
            System.exit(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Error loading L&F: " + exc);
		}

        Client client = new Client();
        client.shouldAnimating = true;
    }
}