package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.MyMapsActivity;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * Profile activity.
 * created by johan
 */
public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
        Player p = spm.getLocalPlayer();
        User u = FriendServerReqs.getSingleUser(p.getUsername(),p.getId());
        //Display the player profile.
        displayProfile(u);
        //Button for message.
        Button myMessages = (Button)findViewById(R.id.myMessages);
        myMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyMessages.class));

            }
        });

        Button myMaps = (Button) findViewById(R.id.myMaps);
        myMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyMapsActivity.class));
            }
        });



    }

    private void displayProfile(User u) {
        TextView text = (TextView) findViewById(R.id.username);
        text.setText("Username : "+u.getUsername()+". ID : "+ u.getPlayerID());
        text = (TextView) findViewById(R.id.noWins);
        text.setText("Number of wins : \n "+u.getNoWins());
        text = (TextView) findViewById(R.id.noLoss);
        text.setText("Number of loss : \n "+u.getNoLoss());
        text = (TextView) findViewById(R.id.personalMessage);
        text.setText("Message : \n \""+u.getMessage()+"\"");

        ImageView img = (ImageView) findViewById(R.id.profilePicture);
        img.setImageResource(ProfileImageAdapter.mThumbsIds[u.getImageID()]);
    }
}
