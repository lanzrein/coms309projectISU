package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.Message;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MessageServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This class is to check the messages of the user.
 * Creator johan
 */
public class MyMessages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);
        final Context context = this;
        //Get all the mesages.
        SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
        Player localPlayer = spm.getLocalPlayer();
        String password = spm.getLocalPassword();

        List<Message> messages = MessageServerReqs.getMessage(localPlayer.getUsername(),password);

        //We proceed pretty much like friends list. offer a preview of every message.

        ListView lv = (ListView) findViewById(R.id.listOfMessages);
        List<String> listOfSenders = new ArrayList<>();
        for(Message m : messages){
            listOfSenders.add(m.getFromUsername());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.activity_listview, listOfSenders);

        lv.setAdapter(adapter);
        //in case you click the message it should display it and offer deletion and reply.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message m = messages.get(i);
                Dialog d = m.displayMessage(context,localPlayer.getUsername(),password,localPlayer.getId());
                d.show();
                //when closing the message refresh the page.
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
}
