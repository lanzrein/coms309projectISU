package com.johan.dev;

import android.preference.PreferenceFragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by johan on 10.09.2017.
 */

public class TcpClient {
    //this is my(johan)ip the one commented is RH VM

    public static final String SERVER_IP = "10.25.69.55";//"10.25.69.55";
    public static final int SERVER_PORT = 4444;

    private  String mServerMessage;
    private  OnMessageReceived mMessageListener = null;

    private boolean mRun = false;
    private PrintWriter mBufferOut;
    private BufferedReader mBufferIn;


    //sconstruct the class. the param listens to message received
    public TcpClient(OnMessageReceived listener){
        mMessageListener = listener;
    }

    //sends the msg entered by client to server
    public void sendMessage(String msg){
        final String toSend = msg;
        Thread thread = new Thread(new Runnable(){
            public void run(){
                try{
                    if(mBufferOut != null && !mBufferOut.checkError()){
                        Log.d("BufferOut", "sending message : " +toSend+"\n");
                        mBufferOut.println(toSend);
                        mBufferOut.flush();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //close connection and release all things
    public void stopClient(){
        sendMessage(Constants.CLOSED_CONNECTION + "user");
        mRun = false;
        if(mBufferOut!=null){
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferOut = null;
        mBufferIn = null;
        mServerMessage = null;
    }


    public void run(){
        mRun = true;

        try{
            //here must put the server IP address
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVER_PORT);
            try {
                mBufferOut = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                mBufferIn = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                //send login name
                sendMessage(Constants.LOGIN_NAME + PreferencesManager.getInstance().getUserName());
                //listening loop
                while (mRun) {
                    mServerMessage = mBufferIn.readLine();
                    if (mServerMessage != null && mMessageListener != null) {
                        mMessageListener.messageReceived(mServerMessage);
                    }
                }

                Log.e("RESPONSE FROM SERVER", "S: received msg : '" + mServerMessage + "'.");
            }catch(Exception e){
                    Log.e("TCP, S:Error",e.toString());


            }finally{
                socket.close();
            }
        }catch(Exception e){
            Log.d("TCP", "C: Error", e);
        }
    }

    //declare the interface
    public interface OnMessageReceived{
        public  void messageReceived(String msg);
    }
}
