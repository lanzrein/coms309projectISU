package edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * Class that displays the maps created by the player.
 * created by johan.
 */
public class MyMapsActivity extends AppCompatActivity {
    ListView lv;
    List<Map> maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_maps);
        //display a list of all the maps.
        //When clicking on the map we need a small dialog like the friend thing to display :
        //- name of the map, description, who made it, thumbnail, etc..
        //same idea as list of friends...


        //first process the maps and get the info for all of them..
        int authorID = SharedPrefManager.getInstance(getApplicationContext()).getLocalPlayer().getId();
        maps = MapServerReqs.getMapsByAuthorID(authorID);
        //process the meta data for the maps..
        if(!MapServerReqs.getMapsData(maps)){
            Toast.makeText(getApplicationContext(), "Error while processing maps. \n Try again later.", Toast.LENGTH_LONG).show();
        }else{
            //get the list view
            setUpListView();

        }

    }

    private void setUpListView() {
        lv = (ListView) findViewById(R.id.map_listview);
        final Context ctx = this;
        List<String> mapsname = new ArrayList<>();
        for(Map m : maps){
            mapsname.add(m.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,mapsname);

        lv.setAdapter(adapter);

        //set up the touch handler.. we want to show a little dialog for each map. and propose to edit it.


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                //recall that i is the idx in the list maps of the map..
                Dialog dialog = maps.get(i).createDialog(ctx);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //refresh.
                        startActivity(getIntent());
                        finish();
                    }
                });
//                dialog.show();


            }
        });

    }
}
