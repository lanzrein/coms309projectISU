package johan.istate.edu.loginviaphp;

import java.util.List;


/**
 *
 * This class describes a player in the login sense.
 * All the things here are his personal preferences.
 * TODO Add a way to have an image.
 * Created by johan on 15.09.2017.
 *     //TODO we will need a second class that describes the player as a statistic.

 */

public final class Player {
    private final int playerID;
    private final String username;
    private float experience;
    private String message;
    private List<Integer> friendsID;//TODO find a way to retrieve friend list

    /**
     * Creates a new player. the parameters are implicit.
     *
     * @param playerID
     * @param username
     * @param message
     */
    public Player(int playerID, String username,
                  String message){
        this.playerID = playerID;
        this.username = username;
        this.message = message;

    }

    /**
     * This creates a new player. Should only be done when setting up an account.
     * @param username the username of the player
     * @return the new @Player created.
     */
    public Player createNew(String username){
        //TODO Check if the playerID is in db
        int playerID =-1;
        //TODO add security to password.
        return new Player(playerID, username,"");

    }


    public static Player getPlayer(String username, String pwd){
        return null;//TODO


    }




    public boolean updateWinLoss(/*DEFINE A GAME AND ITS OUTCOME*/){
        //TODO if the player wins ++ numberWins , if loss ++ numberLoss

        return false;
    }



    public boolean exists(Player player){
        //TODO pull ID from database.
        return true;
    }

//    /**
//     * Same idea as login.
//     * @param appContext
//     * @param string
//     */
//    public void editMessage(final Context appContext, String string){
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(appContext, obj.getString("message"),
//                                        Toast.LENGTH_SHORT).show();
//
//                                JSONObject messageJson = obj.getJSONObject("message");
//
//                                Toast.makeText(appContext, "Successfully changed message to : "+messageJson.getString("message"),
//                                        Toast.LENGTH_SHORT).show()
//                                ;
//
//                                //go back to activity
//                                return;
//
//                            } else {
//                                Toast.makeText(appContext, obj.getString("message"),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(appContext, error.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("REQID", Integer.toString(RequestID.CHANGEMESSAGE.ordinal()));
//                params.put("PlayerID", Integer.toString(playerID));
//                params.put("message", message);
//                return params;
//            }
//        };
//
//        //now we send
//        VolleySingleton.getInstance(appContext).addToRequestQueue(stringRequest);
//    }
//





    @Override
    public String toString(){
        return username + ". ID : " + playerID;
    }


    public int getId() {
        return playerID;
    }

    public String getUsername(){
        return username;
    }


    public int getLevel() {
        return (int)Math.floor(Math.log(experience));//TODO Check if team ok with algorithm
    }

    public String getMessage() {
        return message;
    }

}
