package johan.istate.edu.recitationvolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import johan.istate.edu.recitationvolley.app.AppController;
import johan.istate.edu.recitationvolley.utils.Const;

public class StringActivity extends AppCompatActivity {
    private String TAG = StringActivity.class.getSimpleName();
    private Button btnStringReq;
    private TextView msgResponse;
    private ProgressDialog pDialog;
    private String tag_string_req = "string_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);

        btnStringReq = (Button) findViewById(R.id.stringRequest);
        msgResponse = (TextView) findViewById(R.id.textView);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnStringReq.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                makeStringReq();
            }
        });
    }

    private void showProgressDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideProgressDialog(){
        if(pDialog.isShowing()){
            pDialog.hide();
        }
    }

    //making request
    private void makeStringReq(){
        showProgressDialog();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Const.URL_STRING_REQ
                ,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Log.d(TAG,response.toString());
                msgResponse.setText(response.toString());
                hideProgressDialog();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"ERROR" + error.getMessage());
                hideProgressDialog();
            }
        });
        boolean ttt = (strReq == null);

        AppController ap = AppController.getInstance();
        Log.d("Test", String.valueOf(ap==null));
        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
    }
}
