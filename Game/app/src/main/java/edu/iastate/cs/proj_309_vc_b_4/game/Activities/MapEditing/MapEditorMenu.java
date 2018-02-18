package edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * This class is the menu for the map editor.
 * It contains access to all map editing functionality :
 * -check your maps
 * -create a new map
 * created by johan
 */
public class MapEditorMenu extends AppCompatActivity {

    Button seeMyMaps;
    Button createNewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_editor_menu);

        configButton();

        setText();


    }

    private void setText() {
        TextView title_id = (TextView) findViewById(R.id.title_map_edit);

        String title =
                "Welcome to the Map editing ! \n\n";

        title_id.setText(title);
        TextView intro = (TextView) findViewById(R.id.text_explanation_map_edit);

        String explanation = "We hope you have fun and can create the next best map of the community. But first here are" +
                        "a few rules to make maps more or less balanced : \n"+
                        "- Only one base per map.\n" +
                        "- The map must be in the right side of the map\n"+
                        "- The spawns will automatically be at the edges of the map"+"\n" +
                        "- The spawns must be in the left side of the map\n" +
                        "- There must be at least one way to get to the base\n" +
                        "- That's it have fun ;) ";

        intro.setText(explanation);

    }

    private void configButton() {
        seeMyMaps = (Button) findViewById(R.id.seeMyMaps);
        seeMyMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyMapsActivity.class));
            }
        });

        createNewMap = (Button)findViewById(R.id.createANewMap);
        createNewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO to activity of map editing.
                startActivity(new Intent(getApplicationContext(), EditMapActivity.class));

            }
        });
    }
}
