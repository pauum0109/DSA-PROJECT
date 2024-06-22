/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Save some custom game
*/
package ui;

import java.io.Serializable;
import java.awt.Point;

public class SaveObject implements Serializable {
    public int     playPart   = -1;
    public int     handiNO    = -1;
    public boolean isSelected;
    public Point   playPoint  = null;

    public SaveObject(int playPart, int handiNO, boolean isSelected) {
        this.playPart   = playPart;
        this.handiNO    = handiNO;
        this.isSelected = isSelected;
    }

    public SaveObject(Point playPoint) {
        this.playPoint = playPoint;
    }
}