package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.view.View;


/**
 * Created by Joe on 10/5/2017.
 * This LongClickListener listens to the LobbySlotView objects and initiates drag events
 */

public class PlayerSlotLongClickListener implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View view) {

        //cast to LobbySlotView
        LobbySlotView lsv = (LobbySlotView) view;
        LobbySlot slot = lsv.getSlot();


        //if slot is empty don't start a drag
        if(!slot.isEmpty()) {
            //Creates a ClipData Item from the Views object tag
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            //clip data needs a string array for MIMETYPE
            String[] clipDescriptions = new String[1];
            clipDescriptions[0] = ClipDescription.MIMETYPE_TEXT_PLAIN;

            ClipData dragData = new ClipData((CharSequence) view.getTag(), clipDescriptions, item);

            View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);


            //starts the drag
            //only works with android N and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                view.startDragAndDrop(dragData, shadow, view, 0);

                return true;
            }
        }
        return false;
    }
}
