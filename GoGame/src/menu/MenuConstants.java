package menu;

public interface MenuConstants {
    /**
     *  All Menu Item Strings
     */
    public static final String NEWGAMESTR = "New";
    public static final String COMPUTERPLAYSTR = "Computer Play";
    public static final String CONTINUESTR = "Open(Continue)";
    public static final String SAVESTR = "Save";
    public static final String SAVEASSTR = "Save As...";
    public static final String EXITSTR = "Exit";
    public static final String BACKGOSTR = "Undo(Back)";
    public static final String COUNTSTR = "Count Points Alive";
    public static final String REFRESHSTR = "Refresh";
    public static final String CONNECTSTR = "Connect";
    public static final String INFORMSTR = "Notify My Friend";
    public static final String INFORMTYPESTR = "How To Notify Me";
    public static final String ENDINFORMSTR = "I Am Here";
    public static final String EDITWORDSSTR = "Edit Useful Words";
    public static final String SEPARATORSTR = "--";

    /**
     *  Menus and MenuItems
     */
    public static final String[]   goMenuStrs = {"Game", "Operation", "Server", "Tool"};

    public static final String[]   gameMIStrs = {NEWGAMESTR, COMPUTERPLAYSTR, "--", CONTINUESTR, "--", SAVESTR, SAVEASSTR, "--", EXITSTR};
    public static final String[]   editMIStrs = {BACKGOSTR, "--", COUNTSTR, "--", REFRESHSTR};
    public static final String[]   servMIStrs = {CONNECTSTR};
    public static final String[]   toolMIStrs = {INFORMSTR, INFORMTYPESTR, ENDINFORMSTR, "--", EDITWORDSSTR};
    public static final String[][] goMIStrs   = {gameMIStrs, editMIStrs, servMIStrs, toolMIStrs};

    /**
     *  Mnemonic Description
     */
    static final String[]   goMenuMne = {"G", "O", "S", "T", "H"};

    static final String[]   gameMIMne = {"N", "P", "", "O", "", "S", "A", "", "E"};
    static final String[]   editMIMne = {"B", "", "C", "", "R"};
    static final String[]   servMIMne = {"C"};
    static final String[]   toolMIMne = {"N", "H", "I", "", "E"};
    static final String[][] goMIMne   = {gameMIMne, editMIMne, servMIMne, toolMIMne};

    /**
     *  Accelerator Description
     */
    static final String[]   gameMIAcc = {"N", "P", "", "O", "", "S", "A", "", ""};
    static final String[]   editMIAcc = {"B", "", "", "", "R"};
    static final String[]   servMIAcc = {""};
    static final String[]   toolMIAcc = {"", "", "", "", ""};
    static final String[][] goMIAcc   = {gameMIAcc, editMIAcc, servMIAcc, toolMIAcc};

    /**
     *  Menu Items' Initial Status : -1 separator; 0 false; 1 true.
     */
    public static final int[]   game  = {0, 1, -1, 0, -1, 0, 0, -1, 1};

    public static final int[]   edit  = {0, -1, 0, -1, 1};
  
    public static final int[]   serv  = {1};

    public static final int[]   tool  = {0, 1, 0, -1, 1};
    
    public static final int[][] goMenu = {game, edit, serv, tool};
}