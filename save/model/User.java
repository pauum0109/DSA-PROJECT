package model;

/**
 *
 * @author alice
 */
public class User {

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the cntGame
     */
    public int getCntGame() {
        return cntGame;
    }

    /**
     * @param cntGame the cntGame to set
     */
    public void setCntGame(int cntGame) {
        this.cntGame = cntGame;
    }

    /**
     * @return the cntWin
     */
    public int getCntWin() {
        return cntWin;
    }

    /**
     * @param cntWin the cntWin to set
     */
    public void setCntWin(int cntWin) {
        this.cntWin = cntWin;
    }

    /**
     * @return the cntLose
     */
    public int getCntLose() {
        return cntLose;
    }

    /**
     * @param cntLose the cntLose to set
     */
    public void setCntLose(int cntLose) {
        this.cntLose = cntLose;
    }

    /**
     * @return the isOnline
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * @param online the online to set
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * @return the isPlaying
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * @param playing the playing to set
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }
    private int ID;
    private String username;
    private String password;
    private int cntGame;
    private int cntWin;
    private int cntLose;
    private boolean online;
    private boolean playing;
    private int rank;
    
    public User(int ID, String username, String password, String nickname, String avatar, int numberOfGame, int numberOfWin, int numberOfDraw) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.cntGame = numberOfGame;
        this.cntWin = numberOfWin;
    }
    
    public User(int ID, String username, String password, int cntGame, int cntWin, int cntLose, boolean online, boolean  playing, int rank){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.cntGame = cntGame;
        this.cntWin = cntWin;
        this.cntLose = cntLose;
        this.online = online;
        this.playing = playing;
        this.rank = rank;
    }
    
    public User(int ID, String username, boolean isOnline, boolean isPlaying) {
        this.ID = ID;
        this.username = username;
        this.online = isOnline;
        this.playing = isPlaying;
    }
        
    public User(int ID, boolean isOnline, boolean isPlaying){
        this.ID = ID;
        this.online = isOnline;
        this.playing = isPlaying;
    }
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
}
