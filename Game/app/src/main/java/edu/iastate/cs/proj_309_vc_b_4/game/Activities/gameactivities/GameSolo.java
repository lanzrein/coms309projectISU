package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.EndGame.EndScreen;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.MapHelper;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus.GameSidePanel;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.GameStore;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

@RequiresApi(api = Build.VERSION_CODES.N)
/**
 * Activity ro represent the solo games.
 * It works like the one-v-one but only uses an ai as the opponent .
 * Created by Johan
 */
public class GameSolo extends GameActivity {

    private MainThread mainThread;

    private GamePanel gamePanel;
    private GameSidePanel sidePanel;

    private GameLogic currentSession;
    private GameLogic localSession;
    private HashMap<Integer,GameLogic> sessions;

    private User localPlayer;
    private User opponent;

    private GameStore gameStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_one_v_one);


        gameStore = new GameStore();

        localPlayer = SharedPrefManager.getInstance(getApplicationContext()).getLocalPlayer().converToUser();
        Opponent.setOpponent(new User("AI","",0,0,1,-1));
        opponent = Opponent.opponent;

        //Create a session for each player
        sessions = new HashMap<>();
        sessions.put(localPlayer.getPlayerID(), new GameLogic(MapHelper.map,gameStore));
        sessions.put(opponent.getPlayerID(),new GameLogic(MapHelper.map,gameStore));

        //set the current session, the current session is the one that will be displayed
        currentSession = sessions.get(localPlayer.getPlayerID());
        localSession = currentSession;

        gamePanel = findViewById(R.id.game_panel);

        gamePanel.setArgs(currentSession);
        sidePanel = new GameSidePanel(this,findViewById(R.id.game_side_panel),gamePanel,gameStore,sessions,currentSession,localSession,localPlayer,opponent);

        mainThread = new MainThread(this,gamePanel.getHolder(), gamePanel, sidePanel, sessions,localPlayer,opponent);


        mainThread.setRunning(true);
        mainThread.start();

    }

    @Override
    protected void onPause() {
        //mainThread.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        // mainThread.pause();
        super.onStop();
    }

    @Override
    protected void onResume() {
        // mainThread.unPause();
        super.onResume();
    }


}
