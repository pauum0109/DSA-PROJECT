/* Name: Your Team Number // N/A
Member names & IU code: Nguyen Thi Anh Tho - ITCSIU21236
Purpose: Simplifies the process of filtering files based on their extensions,
supporting a set of common audio file formats.
*/
package ui;

import java.io.File;

public class FilterUtils {
    public final static String   go = "go";
    public final static String[] audio = {"wav", "aif", "rmf", "au", "mid"};

    // Get the extension of a file.
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}