package johan.istate.edu.loginviaphp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import johan.istate.edu.loginviaphp.LoginActivity;
import johan.istate.edu.loginviaphp.Player;

/**
 * This class stores the info about the login session
 * This allows the app to have a quick way to find out info about the player.
 * Created by johan on 21.09.2017.
 */

public class SharedPrefManager {
    //Those are the keys used for the shared preferences
    private static final String SHARED_PREF_NAME ="gamesharedpref";
    private static final String KEY_USERNAME ="keyusername";
    private static final String KEY_ID ="keyid";
    private static final String KEY_MESSAGE="keymessage";

    //TODO add more constants wins loss lvl message IF NEEDED LATER

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    //Constructor

    private SharedPrefManager(Context context){
        mCtx = context;
    }
    //This allows to have only a single instance of SharedPrefManager during the whole app - singleton pattern

    /**
     * Returns the SharedPrefManager for the app
     * @param context current context
     * @return SharedprefManager
     */
    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let user login
    //will store data in the shared preferences

    /**
     * Login a player. Meaning we put the preferences in the Shared Pref.
     * @param player player to login
     */
    public void userLogin(Player player){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID,player.getId());
        editor.putString(KEY_USERNAME,player.getUsername());
        editor.putString(KEY_MESSAGE,player.getMessage());


        //TODO when adding key add them here too
    }

    /**
     * check if player is logged in
     * @return true if logged in
     */
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null) != null;
    }


    /**
     * Getter for the current instance of the player
     * @return The player of the app
     */
    public Player getLocalPlayer(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        Player p = new Player(sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString(KEY_MESSAGE,null));
        return p;
    }


    //this logs out the player

    /**
     * Log out the player.
     * This cleans the SharedPreferences editor.
     */
    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
