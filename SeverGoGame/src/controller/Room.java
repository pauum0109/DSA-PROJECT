package controller;

import java.io.IOException;

/**
 *
 * @author alice
 */
public class Room {

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param user1 the user1 to set
     */
    public void setUser1(ServerThread user1) {
        this.user1 = user1;
    }

    /**
     * @param user2 the user2 to set
     */
    public void setUser2(ServerThread user2) {
        this.user2 = user2;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the user1
     */
    public ServerThread getUser1() {
        return user1;
    }

    /**
     * @return the user2
     */
    public ServerThread getUser2() {
        return user2;
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
   
    private int id;
    private ServerThread user1;
    private ServerThread user2;
    private UserConnected userConnected;
    private String password;
    
    public Room(ServerThread user1){
        System.out.println("Created room ID = : " + Server.ROOM_ID);
        this.id = Server.ROOM_ID++;
        this.password = " ";
        userConnected = new UserConnected();
        this.user1 = user1;
        this.user2 = null;
    }
    
    public void boardCast(String message){
        try{
            user1.write(message);
            user2.write(message);
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }
    
    public int getNumberOfUser() {
        return user2 == null ? 1 : 2;
    }
    
    public int getCompetitorID(int ID_ClientNumber) {
        if (user1.getClientNum() == ID_ClientNumber)
            return user2.getUser().getID();
        return user1.getUser().getID();
    }

    public ServerThread getCompetitor(int ID_ClientNumber) {
        if (user1.getClientNum() == ID_ClientNumber)
            return user2;
        return user1;
    }

    public void setUsersToPlaying() {
        userConnected.updateToPlaying(user1.getUser().getID());
        if (user2 != null) {
            userConnected.updateToPlaying(user2.getUser().getID());
        }
    }

    public void setUsersToNotPlaying() {
        userConnected.updateToNotPlaying(user1.getUser().getID());
        if (user2 != null) {
            userConnected.updateToNotPlaying(user2.getUser().getID());
        }
    }


    public void increaseNumberOfGame() {
        userConnected.addGame(user1.getUser().getID());
        userConnected.addGame(user2.getUser().getID());
    }

    public void decreaseNumberOfGame() {
        userConnected.decreaseGame(user1.getUser().getID());
        userConnected.decreaseGame(user2.getUser().getID());
    }
}
