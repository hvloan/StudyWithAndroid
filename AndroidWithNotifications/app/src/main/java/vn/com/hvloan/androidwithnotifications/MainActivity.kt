package vn.com.hvloan.androidwithnotifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var btnSimpleText: Button
    lateinit var btnBigText: Button
    lateinit var btnBigPicture: Button
    lateinit var btnInbox: Button
    lateinit var btnMessaging: Button

    private val CHANNELID = "NOTIFY"
    private val CHANNELNAME = "NOTIFICATION"
    private val NOTIFICATIONID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        actionComponent()

    }

    private fun actionComponent() {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        btnSimpleText.setOnClickListener {
            notificationManager.sendTextNotification("Basic Text", "This is basic text body")
        }

        btnBigText.setOnClickListener {
            notificationManager.sendBigTextNotification("Big Text", "This is big text body", "Here is BIG TEXT BIG TEXT BIG TEXT BIG TEXT")
        }

        btnBigPicture.setOnClickListener {
            notificationManager.sendBigPictureNotification("Big Picture", "This is big picture body")
        }

        btnInbox.setOnClickListener {
            notificationManager.sendInboxNotification("Inbox Text", "This is inbox text body")
        }

        btnMessaging.setOnClickListener {
            notificationManager.sendMessagingNotification("Massage Text", "This is massage body")
        }
    }

    private fun createChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_HIGH)
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Show Notifications"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)

        }

    }

    //Notifications

    // BASIC TEXT NOTIFICATION
    private fun NotificationManager.sendTextNotification(basicTextTitle: String, basicTextBody: String) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_MUTABLE)

        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setSmallIcon(R.drawable.rainbow)
            .setContentTitle(basicTextTitle)
            .setContentText(basicTextBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())

        createChannel()
    }

    // BIG TEXT NOTIFICATION
    private fun NotificationManager.sendBigTextNotification(bigTextTitle: String, bigTextBody: String, bigText: String) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_MUTABLE)

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(bigText)

        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(bigTextStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setContentTitle(bigTextTitle)
            .setContentText(bigTextBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())

        createChannel()
    }

    // BIG PICTURE NOTIFICATION
    private fun NotificationManager.sendBigPictureNotification(imageTextTitle: String, imageTextBody: String) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_MUTABLE)

        // create the bitmap
        val rainbowImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.rainbow
        )

        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(rainbowImage)
            .bigLargeIcon(null)

        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(bigPicStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setContentTitle(imageTextTitle)
            .setContentText(imageTextBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())

        createChannel()
    }

    // INBOX NOTIFICATION
    private fun NotificationManager.sendInboxNotification(inboxTextTitle: String, inboxTextBody: String) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_MUTABLE)

        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine("Thanks for subscribe")
            .addLine("Here the news for today")
            .addLine("We need you to pay ... ")
            .setBigContentTitle("You got 20 Emails")
            .setSummaryText("You got 17 more emails")

        // create the bitmap
        val rainbowImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.rainbow
        )


        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(inboxStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setLargeIcon(rainbowImage)
            .setContentTitle(inboxTextTitle)
            .setContentText(inboxTextBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())

        createChannel()
    }

    // MESSAGING NOTIFICATION
    private fun NotificationManager.sendMessagingNotification(massageTextTitle: String, massageTextBody: String) {

        cancelAll()
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent  = PendingIntent.getActivity(applicationContext, NOTIFICATIONID, contentIntent, PendingIntent.FLAG_MUTABLE)

        val messagingStyle = NotificationCompat.MessagingStyle("Jessica")
            .setConversationTitle(applicationContext.getString(R.string.messaging))
            .addMessage("Hi! I'm hungry!",System.currentTimeMillis()-60000,"Cat")
            .addMessage("Where are you?",System.currentTimeMillis()-60000,"Cat")
            .addMessage("Lets go play",System.currentTimeMillis()-30000,"Dog")
            .addMessage("Miss you",System.currentTimeMillis(),"Dog")
            .setGroupConversation(true)



        // Build the notification
        val builder = NotificationCompat
            .Builder(applicationContext,
                CHANNELID)
            .setStyle(messagingStyle)
            .setSmallIcon(R.drawable.rainbow)
            .setContentTitle(massageTextTitle)
            .setContentText(massageTextBody)
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        notify(NOTIFICATIONID, builder.build())

        createChannel()
    }

    private fun initComponent() {
        btnSimpleText = findViewById(R.id.btnSimpleText)
        btnBigPicture = findViewById(R.id.btnBigPicture)
        btnBigText = findViewById(R.id.btnBigText)
        btnInbox = findViewById(R.id.btnInbox)
        btnMessaging = findViewById(R.id.btnMessaging)
    }
}