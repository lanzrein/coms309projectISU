package johan.istate.edu.loginviaphp;


import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import johan.istate.edu.loginviaphp.utils.SharedPrefManager;
import johan.istate.edu.loginviaphp.utils.VolleySingleton;

import static johan.istate.edu.loginviaphp.utils.VolleySingleton.SERVER_ADDRESS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private ProgressBar progressBar;
    private Button button;
    private TextView backToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Chekc if user is already logged in
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, null));//TODO put main menu instead of null
        }

        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                userLogin();
            }
        });

        backToLogin = (TextView) findViewById(R.id.backToRegister);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to register screen
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void userLogin() {
        final String sUsername = username.getText().toString();
        String sPasswordBeforeHash = password.getText().toString();
        if (checkIfEmpty(sUsername, "username", username)) return;
        if (checkIfEmpty(sPasswordBeforeHash, "password", password)) return;
        final String sPassword = Encryption.hash(sPasswordBeforeHash);
        //since we dont know the playerID yet we put -1.
//        final RequestPlayer reqPlayer = new RequestPlayer(RequestID.LOGIN.ordinal(),sUsername,sPassword,-1);
//        final Gson gson = new Gson();
//        JSONObject json = null;
//        try {
//            json = new JSONObject(gson.toJson(reqPlayer));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("JSON","Json is : "+json.toString());
//        if(json == null){
//            Toast.makeText(getApplicationContext(),"Error : json parsing",Toast.LENGTH_SHORT).show();
//            return;
//        }
        String url = SERVER_ADDRESS+"game/login_request.php"+"?username="+sUsername+"&password="+sPassword;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        String obj = response.toString();
                        Toast.makeText(getApplicationContext(), obj, Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        Player player = gson.fromJson(obj,Player.class);
                        finish();
                        Toast.makeText(getApplicationContext(),"We have a player ! : "+player.getUsername() +" welcome back !",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(),PlayMenuActivity.class));

                                /*GOTO MAIN ACTIVITY TODO*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Volley :" + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        //now we send
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

    //TODO maybe put it in an interface...
    public static boolean checkIfEmpty(String sUsername, String item,EditText eText) {
        if(TextUtils.isEmpty(sUsername)){
            eText.setError("Please enter "+item);
            eText.requestFocus();
            return true;
        }
        return false;
    }
}

