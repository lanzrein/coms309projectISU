package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import edu.iastate.cs.proj_309_vc_b_4.game.R;

/**
 * A class to display easily all of our profile picutre.
 * In case new image are added they should only be added where specify.
 */
//taken from an adapted
//https://developer.android.com/guide/topics/ui/layout/gridview.html
public final class ProfileImageAdapter extends BaseAdapter {
    private final Context mCtx;

    //reference to image.
    public static Integer[] mThumbsIds = {R.drawable.default_picture,R.drawable.dog,R.drawable.hammer,
            R.drawable.snake, R.drawable.base,R.drawable.helmet,R.drawable.rabbit,R.drawable.logo
            //ADD ANY NEW PROFILE PICTURES HERE AND THE REST WILL BE DONE AUTOMATICALLY ( SHOULD )
        };

    /**
     * Constructor.
     * @param c
     */
    public ProfileImageAdapter(Context c){
        mCtx = c;
    }

    /**
     *
     * @return number of items in the adapter
     */
    public int getCount(){
        return mThumbsIds.length;
    }

    /**
     * DO NOT USE
     * @param pos
     * @return
     */
    public long getItemId(int pos){
        return 0;
    }

    /**
     * DO NOT USE
     * @param pos
     * @return
     */
    public Object getItem(int pos){
        return null;
    }

    /**
     * Returns the view of the image adapter.
     * It sets them in 2 colons.
     *
     * @param position the position of the item
     * @param view
     * @param parent
     * @return the view created
     */
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
