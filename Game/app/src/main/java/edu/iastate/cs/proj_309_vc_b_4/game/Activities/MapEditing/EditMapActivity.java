package edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.TerrainTypeImageAdatper;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * This activity takes care of map editing.
 * When a user edits a map they use this activity.
 * created by Johan.
 */
public class EditMapActivity extends AppCompatActivity {

    private MapPanel mapPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_edit_map);

        //we need to display the MapPanel.
        createMapPanel();
        //in case we have it from somewhere else..
        if(MapHelper.isAvailable()){
            mapPanel.setMap(MapHelper.map,true);
            MapHelper.delete();
        }


        mapPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mapPanel.refreshCanvas();
            }
        });

        prepareButtons();
        prepareGridTerrainType();
    }

    private void prepareGridTerrainType() {
        GridView gv = (GridView) findViewById(R.id.gridTerrainType);
        gv.setAdapter(new TerrainTypeImageAdatper(getApplicationContext()));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //i is the index of the terrain type selected.
                mapPanel.setCurrentTerrainType(i);
            }
        });
    }

    private void prepareButtons() {
        Button cancel = (Button) findViewById(R.id.exit_map_edit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),MapEditorMenu.class));
            }
        });


        Button save = (Button) findViewById(R.id.save_map_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //prompt for name and description
                Dialog d = promptNameDescription(mapPanel);

                d.show();

            }
        });
    }

    private Dialog promptNameDescription(MapPanel mapPanel) {
        Dialog dialog = new Dialog(this);
        //this parts force the dialog to fit the screen..
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(R.layout.map_name_description_dialog);

        final Context ctx = this;
        final EditText mapName = (EditText) dialog.findViewById(R.id.mapName);
        final EditText mapDescription = (EditText) dialog.findViewById(R.id.descriptionOfMap);


        Button save = (Button) dialog.findViewById(R.id.confirm_map_creation);

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String name = mapName.getText().toString();
                String description = mapDescription.getText().toString();
                if(name.isEmpty() || description.isEmpty()){
                    Toast.makeText(ctx,"Enter a valid name and description",Toast.LENGTH_LONG).show();

                }else{
                    mapPanel.setName(name);
                    mapPanel.setDescription(description);
                    if(mapPanel.isValidMap()) {
                        Map m = mapPanel.saveMap();
                        if (m != null) {
                            Toast.makeText(ctx, "Congrats on your new map!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            startActivity(new Intent(ctx, MapEditorMenu.class));
                        }else{
                            Toast.makeText(ctx,"Error try again later.",Toast.LENGTH_LONG).show();

                        }
                    }else{
                        Toast.makeText(ctx,"The map is not valid!",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        Button cancel = (Button)dialog.findViewById(R.id.cancel_confirm_map_creation);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




        return dialog;

    }

    private void createMapPanel() {

        mapPanel = (MapPanel)findViewById(R.id.map_frame);

    }
}
