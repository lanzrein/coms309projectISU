package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.MapHelper;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joseph on 11/5/2017.
 * This class listens to all the map buttons and remembers which one was the last clicked. This is used to select the map in the GameLobby.
 */

public class MapButtonOnClickListener implements View.OnClickListener {

    private ImageButton selected;
    private final Map map;


    /**
     * Constructs a new MapButtonOnClickListener and sets the map that is associated with the view.
     * @param map
     */
    public MapButtonOnClickListener(Map map){
        this.map = map;
    }
    @Override
    public void onClick(View view) {
        if(selected!=null){
            //unhighlight old button
            selected.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        }
        selected = (ImageButton) view;
        selected.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.OVERLAY);
        MapHelper.set(map);
    }

    /**
     * Returns the selected map
     * @return
     */
    @Deprecated
    public Map getSelectedMap(){
        return map;
    }
}
