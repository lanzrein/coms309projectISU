package edu.iastate.coms309;

/**
 * Created by johan on 10.09.2017.
 */
public class User {

    private String username;
    private String msg;
    private User userToSendMsg;
    private int userID;

    public User(String username, String msg){
        this.username = username;
        this.msg = msg;
    }
    public User(){};

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public User getUserToSendMessage() {
        return userToSendMsg;
    }

    public void setUserToSendMessage(User userToSendMessage) {
        this.userToSendMsg = userToSendMessage;
    }


}
