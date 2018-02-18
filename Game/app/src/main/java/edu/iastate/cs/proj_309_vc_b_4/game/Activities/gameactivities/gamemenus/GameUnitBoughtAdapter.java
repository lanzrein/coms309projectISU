package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.UnitBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joseph on 11/30/2017.
 */

/**
 * This adapter displays the units that the player has bought in the SetLineup menu
 */
public class GameUnitBoughtAdapter extends BaseAdapter {
    private Context context;

    private HashMap<UnitBuyable,Integer> unitsBought;
    private UnitLineupViewController lineupViewController;
    private UnitLineupEditor lineup;

    /**
     * Constructs a new GameUnitBoughtAdapater
     * @param context context of the activity
     * @param lineupViewController instance of the unitLineUpViewController, units will be dragged to and from this view controller
     * @param unitsBought This hashmap contains units as a key that point to the number of that unit that the player has bought
     * @param lineup Instance of the lineup editor, this object contains the current state
     */
    public GameUnitBoughtAdapter(Context context, UnitLineupViewController lineupViewController, HashMap<UnitBuyable,Integer> unitsBought, UnitLineupEditor lineup){
        this.context = context;
        this.unitsBought = unitsBought;
        this.lineup = lineup;
        this.lineupViewController = lineupViewController;
    }

    @Override
    public int getCount() {
        return unitsBought.size();
    }

    @Override
    public Object getItem(int i) {
        UnitBuyable item = getUnitBuyable(i);
        return item.getName() + ": " + unitsBought.get(item);
    }

    public UnitBuyable getUnitBuyable(int i){
        return (UnitBuyable) unitsBought.keySet().toArray()[i];

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout tv;
        if(view == null){
            tv = (LinearLayout) View.inflate(context, R.layout.game_lineup_unit_bought, null);
        } else {
            tv = (LinearLayout) view;
        }
        TextView entryName = tv.findViewById(R.id.game_lineup_bought_entry_name);
        entryName.setText((String)getItem(i));

        //listens for drag events
        tv.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                final int action = dragEvent.getAction();
                boolean returnValue = false;
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        CharSequence label = dragEvent.getClipDescription().getLabel();
                        if("swap".equals(label))
                            returnValue = true;
                        break;
                    case DragEvent.ACTION_DROP:
                         UnitEntryView dragView = (UnitEntryView) dragEvent.getLocalState();
                         lineupViewController.remove(dragView);
                         returnValue = true;
                        break;
                }
                return returnValue;
            }
        });

        //listens and begins drag events
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //can't add to lineup because there are no more units available
                if (lineup.getRemaining(getUnitBuyable(i)) <= 0) {
                    return false;
                }

                //start with one unit in this entry
                UnitLineupEntry entry = new UnitLineupEntry(lineup,getUnitBuyable(i),1);

                ClipData.Item item = new ClipData.Item((CharSequence) "append");
                String[] clipDescriptions = new String[1];
                clipDescriptions[0] = ClipDescription.MIMETYPE_TEXT_PLAIN;
                ClipData dragData = new ClipData("append", clipDescriptions, item);

                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(dragData, shadow, entry, 0);
                    return  true;
                }
                return false;
            }
        });

        tv.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        return tv;
    }
}
