package com.johan.dev;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by johan on 10.09.2017.
 */

public class ClientActivity extends Activity {

    private ListView mList;
    private ArrayList<String> arrayList;
    private ClientListAdapter mAdapter;
    private TcpClient mTcpClient;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        arrayList = new ArrayList<String>();

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button send = (Button) findViewById(R.id.send_button);

        //link listView java to the xml one
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ClientListAdapter(this,arrayList);
        mList.setAdapter(mAdapter);

        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               String msg = editText.getText().toString();

                //add the msg to arrayList
                arrayList.add("C:" + msg);
                //send msg to server
                boolean xx= (mTcpClient == null);
                Log.e("Tcp check",String.valueOf(xx) );
                if(mTcpClient != null){
                    Log.d("Sending..", "sending");
                    mTcpClient.sendMessage(msg);
                }

                //refresh the list
                mAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }


    @Override
    protected void onPause(){
        super.onPause();
        //disconnect
        if(mTcpClient!=null) {
            mTcpClient.stopClient();
            mTcpClient = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(mTcpClient != null){
            //enable connect button / disable the disconnect one
            menu.getItem(1).setEnabled(true);
            menu.getItem(0).setEnabled(false);
        }else{
            menu.getItem(1).setEnabled(false);
            menu.getItem(0).setEnabled(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.connect:
                String username = PreferencesManager.getInstance().getUserName();
                if(username!=null) {
                    new ConnectTask().execute("");
                }else{
                    Toast.makeText(this,"Please go to preferences ans set a username",Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.disconnect:
                if(mTcpClient == null){
                    return true;
                }
                mTcpClient.stopClient();
                mTcpClient =null;
                arrayList.clear();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.preferences:
                startActivity(new Intent(this, PreferencesActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {
        //create a new tcpclient
        @Override
        protected TcpClient doInBackground(String... msg){
            Log.d("tcp client", "tcpiiing");
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived(){

                @Override
                public void messageReceived(String msg){
                    publishProgress(msg);
                }
            });
            mTcpClient.run();
            return null;
        }


        @Override
        protected void onProgressUpdate(String... values){
            super.onProgressUpdate(values);
            //add the msg from server in array list
            arrayList.add(values[0]);
            //notify adapter that the data has changed.

            mAdapter.notifyDataSetChanged();
        }
    }


}
