package johan.istate.edu.loginviaphp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private static final String REGISTER_URL = "http://proj-309-vc-b-4.cs.iastate.edu/game/register_request.php";
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //e1=(EditText)findViewById(R.id.editText);
        editTextUsername = (EditText) findViewById(R.id.usernameRegister);
        editTextPassword = (EditText) findViewById(R.id.passwordRegister);

        b1=(Button)findViewById(R.id.goToLogin);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        b2=(Button)findViewById(R.id.buttonRegister);
        b2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(v == b2){
                    createUser();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createUser() {
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = Encryption.hash(editTextPassword.getText().toString().trim().toLowerCase());

        register(username,password);
    }

    private void register(String username, String password) {
        String urlSuffix = "?username="+username+"&password="+password;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressBar loading = (ProgressBar)findViewById(R.id.register_progress);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                Player player = gson.fromJson(s,Player.class);

                if(player != null)
                {
                    try {
                        String s1 = editTextUsername.getText().toString();
                        //Intent i1 = new Intent(MainActivity.this, Home.class);
                        //i1.putExtra("arg", s1);
                        //startActivity(i1);
                        Toast.makeText(getApplicationContext(),"You successfully made an account congrats.",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;
                    StringBuilder sb= new StringBuilder();
                    while((result = bufferedReader.readLine())!=null) {
                        sb.append(result);
                        Log.d("Reponse php", result);
                    }
                    return sb.toString();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Check your Internet connection",Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
