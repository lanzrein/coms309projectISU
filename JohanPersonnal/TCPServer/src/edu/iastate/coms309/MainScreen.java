package edu.iastate.coms309;

import java.util.Scanner;

//not really usefull but just to see what happens.
// in real project we will not have a visual interface
//just to refresh also a little bit javafx

public class MainScreen {

    private TcpServer mServer;
    public static final String SERVER_NAME = "Server";

    public MainScreen() {

        String msg;
        mServer = new TcpServer(new TcpServer.OnMessageReceived() {
            @Override
            //this method declared in the interface from TCPServer class is implemented here
            //this method is called for every msg
            public void messageReceived(String message) {
                System.out.println("\n " + message);
            }
        });
        mServer.start();


        while(true){
            Scanner scanner = new Scanner(System.in);
            msg = scanner.nextLine();
            if(msg.equals("exit")){
                close();
            }else{
                if (mServer != null && !msg.equals("")) {
                    // send the message to the client
                    mServer.sendMessage(new User(SERVER_NAME, msg));
                }
                // clear text its like flushing a buffer
                msg = "";
            }

        }

    }

    public void close(){
        if (mServer != null) {
            mServer.close();
        }

    }


}