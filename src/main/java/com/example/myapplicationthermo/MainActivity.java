package com.example.myapplicationthermo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView;
    private TextView textView2;
    private SensorManager sensorManager;
    private Sensor hofok;
    private Boolean isrunning;
    private Boolean isrunningpara;
    private Sensor paratartalom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null)
        {
            hofok = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isrunning = true;
        }
        else
        {
            textView.setText("Nincs ilyen hőfok szenzor");
            isrunning = false;
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)!=null)
        {
            paratartalom = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isrunningpara = true;
        }
        else
        {
            textView.setText("Nincs ilyen páratartalom szenzor");
            isrunningpara = false;
        }



    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            if (event.values[0] <= 0) {
                textView.setText("Most " + event.values[0] + "°C van. Még akár havazásra is számíthatsz.");
            } else if (event.values[0] <= 20) {
                textView.setText("Most " + event.values[0] + "°C van. Ötlözz melegen!");
            } else if (event.values[0] <= 37) {
                textView.setText("Most " + event.values[0] + "°C van. Ma nagyon meleg lesz.");
            } else {
                textView.setText("Most " + event.values[0] + "°C van. Kánikula van, húzódj az árnyékba!");
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            if (event.values[0] <= 60 && event.values[0] >= 40  ) {
                textView2.setText("A páratartalom " + event.values[0] + "%. Éppen ideális");
            }else if (event.values[0] >60){
                textView2.setText("A páratartalom " + event.values[0] + "%. Páramentesítsen!");
            }else
            {
                textView2.setText("A páratartalom " + event.values[0] + "%. Párásítson!");
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isrunning)
        {
            sensorManager.registerListener(this, hofok, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isrunningpara)
        {
            sensorManager.registerListener(this, paratartalom, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isrunning)
        {
            sensorManager.unregisterListener(this);
        }
        if(isrunningpara)
        {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}







