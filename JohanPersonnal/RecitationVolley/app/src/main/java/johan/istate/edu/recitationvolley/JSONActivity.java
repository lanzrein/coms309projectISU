package johan.istate.edu.recitationvolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import johan.istate.edu.recitationvolley.app.AppController;
import johan.istate.edu.recitationvolley.utils.Const;

public class JSONActivity extends AppCompatActivity {
    private String TAG = JSONActivity.class.getSimpleName();
    private Button btnJsonObj;
    private Button btnJsonArry;
    private TextView msgResponse;
    private String tag_json_obj = "jobj_req";
    private String tag_json_array= "jarray_req";
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        btnJsonArry = (Button) findViewById(R.id.JSONArrReq);
        btnJsonObj = (Button) findViewById(R.id.JSONObjRequest);
        msgResponse = (TextView) findViewById(R.id.textView);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading..");
        pDialog.setCancelable(false);
        btnJsonObj.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                makeJsonObjReq();
            }
        });
        btnJsonArry.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                makeJsonArryReq();
            }
        });
    }

    private void hideProgressDialog(){
        if(pDialog.isShowing()){
            pDialog.hide();
        }
    }
    private void showProgressDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void makeJsonObjReq(){
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, Const.URL_JSON_OJECT,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        Log.d(TAG,response.toString());
                        msgResponse.setText((response.toString()));
                        hideProgressDialog();
                    }


                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError err){
                        VolleyLog.d(TAG,"ERROR:"+err.getMessage());
                        hideProgressDialog();
                    }
                }) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("name","Androidhive");
                params.put("email", "abs@androidhive.info");
                params.put("pass", "password123");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(
                jsonObjReq,tag_json_obj
        );

    }


    private void makeJsonArryReq(){
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(
                Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"ERROR" + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(req,
                tag_json_array);

    }

}
