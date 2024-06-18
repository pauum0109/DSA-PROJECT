package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import model.User;

/**
 *
 * @author alice
 */
public class ServerThread implements Runnable{

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * @param reader the reader to set
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * @return the writer
     */
    public BufferedWriter getWriter() {
        return writer;
    }

    /**
     * @param writer the writer to set
     */
    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    /**
     * @return the isClosed
     */
    public boolean isIsClosed() {
        return isClosed;
    }

    /**
     * @param isClosed the isClosed to set
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the SocketServer
     */
    public Socket getSocketServer() {
        return SocketServer;
    }

    /**
     * @return the clientNum
     */
    public int getClientNum() {
        return clientNum;
    }

    /**
     * @return the userConnected
     */
    public UserConnected getUserConnected() {
        return userConnected;
    }

    /**
     * @return the clientIP
     */
    public String getClientIP() {
        return clientIP;
    }
    private User user;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean isClosed;
    private Room room;
    
    private final Socket SocketServer;
    private final int clientNum;
    private final UserConnected userConnected;
    private final String clientIP;
    
    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.SocketServer = socketOfServer;
        this.clientNum = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        userConnected = new UserConnected();
        isClosed = false;
        room = null;
        String ipconfig = "192.168.0.102";

        if (this.SocketServer.getInetAddress().getHostAddress().equals(ipconfig)) {
            clientIP = ipconfig;
        } else {
            clientIP = this.SocketServer.getInetAddress().getHostAddress();
        }
    }


    public String getStringFromUser(User user1) {
        return user1.getID() + "," + user1.getUsername()
                + "," + user1.getPassword() + "," + user1.getCntGame()+ "," +
                user1.getCntWin() + "," + user1.getRank();
    }

    public void goToOwnRoom() throws IOException {
        write("go-to-room," + getRoom().getId() + "," + getRoom().getCompetitor(this.getClientNum()).getClientIP() + ",1," + getStringFromUser(getRoom().getCompetitor(this.getClientNum()).getUser()));
        getRoom().getCompetitor(this.clientNum).write("go-to-room," + getRoom().getId() + "," + this.getClientIP() + ",0," + getStringFromUser(getUser()));
    }

    public void goToPartnerRoom() throws IOException {
        write("go-to-room," + getRoom().getId() + "," + getRoom().getCompetitor(this.getClientNum()).getClientIP()+ ",0," + getStringFromUser(getRoom().getCompetitor(this.getClientNum()).getUser()));
        getRoom().getCompetitor(this.clientNum).write("go-to-room," + getRoom().getId() + "," + this.getClientIP() + ",1," + getStringFromUser(getUser()));
    }

    @Override
    public void run() {
        try {
            // Open thread -> Server
            reader = new BufferedReader(new InputStreamReader(SocketServer.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(SocketServer.getOutputStream()));
            System.out.println("Successfull, ID: " + clientNum);
            write("server-send-id" + "," + this.clientNum);
            String message;
            while (!isIsClosed()) {
                message = reader.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");
                
                //Verify
                if (messageSplit[0].equals("client-verify")) {
                    System.out.println(message);
                    User user1 = userConnected.verifyUser(new User(messageSplit[1], messageSplit[2]));
                    if (user1 == null)
                        write("wrong-user," + messageSplit[1] + "," + messageSplit[2]);
                    else if (!user1.isIsOnline()&& !userConnected.checkIsBanned(user1)) {
                        write("login-success," + getStringFromUser(user1));
                        this.setUser(user1);
                        userConnected.updateToOnline(this.getUser().getID());
                        Server.serverThreadBus.boardCast(clientNum, "chat-server," + user1.getUsername()+ " is online.");
                        Server.admin.addMessage("[" + user1.getID() + "] " + user1.getUsername()+ " is online.");
                    } else if (!userConnected.checkIsBanned(user1)) {
                        write("dupplicate-login," + messageSplit[1] + "," + messageSplit[2]);
                    } else {
                        write("banned-user," + messageSplit[1] + "," + messageSplit[2]);
                    }
                }
                
                //Register
                if (messageSplit[0].equals("register")) {
                    boolean checkdup = userConnected.checkDuplicated(messageSplit[1]);
                    if (checkdup) write("duplicate-username,");
                    else {
                        User userRegister;
                        userRegister = new User(messageSplit[1], messageSplit[2]);
                        userConnected.addUser(userRegister);
                        this.setUser(userConnected.verifyUser(userRegister));
                        userConnected.updateToOnline(this.getUser().getID());
                        Server.serverThreadBus.boardCast(clientNum, "chat-server," + this.getUser().getUsername()+ " is online.");
                        write("login-success," + getStringFromUser(this.getUser()));
                    }
                }
                
                //Offline
                if (messageSplit[0].equals("offline")) {
                    userConnected.updateToOffline(this.getUser().getID());
                    Server.admin.addMessage("[" + getUser().getID() + "] " + getUser().getUsername() + " was offline.");
                    Server.serverThreadBus.boardCast(clientNum, "chat-server," + this.getUser().getUsername()+ " was offline.");
                    this.setUser(null);
                }
                //List friend
                if (messageSplit[0].equals("view-friend-list")) {
                    List<User> friends = userConnected.getListFriend(this.getUser().getID());
                    StringBuilder res = new StringBuilder("return-friend-list,");
                    for (User friend : friends) {
                        res.append(friend.getID()).append(",").append(friend.getUsername()).append(",").append(friend.isIsOnline()? 1 : 0).append(",").append(friend.isIsPlaying()? 1 : 0).append(",");
                    }
                    System.out.println(res);
                    write(res.toString());
                }
                
                //Chat
                if (messageSplit[0].equals("chat-server")) {
                    Server.serverThreadBus.boardCast(clientNum, messageSplit[0] + "," + getUser().getUsername()+ " : " + messageSplit[1]);
                    Server.admin.addMessage("[" + getUser().getID() + "] " + getUser().getUsername()+ " : " + messageSplit[1]);
                }
                
                //Find room
                if (messageSplit[0].equals("go-to-room")) {
                    int roomName = Integer.parseInt(messageSplit[1]);
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (serverThread.getRoom() != null && serverThread.getRoom().getId() == roomName) {
                            isFinded = true;
                            if (serverThread.getRoom().getNumberOfUser() == 2) {
                                write("room-fully,");
                            } else {
                                if (serverThread.getRoom().getPassword() == null || serverThread.getRoom().getPassword().equals(messageSplit[2])) {
                                    this.setRoom(serverThread.getRoom());
                                    getRoom().setUser2(this);
                                    getRoom().increaseNumberOfGame();
                                    this.userConnected.updateToPlaying(this.getUser().getID());
                                    goToPartnerRoom();
                                } else {
                                    write("room-wrong-password,");
                                }
                            }
                            break;
                        }
                    }
                    if (!isFinded) {
                        write("room-not-found,");
                    }
                }
                
                //Get rank
                if (messageSplit[0].equals("get-rank-charts")) {
                    List<User> ranks = userConnected.getUserStaticRank();
                    StringBuilder res = new StringBuilder("return-get-rank-charts,");
                    for (User user : ranks) {
                        res.append(getStringFromUser(user)).append(",");
                    }
                    System.out.println(res);
                    write(res.toString());
                }
                
                //Create room
                if (messageSplit[0].equals("create-room")) {
                    room = new Room(this);
                    if (messageSplit.length == 2) {
                        getRoom().setPassword(messageSplit[2]);
                        write("your-created-room," + getRoom().getId() + "," + messageSplit[1]);
                        System.out.println("Create sucessfull, password is " + messageSplit[1]);
                    } 
                    else {
                        write("your-created-room," + getRoom().getId());
                        System.out.println("Create sucessfull");
                    }
                    userConnected.updateToPlaying(this.getUser().getID());
                }
                
                //View Room
                if (messageSplit[0].equals("view-room-list")) {
                    StringBuilder res = new StringBuilder("room-list,");
                    int number = 1;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (number > 8) break;
                        if (serverThread.getRoom() != null && serverThread.getRoom().getNumberOfUser() == 1) {
                            res.append(serverThread.getRoom().getId()).append(",").append(serverThread.getRoom().getPassword()).append(",");
                        }
                        number++;
                    }
                    write(res.toString());
                    System.out.println(res);
                }
                
                //Friend
                if (messageSplit[0].equals("check-friend")) {
                    String res = "check-friend-response,";
                    res += (userConnected.checkIsFriend(this.getUser().getID(), Integer.parseInt(messageSplit[1])) ? 1 : 0);
                    write(res);
                }
                
                //Quick room
                if (messageSplit[0].equals("quick-room")) {
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (serverThread.getRoom() != null && serverThread.getRoom().getNumberOfUser() == 1 && serverThread.getRoom().getPassword().equals(" ")) {
                            serverThread.getRoom().setUser2(this);
                            this.setRoom(serverThread.getRoom());
                            getRoom().increaseNumberOfGame();
                            System.out.println("Đã vào phòng " + getRoom().getId());
                            goToPartnerRoom();
                            userConnected.updateToPlaying(this.getUser().getID());
                            isFinded = true;
                            //Invite into rooms
                            break;
                        }
                    }

                    if (!isFinded) {
                        this.setRoom(new Room(this));
                        userConnected.updateToPlaying(this.getUser().getID());
                        System.out.println("Không tìm thấy phòng, tạo phòng mới");
                    }
                }
                
                //Cancel room
                if (messageSplit[0].equals("cancel-room")) {
                    userConnected.updateToNotPlaying(this.getUser().getID());
                    System.out.println("Đã hủy phòng");
                    this.setRoom(null);
                }
                
                //Invite the other player into room
                if (messageSplit[0].equals("join-room")) {
                    int ID_room = Integer.parseInt(messageSplit[1]);
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (serverThread.getRoom() != null && serverThread.getRoom().getId() == ID_room) {
                            serverThread.getRoom().setUser2(this);
                            this.setRoom(serverThread.getRoom());
                            System.out.println("Đã vào phòng " + getRoom().getId());
                            getRoom().increaseNumberOfGame();
                            goToPartnerRoom();
                            userConnected.updateToPlaying(this.getUser().getID());
                            break;
                        }
                    }
                }
                
                //Request friend
                if (messageSplit[0].equals("make-friend")) {
                    Server.serverThreadBus.getServerThreadByUserID(Integer.parseInt(messageSplit[1]))
                            .write("make-friend-request," + this.getUser().getID() + "," + userConnected.getNickNameByID(this.getUser().getID()));
                }
                
                //Confrim make friend
                if (messageSplit[0].equals("make-friend-confirm")) {
                    userConnected.makeFriend(this.getUser().getID(), Integer.parseInt(messageSplit[1]));
                    System.out.println("Kết bạn thành công");
                }
                
                //Request play
                if (messageSplit[0].equals("duel-request")) {
                    Server.serverThreadBus.sendMessageToUserID(Integer.parseInt(messageSplit[1]),
                            "duel-notice," + this.getUser().getID() + "," + this.getUser().getUsername());
                }
                
                //Confirm play
                if (messageSplit[0].equals("agree-duel")) {
                    this.setRoom(new Room(this));
                    int ID_User2 = Integer.parseInt(messageSplit[1]);
                    ServerThread user2 = Server.serverThreadBus.getServerThreadByUserID(ID_User2);
                    getRoom().setUser2(user2);
                    user2.setRoom(getRoom());
                    getRoom().increaseNumberOfGame();
                    goToOwnRoom();
                    userConnected.updateToPlaying(this.getUser().getID());
                }
                
                //Cancel play
                if (messageSplit[0].equals("disagree-duel")) {
                    Server.serverThreadBus.sendMessageToUserID(Integer.parseInt(messageSplit[1]), message);
                }
                
                //Play 
                if (messageSplit[0].equals("caro")) {
                    getRoom().getCompetitor(clientNum).write(message);
                }
                if (messageSplit[0].equals("chat")) {
                    getRoom().getCompetitor(clientNum).write(message);
                }
                if (messageSplit[0].equals("win")) {
                    userConnected.addWinGame(this.getUser().getID());
                    getRoom().increaseNumberOfGame();
                    getRoom().getCompetitor(clientNum).write("caro," + messageSplit[1] + "," + messageSplit[2]);
                    getRoom().boardCast("new-game,");
                }
                if (messageSplit[0].equals("lose")) {
                    userConnected.addWinGame(getRoom().getCompetitor(clientNum).user.getID());
                    getRoom().increaseNumberOfGame();
                    getRoom().getCompetitor(clientNum).write("competitor-time-out");
                    write("new-game,");
                }
                if (messageSplit[0].equals("draw-request")) {
                    getRoom().getCompetitor(clientNum).write(message);
                }
                if (messageSplit[0].equals("draw-confirm")) {
                    getRoom().increaseNumberOfGame();
                    getRoom().increaseNumberOfGame();
                    getRoom().boardCast("draw-game,");
                }
                if (messageSplit[0].equals("draw-refuse")) {
                    getRoom().getCompetitor(clientNum).write("draw-refuse,");
                }
                if (messageSplit[0].equals("voice-message")) {
                    getRoom().getCompetitor(clientNum).write(message);
                }
                if (messageSplit[0].equals("left-room")) {
                    if (getRoom() != null) {
                        getRoom().setUsersToNotPlaying();
                        getRoom().decreaseNumberOfGame();
                        getRoom().getCompetitor(clientNum).write("left-room,");
                        room.getCompetitor(clientNum).room = null;
                        this.setRoom(null);
                    }
                }
            }
        } catch (IOException e) {
            //Change
            setIsClosed(true);
            //Update state of user
            if (this.getUser() != null) {
                userConnected.updateToOffline(this.getUser().getID());
                userConnected.updateToNotPlaying(this.getUser().getID());
                Server.serverThreadBus.boardCast(clientNum, "chat-server," + this.getUser().getUsername() + " was offline.");
                Server.admin.addMessage("[" + getUser().getID() + "] " + getUser().getUsername()+ " was offline.");
            }

            //Remove thread
            Server.serverThreadBus.remove(clientNum);
            System.out.println(this.clientNum + " was exit.");
            if (getRoom() != null) {
                try {
                    if (getRoom().getCompetitor(clientNum) != null) {
                        getRoom().decreaseNumberOfGame();
                        getRoom().getCompetitor(clientNum).write("left-room,");
                        room.getCompetitor(clientNum).room = null;
                    }
                    this.setRoom(null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    public void write(String message) throws IOException {
        getWriter().write(message);
        getWriter().newLine();
        getWriter().flush();
    }

}
