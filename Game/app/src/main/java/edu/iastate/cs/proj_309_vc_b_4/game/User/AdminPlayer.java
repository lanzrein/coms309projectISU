package edu.iastate.cs.proj_309_vc_b_4.game.User;

/**
 * Created by JeremyC on 11/29/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.ImageAdapter;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.PlayerReportServReq;

/**
 * This class handles the players' data that is retrieved from the database.
 * Created by JeremyC on 11/27/2017.
 */

public class AdminPlayer {
    //variable for data retrieved from database.
    private String username;
    private int PlayerID;
    private int numberWins;
    private int numberLoss;
    private int numberReports;
    private int imageID;
    private String message;


    public AdminPlayer(int PlayerID, String username, int numberWins, int numberLoss, int numberReports, int imageID, String message) {
        this.PlayerID = PlayerID;
        this.username = username;
        this.numberWins = numberWins;
        this.numberLoss = numberLoss;
        this.numberReports = numberReports;
        this.imageID = imageID;
        this.message = message;
    }

    /**
     * Retrieve the username of the player
     *
     * @return player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A pop up dialog that present a list of information regarding a certain player to the administrator.
     * The dialog also provides the adminisrtrator an opportunity to ban the player.
     *
     * @param context Activity's context
     * @return the information of the player
     */
    public Dialog popUpInfo(Context context) {
        final Context ctx = context;
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.report_players_popup);

        dialog.setTitle("Player Report");
        TextView text = (TextView) dialog.findViewById(R.id.playerID);
        text.setText("MapID : \n" + PlayerID);
        TextView text1 = (TextView) dialog.findViewById(R.id.username);
        text1.setText("Username : " + username);
        text = (TextView) dialog.findViewById(R.id.noWins);
        text.setText("Number of wins : \n " + numberWins);
        text = (TextView) dialog.findViewById(R.id.noLoss);
        text.setText("Number of loss : \n " + numberLoss);
        text = (TextView) dialog.findViewById(R.id.personalMessage);
        text.setText("Message : \n \"" + message + "\"");
        text = (TextView) dialog.findViewById(R.id.numReports);
        text.setText("Number of reports: \n" + numberReports);

        ImageView img = (ImageView) dialog.findViewById(R.id.profilePicture);
        img.setImageResource(chooseImage());

        //close dialog button
        Button close = (Button) dialog.findViewById(R.id.closeReport);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //ban player button
        Button ban = (Button) dialog.findViewById(R.id.banUser);
        ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rm = PlayerReportServReq.deletePlayer(PlayerID);
                Toast.makeText(ctx,"Player Deleted!",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;

            }

        });

        return dialog;

    }

    private int chooseImage() {
        return ImageAdapter.mThumbsIds[imageID];
    }


}
