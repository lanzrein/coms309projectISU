package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.MapHelper;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.GameSolo;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.One_v_One;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.SetupGame;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.LobbyServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SoloGameAI;

/**
 * This activity is the lobby screen that players enter before they begin playing the game. Here players will select the map they want to play on and the race they want to play as.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MultiplayerGameLobbyActivity extends Activity {

    private static final String raceSelectorTitle = "Race Selector";
    private static final String mapSelectorTitle = "Map Selector";
    private List<Map> maps = new ArrayList<>();


    private Race raceChosen;
    private GameMode gameMode;


    private TextView mapSelectorHeader,raceSelectorHeader;
    private Button startButton;


    private ArrayList<LobbySlotView> teamA,teamB;
    private LobbySlotContainer lobby;


    private boolean isLeader;
    private int gameID;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_game_lobby);

        maps = MapServerReqs.getAllMapMeta1();
        MapServerReqs.getMapsData(maps);

        //get gamemode from intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gameMode = (GameMode) bundle.get(PlayMenuActivity.SELECTED_GAME_MODE);
            isLeader = (boolean)bundle.get(PlayMenuActivity.LEADERSTATUS);
            gameID = (int)bundle.get(PlayMenuActivity.GAMEID);
        } else {
            //default to SOLO if there wasn't an intent
            gameMode = GameMode.SOLO;
        }




        //set titles
        mapSelectorHeader = (TextView) findViewById(R.id.map_selector_header);
        raceSelectorHeader = (TextView) findViewById(R.id.race_selector_header);

        mapSelectorHeader.setText(mapSelectorTitle);
        raceSelectorHeader.setText(raceSelectorTitle);

        //get the start button from the layout
        startButton = findViewById(R.id.start_button);


        //init everything
        if(isLeader) {
            initMapSelector();
        }
        initPlayerLists();
        initRaceSelector();

        // play button listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the game is valid (has enough players, map is selected, race is selected) start the game
                SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                int playerID = spm.getLocalPlayer().getId();
                if(raceChosen == null){
                    Toast.makeText(getApplicationContext(), "You need to select a race", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isLeader && MapHelper.map.getID()<=0){
                    Toast.makeText(getApplicationContext(), "Select a map ! ", Toast.LENGTH_SHORT).show();
                    return;

                }
                SetupGame stp;
                if(gameMode != GameMode.SOLO) {
                    stp = LobbyServerReqs.gameLobbyPoll(getApplicationContext(), MapHelper.map.getID(), raceChosen.ordinal(), playerID, gameID);
                    stp.setID(gameMode);
                    for(Map m : maps){
                        if(m.getID() == stp.getMapID()){
                            MapHelper.set(m);
                            break;
                        }
                    }
                }else{
                    SoloGameAI sAI = new SoloGameAI(1);
                    stp = new SetupGame(playerID,raceChosen.ordinal(),sAI.getRace(),-1,MapHelper.map.getID(),-1,gameMode);
                }
                if(stp == null){
                    //ERROR HANDLING
                    Toast.makeText(getApplicationContext(), "Error sorry try again", Toast.LENGTH_SHORT).show();
                }else{

                    //GOTO game..........
                    switch(gameMode){
                        case ONEVONE:{startActivity(new Intent(getApplicationContext(), One_v_One.class));break;}
                        case SOLO:{startActivity(new Intent(getApplicationContext(), GameSolo.class));break;}
                        default : Toast.makeText(getApplicationContext(), "Other modes not ready sorry", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }

    private void initMapSelector(){


        //retrieve maps from db


        //layout for the horizontal scroll view
        //this is for the map selector section
        LinearLayout ll = (LinearLayout)findViewById(R.id.map_selector_container);
        ll.setBackgroundColor(Color.CYAN);





        for(int i = 0;i<maps.size();i++) {
            //use the lobby_map_sel layout template
            View v = View.inflate(this, R.layout.lobby_map_sel, null);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ImageButton mapButton = v.findViewById(R.id.lobby_sel_map_img);
                mapButton.setBackground(new BitmapDrawable(getResources(),maps.get(i).getMapThumbnail(this)));
                mapButton.getBackground().setFilterBitmap(false);

                //this listener will listen to all the map buttons
                MapButtonOnClickListener mb = new MapButtonOnClickListener(maps.get(i));

                mapButton.setOnClickListener(mb);
            }
            ((TextView)v.findViewById(R.id.lobby_sel_map_name)).setText(maps.get(i).getName()+i);

            //sets the tool tip to display map description
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((TextView) v.findViewById(R.id.lobby_sel_map_name)).setTooltipText(maps.get(i).getName()+" " + i +" "+maps.get(i).getMapDescription());
            }



            ll.addView(v);
        }

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x = view.getX();
                float y = view.getY();
                Log.d("coord : ",x+","+y);
            }
        });


    }

    private void initRaceSelector(){
        //setup the race selector section
        //Don't currently have image assets for race so for now we use our dummy map thumbnail
        RaceSelectorAdapter raceSelector = new RaceSelectorAdapter(this,MapHelper.map.getMapThumbnail(this));
        ListView raceSelectorContainer = (ListView) findViewById(R.id.race_selector_list);
        raceSelectorContainer.setAdapter(raceSelector);
        raceSelectorContainer.setBackgroundColor(Color.CYAN);
        raceSelectorContainer.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        raceSelectorContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //we choose arbitrary order HUMAN-DWARF-ORC ( or whatver )
                raceSelectorContainer.setSelection(i);
                raceChosen = Race.values()[i];
            }
        });
    }

    private void initPlayerLists(){
        //initialize the player lists
        teamA = new ArrayList<>();
        teamB = new ArrayList<>();
        LinearLayout attackers = findViewById(R.id.teama_container);
        LinearLayout defenders = findViewById(R.id.teamb_container);


        //set up the team slots
        lobby = new LobbySlotContainer(gameMode);

        //team A slots
        for(int i =0 ;i<lobby.getTeamASize();i++) {
            LobbySlotView slot = (LobbySlotView) View.inflate(this, R.layout.lobby_players_display, null);
            teamA.add(slot);
            ((TextView)slot.findViewById(R.id.username)).setText(lobby.getSlotTeamA(i).getName());
        }
        //teamB slots
        for(int i =0;i<lobby.getTeamBSize();i++) {
            LobbySlotView slot =(LobbySlotView) View.inflate(this, R.layout.lobby_players_display, null);
            teamB.add(slot);
            ((TextView)slot.findViewById(R.id.username)).setText(lobby.getSlotTeamB(i).getName());
        }

        //set click and drag listeners for the team A slots
        for (int i = 0; i < teamA.size(); i++){
            //assign the slot to the right view
            teamA.get(i).assignSlot(lobby.getSlotTeamA(i));

            //add view to the attackers layout
            attackers.addView(teamA.get(i));

            //set listeners
            teamA.get(i).setOnLongClickListener(new PlayerSlotLongClickListener());
            teamA.get(i).setOnDragListener(new PlayerSlotDragListener());
        }

        //set click and drag listeners for the team B slots
        for(int i = 0; i< teamB.size();i++) {
            //assign the slot to the right view
            teamB.get(i).assignSlot(lobby.getSlotTeamB(i));

            //add view to the defenders layout
            defenders.addView(teamB.get(i));

            //set listeners
            teamB.get(i).setOnLongClickListener(new PlayerSlotLongClickListener());
            teamB.get(i).setOnDragListener(new PlayerSlotDragListener());
        }
    }

    private enum Race{
        HUMAN,DWARF,ORC;
    }
}

