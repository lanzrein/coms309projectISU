package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.Lobby;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.LobbyServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This activity lets the user select their desired gamemode. After selecting the gamemode, they are brought into a lobby for that gamemode
 */
public class PlayMenuActivity extends AppCompatActivity {

    public static Lobby LOBBYINFO;

    private int lastClick = 0;
    private TextView gameDescription;
    private final String soloDescription = "Play offline against an AI";
    private final String ov1Description = "Play online against a friend";
    private final String ov2Description = "Play online with two friends.";
    private final String ovmDescription = "Play online with up to 3 friends. One person must defend against the combined enemy forces!";

    public static final String SELECTED_GAME_MODE = "edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.SELECTED_GAME_MODE";
    public static final String LEADERSTATUS = "edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.LEADERSTATUS";
    public static final String GAMEID = "edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.GAMEID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu);


        lastClick = R.id.solo_button;
        gameDescription = (TextView) findViewById(R.id.game_description);
        gameDescription.setText(soloDescription);

        findViewById(R.id.solo_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(lastClick == view.getId()){
                    //start the game lobby activity for a SOLO game
                    enterLobby(GameMode.SOLO, Lobby.lobbySolo(SharedPrefManager.getInstance(getApplicationContext()).getLocalPlayer()));
                }
                else{
                    lastClick = view.getId();
                    gameDescription.setText(soloDescription);
                }
            }
        });
        findViewById(R.id.one_v_one_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(lastClick == view.getId()){
                    //start the game lobby activity for a ONEVONE game
//                    enterLobby(GameMode.ONEVONE);
                    lookForGame(GameMode.ONEVONE);
                }
                else{
                    lastClick = view.getId();
                    gameDescription.setText(ov1Description);
                }
            }
        });

        findViewById(R.id.one_v_two_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(lastClick == view.getId()){
                    //start the game lobby activity for a ONEVTWO game
//                    enterLobby(GameMode.ONEVTWO);
                    lookForGame(GameMode.ONEVTWO);
                }
                else{
                    lastClick = view.getId();
                    gameDescription.setText(ov2Description);

                }
            }
        });

        findViewById(R.id.one_v_many_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(lastClick == view.getId()){
                    //start the game lobby activity for a ONEVMANY game
//                    enterLobby(GameMode.ONEVMANY);
                    lookForGame(GameMode.ONEVMANY);
                }
                else{
                    lastClick = view.getId();
                    gameDescription.setText(ovmDescription);
                }
            }
        });

    }

    private void enterLobby(GameMode gm,Lobby currLobby){
        Intent intent = new Intent(this, MultiplayerGameLobbyActivity.class);
        intent.putExtra(SELECTED_GAME_MODE, gm);
        intent.putExtra(LEADERSTATUS, currLobby.isLeader(SharedPrefManager.getInstance(getApplicationContext()).getLocalPlayer()));
        intent.putExtra(GAMEID, currLobby.getGameID());
        LOBBYINFO = currLobby; //its shared.

        startActivity(intent);
    }


    /**
     * Look for a game in the given game mode
     * @param gm the gamemode
     */
    public void lookForGame(GameMode gm){
        //It work in a loop until cancelled.
        Lobby lobby = LobbyServerReqs.createLobby(gm, getApplicationContext());

        if(lobby != null){

            enterLobby(gm, lobby);

        }

    }





}
