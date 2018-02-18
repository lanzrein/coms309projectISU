package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.EndGame.EndScreen;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;

/**
 * Created by Alex on 10/7/2017.
 * An implementation of this activity is used to host the game.
 */
public abstract class GameActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Each game Activity has a onGameEnd() method. This method will exit the game redirect the player to the end game screen.
     * @param winner the winner's User instance
     * @param lost the loser's User instance
     * @param mapID the map id that was being played
     * @param score the socre of the local player
     * @param killCount the number of units that the local player had killed
     * @param time the amount of time, in minutes, that the game lasted
     */
    public void onGameEnd(User winner,User lost,int mapID,long score,int killCount,long time){
        //the game has ended
        Intent intent = new Intent(this, EndScreen.class);
        intent.putExtra(EndScreen.WINNER_TAG, winner.getPlayerID());
        intent.putExtra(EndScreen.LOSER_TAG, lost.getPlayerID());
        intent.putExtra(EndScreen.MAP_ID_TAG, mapID);
        intent.putExtra(EndScreen.LOCAL_PLAYER_SCORE_TAG, score);
        intent.putExtra(EndScreen.LOCAL_PLAYER_KILL_COUNT_TAG, killCount);
        intent.putExtra(EndScreen.TIME_TAG, time);

        startActivity(intent);
        //finish the game activity
        finish();
    }


}
