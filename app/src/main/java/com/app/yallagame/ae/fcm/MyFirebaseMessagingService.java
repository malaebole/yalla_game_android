package com.app.yallagame.ae.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.yallagame.ae.util.AppManager;
import com.app.yallagame.ae.util.Constants;
import com.app.yallagame.ae.util.Functions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
            Map<String, String> stringMap = remoteMessage.getData();
            String notType = stringMap.get("type");
            String notificationTitle = remoteMessage.getNotification().getTitle();
            Intent intent = new Intent("receive_push");
            intent.putExtra("type", notType);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

           // sendMyNotification(remoteMessage.getNotification().getBody(), notType, notificationTitle);

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        SharedPreferences preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.kFCMToken, s);
        editor.apply();
        if (Functions.getPrefValue(this, Constants.kIsSignIn).equalsIgnoreCase("1")) {
           // sendFcmTokenApi(s);
        }
    }

//    private void moveToRatingScreen(String notType, String bookingId, String clubId, String bookingType, String isRated) {
//        if (notType.equalsIgnoreCase(Constants.kBookingCompleteNotification) && !bookingId.isEmpty()) {
////            Intent intent = new Intent(this, PlayerMainTabsActivity.class);
//            Intent intent = new Intent("move_to_rating");
//            intent.putExtra("type", notType);
//            intent.putExtra("booking_id", bookingId);
//            intent.putExtra("club_id", clubId);
//            intent.putExtra("booking_type", bookingType);
//            intent.putExtra("is_rated", isRated);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
////            startActivity(intent);
//        }
//    }

//    private void sendMyNotification(String message, String notType, String title) {
//            Intent intent1 = new Intent(getApplicationContext(), NotificationsActivity.class);
//            intent1.putExtra("from_notif", true);
//            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
//
//            Uri soundUri1= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            String NOTIFICATION_CHANNEL_ID1 = "my_channel_id_01";
//
//            NotificationManager notificationManager1 =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID1, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
//
//                // Configure the notification channel.
//                notificationChannel.setDescription("");
//                notificationChannel.enableLights(true);
//                notificationChannel.setLightColor(Color.RED);
//                notificationChannel.setShowBadge(true);
//                notificationManager1.createNotificationChannel(notificationChannel);
//            }
//            NotificationCompat.Builder notificationBuilder1 = new NotificationCompat.Builder(this, "my_channel_id_01")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(title)
//                    .setContentText(message)
//                    .setAutoCancel(true)
//                    .setLights(Color.RED, 500, 500)
//                    .setSound(soundUri1);
//            notificationBuilder1.setContentIntent(pendingIntent1);
//            notificationManager1.notify(1, notificationBuilder1.build());
//
//
//    }
//
//    protected void sendFcmTokenApi(String token) {
//        String uniqueID = Functions.getPrefValue(this, Constants.kDeviceUniqueId);
//        Call<ResponseBody> call = AppManager.getInstance().apiInterface.sendFcmToken(uniqueID, "android", token);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.body() != null) {
//                    try {
//                        JSONObject object = new JSONObject(response.body().string());
//                        if (object.getInt(Constants.kStatus) == Constants.kSuccessCode) {
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }

}
