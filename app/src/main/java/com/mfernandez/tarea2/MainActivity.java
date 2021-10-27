package com.mfernandez.tarea2;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    Sensor sensor1, sensor2;
    SensorManager sensorManager;

    TextView lbl_sensor1, lbl_sensor2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lbl_sensor1 = (TextView) findViewById(R.id.sensor1);
        lbl_sensor2 = (TextView) findViewById(R.id.lbl_sensor2);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor1 = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor2 = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, sensor1,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensor2,SensorManager.SENSOR_DELAY_NORMAL);



    }

    public void llamarADormir(View v){
        Toast.makeText(this, "Iniciando sleep", Toast.LENGTH_SHORT).show();
        for(int i = 0; i <= 10; i++){
            adormir();
        }
        Toast.makeText(this, "Fin sleep", Toast.LENGTH_SHORT).show();
    }

    private void adormir(){
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void segundoHilo(View v){
        Toast.makeText(this, "Iniciando segundo Hilo", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++){
                    adormir();
                }
                Toast.makeText(getApplicationContext(), "Finalizando segundo hilo", Toast.LENGTH_SHORT).show();
            }
        }).start();
        Toast.makeText(this, "Fin segundo hilo?", Toast.LENGTH_SHORT).show();
    }

    public void tercerHilo(View v){
        Toast.makeText(this, "Iniciando tercer hilo", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++){
                    adormir();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Finalizando tercer hilo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
        Toast.makeText(this, "Fin tercer hilo?", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                lbl_sensor1.setText(String.valueOf(sensorEvent.values[0]));
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                lbl_sensor2.setText(String.valueOf(sensorEvent.values[0]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}