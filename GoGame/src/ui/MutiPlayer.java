package ui;

import controller.Play;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.User;
import model.core.Goban;
import model.core.Main;
import model.gui.GUI;
import static model.gui.GUI.TOKEN_INITIAL_SIZE;
import model.gui.PassMove;
import model.gui.UndoRedo;
import model.gui.ValidateScore;
import model.score.Scorer;

/**
 *
 * @author alice
 */
public class MutiPlayer extends javax.swing.JFrame {
    public static final int TOKEN_INITIAL_SIZE = 35;
    private final User competitor;
    private final String competitorIP;
    private int userWin;
    private int competitorWin;
    private boolean isSending;
    private boolean isListening;
    private final Timer timer;
    private Integer second;
    private Integer minute;
    private int numberOfMatch;
    private GUI goGameGUI;
    private Goban goban;
    
    
    public MutiPlayer(User competitor, int roomID, int isStart, String competitorIP) {
        initComponents();
        this.setTitle("Go Game");
        this.setIconImage(new ImageIcon("/resources/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(null);
        goban = new Goban(13,13, 5);
        goGameGUI = new GUI(goban);
        gamePanel.add(goGameGUI.getContentPane());
        this.competitor = competitor;
        this.competitorIP = competitorIP;

        isSending = false;
        isListening = false;
        
        playerUsernameValue.setText(Play.user.getUsername());
        playerMatchesValue.setText(Integer.toString(Play.user.getCntGame()));
        playerWinValue.setText(Integer.toString(Play.user.getCntWin()));
        roomNameLabel.setText("Room: " + roomID);
        competitorUsernameValue.setText(competitor.getUsername());
        competitorMatchesValue.setText(Integer.toString(competitor.getCntGame()));
        competitorWinValue.setText(Integer.toString(competitor.getCntWin()));
        playerPosButton.setVisible(false);
        competitorPosButton.setVisible(false);
        loseRequestButton.setVisible(false);
        playerTurnLabel.setVisible(false);
        competitorTurnLabel.setVisible(false);
        countDownLabel.setVisible(false);
        messageTextArea.setEditable(false);
        scoreLabel.setText("0 - 0");
        
        second = 60;
        minute = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                if (second == 0) {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second = 60;
                    minute = 0;
                    try {
                        Play.openView(Play.View.GAME_PLAYER, "Overtime!!! You lose!!!", "Create new game");
//                        increaseWinMatchToCompetitor();
                        Play.socketHandle.write("lose,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }

                } else {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                }

            }

        });
    }
    
    public void stopAllThread() {
        timer.stop();
//        voiceCloseMic();
//        voiceStopListening();
    }
    
    public void setUndoEnabled(boolean state) {
        undoItem.setEnabled(state);
    }

    public void setRedoEnabled(boolean state) {
        redoItem.setEnabled(state);
    }

    public void setPassEnabled(boolean state) {
        passItem.setEnabled(state);
    }
    
    public Goban getGoban() {
        return goban;
    }
    
    public void startTimer() {
        countDownLabel.setVisible(true);
        second = 60;
        minute = 0;
        timer.start();
    }
    
    public void displayCompetitorTurn() {
        countDownLabel.setVisible(false);
        competitorTurnLabel.setVisible(true);
        competitorPosButton.setVisible(true);
        playerTurnLabel.setVisible(false);
        loseRequestButton.setVisible(false);
        playerPosButton.setVisible(false);
    }

    public void displayUserTurn() {
        countDownLabel.setVisible(false);
        competitorTurnLabel.setVisible(false);
        competitorPosButton.setVisible(false);
        playerTurnLabel.setVisible(true);
        loseRequestButton.setVisible(true);
        playerPosButton.setVisible(true);
    }
    
    public void newgame() {
        Random random = new Random();
        int randNum = random.nextInt();
        if (randNum % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "You go first");
            startTimer();
            displayUserTurn();
            countDownLabel.setVisible(true);
            playerPosButton.setIcon(new ImageIcon("/resources/black.png"));
            competitorPosButton.setIcon(new ImageIcon("/resources/white.png"));
        } else {
            JOptionPane.showMessageDialog(rootPane, "The competitor goes first");
            displayCompetitorTurn();
            playerPosButton.setIcon(new ImageIcon("/resources/white.png"));
            competitorPosButton.setIcon(new ImageIcon("/resources/black.png"));
        }
//        goban = new Goban(13,13, 5);
//        goGameGUI = new GUI(goban);
//        gamePanel.add(goGameGUI.getContentPane());
        numberOfMatch++;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        competitorLabel = new javax.swing.JLabel();
        competitorInfo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        roomNameLabel = new javax.swing.JLabel();
        microStatusButton = new javax.swing.JButton();
        speakerStatusButton = new javax.swing.JButton();
        playerTurnLabel = new javax.swing.JLabel();
        competitorTurnLabel = new javax.swing.JLabel();
        countDownLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        messageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        playerPosButton = new javax.swing.JButton();
        competitorPosButton = new javax.swing.JButton();
        scoreLabel = new javax.swing.JLabel();
        loseRequestButton = new javax.swing.JButton();
        playerUsernameValue = new javax.swing.JLabel();
        playerMatchesValue = new javax.swing.JLabel();
        playerWinValue = new javax.swing.JLabel();
        competitorUsernameValue = new javax.swing.JLabel();
        competitorMatchesValue = new javax.swing.JLabel();
        competitorWinValue = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        gamePanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        gameNewItem = new javax.swing.JMenuItem();
        openItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        passItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ruleItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        playerLabel.setText("You");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(playerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        competitorLabel.setText("Competitor");

        competitorInfo.setText("Info");
        competitorInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                competitorInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(competitorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(competitorInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(competitorLabel)
                    .addComponent(competitorInfo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Username");

        jLabel3.setText("Matches");

        jLabel4.setText("Win");

        jLabel6.setText("Username");

        jLabel7.setText("Matches");

        jLabel8.setText("Win");

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        roomNameLabel.setText("{Room name}");

        microStatusButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mute.png"))); // NOI18N
        microStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                microStatusButtonActionPerformed(evt);
            }
        });

        speakerStatusButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mutespeaker.png"))); // NOI18N
        speakerStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speakerStatusButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomNameLabel)
                .addGap(67, 67, 67)
                .addComponent(microStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(speakerStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(roomNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(microStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(speakerStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );

        playerTurnLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerTurnLabel.setText("Your turn");

        competitorTurnLabel.setText("Competitor's turn");

        countDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countDownLabel.setText("Time: 00:00");

        messageTextArea.setColumns(20);
        messageTextArea.setRows(5);
        jScrollPane1.setViewportView(messageTextArea);

        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyPressed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        playerPosButton.setPreferredSize(new java.awt.Dimension(30, 30));

        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("0 - 0");

        loseRequestButton.setText("Resignation");
        loseRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loseRequestButtonActionPerformed(evt);
            }
        });

        playerUsernameValue.setText("{username}");

        playerMatchesValue.setText("{number}");

        playerWinValue.setText("{number}");

        competitorUsernameValue.setText("{username}");

        competitorMatchesValue.setText("{number}");

        competitorWinValue.setText("{number}");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(messageTextField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(playerTurnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(175, 175, 175)
                                .addComponent(competitorTurnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addGap(52, 52, 52)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(playerWinValue)
                                            .addComponent(playerMatchesValue)
                                            .addComponent(playerUsernameValue)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(53, 53, 53)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(competitorWinValue)
                                            .addComponent(competitorMatchesValue)
                                            .addComponent(competitorUsernameValue)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(countDownLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sendButton)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(loseRequestButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(playerPosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(competitorPosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(playerUsernameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(playerMatchesValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(playerWinValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(competitorUsernameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(competitorMatchesValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(competitorWinValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playerPosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(scoreLabel))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(competitorPosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTurnLabel)
                    .addComponent(competitorTurnLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countDownLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loseRequestButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gamePanel.setBackground(new java.awt.Color(153, 204, 255));
        gamePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 462, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jMenu3.setText("Menu");

        gameNewItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        gameNewItem.setText("New game");
        gameNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameNewItemActionPerformed(evt);
            }
        });
        jMenu3.add(gameNewItem);

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        jMenu3.add(openItem);

        saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveItem.setText("Save");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        jMenu3.add(saveItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu3.add(exitItem);

        jMenuBar1.add(jMenu3);

        jMenu1.setText("Edit");

        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        undoItem.setText("Undo");
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });
        jMenu1.add(undoItem);

        redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        redoItem.setText("Redo");
        jMenu1.add(redoItem);

        passItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        passItem.setText("Pass");
        jMenu1.add(passItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        ruleItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ruleItem.setText("Rule");
        ruleItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruleItemActionPerformed(evt);
            }
        });
        jMenu2.add(ruleItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitItemActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        try {
            if (messageTextField.getText().isEmpty()) {
                throw new Exception("Please enter message!");
            }
            String temp = messageTextArea.getText();
            temp += "Me: " + messageTextField.getText() + "\n";
            messageTextArea.setText(temp);
            Play.socketHandle.write("chat," + messageTextField.getText());
            messageTextField.setText("");
            messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void loseRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loseRequestButtonActionPerformed

    }//GEN-LAST:event_loseRequestButtonActionPerformed

    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoItemActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Rule: Blablablabla\n");
    }//GEN-LAST:event_undoItemActionPerformed

    private void competitorInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_competitorInfoActionPerformed
        Play.openView(Play.View.COMPETITOR_INFO, competitor);
    }//GEN-LAST:event_competitorInfoActionPerformed

    private void microStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_microStatusButtonActionPerformed
        
    }//GEN-LAST:event_microStatusButtonActionPerformed

    private void speakerStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speakerStatusButtonActionPerformed
       
    }//GEN-LAST:event_speakerStatusButtonActionPerformed

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            try {
                if (messageTextField.getText().isEmpty()) {
                    return;
                }
                String temp = messageTextArea.getText();
                temp += "Me: " + messageTextField.getText() + "\n";
                messageTextArea.setText(temp);
                Play.socketHandle.write("chat," + messageTextField.getText());
                messageTextField.setText("");
                messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }//GEN-LAST:event_messageTextFieldKeyPressed

    private void ruleItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruleItemActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Rule: blablablabla\n");
    }//GEN-LAST:event_ruleItemActionPerformed

    private void gameNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameNewItemActionPerformed
        String[] gobanSizeChoises = {"9x9", "13x13", "19x19"};
        String[] handicapChoises = new String[9];
        for (int i = 0; i < handicapChoises.length; i++) {
            handicapChoises[i] = Integer.toString(i);
        }

        String gobanSizeString = (String) JOptionPane.showInputDialog(null, "Choose the size of GoGame", "New Game", JOptionPane.QUESTION_MESSAGE, null, gobanSizeChoises, gobanSizeChoises[2]);
        String handicapString = (String) JOptionPane.showInputDialog(null, "Choose the number of initial black stones before black can play (0 to 8)", "New Game", JOptionPane.QUESTION_MESSAGE, null, handicapChoises, handicapChoises[0]);

        try {
            int gobanSize = Integer.parseInt(gobanSizeString.split("x")[0]);
            int handicap = Integer.parseInt(handicapString);
            Main.newGame(gobanSize, handicap);
            MutiPlayer.this.setVisible(false);
            MutiPlayer.this.dispose();
        } catch (Exception ex) {
            //
        }
    }//GEN-LAST:event_gameNewItemActionPerformed

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
        try {
                JFileChooser openFile = new JFileChooser(".");
                openFile.addChoosableFileFilter(new FileNameExtensionFilter(".save", "Go save game"));
                //openFile.setAcceptAllFileFilterUsed(false);
                if (openFile.showOpenDialog(MutiPlayer.this) == JFileChooser.APPROVE_OPTION) {
                    String file = openFile.getSelectedFile().getCanonicalPath();
                    MutiPlayer.this.setVisible(false);
                    MutiPlayer.this.dispose();
                    Main.loadGame(file);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_openItemActionPerformed

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        try {
            JFileChooser saveFile = new JFileChooser(".");
            saveFile.addChoosableFileFilter(new FileNameExtensionFilter(".save", "Go save game"));
            saveFile.setAcceptAllFileFilterUsed(false);
            if (saveFile.showSaveDialog(MutiPlayer.this) == JFileChooser.APPROVE_OPTION) {
                String file = saveFile.getSelectedFile().getCanonicalPath();
                //Putting the correct extension
                file = file.replaceAll("\\.save?$","");
                file = file + ".save";

                if (!MutiPlayer.this.getGoban().getGameRecord().save(file)) {
                    // there were a problem saving the game
                    JOptionPane.showMessageDialog(MutiPlayer.this,
                            "The game could not be saved, try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveItemActionPerformed
   
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton competitorInfo;
    private javax.swing.JLabel competitorLabel;
    private javax.swing.JLabel competitorMatchesValue;
    private javax.swing.JButton competitorPosButton;
    private javax.swing.JLabel competitorTurnLabel;
    private javax.swing.JLabel competitorUsernameValue;
    private javax.swing.JLabel competitorWinValue;
    private javax.swing.JLabel countDownLabel;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem gameNewItem;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loseRequestButton;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JButton microStatusButton;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JMenuItem passItem;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerMatchesValue;
    private javax.swing.JButton playerPosButton;
    private javax.swing.JLabel playerTurnLabel;
    private javax.swing.JLabel playerUsernameValue;
    private javax.swing.JLabel playerWinValue;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JMenuItem ruleItem;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton speakerStatusButton;
    private javax.swing.JMenuItem undoItem;
    // End of variables declaration//GEN-END:variables
}
