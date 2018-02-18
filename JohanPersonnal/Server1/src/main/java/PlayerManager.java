
import java.io.*;
import java.net.Socket;

/**
 * This class manages an instance of a client. (a player)
 *
 * Created by johan on 23.09.2017.
 */
public class PlayerManager extends Thread {
    //Contains info about the current user we have the same class in the
    //game folder.
    private Player player;
    //socket that links the player to our server so we know where to send it
    private Socket socket;
    private PrintWriter bufferSender;
    //used to stop a reading operation
    private boolean running;


    public PlayerManager(Socket s){
        this.player = null;
        //TODO when we find out how to handle JSON - create a player from here.
        socket = s;
        running = true;
    }

    public Player getPlayer(){
        return player;
    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run(){
        super.run();
        try{
            //sends a message to client
            bufferSender = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())
                    )
            );
            //read messaged from the client
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            //now we wait for a client to connect.
            while(running){
                String message = null;
                try{
                    message = in.readLine();
                    System.out.println(message);
                }catch(IOException e){
                    System.err.println("Error reading message" +e.getMessage());
                }
                //here we have to handle the message and see what kind of request it is.


            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void sendMessage(String message){
        if(bufferSender!= null && !bufferSender.checkError()){
            bufferSender.println(message);
            bufferSender.flush();
        }
    }

    public boolean hasCommand(String message){
        return false;
    }

}
