package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.GamePanel;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.Opponent;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.Buyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.GameStore;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.TurretBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.UnitBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;

/**
 * Created by Joe on 11/22/2017.
 * This class is a controller/view for the side panel.
 */
public class GameSidePanel {

    private Context context;
    private LinearLayout sidePanel;
    private TextView gameScore;
    private TextView baseHP;
    private TextView viewIndicator;

    private Button startWaveButton,storeButton, unitLineupButton,swichMapButton;

    private GamePanel gamePanel;

    private GameStore gameStore;
    private Dialog storePopup,unitLineupPopup;
    private UnitLineupViewController lineupViewController;

    private HashMap<Integer,GameLogic> sessions;
    private GameLogic currentSession;
    private GameLogic localSession;

    private User localPlayer,opponenet;

    /**
     * Contructs a new Side Panel
     * @param context context of the activity, used setting up the views
     * @param sidePanel A linear layout that holds all the components of the side panel
     * @param gamePanel The GamePanel surfaceview that will be displayed along side the side panel
     * @param gameStore An instance of a gamestore, used to populate the store with entries
     * @param sessions contains all the game sessions, used for modifying the game state based on user input
     * @param currentSession An instance of the game perpsective that will be displayed fist
     * @param localSession An instance of the game that belongs to the local player
     * @param localPlayer an instance of the local user
     * @param opponent an instance of the opponent user
     */
    public GameSidePanel(Context context, LinearLayout sidePanel, GamePanel gamePanel, GameStore gameStore, HashMap<Integer,GameLogic> sessions, GameLogic currentSession,GameLogic localSession,User localPlayer,User opponent) {
        this.context = context;
        this.sidePanel = sidePanel;
        this.gamePanel = gamePanel;
        this.sessions = sessions;
        this.currentSession = currentSession;
        this.gameStore = gameStore;
        this.localSession = localSession;
        this.localPlayer = localPlayer;
        this.opponenet = opponent;
        setUpSidePanel();
    }

