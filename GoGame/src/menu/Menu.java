/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Manages the creation and status handling of menus and menu items in a graphical user interface.
*/
package menu;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import util.FontManager;

public class Menu {
    //Menus
    private JMenu[] goMenu;

    //Menu Items Vector
    private static Vector<JMenuItem[]> goMIVect;

    //Menu Listener
    private ActionListener listener;

    //Status used for store old status of net related menus.
    private static boolean newgameStatus    = false;
    private static boolean continueStatus   = false;
    private static boolean backgoStatus     = false;
    private static boolean countStatus      = false;
    private static boolean informStatus     = false;
    private static boolean informtypeStatus = false;
    private static boolean endinformStatus  = false;

    public Menu(JMenuBar goMB, ActionListener menuListener) {
        listener = menuListener;

        int j    = MenuConstants.goMenuStrs.length;
        goMenu   = new JMenu[j];
        goMIVect = new Vector<JMenuItem[]>(j, 1);

        for (int i = 0; i < j; i++) {
            goMenu[i] = new JMenu(MenuConstants.goMenuStrs[i]);
            goMenu[i].setFont(FontManager.menuFont);
            goMenu[i].setMnemonic((new StringBuffer(MenuConstants.goMenuMne[i])).charAt(0));
            goMIVect.add(goMenuAddItems(i));   //Inner method is defined as follows.   
            goMB.add(goMenu[i]);
        }
    }

    private JMenuItem[] goMenuAddItems(int num) {
        int j = MenuConstants.goMIStrs[num].length;
        
        JMenuItem[] goMIs = new JMenuItem[j];
        
        for (int i = 0; i < j; i++) {
            if (MenuConstants.goMIStrs[num][i].equals(MenuConstants.SEPARATORSTR)) {
                goMenu[num].addSeparator();
            }
            else {
                goMIs[i] = goMenu[num].add(MenuConstants.goMIStrs[num][i]);
                goMIs[i].setFont(FontManager.menuFont);
                goMIs[i].addActionListener(listener);
                if (!MenuConstants.goMIMne[num][i].equals("")) {
                    goMIs[i].setMnemonic((new StringBuffer(MenuConstants.goMIMne[num][i])).charAt(0));
                }
                if (!MenuConstants.goMIAcc[num][i].equals("")) {
                    if (MenuConstants.goMIAcc[num][i].equals("N")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
                    } 
                    else if (MenuConstants.goMIAcc[num][i].equals("P")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
                    }
                    else if (MenuConstants.goMIAcc[num][i].equals("O")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
                    }
                    else if (MenuConstants.goMIAcc[num][i].equals("S")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
                    }
                    else if (MenuConstants.goMIAcc[num][i].equals("A")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
                    }
                    else if (MenuConstants.goMIAcc[num][i].equals("B")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
                    }
                    else if (MenuConstants.goMIAcc[num][i].equals("R")) {
                        goMIs[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
                    }
                }
                if (MenuConstants.goMenu[num][i] == 0)
                    goMIs[i].setEnabled(false);
            }
        }        
        return goMIs;
    }

    public static boolean getMIStatus(String miStr) {
        boolean retB = false;
        int len1 = MenuConstants.goMIStrs.length;

        for (int i = 0; i < len1; i++) {
            int len2 = MenuConstants.goMIStrs[i].length;
            for (int j = 0; j < len2; j++) {
                if (MenuConstants.goMIStrs[i][j].equals(miStr)) {
                    JMenuItem[] tempMIs = (JMenuItem[])goMIVect.elementAt(i);
                    retB = tempMIs[j].isEnabled();
                    i = len1;  //To exit the outer for loop
                    break;
                }
            }
        }
        return retB;
    }

    public static void setMIStatus(String miStr, boolean isActive) {
        int len1 = MenuConstants.goMIStrs.length;

        for (int i = 0; i < len1; i++) {
            int len2 = MenuConstants.goMIStrs[i].length;
            for (int j = 0; j < len2; j++) {
                if (MenuConstants.goMIStrs[i][j].equals(miStr)) {
                    JMenuItem[] tempMIs = (JMenuItem[])goMIVect.elementAt(i);
                    tempMIs[j].setEnabled(isActive);
                    return;
                }
            }
        }
    }

    public static void disableNetMenus() {
        newgameStatus   = getMIStatus(MenuConstants.NEWGAMESTR);
        continueStatus  = getMIStatus(MenuConstants.CONTINUESTR);
        backgoStatus    = getMIStatus(MenuConstants.BACKGOSTR);
        countStatus     = getMIStatus(MenuConstants.COUNTSTR);
        informStatus    = getMIStatus(MenuConstants.INFORMSTR);
        endinformStatus = getMIStatus(MenuConstants.ENDINFORMSTR);
        setMIStatus(MenuConstants.NEWGAMESTR, false);
        setMIStatus(MenuConstants.CONTINUESTR, false);
        setMIStatus(MenuConstants.BACKGOSTR, false);
        setMIStatus(MenuConstants.COUNTSTR, false);
        setMIStatus(MenuConstants.INFORMSTR, false);
        setMIStatus(MenuConstants.ENDINFORMSTR, false);
    }

    public static void restoreNetMenus() {
        setMIStatus(MenuConstants.NEWGAMESTR, newgameStatus);
        setMIStatus(MenuConstants.CONTINUESTR, continueStatus);
        setMIStatus(MenuConstants.BACKGOSTR, backgoStatus);
        setMIStatus(MenuConstants.COUNTSTR, countStatus);
        setMIStatus(MenuConstants.INFORMSTR, informStatus);
        setMIStatus(MenuConstants.ENDINFORMSTR, endinformStatus);
    }
}