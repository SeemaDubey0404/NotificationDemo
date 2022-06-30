package com.example.notificationdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val channel_ID = "com.example.notificationdemo.channel1"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLY = "key_reply"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificaionChannel(channel_ID, "demo", "this is demo")
        button.setOnClickListener {
            displaynotiifcation()
        }
    }

    fun displaynotiifcation() {
        val notificationId = 44
        val tapResultIntent = Intent(this, SecoondActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, tapResultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        //reply action

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("insert your name here")
            build()
        }
        val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
            0, "Reply", pendingIntent
        ).addRemoteInput(remoteInput).build()
        //add action to a notification
        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2: PendingIntent = PendingIntent.getActivity(
            this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action2: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "details", pendingIntent2).build()

        //add action to a notification
        val intent3 = Intent(this, SettingsActivity::class.java)
        val pendingIntent3: PendingIntent = PendingIntent.getActivity(
            this, 0, intent3, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action3: NotificationCompat.Action =
            NotificationCompat.Action.Builder(0, "settings", pendingIntent3).build()


        val notification =
            NotificationCompat.Builder(this, channel_ID).setContentTitle("demo title")
                .setContentText("this is a ntification")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //  .setContentIntent(pendingIntent)
                .addAction(replyAction)
                .addAction(action2)
                .addAction(action3)
                .build()
        notificationManager?.notify(notificationId, notification)

    }

    private fun createNotificaionChannel(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val imprtance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, imprtance).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)

        }
    }
}