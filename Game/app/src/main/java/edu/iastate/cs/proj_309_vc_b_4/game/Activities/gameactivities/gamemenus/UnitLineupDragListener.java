package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;


import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joe on 12/2/2017.
 *
 * This drag listener, when set on a UnitEntryView, will handle drag events.
 */
public class UnitLineupDragListener implements View.OnDragListener {

    private Context context;
    private UnitLineupEditor unitLineupEditor;
    private UnitLineupViewController list;

    /**
     * Constructs a new UnitLineupDragListener
     * @param context context of this activity
     * @param list an instance of UnitLineupViewController which contains all the UnitEntryViews
     * @param unitLineupEditor instance of the unitLineupEditor
     */
    public UnitLineupDragListener(Context context,UnitLineupViewController list, UnitLineupEditor unitLineupEditor){
        this.context = context;
        this.unitLineupEditor = unitLineupEditor;
        this.list = list;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        final int action = dragEvent.getAction();
        boolean returnStatus = false;
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                CharSequence label = dragEvent.getClipDescription().getLabel();
                 returnStatus = "append".equals(label);
                 break;
            case DragEvent.ACTION_DROP:
                //create new view entry in this list
                UnitEntryView entryView = (UnitEntryView) View.inflate(context, R.layout.game_lineup_unit, null);
                list.addEntryView(entryView);
                UnitLineupEntry entry = (UnitLineupEntry) dragEvent.getLocalState();
                entryView.assignEntry(entry);
                unitLineupEditor.appendEntry(entry);
                returnStatus = true;
                break;
        }
        return returnStatus;
    }
}
