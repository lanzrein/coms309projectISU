package edu.iastate.cs.proj_309_vc_b_4.game.Activities;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.Encryption;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton;

import static edu.iastate.cs.proj_309_vc_b_4.game.Activities.RegisterActivity.checkIfEmpty;
import static edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton.SERVER_ADDRESS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private final Context context = this;

    private EditText username;
    private EditText password;
    private ProgressBar progressBar;
    private Button button;
    private TextView backToLogin;
    private Button buttonTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Chekc if user is already logged in
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, null));
        }

        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

    /**
     * takes the actions to login the user when he confirms the login.
     * It will either proceed to an other activity ( Main menu )
     * or show a toast explaining the error that happened.
     * It requires a higher API because we encrypt the password.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void userLogin() {
        final String sUsername = username.getText().toString();
        String sPasswordBeforeHash = password.getText().toString();
        if (checkIfEmpty(sUsername, "username", username)) return;
        if (checkIfEmpty(sPasswordBeforeHash, "password", password)) return;
        final String sPassword = Encryption.hash(sPasswordBeforeHash);
        String url = SERVER_ADDRESS+"/game/account/login_request.php"+"?username="+sUsername+"&password="+sPassword;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                            String obj = response;
                            Log.d("Object JSON : ", obj);
                            if(!response.contains("ERROR")) {
                                Gson gson = new Gson();
                                Player player = gson.fromJson(obj, Player.class);
//                                finish();
                                Toast.makeText(getApplicationContext(), "We have a player ! : " + player.getUsername() + " welcome back !", Toast.LENGTH_SHORT).show();
                                //add it to the shared preferences.
                                SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                                spm.userLogin(player,sPassword);
                                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Error during logging. try again.", Toast.LENGTH_LONG).show();

                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error Volley :"+error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        });


        //now we send
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}

