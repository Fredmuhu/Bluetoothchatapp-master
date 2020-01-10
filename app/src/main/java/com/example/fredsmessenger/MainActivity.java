package com.example.fredsmessenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
Button buttonON, buttonOFF, buttonSCAN, buttonDisc;
     ListView listView;
     ArrayAdapter aAdapter;
     BluetoothAdapter myBluetoothAdapter;

Intent btEnablingIntent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestCodeForEnable){
            if(resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(), "Bluetooth is enabled", Toast.LENGTH_LONG).show();
            }else if (resultCode==RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Bluetooth enabling failed", Toast.LENGTH_LONG).show();

            }
        }
    }

    int requestCodeForEnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                buttonON=(Button) findViewById(R.id.btON);
                buttonOFF=(Button) findViewById(R.id.btOFF);
                buttonSCAN= (Button) findViewById(R.id.btScan);
                buttonDisc= (Button) findViewById(R.id.btDisc);
                listView= (ListView) findViewById(R.id.deviceList);
                myBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
                btEnablingIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                requestCodeForEnable=1;
buttonDisc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openDiscoverDevices();
    }

    public void openDiscoverDevices() {
        Intent myIntent= new Intent(MainActivity.this, AvailableDevices.class);
    startActivity(myIntent);
    }
});


                bluetoothONMethod();
                bluetoothOFFMethod();
                bluetoothSCANMethod();

            }
        });
    }

    private void bluetoothSCANMethod() {
        buttonSCAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> bt=myBluetoothAdapter.getBondedDevices();
        String[] strings=new String[bt.size()];
        int index=0;

        if (bt.size()>0){
            for (BluetoothDevice device:bt){
                strings[index]=device.getName();
                index++;

            }
            aAdapter= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
            listView.setAdapter(aAdapter);

        }
            }
        });
    }

    private void bluetoothOFFMethod() {
        buttonOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBluetoothAdapter.isEnabled()) {
                    myBluetoothAdapter.disable();
                }
            }
        });
    }

    private void bluetoothONMethod() {
        buttonON.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(myBluetoothAdapter==null){

                    Toast.makeText(getApplicationContext(),"Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
                }else
                {
                    if(!myBluetoothAdapter.isEnabled())
                    {
                        startActivityForResult(btEnablingIntent,requestCodeForEnable);

                    }
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
