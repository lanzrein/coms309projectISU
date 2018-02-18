
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * This class handles most of the accesses to the database
 *
 * Created by johan on 15.09.2017.
 */

public class DatabaseHandler{
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309vcb4";


    //credentials
    private static final String USERNAME = "dbu309vcb4";
    private static final String PASSWORD = "qCaVEG0Z";
    private static Connection conn = null;
    private static Statement stmt = null;

    private static DatabaseHandler dbHandler;



    /**
     * The private constructor for our database
     */
    private DatabaseHandler(){
        try{
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * We use the singleton pattern so we have only one connection
     * @return the database handler
     */
    public static DatabaseHandler getDbHandler(){
        if(dbHandler == null){
            dbHandler = new DatabaseHandler();
        }
        return dbHandler;
    }

    /**
     * Will probe the database to see if a player exists that has the username and password
     * @param username the username
     * @param password the password
     * @return a player if he exists. null otherwise.
     */
    public Player extractPlayer(String username, String password, int playerID){
        String sql = "SELECT PlayerID,username,message from Player p where p.username = \""+username +"\" and"
                +" p.password = \""+password+"\";";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.wasNull())return null;
            while(rs.next()){
                Integer id = rs.getInt("PlayerID");
                String message = rs.getString("message");
                return new Player(id,username,message);
            }
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return null;
    }

    public Player insertPlayer(String username, String password){
            //to get the ID we get the sizeof the database. and +1 it
        String sql = "Select * from Player";
        int id = 0;
        try{
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                id++;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
            id++;
        /*
        PlayerID int not null primary key,
username char(10),
password char(12),
experience float,
level int,
numberWins int,
numberLoss int,
numberReports int,
message char(255),
friends int(255)
         */
        sql = "insert into Player(PlayerID,username,password,experience,numberWins,numberLoss,"
                +"numberReports,message,friends) values ("+id+",\"" +
                username+"\",\""+password+"\",0,0,0,0,\"\",0);";//this string looks terrible
        System.out.println(sql);
        try {
            stmt.execute(sql);
            return extractPlayer(username,password,id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }


    public Player updatePlayer(Player player, String message) {
        String sql = "update Player set message = \""+player.getMessage()+"\" where p.username= \""+
                player.getUsername()+"\" and p.id = "+player.getId();
        try{
            stmt.execute(sql);
            //return extractPlayer(player.getUsername(),"password..:");//not working yet TODO
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
