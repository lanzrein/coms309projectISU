package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.AdminMenu.AdminMain;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.Leaderboard.LeaderboardsActivity;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.MapEditorMenu;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.PlayMenuActivity;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.PlayerReportServReq;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This is the main menu.
 * The place where the player can access all the different options form the game
 * the code is very repetitive and easy to understand. not tricks here.
 *
 */
public final class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        setContentView(R.layout.activity_main_menu);

        //editMessage for play menu
        Button playmenu = (Button) findViewById(R.id.playMenu);
        playmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlayMenuActivity.class));
            }
        });
        //editMessage for leaderboards
        Button leaderboards = (Button) findViewById(R.id.leaderboards);
        leaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LeaderboardsActivity.class));
            }
        });
        //editMessage for friends
        Button friends = (Button) findViewById(R.id.friendsList);
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FriendsList.class));
            }
        });
        //editMessage for mapEditor menu
        Button mapEditing = (Button) findViewById(R.id.mapEditor);
        mapEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                startActivity(new Intent(getApplicationContext(), MapEditorMenu.class));
            }
        });
        //editMessage for settings
        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
//                Toast.makeText(getApplicationContext(), "Sorry not ready yet come back soon", Toast.LENGTH_LONG).show();
            }
        });

        //Admin Menu
        Button admin = (Button) findViewById(R.id.adminMenu);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                String username = spm.getLocalPlayer().getUsername();
                String password = spm.getLocalPassword();
                if(PlayerReportServReq.checkAdmin(username,password))
                {
                    finish();
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    startActivity(new Intent(getApplicationContext(), AdminMain.class));
                }

                else{
                    Toast.makeText(context,"Access Denied!",Toast.LENGTH_LONG).show();
                }

            }
        });


        //log out
        Button logOut = (Button) findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

        //my profile button..
        Button myProfile = (Button) findViewById(R.id.myProfile);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyProfileActivity.class));

            }
        });



    }
}
