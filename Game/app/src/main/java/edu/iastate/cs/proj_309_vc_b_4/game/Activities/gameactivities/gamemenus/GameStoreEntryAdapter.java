package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus.GameSidePanel;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.Buyable;
import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * Created by Joe on 11/20/2017.
 */

/**
 * Adapter that allows store items to be displayed in the store via a list view
 */
public class GameStoreEntryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Buyable> entries;
    private GameSidePanel gameSidePanel;

    /**
     * Contructs a new GameStoreEntryAdapter
     * @param gameSidePanel instance of the side panel
     * @param context context of the activity
     * @param entries list of buyables that will be displayed in the store
     */
    public GameStoreEntryAdapter(GameSidePanel gameSidePanel,Context context, ArrayList<Buyable> entries){
        this.gameSidePanel = gameSidePanel;
        this.context = context;
        this.entries = entries;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int i) {
        return entries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout tv;
        if(view == null){
            tv = (LinearLayout) View.inflate(context, R.layout.game_store_entry, null);
        } else {
            tv = (LinearLayout) view;
        }
        TextView entryName = tv.findViewById(R.id.game_store_entry_text);
        Button buyButton = tv.findViewById(R.id.game_store_entry_buy);
        entryName.setText(((Buyable)getItem(i)).getName()+" - " + ((Buyable) getItem(i)).getCost());

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameSidePanel.onStoreBuy((Buyable) getItem(i));
            }
        });

        return tv;
    }
}
