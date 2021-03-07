package com.example.pocketfootball;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    int vector_X, vector_Y, aceleracion= 10,px,py;
    float  inicial_Y, inicial_X;
    TextView lblBalon, posteINorte, posteDNorte, porteISur, posteDSur,lblGol;
    Display display ;
    int golA=0,golAb=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = getWindowManager().getDefaultDisplay();
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        lblBalon = findViewById(R.id.balon);
        posteINorte = findViewById(R.id.posteINorte);
        posteDNorte = findViewById(R.id.posteDNorte);
        porteISur = findViewById(R.id.posteISur);
        posteDSur = findViewById(R.id.posteDSur);
        lblGol = findViewById(R.id.txtMarcador);
        lblGol.setText("Marcador:"+golA+"-"+golAb);

        Point size = new Point();
        display.getSize(size);
        px = size.x;
        py = size.y;
        inicial_X = lblBalon.getX();
        inicial_Y = lblBalon.getY();
        Log.d("CALIS","Y:"+py);
        Log.d("CALIS","X:"+px);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (lblBalon.getX()>posteINorte.getX() & lblBalon.getX()< posteINorte.getX() & lblBalon.getY() >20 & lblBalon.getY() <=27  ){
            Log.d("CALIS","Gooool arribaaaaa");
            vector_X=0;
            vector_Y=0;
            lblBalon.setY(inicial_Y);
            lblBalon.setX(inicial_X);
            golA++;
            lblGol.setText("Marcador:"+golA+"-"+golAb);
        }else
            if (lblBalon.getX()> porteISur.getX() & lblBalon.getX()< posteDSur.getX() & lblBalon.getY() >=py-400){
            //Log.d("CALIS","Gooool abajoooooooo");
            vector_X=0;
            vector_Y=0;
            lblBalon.setY(inicial_Y);
            lblBalon.setX(inicial_X);
            golAb++;
            lblGol.setText("Marcador:"+golA+"-"+golAb);
        }else {
            if (event.values[0] < 0.1 & lblBalon.getX() < px - lblBalon.getWidth()) {
                vector_X += 5;
                lblBalon.setTranslationX(vector_X * aceleracion);
            } else if (event.values[0] > 0.1 & lblBalon.getX() > 0) {
                vector_X -= 5;
                lblBalon.setTranslationX(vector_X * aceleracion);
            }
            if (event.values[1] > 0.1 & lblBalon.getY() < py - 400) {
                vector_Y += 5;
                lblBalon.setTranslationY(vector_Y * aceleracion);
            } else if (event.values[1] < 0.1 & lblBalon.getY() > 50) {
                vector_Y -= 5;
                lblBalon.setTranslationY(vector_Y * aceleracion);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}