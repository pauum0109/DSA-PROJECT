/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Create the background when run the game
*/
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import util.ImageManager;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName, Component cmp) {
        this.backgroundImage = ImageManager.load(fileName, cmp);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
