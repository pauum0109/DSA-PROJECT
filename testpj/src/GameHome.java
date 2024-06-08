import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameHome extends JFrame {

    public GameHome() {
        // Set title and default close operation
        setTitle("Game Title");
        setIconImage(new ImageIcon("resources/logo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Game Title", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // User Info Panel
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(3, 1));
        userInfoPanel.add(new JLabel("Username: User1"));
        userInfoPanel.add(new JLabel("Score: 1000"));
        userInfoPanel.add(new JLabel("Avatar: [Image]"));
        add(userInfoPanel, BorderLayout.WEST);

        // Chat Panel
        JPanel chatPanel = new JPanel(new BorderLayout());
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        JTextField chatInput = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatArea.append("You: " + chatInput.getText() + "\n");
                chatInput.setText("");
            }
        });
        JPanel chatInputPanel = new JPanel(new BorderLayout());
        chatInputPanel.add(chatInput, BorderLayout.CENTER);
        chatInputPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(chatInputPanel, BorderLayout.SOUTH);
        add(chatPanel, BorderLayout.SOUTH);

        // Game Selection Panel
        JPanel gameSelectionPanel = new JPanel(new GridLayout(3, 1));
        JButton playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");
        JButton settingsButton = new JButton("Settings");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gameSelectionPanel.add(playButton);
        gameSelectionPanel.add(settingsButton);
        gameSelectionPanel.add(exitButton);
        add(gameSelectionPanel, BorderLayout.CENTER);

        // Set visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameHome();
            }
        });
    }
}
