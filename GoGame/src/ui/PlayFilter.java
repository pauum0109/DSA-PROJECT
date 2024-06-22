/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Handle to play file
*/
package ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PlayFilter extends FileFilter {
    // Accept all directories and all go files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = FilterUtils.getExtension(f);
	if (extension != null) {
            if (extension.equals(FilterUtils.go)) {
                return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return "Go Game Files";
    }
}