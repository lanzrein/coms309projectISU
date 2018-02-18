import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;


/**
 * Created by johan on 25.09.2017.
 */
public class PlayerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t){
        try{
            //first we need to read the request.
            String method = t.getRequestMethod();
            if(method.equals(Method.POST.toString())){
                //we had a post request.
                StringBuilder sb = new StringBuilder();
                //we need to read the body of the request. use a try-with-ressource to make it easier ( takes care of closing the streams)
                try(BufferedReader inReader = new BufferedReader(new InputStreamReader(t.getRequestBody()))){
                    String line;
                    while((line = inReader.readLine()) != null){
                        sb.append(line);
                    }
                }
                String request = sb.toString();
                System.out.println(request);
                Gson gson = new Gson();
                RequestPlayer reqPlayer = gson.fromJson(request,RequestPlayer.class);//TODO not working in case of different request. might look into smthing else.
                //TODO MODULARISER
                System.out.println("Got request with ID : "+reqPlayer.getId()+"username is : "+reqPlayer.getUsername()+" and password is : "+reqPlayer.getPassword());

                if(reqPlayer == null){
                    try{
                        sendErrorCode(404,t);
                        return ;
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
                //we have our request from the player.
                boolean check = handleRequest(reqPlayer,t);
                //if it was okay we proceed else we send an error message back
                //to our client
                if(!check){
                    try{
                        sendErrorCode(400, t);//400 = = bad request;
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }


            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * We send an error code to our client
     * @param errCode the error code
     * @param t the client
     * @throws IOException
     */
    private void sendErrorCode(int errCode, HttpExchange t) throws IOException{
        t.sendResponseHeaders(errCode,0);

    }

    /**
     * We switch on the reqquest id and see how to satisfy it
     *
     * @param reqPlayer th e requestPlayer
     * @param t the client
     * @return true if the request was satisfied. false else.
     */
    private boolean handleRequest(RequestPlayer reqPlayer, HttpExchange t) {
        switch(reqPlayer.getId()){
            case RequestID.LOGIN:
                return login(reqPlayer,t);

            case RequestID.REGISTER:
                return register(reqPlayer,t);

            case RequestID.CHANGEMESSAGE:
                return changeMessage(reqPlayer,t);
            default:
                return false;
        }
    }

    private boolean changeMessage(RequestPlayer reqPlayer, HttpExchange t) {
        //update in the database. TODO !!
        DatabaseHandler dbHandler = DatabaseHandler.getDbHandler();
        Player p = dbHandler.updatePlayer(null/*here player*/,/*here will insert msg*/"message");
        if(p==null){
            return false;

        }
        boolean sent = sendBackToClient(t,p.getMessage());
        return sent;


    }

    /**
     * Try to register the request of the player by creating a new player.
     * @param reqPlayer the request of the player.
     * @param client the client to who to send back request.
     * @return true if the player was successfully registered AND the answer was sent back to client. TODO maybe not good...
     */
    private boolean register(RequestPlayer reqPlayer, HttpExchange client) {
        //we try to insert player in DB
        DatabaseHandler dbHandler = DatabaseHandler.getDbHandler();
        Player p = dbHandler.insertPlayer(reqPlayer.getUsername(),reqPlayer.getPassword());
        if(p==null){
            return false;
        }
        //Transform our player into a nice
        //JSON object.
        Gson gson = new Gson();
        String json = gson.toJson(p);
        boolean sent = sendBackToClient(client,json);
        return sent;
    }

    /**
     * Try to login the player request.
     * @param reqPlayer the player request.
     * @param client the client
     * @return true if the playe was logged in.
     */
    private boolean login(RequestPlayer reqPlayer,HttpExchange client) {
        //go into database and check for player if he exists.
        DatabaseHandler dbHandler = DatabaseHandler.getDbHandler();
        Player p = dbHandler.extractPlayer(reqPlayer.getUsername(),reqPlayer.getPassword(),reqPlayer.getPlayerID());
        if(p==null){
            return false;
        }
        //now we try to send info back.
        //Transform our player into a nice
        //JSON object.
        Gson gson = new Gson();
        String json = gson.toJson(p);
        boolean sent = sendBackToClient(client, json);
        return sent;
    }

    /**
     *
     * This sends to the client some player infos
     * @param client
     * @param json a json done string.
     * @return
     */
    private boolean sendBackToClient(HttpExchange client, String json) {

        //Start sending
        try {
            //in http 200 means OK
            client.sendResponseHeaders(200,json.length());
            OutputStream os = client.getResponseBody();
            os.write(json.getBytes());
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }


    }
}
