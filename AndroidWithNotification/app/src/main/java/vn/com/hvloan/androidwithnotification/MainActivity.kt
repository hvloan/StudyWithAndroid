package vn.com.hvloan.androidwithnotification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationBuilder: Notification.Builder
    lateinit var notificationChannel: NotificationChannel
    private val channelID = "vn.com.hvloan.androidwithnotification"
    private val channelDescription = "VKU N5 Notification Demo"

    lateinit var titleNotificationBasic: TextInputEditText
    lateinit var contentNotificationBasic: TextInputEditText
    private lateinit var buttonCreateNotificationBasic: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        actionComponent()

    }

    private fun actionComponent() {
        buttonCreateNotificationBasic.setOnClickListener {
            createNotificationBasic()
        }
    }

    private fun createNotificationBasic() {
        val titleNotification = titleNotificationBasic.text
        val contentNotification = contentNotificationBasic.text

        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_MUTABLE)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID,channelDescription, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder = Notification.Builder(this,channelID)
            notificationBuilder.setContentTitle(titleNotification)
                .setContentText(contentNotification)
                .setSmallIcon(R.drawable.ic_notifications)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        else{
            notificationBuilder = Notification.Builder(this)
            notificationBuilder.setContentTitle(titleNotification)
                .setContentText(contentNotification)
                .setSmallIcon(R.drawable.ic_notifications)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        notificationManager.notify(6789,notificationBuilder.build())

    }

    private fun initComponent() {
        titleNotificationBasic = findViewById(R.id.titleNotificationBasic)
        contentNotificationBasic = findViewById(R.id.contentNotificationBasic)
        buttonCreateNotificationBasic = findViewById(R.id.btnCreateNotificationBasic)
    }
}