    private void setUpSidePanel(){
        //text views to display the score and the base hp
        gameScore = new TextView(context);
        baseHP = new TextView(context);
        viewIndicator = new TextView(context);

        //create buttons
        startWaveButton = new Button(context);
        storeButton = new Button(context);
        unitLineupButton = new Button(context);
        swichMapButton = new Button(context);

        //set initial score
        gameScore.setText("Score: "+localSession.getCash());
        //Initial hp
        baseHP.setText("Base HP: " + localSession.getBaseHP());
        //display which view is showing, initially the users map is always showing
        viewIndicator.setText("Currently Viewing\n"+localPlayer.getUsername());

        //set button text
        startWaveButton.setText("Start Wave");
        storeButton.setText("Store");
        unitLineupButton.setText("Wave Lineup");
        swichMapButton.setText("Switch View");

        //create listeners
        startWaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendingWave.setOwnWave(lineupViewController.getLineupEditor().compileUnitList());

                if(sessions.get(localPlayer.getPlayerID()).equals(currentSession)) {
                    //if we are on the screen of the home player...
                    ArrayList<Unit> units = SendingWave.getEnemyWave();
                    if(!units.isEmpty()) {
                        currentSession.startWave(units);
                    }

                    SendingWave.clearEnemyWave();
                }else{
                    //the enemy screen...
                    currentSession.startWave(lineupViewController.getLineupEditor().compileUnitList());

                }
            }
        });

        //create a store pop up when store button is clicked
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display store
                DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
                int storeWidth = (int)(displayMetrics.widthPixels*.8);
                int storeHeight = (int)(displayMetrics.heightPixels*.8);
                storePopup.getWindow().setLayout(storeWidth, storeHeight);
                storePopup.show();
            }
        });

        unitLineupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display lineup editor
                DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
                int storeWidth = (int)(displayMetrics.widthPixels*.8);
                int storeHeight = (int)(displayMetrics.heightPixels*.8);
                unitLineupPopup.getWindow().setLayout(storeWidth, storeHeight);
                unitLineupPopup.show();
            }
        });

        swichMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentSession==localSession){
                    currentSession = sessions.get(opponenet.getPlayerID());
                    viewIndicator.setText("Currently Viewing\n"+opponenet.getUsername());
                } else {
                    currentSession = localSession;
                    viewIndicator.setText("Currently Viewing\n"+localPlayer.getUsername());
                }
                gamePanel.setGameLogic(currentSession);
            }
        });

        //add hp and score text view to side panel
        sidePanel.addView(viewIndicator);
        sidePanel.addView(baseHP);
        sidePanel.addView(gameScore);

        //add buttons to side panel
        sidePanel.addView(startWaveButton);
        sidePanel.addView(storeButton);
        sidePanel.addView(unitLineupButton);
        sidePanel.addView(swichMapButton);

        //sets up the store popup window
        setupStore();

        //sets up the unit lineup popup window
        setupUnitLineup();
    }

    /**
     * Updates the displayed score on the side panel
     */
    public void updateScore(){
        //Android only lets you change views on the thread they are created on.
        //So this lets us update the score from the main thread
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameScore.setText("Cash: " + localSession.getCash());
            }
        });
    }

    /**
     * Updates the displayed base hp, will display the hp of the base that is currently on the screen
     */
    public void updateBaseHP(){
        //same thing as the updateScore() method but with base hp
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseHP.setText("Base HP: "+ currentSession.getBaseHP());
            }
        });
    }

    /**
     * Called whenever something is bought from the store, will return true if the item is succesfully bought
     * @param item the item that was bought from the store
     * @return true if the item is successfully bought
     */
    public boolean onStoreBuy(Buyable item){
        Log.d("GAME", "Bought: " + item.getName() + ", " + item.getCost());

        //can't buy turret if you are viewing other players map
            if (item.getClass().equals(TurretBuyable.class)) {
                if (currentSession == localSession) {
                    if (currentSession.buyTurret((TurretBuyable) item)) {
                        storePopup.cancel();
                        Toast.makeText(context,"Click on the map to place your turret",Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    Toast.makeText(context, "Can't buy turret for other team!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else if (item.getClass().equals(UnitBuyable.class)) {
                return localSession.buyUnit((UnitBuyable) item);
            }
         else {
            return false;
        }
    }

    private void setupStore(){
        storePopup = new Dialog(context);
        storePopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        storePopup.setContentView(R.layout.game_store_panel);
        storePopup.setCanceledOnTouchOutside(true);
        storePopup.setCancelable(true);

        ListView storeUnits = storePopup.findViewById(R.id.game_store_unit_upgrades);
        ListView storeTurrets = storePopup.findViewById(R.id.game_store_turret_upgrades);

        storeUnits.setAdapter(new GameStoreEntryAdapter(this,context,gameStore.getUnitBuyables()));
        storeTurrets.setAdapter(new GameStoreEntryAdapter(this,context,gameStore.getTurretBuyables()));
    }

    private void setupUnitLineup(){
        unitLineupPopup = new Dialog(context);
        unitLineupPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        unitLineupPopup.setContentView(R.layout.game_lineup_panel);
        unitLineupPopup.setCancelable(true);
        unitLineupPopup.setCanceledOnTouchOutside(true);

        LinearLayout lineup = unitLineupPopup.findViewById(R.id.game_lineup_list);
        ListView unitPool = unitLineupPopup.findViewById(R.id.game_lineup_unitpool);

        UnitLineupEditor lineupEditor = new UnitLineupEditor(currentSession.getBoughtUnits());

        lineupViewController = new UnitLineupViewController(context, lineup, lineupEditor);
        unitPool.setAdapter(new GameUnitBoughtAdapter(context,lineupViewController,currentSession.getBoughtUnits(),lineupEditor));
    }
}
