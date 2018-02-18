package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joe on 10/5/2017.
 * This drag listener will handle drag events for LobbySlotViews
 */

public class PlayerSlotDragListener implements View.OnDragListener {


    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {

        //defines a variable to store the action type for the incoming event
        final int action = dragEvent.getAction();

        //this is the slot that we are dragging around
        //LobbySlot slot = (LobbySlot)dragEvent.getLocalState();

        //Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                view.setBackgroundColor(Color.BLUE);
                Log.d("lobby_menu", dragEvent.getLocalState() + " entered " + ((TextView) view.findViewById(R.id.username)).getText());
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                view.setBackgroundColor(Color.BLACK);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                view.setBackgroundColor(Color.BLACK);
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                break;
            case DragEvent.ACTION_DROP:
                //move the dragged slot into the dropped slot

                //this is the view that is being dragged
                View dragView = (View) dragEvent.getLocalState();


                //check if view is being dropped into itself
                if (dragView != view) {
                    //cast views to LobbySlotViews
                    LobbySlotView dragSlotView = (LobbySlotView) dragView;
                    //this is the view that is being dropped into
                    LobbySlotView dropSlotView = (LobbySlotView) view;

                    //swap the slots in the lobby slot container
                    LobbySlot dragged, dropped;
                    dragged = dragSlotView.getSlot();
                    dropped = dropSlotView.getSlot();
                    swap(dragged,dropped);

                    dragSlotView.assignSlot(dropped);
                    dropSlotView.assignSlot(dragged);



                    /*
                    LinearLayout dragParent = (LinearLayout) dragSlotView.getParent();
                    LinearLayout dropParent = (LinearLayout) dropSlotView.getParent();

                    //remove the views
                    dragParent.removeView(dragView);
                    dropParent.removeView(view);


                    //add the views into the propper containers
                    dragParent.addView(dragView);
                    dropParent.addView(view);
                    dragView.setVisibility(View.VISIBLE);
                    */
                    return true;
                }
                return false;
        }
        return false;
    }

    //swaps the two slots
    private void swap(LobbySlot a, LobbySlot b) {
        if (a == b)
            return;

        if (a.next == b) { // right next to each other
            a.next = b.next;
            b.prev = a.prev;

            if (a.next != null)
                a.next.prev = a;

            if (b.prev != null)
                b.prev.next = b;


            b.next = a;
            a.prev = b;
        } else {
            LobbySlot p = b.prev;
            LobbySlot n = b.next;

            b.prev = a.prev;
            b.next = a.next;

            a.prev = p;
            a.next = n;

            if (b.next != null)
                b.next.prev = b;
            if (b.prev != null)
                b.prev.next = b;

            if (a.next != null)
                a.next.prev = a;
            if (a.prev != null)
                a.prev.next = a;

        }
    }
}
