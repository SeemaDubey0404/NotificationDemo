package com.example.notificationdemo

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main2.*

class SecoondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        receiveInput()
    }

    private fun receiveInput() {
        val KEY_REPLY = "key_reply"

        val intent: Intent = this.intent
        val remoteInput: Bundle = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            result_text_view.text = inputString
            val channel_ID = "com.example.notificationdemo.channel1"
            val notificationId = 44
            val repliedNotification = NotificationCompat.Builder(this, channel_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("your reply received")
                .build()
            val notificationManger: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.notify(notificationId, repliedNotification)


        }

    }
}