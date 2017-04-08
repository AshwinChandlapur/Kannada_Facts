package com.vadeworks.kannadafacts;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;

public class MainActivitykan extends AppCompatActivity {
    TextView fact;
    FrameLayout background;
    backgrounds backgroundcolor=new backgrounds();
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    int randindex=0;


    String[] imgs={"http://i.dailymail.co.uk/i/pix/2010/04/28/article-0-02211C5F000004B0-464_306x423.jpg",
            "https://s-media-cache-ak0.pinimg.com/564x/fe/07/f2/fe07f2d2d179cd55f5b51a0b8fab8e8b.jpg",
            "https://flipsideflorida.files.wordpress.com/2015/04/pablo-picasso-buste-de-femme-1.jpg",
            "https://s-media-cache-ak0.pinimg.com/originals/25/77/2e/25772e16e5fd0620c82ec86356998b60.jpg",
            "http://corioblog.com/picasso_matador.jpg",
            "http://i.imgur.com/DvpvklR.png"
    };

    //initiate cursor and point to null
    Cursor c=null;

    //to get db length
    int db_length;

    //img view and ImageButton instances
    ImageView ivinst;
    ImageButton share_kan_fact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_activitykan);
        //Font Setting for Facts
        fact=(TextView)findViewById(R.id.fact);
        Typeface myFont = Typeface.createFromAsset(getAssets(),"fonts/quicksand.otf");
        fact.setTypeface(myFont);



        //create a DBhelper instance to get cursor
        DatabaseHelper myDbHelper = new DatabaseHelper(MainActivitykan.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        //put cursor in allfacts table of facts db
        c = myDbHelper.query("allfacts", null, null, null, null, null, null);

        //1st row of allfacts table
        c.moveToFirst();

        //gettotal no of rows in table
        db_length=c.getCount();

//Get share button and set onclick listener
        share_kan_fact =(ImageButton)findViewById(R.id.kan_fact);
        share_kan_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "https://play.google.com/store/apps/details?id=" + getPackageName() ;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        fact.getText()+ "\n\nFor more Interesting facts Install the app \""+ getString(R.string.app_name)+ "\"\n " + str);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        //set visibility to Gone at Oncreate()
        share_kan_fact.setVisibility(View.GONE);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        background=(FrameLayout)findViewById(R.id.background);
        background.setBackgroundColor(getResources().getColor(backgroundcolor.getBackground()));
        background.setOnTouchListener(new OnSwipeTouchListener(this)
        {
            @Override
            public void onSwipeTop() {
                // super.onSwipeTop();
                next();
            }

            @Override
            public void onSwipeRight() {
                next();
                //super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                next();
                // super.onSwipeLeft();
            }

            @Override
            public void onSwipeBottom() {
                next();
                //super.onSwipeBottom();
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return super.onTouch(v, event);
            }
        });
    }

    private void next() {

        //      set share button visibility to visible onSwipe call
        share_kan_fact.setVisibility(View.VISIBLE);

//      get randnum to point to a random row
        int c_row=randnum();

        randindex++;
        if(randindex==5) randindex=randindex-5;
//        Toast.makeText(MainActivitykan.this, "The rand num :" +  c.getString(3), Toast.LENGTH_SHORT).show();

//      point to rand row
        c.moveToPosition(c_row);
//        Toast.makeText(MainActivitykan.this, "The url:" + c_row + "\n" +  c.getString(1) + "\n" + c.getString(3), Toast.LENGTH_SHORT).show();

//      set fact textview
//       use c.getString(COL) method to get ids if required
        fact.setText(c.getString(2));

        //background.setBackgroundResource(R.drawable.mybg);


        ivinst=(ImageView)findViewById(R.id.hideme);

        Glide.with(this.getApplicationContext())
                .load(c.getString(3))
                .placeholder(R.drawable.mybg)
                .into(ivinst);


        Glide.with(this.getApplicationContext())
                .load(imgs[randindex+1])
                .preload();
        background.setBackgroundColor(getResources().getColor(backgroundcolor.getBackground()));
    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder().build();
                interstitial = new InterstitialAd(MainActivitykan.this);
// Insert the Ad Unit ID
                interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
                interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
                interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
// Call displayInterstitial() function
                        if (interstitial.isLoaded()) {
                            interstitial.show();
                        }
                    }
                });
            }
        }, 2500);
        Intent intent=new Intent(MainActivitykan.this,SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public int randnum()
    {
        Random r = new Random();
        return r.nextInt(db_length);
    }


}
