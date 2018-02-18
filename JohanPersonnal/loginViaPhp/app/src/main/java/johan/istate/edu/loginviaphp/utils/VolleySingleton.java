package johan.istate.edu.loginviaphp.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Class to handle all volley request.
 * We use the singleton pattern in order to have a single instance for all the current instance
 * Created by johan on 21.09.2017.
 */

public class VolleySingleton {
    //our current instance
    private static VolleySingleton mInstance;
    //private attributes
    private RequestQueue mRequestQueue;
    private static Context mCtx;



    //When working on it to debug try "localhost"
    //else "proj-309-vc-b-4.cs.iastate.edu";
    //public final static String SERVER_ADDRESS ="http://proj-309-vc-b-4.cs.iastate.edu";
    public final static String SERVER_ADDRESS = "http://proj-309-vc-b-4.cs.iastate.edu/";
    /**
     * Private contructor takes the current context
     * @param context the context
     */
    private VolleySingleton(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * This method returns the current VolleySingleton instance.
     * It is synchronized so there is no concurrent or race issues
     * @param context the current context
     * @return the VolleySingleton
     */
    public static synchronized VolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Get the request queue of our Instance. Again we only want one single request queue.
     * @return the request queue
     */
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Add a request of any type to our queue
     * @param req the request
     * @param <T> the type of the Request.
     */
    public <T> void addToRequestQueue(Request<T> req){
        Log.d("VOLLEYQUEUE", "Adding to queue request : "+(req).toString());
        getRequestQueue().add(req);
    }

}
