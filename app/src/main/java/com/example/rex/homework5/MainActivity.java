package com.example.rex.homework5;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

;


public class MainActivity extends ActionBarActivity {
    private Button btnOK,btnCancel;
    private TextView txtView;
    private EditText editText;
    private MyService myService;
    private boolean bound;
    protected NotificationManager notificationManager;

    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService= ((MyService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {myService=null;}
    };

    private android.os.Handler handler = new android.os.Handler();

    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            txtView.setText(editText.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();

    }

    protected void findviews(){
        btnOK = (Button)findViewById(R.id.btnOK);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        txtView =(TextView)findViewById(R.id.txtView);
        editText = (EditText)findViewById(R.id.editText);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bound){
                    Intent intent= new Intent(MainActivity.this, MyService.class);
                    bindService(intent,serviceConn,BIND_AUTO_CREATE);
                    bound = true;
                    handler.postDelayed(runnable,3000);
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bound){
                    myService.cancelNotification();
                    unbindService(serviceConn);
                    txtView.setText("");
                    editText.setText("");
                    bound=false;
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
