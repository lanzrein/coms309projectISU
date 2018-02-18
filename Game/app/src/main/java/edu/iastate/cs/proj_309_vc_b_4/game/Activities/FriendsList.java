package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This Activity helps to manage the friends list from there you can manage friends
 * and add new ones
 * Creator : Johan
 */
public class FriendsList extends Activity {
    //a few variables
    ListView l;
    List<User> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context ctx = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        //button to add friend
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = addFriendPopUp();
                d.show();
            }
        });

        l=(ListView)findViewById(R.id.l1);

        //We will need all the friends. so we load them up.
        SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
        String username = spm.getLocalPlayer().getUsername();
        String password = spm.getLocalPassword();

        friends = FriendServerReqs.getFriends(username,password);
        List<String> friendsUsername = new ArrayList<>();
        for(User u : friends){
            //We just display the username on the list.
            friendsUsername.add(u.getUsername());
        }
        //Define adapter 1)Context 2) layout for row 3) array of data.
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,friendsUsername);
        //assigne adapter to list

        l.setAdapter(adapter);
        //in case of a click on a user show its pop up.
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("InfoClick: ", i+" : id, \n "+l+" : l \n");
                User u = friends.get(i);
                Dialog d = u.createDialog(ctx, true);
                d.show();
                d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        finish();
                        startActivity(getIntent());
                    }
                });
            }
        });


}

    /**
     * This creates a pop up to offer the possibility to add a friend.
     * It only creates it and then return it the user has to then call show() on the return dialog to show it.
     *
     * @return the dialog created
     */
    private Dialog addFriendPopUp() {
        final Dialog dialog = new Dialog(this);
        final Context ctx = this;
        dialog.setContentView(R.layout.addfriend_pop_up);
        //our fields for username and id
        final EditText editUsername = (EditText) dialog.findViewById(R.id.editUsername);
        final EditText editID = (EditText) dialog.findViewById(R.id.editID);

        Button addFriend = (Button) dialog.findViewById(R.id.confirm);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString();
                int id = -1;
                try {
                    id = Integer.parseInt(editID.getText().toString());
                }catch(NumberFormatException nfe){
                    Toast.makeText(ctx,"Enter a valid id and username",Toast.LENGTH_LONG).show();
                    return;
                }
                if(id < 0 || username.isEmpty()){
                    Toast.makeText(ctx,"Enter a valid id and username",Toast.LENGTH_LONG).show();
                }

                if(FriendServerReqs.existsPlayer(username,id)){
                    User.addFriend(id, ctx);
                    dialog.dismiss();
                    //refresh the activity.
                    finish();
                    startActivity(getIntent());
                }else{
                    Toast.makeText(ctx,"Player does not exist !", Toast.LENGTH_LONG).show();
                }
                return;
            }
        });

        Button close = (Button) dialog.findViewById(R.id.cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return dialog;
    }










}

