package com.johan.recitation3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sms_activity extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText smsBody;
    private Button smsManagerBtn;
    private Button smsSendToBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_activity);

        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        smsBody=(EditText)findViewById(R.id.smsBody);
        smsManagerBtn = (Button) findViewById(R.id.smsManager);
        smsSendToBtn = (Button)findViewById(R.id.smsSIntent);

        smsManagerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sensSmsByManager();
            }
        });

        smsSendToBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                sendSmsBySIntent();
            }
        });
    }


    public void sensSmsByManager(){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber.getText().toString(),null
            ,smsBody.getText().toString(),null,null);
            Toast.makeText(getApplicationContext(), "your sms has been sent!", Toast.LENGTH_LONG)
                    .show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Your sms failed...",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void sendSmsBySIntent(){
        Uri uri = Uri.parse("smsto:"+phoneNumber.getText().toString());
        Intent smsSintent = new Intent(Intent.ACTION_SENDTO,uri);
        smsSintent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsSintent);
        }catch(Exception e){
            Toast.makeText(sms_activity.this,"Your sms failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
