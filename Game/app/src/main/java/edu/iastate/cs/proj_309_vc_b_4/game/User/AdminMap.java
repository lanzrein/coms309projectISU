package edu.iastate.cs.proj_309_vc_b_4.game.User;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapReportServReq;

/**
 * This class handles the players' data that is retrieved from the database.
 * Created by JeremyC on 11/13/2017.
 */
public class AdminMap
{
    //variable for data retrieved from database.
    private String mapName;
    private int mapID;
    private String mapDescription;
    private int NumOffensiveRep;
    private int NumUnfairRep;


    public AdminMap(int mapID, String mapName, String mapDescription, int NumOffensiveRep, int NumUnfairRep)
    {
        this.mapID = mapID;
        this.mapName = mapName;
        this.mapDescription = mapDescription;
        this.NumOffensiveRep = NumOffensiveRep;
        this.NumUnfairRep = NumUnfairRep;
    }


    /**
     * Retrieve the name of the Map.
     * @return Map's name
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * A pop up dialog that present a list of information regarding a certain map to the administrator.
     * The dialog also provides the adminisrtrator an opportunity to ban/delete the map.
     * @param context Activity's context
     * @return the information about the map.
     */
    public Dialog popUpMapInfo(Context context) {
        final Context ctx = context;
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.report_map_popup);

        dialog.setTitle("Map Report");
        TextView text = (TextView) dialog.findViewById(R.id.mapID);
        text.setText("MapID : \n" + mapID);
        text = (TextView) dialog.findViewById(R.id.mapName);
        text.setText("Map Name : \n " + mapName);
        text = (TextView) dialog.findViewById(R.id.mapDescription);
        text.setText("Message : \n \"" + mapDescription + "\"");
        text = (TextView) dialog.findViewById(R.id.numOffReports);
        text.setText("Map description : \n " + NumOffensiveRep);
        text = (TextView) dialog.findViewById(R.id.numUnfReports);
        text.setText("Number of reports: \n" + NumUnfairRep);


        //close dialog button
        Button close = (Button) dialog.findViewById(R.id.closeMap);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //delete map button
        Button ban = (Button) dialog.findViewById(R.id.banUser1);
        ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean rm = MapReportServReq.deleteMap(mapID);
                Toast.makeText(ctx,"Map deleted!",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;

            }
        });

        return dialog;

    }

}