package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * adapter for the terrain type.
 * Works like the profile image adapter.
 * Created by johan on 23.11.2017.
 */

public final class TerrainTypeImageAdatper extends BaseAdapter {
    private final Context mCtx;


    //ref for images.. needs to be in order
    /*   GRASS,
    PATH,
    WATER,
    ROCK,
    BASE,
    SPAWN;*/
    public static Integer[] mThumbsIds = {R.drawable.grass_texture,R.drawable.path_texture, R.drawable.water_texture,R.drawable.rock_texture,
        R.drawable.base_texture};

    public TerrainTypeImageAdatper(Context ctx){this.mCtx = ctx;}


    public int getCount(){
        return mThumbsIds.length;
    }

    public long getItemId(int pos){
        return 0;
    }
    public Object getItem(int pos){
        return null;
    }


    public View getView(int position, View view, ViewGroup parent){
        ImageView iView;
        if(view == null){

            iView = new ImageView(mCtx);
            iView.setLayoutParams(new GridView.LayoutParams(200, 200));
            iView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iView.setPadding(8, 8, 8, 8);
        }else{
            iView = (ImageView) view;
        }

        iView.setImageResource(mThumbsIds[position]);
        return iView;
    }
}
