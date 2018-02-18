package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joseph on 11/30/2017.
 * This is a custom view that contains an instance of UnitLineupEntry that this view will display
 */
public class UnitEntryView extends LinearLayout {

    private UnitLineupEntry entry;

    private TextView entryName;
    private TextView count;
    private Button inc;
    private Button dec;




    public UnitEntryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void updateCount(){
        count.setText(""+entry.getCount());
    }


    /**
     * Assigns a new UnitLineupEntry for this view to display
     * @param entry the new UnitLineupEntry
     */
    public void assignEntry(UnitLineupEntry entry){
        this.entry = entry;

        if(entryName==null){
            entryName = findViewById(R.id.game_lineup_entry_name);
        }
        if(count==null){
            count = findViewById(R.id.game_lineup_entry_count);
        }
        if(dec==null){
            dec = findViewById(R.id.game_lineup_entry_dec_button);
        }
        if (inc == null) {
            inc = findViewById(R.id.game_lineup_entry_inc);
        }

        entryName.setText(entry.getUnit().getName());
        updateCount();

        if(!inc.hasOnClickListeners()) {
            inc.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    entry.incCount();
                    updateCount();
                }
            });
        }
        if(!dec.hasOnClickListeners()) {
            dec.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    entry.decCount();
                    updateCount();
                }
            });
        }

    }

    /**
     * Returns the UnitLineupEntry that this view is displaying
     * @return the UnitLineupEntry that this view is displaying
     */
    public UnitLineupEntry getEntry(){
        return entry;
    }

}
