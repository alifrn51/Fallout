package com.fall.fallout.utils.serviceLocation

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.fall.fallout.R
import com.fall.fallout.utils.Constants
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FallDetectionService :  SensorEventListener, LifecycleService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    //sensor acceleration
    private var  sensorManager: SensorManager? = null
    private var acceleration: Sensor? =null
    private var range = 0

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        initializingSensorServices()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext),
            interval = 3000L
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        fallDetection?.isFallDetect?.observe(this ){ isFall ->


            if (isFall){
                notificationManager.notify(Constants.NOTIFICATION_FALL_ID,  createNotificationForService().setContentText("Fall detected!").build())
            }

        }


        startForeground(Constants.NOTIFICATION_ID, createNotificationForService().build())

    }

    private fun createNotificationForService(): NotificationCompat.Builder{

        return  NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Fallout")
            .setContentText("The application is running...!")
            .setSmallIcon(R.drawable.ic_logo)
            .setOngoing(false)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {





        /*locationClient.getLocationUpdates()
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString().takeLast(3)
                val long = location.longitude.toString().takeLast(3)
                val updatedNotification = notification.setContentText(
                    "Location : ($lat, $long"
                )
                notificationManager.notify(Constants.NOTIFICATION_ID, updatedNotification.build())

            }
            .launchIn(serviceScope)*/
    }

    private fun stop() {


        stopForeground(Constants.NOTIFICATION_ID)
        stopSelf()

    }



    override fun onDestroy() {
        super.onDestroy()

        sensorManager?.unregisterListener(this)
        serviceScope.cancel()

        fallDetection = null
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        var fallDetection: FallDetection? = null


    }

    //initializing sensor services
    private fun initializingSensorServices() {
        sensorManager = applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
        acceleration = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        range = acceleration!!.maximumRange.toInt()
        sensorManager!!.registerListener(this, acceleration, 20000)

        fallDetection = FallDetection(applicationContext, range)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        fallDetection?.detectFall(event)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}