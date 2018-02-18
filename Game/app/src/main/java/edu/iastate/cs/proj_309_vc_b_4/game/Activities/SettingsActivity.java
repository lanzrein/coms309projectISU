package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SettingsServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This activity is the settings menu from there the user can manage his settings, email the admins or just delete the acocun
 */
public class SettingsActivity extends AppCompatActivity {


    SeekBar sound;
    AudioManager audioManager;
    Button editMessage;
    Button changePicture;
    Button sendEmail;
    Button deleteAccount;
    Button checkMyProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sound = (SeekBar) findViewById(R.id.soundBar);
        editMessage = (Button) findViewById(R.id.editMessageButton) ;
        changePicture = (Button) findViewById(R.id.changePictureButton) ;
        sendEmail = (Button) findViewById(R.id.sendEmailButton) ;
        deleteAccount = (Button) findViewById(R.id.deleteAccountButton) ;
        checkMyProfile = (Button) findViewById(R.id.seeMyProfileButton) ;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        editMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = editMessagePopUp();

                dialog.show();
            }

        });

        changePicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog d = changeProfilePicture();
                d.show();
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = feedbackPopUp();
                dialog.show();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = deleteAccountPop();
                dialog.show();
            }
        });

        sound.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)); //??
        sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                //empty
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                //empty
            }
        });

        checkMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyProfileActivity.class));
                finish();
            }
        });




    }

    /**
     * Creates a Dialog that offers to delete the account.
     * It will give a confirmation button.
     * @return Dialog created
     */
    private Dialog deleteAccountPop() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Are you sure ? There is no coming back.");

        dialog.setContentView(R.layout.edit_message_popup);
        EditText text = (EditText) dialog.findViewById(R.id.editMessage);
        text.setVisibility(View.GONE);
        Button confirmAction = (Button) dialog.findViewById(R.id.confirmEdit);
        confirmAction.setText("Yes");
        confirmAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Here we erase the account and close it all.
                SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                boolean del = SettingsServerReqs.deleteAccount(spm.getLocalPlayer().getUsername(),spm.getLocalPassword());
                if(del){
                    //log out and finish the app.
                    dialog.dismiss();
                    spm.logout();
                    Toast.makeText(getApplicationContext(),"Goodbye :(", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Something went wrong sorry", Toast.LENGTH_LONG).show();
                }

            }
        });



        Button closePop = (Button) dialog.findViewById(R.id.closeEdit);
        closePop.setText("No");
        closePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    /**
     * A pop up to send feedback to the creative team.
     * It will porpose to enter a message and then send it to the server.
     * @return The dialog created.
     */
    private Dialog feedbackPopUp() {
        Dialog dialog = new Dialog(this);
        dialog.show();
        dialog.setTitle("Send Email");

        dialog.setContentView(R.layout.edit_message_popup);
        EditText text = (EditText) dialog.findViewById(R.id.editMessage);
        Button confirmAction = (Button) dialog.findViewById(R.id.confirmEdit);
        confirmAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text.getText().toString();
                if(message.isEmpty()){
                    //prevent dummy message.
                    Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_LONG).show();
                }else{
                    //Get the player info and then send.
                    SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                    Player player = spm.getLocalPlayer();
                    boolean change = SettingsServerReqs.sendEmailToTeam(message, player.getUsername(),player.getId());
                    if(change){
                        Toast.makeText(getApplicationContext(), "Email sent thanks for feedback ! ", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Sorry there was an error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



        Button closePop = (Button) dialog.findViewById(R.id.closeEdit);
        closePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return dialog;
    }

    /**
     * The Dialog for editing your personnal message.
     * IT offers an edit text, confirm and cancel button.
     *
     * @return The dialog created.
     */
    private Dialog editMessagePopUp() {
        Dialog dialog = new Dialog(this);

        dialog.setTitle("New Message");

        dialog.setContentView(R.layout.edit_message_popup);
        EditText text = (EditText) dialog.findViewById(R.id.editMessage);
        Button confirmAction = (Button) dialog.findViewById(R.id.confirmEdit);
        confirmAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_LONG).show();
                }else{
                    SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                    Player player = spm.getLocalPlayer();
                    boolean change = SettingsServerReqs.changeMessageTo(message, player.getUsername(),spm.getLocalPassword());
                    if(change){
                        Toast.makeText(getApplicationContext(), "Change successful ! ", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Sorry there was an error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });





        Button closePop = (Button) dialog.findViewById(R.id.closeEdit);
        closePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return dialog;

    }

    /**
     * Dialog to change profile picture. When you select a picture it will set it as your profile picture ( no confirmation )
     * @return dialog
     */
    private Dialog changeProfilePicture(){
        Dialog dialog = new Dialog(this);

        dialog.setTitle("Choose the image!");

        dialog.setContentView(R.layout.grid_profile_pictures);

        GridView gridView = (GridView) dialog.findViewById(R.id.gridProfilePictures);

        gridView.setAdapter(new ProfileImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                Player p = spm.getLocalPlayer();
                boolean change = SettingsServerReqs.changeImageTo(i,p.getUsername(), spm.getLocalPassword());
                if(change){
                    Toast.makeText(getApplicationContext(),"Change successful!", Toast.LENGTH_SHORT);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Error try again.", Toast.LENGTH_SHORT);
                }
            }
        });


        Button closePop = (Button) dialog.findViewById(R.id.cancel_profile_change);
        closePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return dialog;

    }


}
