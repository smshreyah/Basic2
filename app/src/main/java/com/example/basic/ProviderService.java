package com.example.basic;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;

import com.aware.providers.Accelerometer_Provider;
import com.aware.utils.DatabaseHelper;
import com.aware.utils.Https;

import java.util.Hashtable;

public class ProviderService extends IntentService {

    public ProviderService() {
        super("ProviderService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        //String dataString = workIntent.getDataString();

        //Get the last 1000 records from accelerometer
        Cursor accelerometer_data = getContentResolver().query(Accelerometer_Provider.Accelerometer_Data.CONTENT_URI, null, null, null, Accelerometer_Provider.Accelerometer_Data.TIMESTAMP + "DESC LIMIT 1000");
        if( accelerometer_data != null && accelerometer_data.getCount() > 0 ) {
            Https https = new Https(getResources().openRawResource(R.raw.comwell));
            //put your certificate in /res/raw/your_certificate.crt
            Hashtable<String, String> postData = new Hashtable<>();
            postData.put("accelerometer_data", DatabaseHelper.cursorToString(accelerometer_data));
            https.dataPOST("https://compwell.ece.rice.edu/AWARE/index.php/webservice/index/2/Yc0ElAuSrvwv", postData, false); //set to true if your server supports gzip compression
        }
        if( accelerometer_data != null && ! accelerometer_data.isClosed() ) accelerometer_data.close();

    }

}
