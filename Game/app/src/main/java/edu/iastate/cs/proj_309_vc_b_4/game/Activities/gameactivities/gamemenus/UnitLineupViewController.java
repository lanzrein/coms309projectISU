package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Joe on 12/2/2017.
 * This class contains and controls the unit wave lineup view
 */
public class UnitLineupViewController {

    private LinearLayout list;
    private UnitLineupEditor lineupEditor;
    private Context context;

    /**
     * Constructs a new UnitLineupViewController
     * @param context context of the activity
     * @param list Linear layout that will hold the unit lineup entry views
     * @param lineupEditor Instance of a unitLineupEditor, This will hold the state of the lineup
     */
    public UnitLineupViewController(Context context, LinearLayout list, UnitLineupEditor lineupEditor) {
        this.context = context;
        this.list = list;
        this.lineupEditor = lineupEditor;
        TextView header = new TextView(context);
        header.setText("Lineup", TextView.BufferType.SPANNABLE);
        list.addView(header);
        UnitLineupDragListener unitLineupDragListener = new UnitLineupDragListener(context, this,lineupEditor);
        list.setOnDragListener(unitLineupDragListener);
    }

    /**
     * Adds a new Entry View to this list and assigns the correct drag and click listeners to it.
     * @param entryView The view to be added to this list
     */
    public void addEntryView(UnitEntryView entryView) {
        list.addView(entryView);

        //set listeners for view
        entryView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //this will create drag events for swapping lineup entries

                ClipData.Item item = new ClipData.Item((CharSequence) "swap");
                String[] clipDescriptions = new String[1];
                clipDescriptions[0] = ClipDescription.MIMETYPE_TEXT_PLAIN;
                ClipData dragData = new ClipData("swap", clipDescriptions, item);

                View.DragShadowBuilder shadow = new View.DragShadowBuilder(view);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(dragData, shadow, entryView, 0);
                    return true;
                }
                return false;

            }
        });

        entryView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                final int action = dragEvent.getAction();
                boolean status = false;
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        CharSequence label = dragEvent.getClipDescription().getLabel();
                        status = "swap".equals(label);
                        break;
                    case DragEvent.ACTION_DROP:
                        //swap the views
                        UnitEntryView dragView = (UnitEntryView) dragEvent.getLocalState();
                        UnitEntryView dropView = (UnitEntryView) view;


                        UnitLineupEntry dragEntry = dragView.getEntry();
                        UnitLineupEntry dropEntry = dropView.getEntry();

                        dragView.assignEntry(dropEntry);
                        dropView.assignEntry(dragEntry);

                        lineupEditor.swap(dragEntry, dropEntry);
                        status = true;
                        break;
                }
                return status;
            }
        });
    }

    /**
     * Removes the given view from this list
     * @param entryView The view to remove
     */
    public void remove(UnitEntryView entryView){
        lineupEditor.remove(entryView.getEntry());
        list.removeView(entryView);
    }

    /**
     * Returns an instance of a lineupEditor
     * @return This object's instance of the lineupEditor
     */
    public UnitLineupEditor getLineupEditor(){
        return lineupEditor;
    }


}
