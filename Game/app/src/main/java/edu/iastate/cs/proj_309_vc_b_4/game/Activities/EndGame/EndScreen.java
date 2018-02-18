package edu.iastate.cs.proj_309_vc_b_4.game.Activities.EndGame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.Opponent;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.PlayMenuActivity;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapReportServReq;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.UpdateWinLoss;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.endGameStorage;

/**
 * Created by JeremyC on 12/2/2017.
 */

/**
 * The end game screen will be displayed when a game ended.
 * The screen will display the victor and the statistics of the game.
 * It will also allow players the opportunity to report the map, add the opponent as a friend, or to dismiss the
 * screen and go back to the play menu.
 */
public class EndScreen extends AppCompatActivity
{
    public static final String WINNER_TAG = "edu.iastate.cs.proj_edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.EndGame.EndScreen.WINNER_TAG";
    public static final String LOSER_TAG = "edu.iastate.cs.proj_edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.EndGame.EndScreen.LOSER_TAG";
    public static final String LOCAL_PLAYER_SCORE_TAG = "edu.iastate.cs.proj_edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.EndGame.EndScreen.LOCAL_PLAYER_SCORE_TAG";
    public static final String LOCAL_PLAYER_KILL_COUNT_TAG = "edu.iastate.cs.proj_edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.EndGame.EndScreen.LOCAL_PLAYER_KILL_COUNT_TAG";
    public static final String TIME_TAG = "edu.iastate.cs.proj_edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.EndGame.EndScreen.TIME_TAG";
    public static final String MAP_ID_TAG = "edu.iastate.cs.proj_edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.EndGame.EndScreen.MAP_ID_TAG";


    int status = 0;
    boolean unfairRep = false; //variable to keep track whether a report has been made.
    boolean offReport = false; //variable to keep track whether a report has been made.
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        //get intent values
        int winnerID,loserID,unitsKilled,mapID;
        long score,time;
        Intent intent = getIntent();
        winnerID = intent.getExtras().getInt(WINNER_TAG);
        loserID = intent.getExtras().getInt(LOSER_TAG);
        unitsKilled = intent.getExtras().getInt(LOCAL_PLAYER_KILL_COUNT_TAG);
        score = intent.getExtras().getLong(LOCAL_PLAYER_SCORE_TAG);
        time = intent.getExtras().getLong(TIME_TAG);
        mapID = intent.getExtras().getInt(MAP_ID_TAG);

        endGameStorage.setScore(score);
        endGameStorage.setMapID(mapID);

        TextView remarks;

        UpdateWinLoss.increaseWin(winnerID);
        remarks = (TextView) findViewById(R.id.remarks);
        UpdateWinLoss.increaseLoss(loserID);

        if(winnerID == SharedPrefManager.getInstance(getApplicationContext()).getLocalPlayer().getId()) {
            remarks.setText("Victory!!!");
        } else {
            remarks.setText("Opponent's Victory!!!");
        }

        TextView text0 = (TextView) findViewById(R.id.refer);
        text0.setText("Your Statistical Report");
        TextView text = (TextView) findViewById(R.id.time);
        text.setText("Time :  "+time);
        TextView text1 = (TextView) findViewById(R.id.score);
        text1.setText("Score: "+ score);
        TextView text2 = (TextView) findViewById(R.id.unitkill);
        text2.setText("Number of Units Killed :"+unitsKilled);

        //add opponent as friend.
        final Button addOpponent = (Button) findViewById(R.id.addOpponent);
        addOpponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this adds the opponent.
                Player p = SharedPrefManager.getInstance(getApplicationContext()).getLocalPlayer();
                String pw = SharedPrefManager.getInstance(getApplicationContext()).getLocalPassword();
                boolean added = FriendServerReqs.addFriend(Opponent.opponent.getPlayerID(),p.getUsername(),pw);
                if(added){
                    Toast.makeText(ctx,"Successfully added "+Opponent.opponent.getUsername()+"!",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ctx,"Sorry an error occured. ",Toast.LENGTH_LONG).show();

                }
            }
        });

        //report map being unfair.
        final Button reportUnfair = (Button) findViewById(R.id.ReportUnfair);
        reportUnfair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unfairRep == false)
                {
                    boolean rep = MapReportServReq.updateUnfairRep(endGameStorage.getMapID());
                    Toast.makeText(ctx,"Map Reported!",Toast.LENGTH_LONG).show();
                    unfairRep = true;
                    return;
                }

                else if(unfairRep == true)
                {
                    Toast.makeText(ctx,"Map has already been Reported!",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        //report map being offensive.
        final Button reportOff = (Button) findViewById(R.id.ReportInapp);
        reportOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offReport == false)
                {
                    boolean rep = MapReportServReq.updateOffenseRep(endGameStorage.getMapID());
                    Toast.makeText(ctx,"Map Reported!",Toast.LENGTH_LONG).show();
                    offReport = true;
                    return;
                }

                else if(offReport == true)
                {
                    Toast.makeText(ctx,"Map has already been Reported!",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        // dismiss the current activity and navigate back to the play menu.
        final Button dismiss = (Button) findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opponent.clearOpponent();
            startActivity(new Intent(getApplicationContext(), PlayMenuActivity.class));
            finish();
            }
        });


    }

}
