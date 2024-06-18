package controller;

import javax.swing.JFrame;
import model.User;
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
    public static JoinRoomPassword joinRoomPassword;
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
//    public static JFrame getVisibleJFrame() {
//        if (roomListFrm != null && roomListFrm.isVisible())
//            return roomListFrm;
//        if (friendListFrm != null && friendListFrm.isVisible()) {
//            return friendListFrm;
//        }
//        if (createRoomPasswordFrm != null && createRoomPasswordFrm.isVisible()) {
//            return createRoomPasswordFrm;
//        }
//        if (joinRoomPasswordFrm != null && joinRoomPasswordFrm.isVisible()) {
//            return joinRoomPasswordFrm;
//        }
//        if (rankFrm != null && rankFrm.isVisible()) {
//            return rankFrm;
//        }
//        return homePageFrm;
//    }

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
//                case FRIEND_LIST:
//                    friendListFrm = new FriendListFrm();
//                    friendListFrm.setVisible(true);
//                    break;
//                case FIND_ROOM:
//                    findRoomFrm = new FindRoomFrm();
//                    findRoomFrm.setVisible(true);
//                    break;
//                case WAITING_ROOM:
//                    waitingRoomFrm = new WaitingRoomFrm();
//                    waitingRoomFrm.setVisible(true);
//                    break;
//
//                case CREATE_ROOM_PASSWORD:
//                    createRoomPasswordFrm = new CreateRoomPasswordFrm();
//                    createRoomPasswordFrm.setVisible(true);
//                    break;
//                case RANK:
//                    rankFrm = new RankFrm();
//                    rankFrm.setVisible(true);
//                    break;
//                case GAME_AI:
//                    gameAIFrm = new GameAIFrm();
//                    gameAIFrm.setVisible(true);
//                    break;
//                case ROOM_NAME_FRM:
//                    roomNameFrm = new RoomNameFrm();
//                    roomNameFrm.setVisible(true);
            }
        }
    }
    
    public static void openView(View viewName, int arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case JOIN_ROOM:
                    joinRoomPassword = new JoinRoomPassword(arg1, arg2);
                    joinRoomPassword.setVisible(true);
                    break;
//                case FRIEND_REQUEST:
//                    friendRequestFrm = new FriendRequestFrm(arg1, arg2);
//                    friendRequestFrm.setVisible(true);
            }
        }
    }

//    public static void openView(View viewName, User competitor, int room_ID, int isStart, String competitorIP) {
//        if (viewName == View.GAME_CLIENT) {
//            gameClientFrm = new GameClientFrm(competitor, room_ID, isStart, competitorIP);
//            gameClientFrm.setVisible(true);
//        }
//    }
//
//    public static void openView(View viewName, User user) {
//        if (viewName == View.COMPETITOR_INFO) {
//            competitorInfoFrm = new CompetitorInfoFrm(user);
//            competitorInfoFrm.setVisible(true);
//        }
//    }
    
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
//                case ROOM_LIST:
//                    roomListFrm.dispose();
//                    break;
//                case FRIEND_LIST:
//                    friendListFrm.stopAllThread();
//                    friendListFrm.dispose();
//                    break;
//                case FIND_ROOM:
//                    findRoomFrm.stopAllThread();
//                    findRoomFrm.dispose();
//                    break;
//                case WAITING_ROOM:
//                    waitingRoomFrm.dispose();
//                    break;
//                case GAME_CLIENT:
//                    gameClientFrm.stopAllThread();
//                    gameClientFrm.dispose();
//                    break;
//                case CREATE_ROOM_PASSWORD:
//                    createRoomPasswordFrm.dispose();
//                    break;
//                case JOIN_ROOM_PASSWORD:
//                    joinRoomPasswordFrm.dispose();
//                    break;
//                case COMPETITOR_INFO:
//                    competitorInfoFrm.dispose();
//                    break;
//                case RANK:
//                    rankFrm.dispose();
//                    break;
//                case GAME_NOTICE:
//                    gameNoticeFrm.dispose();
//                    break;
//                case FRIEND_REQUEST:
//                    friendRequestFrm.dispose();
//                    break;
//                case GAME_AI:
//                    gameAIFrm.dispose();
//                    break;
//                case ROOM_NAME_FRM:
//                    roomNameFrm.dispose();
//                    break;
            }

        }
    }
    
    public static void closeAllViews() {
        if (login!= null) login.dispose();
        if (register != null) register.dispose();
        if (mainMenu != null) mainMenu.dispose();
//        if (roomListFrm != null) roomListFrm.dispose();
//        if (friendListFrm != null) {
//            friendListFrm.stopAllThread();
//            friendListFrm.dispose();
//        }
//        if (findRoomFrm != null) {
//            findRoomFrm.stopAllThread();
//            findRoomFrm.dispose();
//        }
//        if (waitingRoomFrm != null) waitingRoomFrm.dispose();
//        if (gameClientFrm != null) {
//            gameClientFrm.stopAllThread();
//            gameClientFrm.dispose();
//        }
//        if (createRoomPasswordFrm != null) createRoomPasswordFrm.dispose();
//        if (joinRoomPasswordFrm != null) joinRoomPasswordFrm.dispose();
//        if (competitorInfoFrm != null) competitorInfoFrm.dispose();
//        if (rankFrm != null) rankFrm.dispose();
        if (message != null) message.dispose();
//        if (friendRequestFrm != null) friendRequestFrm.dispose();
//        if (gameAIFrm != null) gameAIFrm.dispose();
//        if (roomNameFrm != null) roomNameFrm.dispose();
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
        GAME_CLIENT,
        COMPETITOR_INFO,
        RANK,
        MESSAGE,
        FRIEND_REQUEST,
        GAME_AI,
        ROOM_NAME
    }
}
