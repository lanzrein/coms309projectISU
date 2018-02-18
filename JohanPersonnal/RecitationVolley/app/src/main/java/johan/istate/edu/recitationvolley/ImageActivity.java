package johan.istate.edu.recitationvolley;

import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Cache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.UnsupportedEncodingException;

import johan.istate.edu.recitationvolley.app.AppController;
import johan.istate.edu.recitationvolley.utils.Const;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG =ImageActivity.class.getSimpleName();
    private Button btnImageReq;
    private NetworkImageView imgNetworkView;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        btnImageReq = (Button) findViewById(R.id.imagRequest);
        imgNetworkView = (NetworkImageView)findViewById(R.id.imgNetwork);
        imageView = (ImageView)findViewById(R.id.imgView);

        btnImageReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeImageRequest();
            }
        });
    }

    private void makeImageRequest(){
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        imgNetworkView.setImageUrl(Const.URL_IMAGE,imageLoader);
        imageLoader.get(Const.URL_IMAGE,ImageLoader.getImageListener(imageView, R.drawable.ico_loading,R.drawable.ico_error));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Const.URL_IMAGE);
        if(entry!=null){
            try{
                String data = new String(entry.data,"UTF-8");
                //handla data...
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }else{
            //'???????
            Log.d(TAG, "IDK WHAT TO DO");
        }

    }
}
