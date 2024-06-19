package controller;

import javax.swing.JFrame;
import model.User;
import model.core.Goban;
import ui.*;

/**
 *
 * @author alice
 */
public class Play {
    public static Login login;
    public static Register register;
    public static User user;
    public static MainMenu mainMenu;
    public static WaitingRoomStatus waitingRoomStatus;
    public static RoomList roomList;
    public static CreateRoom createRoom;
    public static JoinRoom joinRoom;
    public static JoinRoomPassword joinRoomPassword;
    public static CompetitorInfo competitorInfo;
    public static FriendList friendList;
    public static FriendRequest friendRequest;
    public static Rank rank;
    public static ComputerPlayer computerPlayer;
    public static MutiPlayer mutiPlayer;
    public static AutoMatchStatus autoMatchStatus;
    public static Message  message;
    public static SocketHandle socketHandle;
    
    public static void main(String[] args){
        new Play().init();
    }
    
    public void init(){
        login = new Login();
        login.run();
        socketHandle = new SocketHandle();
        socketHandle.run();
    }
    
    public static JFrame getVisibleJFrame() {
        if (roomList != null && roomList.isVisible())
            return roomList;
        if (friendList != null && friendList.isVisible()) {
            return friendList;
        }
        if (createRoom != null && createRoom.isVisible()) {
            return createRoom;
        }
        if (joinRoomPassword != null && joinRoomPassword.isVisible()) {
            return joinRoomPassword;
        }
        if (rank != null && rank.isVisible()) {
            return rank;
        }
        return mainMenu;
    }

    public static void openView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case LOGIN:
                    login = new Login();
                    login.setVisible(true);
                    break;
                case REGISTER:
                    register = new Register();
                    register.setVisible(true);
                    break;
                case MAIN_MENU:
                    mainMenu = new MainMenu();
                    mainMenu.setVisible(true);
                    break;
                case ROOM_LIST:
                    roomList = new RoomList();
                    roomList.setVisible(true);
                    break;
                case FRIEND_LIST:
                    friendList = new FriendList();
                    friendList.setVisible(true);
                    break;
                case FIND_ROOM:
                    autoMatchStatus = new AutoMatchStatus();
                    autoMatchStatus.setVisible(true);
                    break;
                case WAITING_ROOM:
                    waitingRoomStatus = new WaitingRoomStatus();
                    waitingRoomStatus.setVisible(true);
                    break;

                case CREATE_ROOM:
                    createRoom = new CreateRoom();
                    createRoom.setVisible(true);
                    break;
                case RANK:
                    rank = new Rank();
                    rank.setVisible(true);
                    break;
                case GAME_AI:
                    computerPlayer = new ComputerPlayer();
                    computerPlayer.setVisible(true);
                    break;
                case JOIN_ROOM:
                    joinRoom = new JoinRoom();
                    joinRoom.setVisible(true);
            }
        }
    }
    
    public static void openView(View viewName, int arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case JOIN_ROOM_PASSWORD:
                    joinRoomPassword = new JoinRoomPassword(arg1, arg2);
                    joinRoomPassword.setVisible(true);
                    break;
                case FRIEND_REQUEST:
                    friendRequest = new FriendRequest(arg1, arg2);
                    friendRequest.setVisible(true);
            }
        }
    }

    public static void openView(View viewName, User competitor, int room_ID, int isStart, String competitorIP) {
        if (viewName == View.GAME_PLAYER) {
            mutiPlayer = new MutiPlayer(competitor, room_ID, isStart, competitorIP);
            mutiPlayer.setVisible(true);
        }
    }

    public static void openView(View viewName, User user) {
        if (viewName == View.COMPETITOR_INFO) {
            competitorInfo = new CompetitorInfo(user);
            competitorInfo.setVisible(true);
        }
    }
    
    public static void openView(View viewName, String arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case MESSAGE:
                    message = new Message(arg1, arg2);
                    message.setVisible(true);
                    break;
                case LOGIN:
                    login = new Login(arg1, arg2);
                    login.setVisible(true);
            }
        }
    }
    
    public static void closeView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case LOGIN:
                    login.dispose();
                    break;
                case REGISTER:
                    register.dispose();
                    break;
                case MAIN_MENU:
                    mainMenu.dispose();
                    break;
                case ROOM_LIST:
                    roomList.dispose();
                    break;
                case FRIEND_LIST:
                    friendList.stopAllThread();
                    friendList.dispose();
                    break;
                case FIND_ROOM:
                    autoMatchStatus.stopAllThread();
                    autoMatchStatus.dispose();
                    break;
                case WAITING_ROOM:
                    waitingRoomStatus.dispose();
                    break;
                case GAME_PLAYER:
                    mutiPlayer.stopAllThread();
                    mutiPlayer.dispose();
                    break;
                case CREATE_ROOM:
                    createRoom.dispose();
                    break;
                case JOIN_ROOM_PASSWORD:
                    joinRoomPassword.dispose();
                    break;
                case COMPETITOR_INFO:
                    competitorInfo.dispose();
                    break;
                case RANK:
                    rank.dispose();
                    break;
                case MESSAGE:
                    message.dispose();
                    break;
                case FRIEND_REQUEST:
                    friendRequest.dispose();
                    break;
                case GAME_AI:
                    computerPlayer.dispose();
                    break;
                case JOIN_ROOM:
                    joinRoom.dispose();
                    break;
            }

        }
    }
    
    public static void closeAllViews() {
        if (login!= null) login.dispose();
        if (register != null) register.dispose();
        if (mainMenu != null) mainMenu.dispose();
        if (roomList != null) roomList.dispose();
        if (friendList != null) {
            friendList.stopAllThread();
            friendList.dispose();
        }
        if (autoMatchStatus != null) {
            autoMatchStatus.stopAllThread();
            autoMatchStatus.dispose();
        }
        if (waitingRoomStatus != null) waitingRoomStatus.dispose();
        if (mutiPlayer != null) {
            mutiPlayer.stopAllThread();
            mutiPlayer.dispose();
        }
        if (createRoom != null) createRoom.dispose();
        if (joinRoomPassword != null) joinRoomPassword.dispose();
        if (competitorInfo != null) competitorInfo.dispose();
        if (rank != null) rank.dispose();
        if (message != null) message.dispose();
        if (friendRequest != null) friendRequest.dispose();
        if (computerPlayer != null) computerPlayer.dispose();
        if (joinRoom != null) joinRoom.dispose();
    }
    
    public enum View{
        LOGIN,
        REGISTER,
        MAIN_MENU,
        ROOM_LIST,
        FIND_ROOM,
        WAITING_ROOM,
        CREATE_ROOM,
        JOIN_ROOM,
        FRIEND_LIST,
        GAME_PLAYER,
        COMPETITOR_INFO,
        RANK,
        MESSAGE,
        FRIEND_REQUEST,
        GAME_AI,
        JOIN_ROOM_PASSWORD
    }
}
