package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static edu.iastate.cs.proj_309_vc_b_4.game.Activities.ProfileImageAdapter.mThumbsIds;


/**
 * Created by Joseph on 9/24/2017.
 */

/**
 * This Adapter takes a list of races and lets them be displayed/selected
 */
public class RaceSelectorAdapter extends BaseAdapter {

    Context context;
    Bitmap bmp;

    RaceSelectorAdapter(Context context,Bitmap bmp) {
        this.context = context;
        this.bmp = bmp;
    }


    @Override
    public int getCount() {
        //TODO number of races being displayed
//        return 20;
//        return mThumbsIds.length;
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView iView;
        if(view == null){

            iView = new ImageView(context);
            iView.setLayoutParams(new GridView.LayoutParams(200, 200));
            iView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iView.setPadding(8, 8, 8, 8);

        }else{
            iView = (ImageView) view;
        }

        iView.setImageResource(mThumbsIds[i]);
        return iView;
    }
}
