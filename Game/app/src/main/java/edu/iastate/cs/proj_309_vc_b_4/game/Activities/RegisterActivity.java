package edu.iastate.cs.proj_309_vc_b_4.game.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus.PlayMenuActivity;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.Encryption;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton;

import static edu.iastate.cs.proj_309_vc_b_4.game.utils.VolleySingleton.SERVER_ADDRESS;

/**
 * This activity allows a user to create a new account and register it.
 * it is the 1st activity displayed when opening the app.
 */
public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //if the user is actually logged in skipp it and go to main menu_leaderboard
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
//            finish();
            startActivity(new Intent(this, MainMenu.class));
            return ;
        }
        username = (EditText) findViewById(R.id.usernameRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        findViewById(R.id.createAccount).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                registerUser();

                startActivity(new Intent(RegisterActivity.this,MainMenu.class));

            }
        });

        findViewById(R.id.backToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUser(){
        progressBar.setVisibility(View.VISIBLE);
        final String sUsername = username.getText().toString().trim();
        String sPasswordBeforeHash = password.getText().toString().trim();

        if (checkIfEmpty(sUsername,"username",username)) return;
        if (checkIfEmpty(sPasswordBeforeHash,"password",password)) return;
        final String sPassword = Encryption.hash(sPasswordBeforeHash);

        //Need to serialize REQID , username, password to JSON in that order.
        final Gson gson = new Gson();
        //help from requestPlayer
        //since the player is not yet registered we put -1 as his ID
        String url = SERVER_ADDRESS+"/game/account/register_request.php?username="+sUsername+"&password="+sPassword;
        Log.d("URL" , url);
        //This is the actual request..
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                            String obj = response.toString();
                            if(obj.contains("ERROR")){
                                Toast.makeText(getApplicationContext(), "Error during registration try again.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                                Player player = gson.fromJson(obj,Player.class);
                                finish();
                                //okay we can put the player in sharedpreferences
                                SharedPrefManager spm = SharedPrefManager.getInstance(getApplicationContext());
                                spm.userLogin(player,sPassword);
                                startActivity(new Intent(getApplicationContext(), MainMenu.class));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error...", "Response error");
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    /**
     * This checks if a string is empty.
     * And if it is will put focus on the edit text containing it.
      * @param sUsername the string
     * @param item
     * @param eText
     * @return true if sUsername is empty.
     */
    public static boolean checkIfEmpty(String sUsername, String item,EditText eText) {
        if(TextUtils.isEmpty(sUsername)){
            eText.setError("Please enter "+item);
            eText.requestFocus();
            return true;
        }
        return false;
    }

}
