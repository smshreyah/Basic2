package com.example.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    Button sendEMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendEMS = (Button)findViewById(R.id.button);
        sendEMS.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "ONCLICKLISTENER", Toast.LENGTH_SHORT).show();
                        try {
                            ESMFactory factory = new ESMFactory();

                            //define ESM question
                            ESM_Likert esmLikert = new ESM_Likert();
                            esmLikert.setLikertMax(5)
                                    .setLikertMaxLabel("Great")
                                    .setLikertMinLabel("Poor")
                                    .setLikertStep(1)
                                    .setTitle("Likert")
                                    .setInstructions("Likert ESM")
                                    .setSubmitButton("OK");

                            //add them to the factory
                            factory.addESM(esmLikert);

                            //Queue them
                            ESM.queueESM(getApplicationContext(), factory.build());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Intent aware = new Intent(this, Aware.class);

        //Accelerometer
        Aware.setSetting(getApplicationContext(), Aware_Preferences.FREQUENCY_ACCELEROMETER, 200000); //20Hz
        Aware.setSetting(getApplicationContext(), Aware_Preferences.THRESHOLD_ACCELEROMETER, 0.02f); // [x,y,z] > 0.02 to log

        //Applying Settings
        sendBroadcast(new Intent(Aware.ACTION_AWARE_CURRENT_CONTEXT));

        Intent intent = new Intent(this, ProviderService.class);
        startService(intent);

    }


}
