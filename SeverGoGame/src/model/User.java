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
     * @return the isOnline
     */
    public boolean isIsOnline() {
        return isOnline;
    }

    /**
     * @param isOnline the isOnline to set
     */
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * @return the isPlaying
     */
    public boolean isIsPlaying() {
        return isPlaying;
    }

    /**
     * @param isPlaying the isPlaying to set
     */
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
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
    private boolean isOnline;
    private boolean isPlaying;
    private int rank;
    
    public User(){
        
    }
    
    public User(int ID, String username, String password, int cntGame, int cntWin, boolean isOnline, boolean  isPlaying, int rank){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.cntGame = cntGame;
        this.cntWin = cntWin;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
        this.rank = rank;
    }
    
    public User(int ID, String username, boolean isOnline, boolean isPlaying) {
        this.ID = ID;
        this.username = username;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
    }
        
    public User(int ID, boolean isOnline, boolean isPlaying){
        this.ID = ID;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
    }
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
}
