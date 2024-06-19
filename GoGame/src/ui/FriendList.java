package ui;

import controller.Play;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import model.User;

/**
 *
 * @author alice
 */
public class FriendList extends javax.swing.JFrame {
    private List<User> listFriend;
    private boolean isClicked;
    DefaultTableModel defaultTableModel;
    
    public FriendList() {
        initComponents();
        this.setTitle("Go Game");
        this.setIconImage(new ImageIcon("/resources/logo.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        isClicked = false;
        requestUpdate();
        startThread();
    }

    public void startThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (Play.friendList.isDisplayable() && !isClicked) {
                    try {
                        System.out.println("List friend is running");
                        requestUpdate();
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
    
    public void stopAllThread() {
        isClicked = true;
    }

    public void requestUpdate() {
        try {
            Play.socketHandle.write("view-friend-list,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void updateFriendList(List<User> friends) {
        listFriend = friends;
        defaultTableModel.setRowCount(0);
        ImageIcon icon;
        for (User friend : listFriend) {
            if (!friend.isOnline()) {
                icon = new ImageIcon("assets/icon/offline.png");
            } else if (friend.isPlaying()) {
                icon = new ImageIcon("assets/icon/swords-mini.png");
            } else {
                icon = new ImageIcon("assets/icon/swords-1-mini.png");
            }
            defaultTableModel.addRow(new Object[]{
                    "" + friend.getID(),
                    friend.getID(),
                    icon
            });
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Object[][] rows = {
        };
        String[] columns = {"ID","Username",""};
        DefaultTableModel model = new DefaultTableModel(rows, columns){
            @Override
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return ImageIcon.class;
                    default: return Object.class;
                }
            }
        };
        friendListTextArea = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FRIEND LIST");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close3.png"))); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        friendListTextArea.setModel(model);
        friendListTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendListTextAreaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(friendListTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        Play.closeView(Play.View.FRIEND_LIST);
        Play.openView(Play.View.MAIN_MENU);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void friendListTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendListTextAreaMouseClicked
        try {
            if (friendListTextArea.getSelectedRow() == -1) return;
            User friend = listFriend.get(friendListTextArea.getSelectedRow());
            if (!friend.isOnline()) {
                throw new Exception("Player is offline");
            }
            if (friend.isPlaying()) {
                throw new Exception("Player is in match");
            }
            isClicked = true;
            int res = JOptionPane.showConfirmDialog(rootPane, "Do you want to challenge with this friend", "Confirm challenge", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                Play.closeAllViews();
                Play.openView(Play.View.MESSAGE, "Challenge", "Waiting for respond");
                Play.socketHandle.write("duel-request," + friend.getID());
            } else {
                isClicked = false;
                startThread();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_friendListTextAreaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTable friendListTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
