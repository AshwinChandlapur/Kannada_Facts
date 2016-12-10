package com.vadeworks.kannadafacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.pushbots.push.Pushbots;


public class NotifHandler extends AppCompatActivity {


    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_general);

    // Pushbots.sharedInstance().setCustomHandler(NotifHandler.class);

        Typeface myFont = Typeface.createFromAsset(this.getAssets(),"fonts/quicksand.otf");
        TextView heading=(TextView)findViewById(R.id.heading);
        heading.setTypeface(myFont);
        Button feedback=(Button)findViewById(R.id.feedback) ;



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //interstial ad space
                AdRequest adRequests = new AdRequest.Builder().build();
                // Prepare the Interstitial Ad
                interstitial = new InterstitialAd(NotifHandler.this);
// Insert the Ad Unit ID
                interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
                interstitial.loadAd(adRequests);
// Prepare an Interstitial Ad Listener
                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
// Call displayInterstitial() function
                        displayInterstitial();
                    }
                });
//interstital finished
                //Do something after 100ms
            }
        }, 6789);
        final MaterialStyledDialog dialogHeader_1 = new MaterialStyledDialog(this)
                .setIcon(R.mipmap.ic_launcher)
                .withDialogAnimation(true)
                .setTitle("Awesome!")
                .setDescription("Glad to see you liked Karnataka Facts♥ Your 5 Star Rating will help us Serve Better.")
                .setHeaderColor(R.color.feedbackgreen)
                .setPositive("Give Us a Five", new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                })
                .setNegative("Suggestions", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .build();


        Bundle extras = getIntent().getExtras();

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_1.show();
            }
        });

        // extras.getString("imgUrl");
        if (null != extras && getIntent().getExtras().containsKey("message") || getIntent().getExtras().containsKey("imgUrl")
                ||getIntent().getExtras().containsKey("bigText")
                ) {
            TextView message = (TextView) findViewById(R.id.message);
            message.setTypeface(myFont);
            // TextView imgUrl = (TextView) findViewById(R.id.imgUrl);
            // ImageView imgView=(ImageView)findViewById(R.id.imgView);
            message.setText(extras.getString("bigText"));

            //imgUrl.setText(extras.getString("imgUrl"));
            // Picasso.with(this).load(String.valueOf(imgUrl)).into(imgView);}
        }

    }

    void displayInterstitial(){
        if (interstitial.isLoaded()  ) {
            interstitial.show();
        }
    }
}