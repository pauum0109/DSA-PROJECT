package util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class PopupTextArea extends JTextArea implements ActionListener, MouseListener,
                                                          CaretListener, UndoableEditListener, DocumentListener {
    private String undoStr = "Undo";
    private String redoStr = "Redo";
    private String cutStr = "Cut";
    private String copyStr = "Copy";
    private String pasteStr = "Paste";
    private String selectStr = "Select All";
    private String clearStr = "Clear";

    private JPopupMenu popup;
    private JMenuItem undoMI, redoMI, cutMI, copyMI, pasteMI, selectMI, clearMI;

    private UndoManager undo = new UndoManager();

    public PopupTextArea(String text, int xNum, int yNum) {
        super(text, xNum, yNum);

        setMargin(new Insets(5,5,5,5));
        setSelectionColor(Color.green);
        setFont(FontManager.talkAreaFont);
        setLineWrap(true);
        setWrapStyleWord(true);

        //Create the popup menu.
        popup = new JPopupMenu();

        undoMI = new JMenuItem(undoStr);
        undoMI.setFont(FontManager.buttonFont);
        undoMI.addActionListener(this);
        undoMI.setEnabled(false);
        popup.add(undoMI);
        redoMI = new JMenuItem(redoStr);
        redoMI.setFont(FontManager.buttonFont);
        redoMI.addActionListener(this);
        redoMI.setEnabled(false);
        popup.add(redoMI);

        popup.add(new JSeparator());
        cutMI = new JMenuItem(cutStr);
        cutMI.setFont(FontManager.buttonFont);
        cutMI.addActionListener(this);
        cutMI.setEnabled(false);
        popup.add(cutMI);
        copyMI = new JMenuItem(copyStr);
        copyMI.setFont(FontManager.buttonFont);
        copyMI.addActionListener(this);
        copyMI.setEnabled(false);
        popup.add(copyMI);
        pasteMI = new JMenuItem(pasteStr);
        pasteMI.setFont(FontManager.buttonFont);
        pasteMI.addActionListener(this);
        popup.add(pasteMI);

        popup.add(new JSeparator());
        selectMI = new JMenuItem(selectStr);
        selectMI.setFont(FontManager.buttonFont);
        selectMI.addActionListener(this);
        selectMI.setEnabled(false);
        popup.add(selectMI);
        clearMI = new JMenuItem(clearStr);
        clearMI.setFont(FontManager.buttonFont);
        clearMI.addActionListener(this);
        clearMI.setEnabled(false);
        popup.add(clearMI);
     
        // Initialize the selectAll and clear MenuItems' status.
        if ((text != null) && (!text.equals(""))) {
            selectMI.setEnabled(true);
            clearMI.setEnabled(true);
        }

        // Add the PopupMenu to the JTextField.
        addMouseListener(this);

        // Add CaretListeners to it.
        addCaretListener(this);

        // Add UndoableEditListener to it.
        getDocument().addUndoableEditListener(this);

        // Add DocumentListener to it and it need another implementation when this class be instanced. 
        getDocument().addDocumentListener(this);
    }

    private void updateUndoRedoState() {
        if (undo.canUndo()) undoMI.setEnabled(true);
        else undoMI.setEnabled(false);
        if (undo.canRedo()) redoMI.setEnabled(true);
        else redoMI.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(undoStr)) {
            try {
                undo.undo();
            } catch (CannotUndoException cuex) {
                cuex.printStackTrace();
            }
            updateUndoRedoState();
        } 
        else if (e.getActionCommand().equals(redoStr)) {
            try {
                undo.redo();
            } catch (CannotRedoException crex) {
                crex.printStackTrace();
            }
            updateUndoRedoState();
        } 
        else if (e.getActionCommand().equals(cutStr)) {
            cut();
        } 
        else if (e.getActionCommand().equals(copyStr)) {
            copy();
        } 
        else if (e.getActionCommand().equals(pasteStr)) {
            paste();
        } 
        else if (e.getActionCommand().equals(selectStr)) {
            selectAll();
        } 
        else if (e.getActionCommand().equals(clearStr)) {
            selectAll();
            cut();
        } 
    }

    //MouseListener
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }
 
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    //CaretListener
    public void caretUpdate(CaretEvent e) {
        int dot = e.getDot();
        int mark = e.getMark();
        if (dot == mark) { // no selection 
            cutMI.setEnabled(false);
            copyMI.setEnabled(false);
        } else { // some part selected
            cutMI.setEnabled(true);
            copyMI.setEnabled(true);
        }
    }

    //UndoableEditListener
    public void undoableEditHappened(UndoableEditEvent e) {
        //Remember the edit and update the menus.
        undo.addEdit(e.getEdit());
        updateUndoRedoState();       
    }

    //DocumentListener
    public void insertUpdate(DocumentEvent e) {
        String input = getText();
        if ((input != null) && (!input.equals(""))) {
            selectMI.setEnabled(true);
            clearMI.setEnabled(true);
        }
    }

    public void removeUpdate(DocumentEvent e) {
        String input = getText();
        if ((input == null) || ((input != null) && (input.equals("")))) {
            selectMI.setEnabled(false);
            clearMI.setEnabled(false);
        }
    }

    public void changedUpdate(DocumentEvent e) {/*This will not happen here*/}
}