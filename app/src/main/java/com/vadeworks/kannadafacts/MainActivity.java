package com.vadeworks.kannadafacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

//import com.pushbots.push.Pushbots;

public class MainActivity extends AppCompatActivity {
    TextView fact;
    FrameLayout background;
    backgrounds backgroundcolor=new backgrounds();
    InterstitialAd mInterstitialAd;
    int randindex=0;


    //img view and ImageButton instances
    ImageView ivinstance, ivbackinstance;
    ImageButton share_eng_fact;

    //initiate cursor and point to null
    Cursor c=null;
    Cursor csec=null;

    int n_row;
    int c_row;


    //to get db length
    int db_length;
    int facts_read=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        fact=(TextView)findViewById(R.id.fact);
        Typeface myFont = Typeface.createFromAsset(getAssets(),"fonts/quicksand.otf");
        fact.setTypeface(myFont);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1924436259631090/8770872369");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        //create a DBhelper instance to get cursor
        DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
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
        csec = myDbHelper.query("allfacts", null, null, null, null, null, null);

        //1st row of allfacts table
//        c.moveToFirst();

        //gettotal no of rows in table
        db_length=c.getCount();

        c_row=randnum();
        n_row=randnum();


        //Get share button and set onclick listener
        share_eng_fact =(ImageButton)findViewById(R.id.eng_fact);
        share_eng_fact.setOnClickListener(new View.OnClickListener() {
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
        share_eng_fact.setVisibility(View.GONE);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        background=(FrameLayout)findViewById(R.id.background);
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

        facts_read = facts_read+1;

        if(facts_read==7){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }

//      set share button visibility to visible onSwipe call
        share_eng_fact.setVisibility(View.VISIBLE);
//      get randnum to point to a random row
//        randindex++;
//        toast to debug
//        Toast.makeText(MainActivity.this, "c_row :" +  c_row + "\nn_row" + n_row, Toast.LENGTH_SHORT).show();
//        Toast.makeText(MainActivitykan.this, "The rand num :" +  c_row + ": img-" + randindex, Toast.LENGTH_SHORT).show();

//        if(randindex==5) randindex=randindex-5;
//      point to rand row
        c.moveToPosition(c_row);
        csec.moveToPosition(n_row);
//      set fact text view
//      use c.getString(COL) method to get ids if required
        fact.setText(c.getString(1));

        ivinstance=(ImageView)findViewById(R.id.hideme);
        ivbackinstance=(ImageView)findViewById(R.id.backdrop);
        c_row=n_row;
        n_row=randnum();




        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;

            Picasso.with(this.getApplicationContext())
                    .load(c.getString(3))
                    .fit()
                    .centerCrop()
                    .into(ivinstance);


            Picasso.with(this.getApplicationContext())
                    .load(csec.getString(3))
                    .fit()
                    .centerCrop()
                    .into(ivbackinstance);

        }
        else {
            connected = false;
            background.setBackgroundColor(getResources().getColor(backgroundcolor.getBackground()));
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MainActivity.this,SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public int randnum()
    {
        Random r = new Random();
        return r.nextInt(db_length+1);
    }

}
