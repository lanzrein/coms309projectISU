package edu.iastate.cs.proj_309_vc_b_4.game.Activities.AdminMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.AdminMap;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapReportServReq;

/**
 * Display the list of reported map to the admin while also allows admin to decide whether they should ban
 * or ignore the reported maps.
 * Created by JeremyC on 11/29/2017.
 */

public class Reported_maps extends Fragment {
    ListView list;
    List<AdminMap> reports;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getContext();
        reports = MapReportServReq.getReports("/game/admin/RetrieveMapInfo.php");
        List<String> mapName = new ArrayList<>();

        for (AdminMap a : reports) {
            mapName.add(a.getMapName());
        }

        View v = inflater.inflate(R.layout.reported_maps_tab, container, false);
        list = (ListView) v.findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.admin_textview, mapName);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("InfoClick: ", position + " : id, \n " + id + " : l \n");
                AdminMap a = reports.get(position);
                Dialog dialog = a.popUpMapInfo(context);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent intent = new Intent(getContext(), AdminMain.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                });
            }
        });
        return list;
    }


}
