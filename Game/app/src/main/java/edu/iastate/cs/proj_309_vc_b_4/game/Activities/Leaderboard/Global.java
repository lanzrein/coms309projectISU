package edu.iastate.cs.proj_309_vc_b_4.game.Activities.Leaderboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.Lcontent;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.LeaderServReq;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.LeaderboardTempStorage;

/**
 * Displayed the global leaderboard that can be sorted based on Most game played,
 * Fastest time, and win ratio.
 * Created by JeremyC on 11/5/2017.
 */

public class Global extends Fragment
{
    String[][] tableLayout;
    private static ArrayList<Lcontent> list = new ArrayList<>();
    LeaderboardTempStorage temp = new LeaderboardTempStorage();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout_view = inflater.inflate(R.layout.global_tab, container, false);

        final TableView<String[]> tb = (TableView<String[]>) layout_view.findViewById(R.id.globalView);
        tb.setColumnCount(4);


        //populate table
        list = LeaderServReq.FetchData(temp.getGlocal_url());
        setTable();

        //Adapters
        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(), temp.getHeader()));
        tb.setDataAdapter(new SimpleTableDataAdapter(getActivity(), tableLayout));

        return layout_view;
    }


    /**
     * Insert retrieved data into the table cells. When a filter selection is clicked,
     * the method will check the current status(condition) that was selected and
     * output accordingly. Everytime the condition is changed, the activity will be refresh.
     */
    private void setTable() {
        tableLayout = new String[list.size()][4];

        //Win ratio
        if(temp.getStatus() == 0) {
            for (int i = 0; i < list.size(); i++) {
                Lcontent inputs = list.get(i);
                tableLayout[i][0] = Integer.toString(i + 1); //position
                tableLayout[i][1] = inputs.getID(); // Player ID
                tableLayout[i][2] = inputs.getPlayerName(); //username
                tableLayout[i][3] = inputs.getRatio(); //win-lose ration
            }
        }

        //Fastest Time
        else if(temp.getStatus() == 1)
        {
            for (int i = 0; i < list.size(); i++) {
                Lcontent inputs = list.get(i);
                tableLayout[i][0] = Integer.toString(i + 1); //position
                tableLayout[i][1] = inputs.getID(); // Player ID
                tableLayout[i][2] = inputs.getPlayerName(); //username
                tableLayout[i][3] = inputs.getTimeTaken(); //Time Taken
            }
        }

        //Most Game played
        else if(temp.getStatus() == 2)
        {
            for(int i = 0; i < list.size(); i++)
            {
                Lcontent inputs = list.get(i);
                tableLayout[i][0] = Integer.toString(i + 1); //position
                tableLayout[i][1] = inputs.getID(); // Player ID
                tableLayout[i][2] = inputs.getPlayerName(); //username
                tableLayout[i][3] = inputs.calcMostGames(); //Most number of games played.
            }

        }
    }
}
