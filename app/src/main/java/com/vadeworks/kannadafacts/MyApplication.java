package com.vadeworks.kannadafacts;

/**
 * Created by Nikhil on 6/7/17.
 */

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //OneSignal.startInit(this).init();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }


    class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            //get JSON data object bundled with extra content such as bigText etc
            JSONObject data = notification.payload.additionalData;
            String bigText;
            //extract bigPicture and body(heading) from notification obj
            String imgUrl= notification.payload.bigPicture;
            String heads= notification.payload.body;

            if (data != null) {
                bigText = data.optString("bigText", null);
//                imgUrl = data.optString("imgUrl",null);
                if(bigText!=null && imgUrl!=null) {
                    //set components for intent & send
                    Intent intent = new Intent(getApplicationContext(), oneSignal.class);
                    intent.putExtra("bigText", bigText);
                    intent.putExtra("imgUrl", imgUrl);
                    intent.putExtra("heads", heads);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                if (bigText != null)
                    Log.i("OneSignalExample", "customkey set with value: " + bigText);
                if (imgUrl != null)
                    Log.i("OneSignalExample", "customkey set with value: " + imgUrl);
                data.remove(bigText);//This is mandatory, because the Old JSON data will still be stored that causes error while opening newest notification
                data.remove(imgUrl);//
                data.remove(heads);
            }

        }
    }


    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            //get JSON data object bundled with extra content such as bigText etc
            JSONObject data = result.notification.payload.additionalData;
            String bigText,launchUrl;
            //extract bigPicture and body(heading) from notification obj
            String imgUrl=result.notification.payload.bigPicture;
            String heads= result.notification.payload.body;

            if (data != null) {
                bigText = data.optString("bigText", null);
                launchUrl = data.optString("launchUrl",null);
                if(bigText!=null && imgUrl!=null) {
                    Intent intent = new Intent(getApplicationContext(), oneSignal.class);
                    //set components for intent & send
                    intent.putExtra("bigText", bigText);
                    intent.putExtra("imgUrl", imgUrl);
                    intent.putExtra("heads", heads);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                if (bigText != null)
                    Log.i("OneSignalExample", "customkey set with value: " + bigText);
                if (imgUrl != null)
                    Log.i("OneSignalExample", "customkey set with value: " + imgUrl);
                if(launchUrl!=null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(launchUrl));
                    startActivity(browserIntent);
                }
                data.remove(bigText);//This is mandatory, because the Old JSON data will still be stored that causes error while opening newest notification
                data.remove(imgUrl);//
                data.remove(heads);
            }

        }
    }
}