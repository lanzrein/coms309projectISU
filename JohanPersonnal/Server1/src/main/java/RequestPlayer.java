/**
 * Created by johan on 25.09.2017.
 */
public class RequestPlayer {
    private String username;
    private int reqid;
    private String password;
    private int playerID;

    public RequestPlayer(int id, String username, String password,int playerID){
        this.reqid = id;
        this.username = username;
        this.password = password;
        this.playerID = playerID;
    };

    public int getId(){
        return reqid;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getPlayerID(){return playerID;}
}
