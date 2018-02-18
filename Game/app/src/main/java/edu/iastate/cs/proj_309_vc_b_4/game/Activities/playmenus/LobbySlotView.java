package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joe on 10/7/2017.
 *
 * This class is the view representation of a slot
 */

public class LobbySlotView extends LinearLayout {

    private LobbySlot slot;
    private TextView displayName;
    private Button button1, button2;

    public LobbySlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        displayName = this.findViewById(R.id.username);


    }

    /**
     * Assigns this view a slot
     * @param lobbySlot slot for this view to represent
     */
    public void assignSlot(LobbySlot lobbySlot) {
        slot = lobbySlot;
        if (displayName == null) {
            displayName = findViewById(R.id.username);
        }
        displayName.setText(slot.getName());

        if(button1==null){
            button1 = findViewById(R.id.button1);
            button1.setVisibility(View.INVISIBLE);
        }
        if(button2 == null){
            button2 = findViewById(R.id.changePictureButton);
            button2.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Returns the slot that this view is representing
     * @return slot
     */
    public LobbySlot getSlot() {
        return slot;
    }

    public String toString(){
        return slot.getName();
    }

}
