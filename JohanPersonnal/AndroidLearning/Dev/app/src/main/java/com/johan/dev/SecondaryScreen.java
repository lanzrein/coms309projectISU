package com.johan.dev;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by johan on 31.08.2017.
 */

public class SecondaryScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_screen);

    }

    public void onPressSec(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToDragNDrop(View view){
        Intent intent = new Intent(this, DragActivity.class);
        startActivity(intent);
    }

    public void goToClient(View view){
        Intent intent = new Intent(this,ClientActivity.class);
        startActivity(intent);
    }
    public void goBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * makes a pop up appear. ~~ used to see a friends list etc.
     *
     * @param view
     */
    public void popUp(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_dialog_message)
        .setTitle(R.string.confirm_dialog_title)
        .setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                goBack();
                dialog.dismiss();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //cancel
                dialogInterface.dismiss();
            }
        })
        ;
        builder.create().show();
    }
}
