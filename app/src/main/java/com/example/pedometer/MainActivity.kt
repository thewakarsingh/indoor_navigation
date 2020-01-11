package com.example.pedometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity() : AppCompatActivity(), SensorEventListener {
    var running = false
    var sensorManager:SensorManager? = null
    var oldsteps:Float = 0.0f
    var steps:Float=0.1f


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if(stepsSensor == null) {
            Toast.makeText(this, "sensor not found", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager?.registerListener(this,stepsSensor,SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running=false
        sensorManager?.unregisterListener(this)

    }


    override fun onAccuracyChanged(p0:Sensor?,p1:Int){

    }

    override  fun onSensorChanged(event: SensorEvent){

        resetsteps.setOnClickListener(){

            oldsteps=event.values[0]

            stepCount.setText(""+"00")
        }


        if(running){
            steps=event.values[0]-oldsteps
            stepCount.setText(""+steps)

        }
    }


    
}


