package johan.istate.edu.recitationvolley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void stringRequest(View view){
        Intent intent = new Intent(this,StringActivity.class);
        startActivity(intent);
    }

    public void jsonRequest(View view){
        Intent intent = new Intent(this,JSONActivity.class);
        startActivity(intent);
    }
    public void imageRequest(View view){
        Intent intent = new Intent(this,ImageActivity.class);
        startActivity(intent);
    }
}