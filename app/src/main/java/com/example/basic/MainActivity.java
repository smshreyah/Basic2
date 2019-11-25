package com.example.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Likert;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    Button button_ESMNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_ESMNotification = (Button)findViewById(R.id.button);
        Log.d("Main", "Before aware intent");
        Intent aware = new Intent(this, Aware.class);
        startActivity(aware);

        Log.d("Main", "Before provider service intent");
        Intent intent = new Intent(MainActivity.this, ProviderService.class);
        this.startService(intent);

        Log.d("Main", "After provider service");

        Aware.setSetting(this, Aware_Preferences.DEBUG_FLAG, true);

        button_ESMNotification = (Button) findViewById(R.id.button);
        button_ESMNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
                TestESM testESM = new TestESM();
                testESM.test(getApplicationContext());
            }
        });

        //Accelerometer
        Aware.setSetting(getApplicationContext(), Aware_Preferences.FREQUENCY_ACCELEROMETER, 200000); //20Hz
        Aware.setSetting(getApplicationContext(), Aware_Preferences.THRESHOLD_ACCELEROMETER, 0.02f); // [x,y,z] > 0.02 to log

        //Applying Settings
        sendBroadcast(new Intent(Aware.ACTION_AWARE_CURRENT_CONTEXT));



    }


}
