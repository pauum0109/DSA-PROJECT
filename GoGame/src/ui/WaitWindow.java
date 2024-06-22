/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: The ui shows when waiting the competitor join the port and play
*/
package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.FontManager;

public class WaitWindow extends Window {
    private int sourceX = 0, sourceY = 0;
    
    public WaitWindow(Frame fOwner) {
        super(fOwner);

        JPanel waitPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        waitPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createRaisedBevelBorder(),
                            BorderFactory.createEmptyBorder(20, 12, 20, 12)));
        JLabel waitLabel1 = new JLabel("    The system is trying to connect to the server and ");
        JLabel waitLabel2 = new JLabel("waiting to your partner's connecting, please wait ...");
        waitLabel1.setFont(FontManager.labelFont);
        waitLabel2.setFont(FontManager.labelFont);
        waitPanel.add(waitLabel1);
        waitPanel.add(waitLabel2);
        add(waitPanel, BorderLayout.CENTER);

        addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                sourceX = e.getX();
                sourceY = e.getY();
            }
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                Point p = getLocationOnScreen();
                int x = p.x + e.getX() - sourceX;
                int y = p.y + e.getY() - sourceY;

                setLocation(x, y);
            }

            public void mouseMoved(MouseEvent e) {}
        });
    }

    public void setVisible(boolean visible) {
		if (visible) {
			Thread runner = new Thread() {
				public void run() {
					WaitWindow.super.setVisible(true);    
				}
			};
			runner.start();

			synchronized(this) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		} else {
			synchronized(this) {
				notifyAll();
			}
			super.setVisible(false);
		}
    }

    public void dispose() {
        synchronized(this) {
            notifyAll();
        }
        super.dispose();
    }
}