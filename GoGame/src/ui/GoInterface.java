/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Manage the select in game (Validate score,...)
*/
package ui;

import menu.Menu;
import util.Playback;
import util.Button;
import control.Object;
import control.Client;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import util.FontManager;
import menu.MenuConstants;
import control.AcceptListener;
import control.Constants;

public class GoInterface implements AcceptListener {
    //Main Components in this Interface
    private GoBoard      goBoard;
    private GoConfig     goConfig;
    private TalkArea   goTalkArea;
    private SystemArea goSystemArea;

    //Set what interface and function should be provided.
    private JPanel contentPane;   

    //Network pointer
    private Client goNetClient;

    //JFileChooser and it's Dialog owner;
    private JFileChooser goFileChooser;
    private JFrame       dialogOwner;
    private File         goFile = null;

    //The handler of the GoInform whose value will be got from the GoNet.java
    private GoInform     goInform = null;
    private boolean      shouldBeep = false;

    public static boolean isOtherPartExit = false;
    public static boolean isMySocketError = false;
    public static boolean isHeSocketError = false;

    //The MouseListener and the MouseMotionListener while playing and count the points alive.
    private MouseAdapter       playMouseAdapter, countMouseAdapter;
    private MouseMotionAdapter playMouseMotionAdapter, countMouseMotionAdapter;
    private MarkDialog         markDialog = null;
    private boolean            markTurn = false;
    private EndMarkDialog      endMarkDialog = null;

    private boolean hasRestored;  //define whether should restore the network operation

    private ByteArrayOutputStream speakingBAOS = new ByteArrayOutputStream();

    public GoInterface(JFrame owner, JPanel father, Client gNetClient) {
        dialogOwner = owner;
        contentPane = father;
        goNetClient = gNetClient;

        //Create the fileChooser for later usage.
        String goPlayDirName = System.getProperty("user.dir") + File.separator + "data" + 
				File.separator + "records";
        File goPlayDir = new File(goPlayDirName);

        boolean success = true;
        if ((!goPlayDir.exists()) || (!goPlayDir.isDirectory())) {
            if (!goPlayDir.mkdir()) success = false;
        }
        if (!success) goFileChooser = new JFileChooser(System.getProperty("user.dir"));
        else goFileChooser = new JFileChooser(goPlayDirName);

        goFileChooser.addChoosableFileFilter(new PlayFilter());

        contentPane.removeAll();

        //the common part for 4 types
        Border compound1 = BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                                                              BorderFactory.createLoweredBevelBorder());
        Border compound2 = BorderFactory.createCompoundBorder(compound1, 
            BorderFactory.createEmptyBorder(8, 10, 8, 10));

        goBoard = new GoBoard();
        goBoard.setBorder(compound1);
       
        goConfig = new GoConfig(goBoard, goNetClient);
        goConfig.setBorder(compound2);
     
        goTalkArea = new TalkArea(" Talking Area:", goNetClient, dialogOwner);
        goTalkArea.setBorder(compound1);

        goSystemArea = new SystemArea(" System Information Area:", 30, 5, goNetClient, goBoard, goConfig);
        goSystemArea.setBorder(compound1);
        goSystemArea.setPreferredSize(goConfig.getPreferredSize());

        playMouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                boolean remind = true;
                if (goBoard.isMyTurn()) {
                    Point pressPoint = goBoard.inValidArea(new Point(e.getX(), e.getY()));
                    if (pressPoint != null) {
                        if (goBoard.oneStepPlay(pressPoint, goConfig.getPlayPart())) {
                            Object sentObject = new Object(Constants.PLAYING, pressPoint);
                            goNetClient.sendToServer(sentObject);

                            remind = false;
                        }
                    }
                }
                if (remind) Toolkit.getDefaultToolkit().beep();
            }
        };
        goBoard.addMouseListener(playMouseAdapter);

        playMouseMotionAdapter = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (goBoard.isMyTurn()) {
                    Point pressPoint = goBoard.inValidArea(new Point(e.getX(), e.getY()));
                    if (pressPoint != null) {
                        goBoard.indicateCurrentPosition(pressPoint, goConfig.getPlayPart());
                    } else {
                        goBoard.indicateCurrentPosition();
                    }
                }
            }
        };
        goBoard.addMouseMotionListener(playMouseMotionAdapter);

        //Create these two listeners for later usage.
        countMouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (markTurn) {
                    Point pressPoint = goBoard.inValidArea(new Point(e.getX(), e.getY()));
                    if (pressPoint != null) {
                        //if control is pressed, then unmark the dead buttons, that is isDead = false;
                        //else mark the dead buttons, that is isDead = true.
                        boolean isCtrlDown = e.isControlDown();
                        if (goBoard.markDeadButtons(pressPoint, !isCtrlDown)) {
                            Object sentObject = new Object(Constants.MARKING, 
                                                                     pressPoint, !isCtrlDown);
                            goNetClient.sendToServer(sentObject);
                        }
                    }
                }
            }
        };

        countMouseMotionAdapter = new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (markTurn) {
                    Point pressPoint = goBoard.inValidArea(new Point(e.getX(), e.getY()));
                    if (pressPoint != null) {
                        goBoard.indicateCurrentPositionForCount(pressPoint, !e.isControlDown());
                    } else {
                        goBoard.indicateCurrentPosition();
                    }
                }
            }
        };

        //Lay out the components to the content pane.
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(goConfig, BorderLayout.NORTH);
        rightPanel.add(goTalkArea, BorderLayout.CENTER);
        rightPanel.add(goSystemArea, BorderLayout.SOUTH);
 
        contentPane.add(goBoard, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);
    }        

    public void accept(Object acceptObject) {  // The method required by AcceptListener
        if (acceptObject.status == Constants.PING) {
            //do nothing here
        } else if (acceptObject.status == Constants.CONFIG) {
            config(acceptObject);    
        } else if (acceptObject.status == Constants.RECONFIG) {
            reconfig(acceptObject);    
        } else if (acceptObject.status == Constants.PLAYING) {
            playing(acceptObject);
        } else if (acceptObject.status == Constants.TALKING) {
            talking(acceptObject);    
        } else if (acceptObject.status == Constants.ASKING) {
            asking(acceptObject);   
        } else if (acceptObject.status == Constants.CONFIRM) {
            confirm(acceptObject);           
        } else if (acceptObject.status == Constants.ANSWER) {
            answer(acceptObject);
        } else if (acceptObject.status == Constants.MARKING) {
            marking(acceptObject);
        } else if (acceptObject.status == Constants.SPEAKING) {
            speaking(acceptObject);
        }
    }

    private void config(Object acceptObject) {
        Menu.setMIStatus(MenuConstants.NEWGAMESTR, true);

        int playPart = 1 - acceptObject.playPart;
        int handiNum = acceptObject.handiNO;
        boolean isSelected = acceptObject.isSelected;

        runConfig(playPart, handiNum, isSelected);

        String[] strs = new String[3];
        strs[0] = "The play style you have negotiated has been submitted by your partner and it is as follows:\n";
        if (playPart == 0) strs[1] = "  You will play as the black part. ";
        else strs[1] = "  You will play as the white part. ";
        if (handiNum == 0) strs[2] = "No handicap.";
        else if (handiNum == 1) strs[2] = "Handicap is: One player let the other part play fisrt.";
        else strs[2] = "Handicap is: " + handiNum + " Buttons.";

        String str = strs[0] + strs[1] + strs[2];
        goSystemArea.append(str);
    }

    private void reconfig(Object acceptObject) {
        int playPart = 1 - acceptObject.playPart;
        int handiNum = acceptObject.handiNO;
        boolean isSelected = acceptObject.isSelected;

        runConfig(playPart, handiNum, isSelected);
    }

    private void playing(Object acceptObject) {
        Point playPoint = acceptObject.playPoint;
        if (playPoint != null) {
            if (!goBoard.isMyTurn()) {
                goBoard.oneStepPlay(playPoint, 1 - goConfig.getPlayPart());
            } else {
                goBoard.oneStepPlay(playPoint, goConfig.getPlayPart());
            }
        }
    }

    private void talking(Object acceptObject) {
        goTalkArea.append(acceptObject.talkString);
    }

    private void asking(Object acceptObject) {
        if (acceptObject.talkString.equals(Constants.ENDMARKSTR)) {
            //Remove the old listeners and add the new listeners.
            goBoard.removeMouseListener(playMouseAdapter);
            goBoard.removeMouseMotionListener(playMouseMotionAdapter);
            goBoard.setMyTurn(false);

            markTurn = true;        
            goBoard.addMouseListener(countMouseAdapter);
            goBoard.addMouseMotionListener(countMouseMotionAdapter);

            //Popup a dialog to let the user the mark the dead buttons
            if (endMarkDialog == null) {
                endMarkDialog = new EndMarkDialog((Frame)dialogOwner, "Dead Button Remarking", false);
                Dimension dSize = new Dimension(340, 200);
                endMarkDialog.setSize(dSize);
                Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
                int x = dScreen.width - dSize.width;
                int y = (dScreen.height - dSize.height) / 2;
                endMarkDialog.setLocation(x, y);
            }
            endMarkDialog.setVisible(true);
        } else {
            Menu.disableNetMenus();

            //GoNetConstants.NEWGAMESTR, CONTINUESTR, BACKGOSTR, COUNTSTR
            String str = "Your partner wants to " + acceptObject.talkString + ". Do you agree or not?";
            goSystemArea.append(str);
            goSystemArea.setAnswerWhat(acceptObject.talkString);
            goSystemArea.setButtonActive(true);
        }
    }

    private void confirm(Object acceptObject) {
        String str = null;
        if (acceptObject.talkString.equals(Constants.ICONFIEDSTR)) {
            str = "Because of some reason, your partner has iconfied his/her window. " + 
                  "We strongly recommended that you stop the network related operations temporary.";
        }
        else if (acceptObject.talkString.equals(Constants.DEICONFIEDSTR)) {
            str = "Your partner has restored his/her window.";
        }
        else if (acceptObject.talkString.equals(Constants.FILEOPENOKSTR)) {
            Menu.setMIStatus(MenuConstants.NEWGAMESTR, true);
            Menu.setMIStatus(MenuConstants.CONTINUESTR, true);
            //left the save and backgo menu unchanged because the 
            //runConfig() and oneStepPlay() have set their status.
            String str1 = "The go board status and its configuration saved in the file has been opend by your partner.";
            String str2;
            Vector buttonVect = goBoard.getButtonVect();
            if (!buttonVect.isEmpty()) {
                Button tempButton = (Button)buttonVect.lastElement();
                if (tempButton.buttonColor == Color.black)  str2 = " And it's white part's turn now.";
                else str2 = " And it's black part's turn now.";                
            }
            else str2 = " And it's black part's turn now.";
            str = str1 + str2;
        }
        else if (acceptObject.talkString.equals(Constants.FILEOPENCANCELSTR)) {
            Menu.restoreNetMenus();
            
            Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
            Menu.setMIStatus(MenuConstants.SAVESTR, false);
            Menu.setMIStatus(MenuConstants.SAVEASSTR, false);

            str = "Sorry, your partner has cancelled the file open operation.";
        }
        else if (acceptObject.talkString.equals(Constants.FILEOPENERRORSTR)) {
            Menu.restoreNetMenus();
            
            Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
            Menu.setMIStatus(MenuConstants.SAVESTR, false);
            Menu.setMIStatus(MenuConstants.SAVEASSTR, false);

            str = "It's a regret that there are errors happened in the file open operation.";
        }
        else if (acceptObject.talkString.equals(Constants.INFORMTOCOMESTR)) { //The other player is calling me.
            Menu.setMIStatus(MenuConstants.ENDINFORMSTR, true);

            if ((goInform != null) && (goInform.getInformInformation() != null)) goInform.beginInform();
            else goBeep();  //Use the default informing method: beep.

            str = "Your friend is calling you on the other part of the network.";
        }
        else if (acceptObject.talkString.equals(Constants.INFORMCOMINGSTR)) { //The other player is coming now.
            Menu.setMIStatus(MenuConstants.INFORMSTR, true);

            str = "Your partner has received your notification and has come back.";
        }
        else if (acceptObject.talkString.equals(Constants.EXITSTR)) {
            stopNetworkOperation();        

            isOtherPartExit = true;
            str = "Your partner has exited the network and your network related operations have been blocked.";
        }
        else if (acceptObject.talkString.equals(Constants.MYSOCKETERRORSTR)) { //received from GoAcceptThread.java
            stopNetworkOperation();  
            Menu.setMIStatus(MenuConstants.CONNECTSTR, true);
     
            isMySocketError = true;
            str = "Socket error and your network related operations have been blocked. " +
                  "Maybe you have lost the network connection to the server. Reconnect to the server please.";
        }
        else if (acceptObject.talkString.equals(Constants.HESOCKETERRORSTR)) {
            stopNetworkOperation();  
     
            isHeSocketError = true;
            str = "Socket error in your partner's part and your network related operations have " +
                  "been blocked. Maybe he or she has lost the network connection to the server.";
        }
        else if (acceptObject.talkString.equals(Constants.HENETRESTOREDSTR)) {
            restoreNetworkOperation();

            isHeSocketError = false;
            str = "Your partner has connected to the server successfully and your network " + 
                  "related operation has been restored.";
        }
        goSystemArea.append(str);
    }

    private void answer(Object acceptObject) {
        if (acceptObject.isAgree == Constants.AGREE) {
            if (acceptObject.answerWhat.equals(Constants.ENDMARKSTR)) {
                PointsAlive pointsAlive = goBoard.countPointsAlive();
                float blackPoints = pointsAlive.blackPoints;
                float whitePoints = pointsAlive.whitePoints;

                String str = "What you have marked has been agreed by your partner." + 
                             "And the result is: Black Points Alive are " + blackPoints +
                             " White Points Alive are " + whitePoints;
                goSystemArea.append(str);

                restoreMenuAndGoBoard();
            } else {
                String str = "Your idea has been accepted by your partner.";
                goSystemArea.append(str);
    
                if (acceptObject.answerWhat.equals(Constants.COUNTSTR)) {
                    agreeCount();
                } else {
                    goSystemArea.agree(acceptObject);
    
                    if (acceptObject.answerWhat.equals(Constants.CONTINUESTR)) {
                        agreeContinueAdditional();           
                    }
                }
            }
        }
        else if (acceptObject.isAgree == Constants.DISAGREE) {
            goSystemArea.disagree(acceptObject);
        }
    }

    private void marking(Object acceptObject) {
        Point playPoint = acceptObject.playPoint;
        if (playPoint != null) {
            goBoard.markDeadButtons(playPoint, acceptObject.isDead);
        }
    }

    private void speaking(Object acceptObject) {
        if (acceptObject.blockNumber == 1) {
            Playback goPlayback = new Playback(acceptObject.speakingBytes);
            goPlayback.start();
        } else if (acceptObject.blockNumber > 1) {
            if (acceptObject.blockNO == acceptObject.blockNumber) { //the last block
                speakingBAOS.write(acceptObject.speakingBytes, 0, 
                                   java.lang.reflect.Array.getLength(acceptObject.speakingBytes));
                byte[] tmpBytes = speakingBAOS.toByteArray();
                speakingBAOS.reset();

                Playback goPlayback = new Playback(tmpBytes);
                goPlayback.start();
            } else {
                speakingBAOS.write(acceptObject.speakingBytes, 0, Constants.BLOCKLEN);
            }
        }
    }

    private void restoreMenuAndGoBoard() {
        //Restore the menu status and goBoard listeners.
        Menu.restoreNetMenus();
        Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
        Menu.setMIStatus(MenuConstants.COUNTSTR, false);

        goBoard.removeMouseListener(countMouseAdapter);
        goBoard.removeMouseMotionListener(countMouseMotionAdapter);

        goBoard.addMouseListener(playMouseAdapter);
        goBoard.addMouseMotionListener(playMouseMotionAdapter);
    }

    private void agreeCount() {
        //Remove the old listeners and add the new listeners.
        goBoard.removeMouseListener(playMouseAdapter);
        goBoard.removeMouseMotionListener(playMouseMotionAdapter);
        goBoard.setMyTurn(false);

        markTurn = true;
        goBoard.addMouseListener(countMouseAdapter);
        goBoard.addMouseMotionListener(countMouseMotionAdapter);

        //Popup a dialog to let the user the mark the dead buttons
        if (markDialog == null) {
            markDialog = new MarkDialog((Frame)dialogOwner, "Dead Button Marking", false);
            Dimension dSize = new Dimension(300, 150);
            markDialog.setSize(dSize);
            Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = dScreen.width - dSize.width;
            int y = (dScreen.height - dSize.height) / 2;
            markDialog.setLocation(x, y);
        }
        markDialog.setVisible(true);
    }

    private void agreeContinueAdditional() {
        int returnVal = goFileChooser.showOpenDialog(dialogOwner);
        Object sentObject;

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            goFile = goFileChooser.getSelectedFile();
            if (open(goFile)) {
                sentObject = new Object(Constants.CONFIRM, Constants.FILEOPENOKSTR);
                goNetClient.sendToServer(sentObject);

                Menu.setMIStatus(MenuConstants.NEWGAMESTR, true);
                Menu.setMIStatus(MenuConstants.CONTINUESTR, true);
                Menu.setMIStatus(MenuConstants.SAVESTR, false);

                String str1 = "The go board status and its configuration " + 
                              "saved in the file has been opend successfully.";;
                String str2;
                Vector buttonVect = goBoard.getButtonVect();
                if (!buttonVect.isEmpty()) {
                    Button tempButton = (Button)buttonVect.lastElement();
                    if (tempButton.buttonColor == Color.black)  str2 = " And it's white part's turn now.";
                    else str2 = " And it's black part's turn now.";                
                }
                else str2 = " And it's black part's turn now.";
                goSystemArea.append(str1 + str2);
            }
            else {
                sentObject = new Object(Constants.CONFIRM, Constants.FILEOPENERRORSTR);
                goNetClient.sendToServer(sentObject);
                Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
                Menu.setMIStatus(MenuConstants.SAVESTR, false);
                Menu.setMIStatus(MenuConstants.SAVEASSTR, false);
            }
        }
        else {
            sentObject = new Object(Constants.CONFIRM, Constants.FILEOPENCANCELSTR);
            goNetClient.sendToServer(sentObject);
            Menu.setMIStatus(MenuConstants.BACKGOSTR, false);
            Menu.setMIStatus(MenuConstants.SAVESTR, false);
            Menu.setMIStatus(MenuConstants.SAVEASSTR, false);
        }
    }

    private void stopNetworkOperation() {
        goConfig.setButtonActive(false);
        goTalkArea.setEditable(false);
        goTalkArea.removeListeners();
        goBoard.setMyTurn(false);
        goBoard.stopTimer();
        Menu.disableNetMenus();
        hasRestored = false;
    }

    public void restoreNetworkOperation() {
        if (!hasRestored) {
            goConfig.restoreButtonStatus();
            goTalkArea.setEditable(true);
            goTalkArea.restoreListeners();
            goBoard.restoreMyTurn();
            goBoard.startTimer();
            Menu.restoreNetMenus();
            hasRestored = true;
        }
    }

    public void setNetClient(Client goNetClient) {
        this.goNetClient = goNetClient;

        goConfig.setNetClient(goNetClient);
        goTalkArea.setNetClient(goNetClient);
        goSystemArea.setNetClient(goNetClient);
    }

    private synchronized void goBeep() {
        shouldBeep = true;

        while (shouldBeep) {
            Toolkit.getDefaultToolkit().beep();
            try {
                wait(2000);
            } catch (InterruptedException iex) {}
        }
    }

    public synchronized void endInform() {
        if ((goInform != null) && (goInform.getInformInformation() != null)) goInform.endInform();
        else { //end this goBeep()
            shouldBeep = false;
            notifyAll();
        }
    }

    public GoConfig getGoConfig() {
        return goConfig;
    }

    public TalkArea getGoTalkArea() {
        return goTalkArea;
    }

    public SystemArea getSystemArea() {
        return goSystemArea;
    }

    public JFileChooser getFileChooser() {
        return goFileChooser;
    }

    public File getFile() {
        if (goFile == null) return null;
        else return goFile;
    }

    public boolean isChanged() {
        return (goConfig.isChanged() || goBoard.isChanged());
    }

    public void setGoInform(GoInform goInform) {
        this.goInform = goInform;
    }

    public void save(File outFile) {
        int playPart = goConfig.getPlayPart();
        int handiNum = goConfig.getHandiNum();
        boolean isSelected = goConfig.getSelected();
        
        ObjectOutputStream outStream;
        
        try {
            outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)));

            SaveObject configObject = new SaveObject(playPart, handiNum, isSelected);
            outStream.writeObject(configObject);

            Vector buttonVect = goBoard.getButtonVect();

            for (int i = handiNum; i < buttonVect.size(); i++) {
                Button goButton = (Button)buttonVect.elementAt(i);
                SaveObject buttonObject = new SaveObject(new Point(goButton.centerX, goButton.centerY));
                outStream.writeObject(buttonObject);
            }
            outStream.close();
            goConfig.setChanged(false);
            goBoard.setChanged(false);
            Menu.setMIStatus(MenuConstants.SAVESTR, false);
        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private boolean open(File inFile) {
        ObjectInputStream inStream;
        Object  sentObject;
        SaveObject configObject;
        SaveObject buttonObject;

        if (!inFile.exists()) return false;

        boolean returnVal = true;

        try {
            inStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(inFile)));

            configObject = (SaveObject)inStream.readObject();
            
            int playPart = configObject.playPart;
            int handiNum = configObject.handiNO;
            boolean isSelected = configObject.isSelected;

            runConfig(playPart, handiNum, isSelected);

            sentObject = new Object(Constants.RECONFIG, playPart, handiNum, isSelected);
            if (!goNetClient.sendToServer(sentObject))  returnVal = false;
     
            try {
                while (true) {
                    buttonObject = (SaveObject)inStream.readObject();
                    if (goBoard.isMyTurn()) {
                        goBoard.oneStepPlay(buttonObject.playPoint, goConfig.getPlayPart());
                    }
                    else {
                        goBoard.oneStepPlay(buttonObject.playPoint, 1 - goConfig.getPlayPart());
                    }                        
                    sentObject = new Object(Constants.PLAYING, buttonObject.playPoint);
                    if (!goNetClient.sendToServer(sentObject))  returnVal = false;
                }
            } catch (EOFException eofEx) {
                inStream.close();
            }
        } catch (ClassNotFoundException cnfEx) {
            cnfEx.printStackTrace();
            returnVal = false;
        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
            returnVal = false;
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            returnVal = false;
        }
        return returnVal;
    }

    private void runConfig(int playPart, int handiNum, boolean isSelected) {
        int   handiIndex = -1;
        int[] handiNOInts = {1, 2, 3, 4, 5, 6, 8, 9, 13, 17};

        goConfig.setButtonActive(false);

        goConfig.setPlayPart(playPart);
        if (playPart == 0) goConfig.selectBlackButton(true);
        else goConfig.selectWhiteButton(true);
             
        goConfig.setHandiNum(handiNum);

        if (handiNum > 0) {
            for (int i = 0; i < handiNOInts.length; i++) {
                if (handiNum == handiNOInts[i]) {
                    handiIndex = i;
                    break;
                }
            }
            goBoard.setHandiNum(handiNum);
        }

        goConfig.setSelected(isSelected);
        goConfig.selectHandiButton(isSelected);

        if (handiIndex == -1) goConfig.selectHandiNOBox(0);
        else goConfig.selectHandiNOBox(handiIndex);

        if ((handiNum == 0) || (handiNum == 1)) {
            if (playPart == 0) goBoard.setMyTurn(true);
            else goBoard.setMyTurn(false);
        }
        else {
            if (playPart == 0) goBoard.setMyTurn(false);
            else goBoard.setMyTurn(true);
        }
    }

    class MarkDialog extends JDialog {
        MarkDialog(Frame fOwner, String str, boolean modal) {
            super(fOwner, str, modal);

            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            JLabel markLabel1 = new JLabel("Use your mouse to mark the dead buttons ");
            markLabel1.setFont(FontManager.labelFont);
            JLabel markLabel2 = new JLabel("and press the End Marking when finished.");
            markLabel2.setFont(FontManager.labelFont);
            JButton endButton = new JButton("End Marking");
            endButton.setFont(FontManager.buttonFont);
            endButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    markTurn = false;
                    dispose();  
                     
                    //While the user end the marking, asking his/her partner whether he/she agrees.
                    goNetClient.sendToServer(new Object(Constants.ASKING, Constants.ENDMARKSTR));

                    //Tell the user to wait for his/her partner's idea.
                    String str = "The system is now asking your partner whether he/she agrees to " +
                                 "what you have marked. Please wait...";
                    goSystemArea.append(str);          
                }
            });
        
            JPanel panel1 = new JPanel();
            panel1.setBorder(new EmptyBorder(10, 5, 10, 5));
            panel1.add(markLabel1);
            panel1.add(markLabel2);
            JPanel panel2 = new JPanel();
            panel2.add(endButton);

            Container contentPane = getContentPane();
            contentPane.add(panel1, BorderLayout.CENTER);
            contentPane.add(panel2, BorderLayout.SOUTH);
        }
    }

    class EndMarkDialog extends JDialog implements ActionListener {
        EndMarkDialog(Frame fOwner, String str, boolean modal) {
            super(fOwner, str, modal);

            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            String[] labelStrs = {"    Choose Agree if you agree to what your partner ",
                                  "has marked. Or use your mouse to mark the dead ",
                                  "buttons (Ctrl + mouse to unmark) and press the ",
                                  "End Marking when finished."};

            int len = labelStrs.length;
            JLabel[] markLabels = new JLabel[len];
            for (int i = 0; i < len; i++) {
                markLabels[i] = new JLabel(labelStrs[i]);
                markLabels[i].setFont(FontManager.labelFont);
            }

            JButton agreeButton = new JButton("Agree");
            agreeButton.setFont(FontManager.buttonFont);
            agreeButton.addActionListener(this);
            JButton endButton = new JButton("End Marking");
            endButton.setFont(FontManager.buttonFont);
            endButton.addActionListener(this);
        
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel1.setBorder(new EmptyBorder(10, 5, 10, 5));
            for (int i = 0; i < len; i++) panel1.add(markLabels[i]);
            JPanel panel2 = new JPanel();
            panel2.add(agreeButton);  panel2.add(new JPanel());  panel2.add(endButton);

            Container contentPane = getContentPane();
            contentPane.add(panel1, BorderLayout.CENTER);
            contentPane.add(panel2, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Agree")) {
                markTurn = false;
                dispose();
  
                //While the user choose agree, tell his/her partner that the user agrees.
                Object sentObject = new Object(Constants.ANSWER, Constants.AGREE, 
                                                         Constants.ENDMARKSTR);
                goNetClient.sendToServer(sentObject);

                //End the mark and tell user the points alive.
                PointsAlive pointsAlive = goBoard.countPointsAlive();
                float blackPoints = pointsAlive.blackPoints;
                float whitePoints = pointsAlive.whitePoints;

                String str = "The counting result is: Black Points Alive are " + blackPoints +
                             " White Points Alive are " + whitePoints;
                goSystemArea.append(str);

                restoreMenuAndGoBoard();
            } else if (e.getActionCommand().equals("End Marking")) {
                markTurn = false;
                dispose();
                
                //While the user end the marking, asking his/her partner whether he/she agrees.
                goNetClient.sendToServer(new Object(Constants.ASKING, Constants.ENDMARKSTR));

                //Tell the user to wait for his/her partner's idea.
                String str = "The system is now asking your partner whether he/she agrees to " +
                             "what you have marked. Please wait...";
                goSystemArea.append(str);
            }
        }
    }
}