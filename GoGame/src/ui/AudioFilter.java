/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Manage select audio files for user
*/
package ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class AudioFilter extends FileFilter {
    // Accept all directories and all go files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = FilterUtils.getExtension(f);
	if (extension != null) {
            for (int i = 0; i < FilterUtils.audio.length; i++) {
                if (extension.equals(FilterUtils.audio[i])) {
                    return true;
                } 
            }
    	}
        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return "Music or Sound Files";
    }
}