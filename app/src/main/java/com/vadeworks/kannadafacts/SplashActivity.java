package com.vadeworks.kannadafacts;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Random;


public class SplashActivity extends Activity {

    String once ="once";

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 20000000;
    private KenBurnsView mKenBurns;
    private View mhistory;
    private boolean isRevealEnabled = true;
    private FirebaseAnalytics mFirebaseAnalytics;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  android.support.v7.app.ActionBar AB = getSupportActionBar();
        //AB.hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        setAnimation();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Parsers parsers = new Parsers();
//                parsers.parseHeadline();
//            }
//        }).start();


       mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
       // mKenBurns.setImageResource(R.drawable.splash_background2);
        int[] ids = new int[]{R.drawable.splash_background1,R.drawable.splash_background2};
        Random randomGenerator = new Random();
        int r= randomGenerator.nextInt(ids.length);
      this.mKenBurns.setImageDrawable(getResources().getDrawable(ids[r]));

        new Handler().postDelayed(new Runnable() {

           @Override
           public void run() {
              Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }
       }, SPLASH_TIME_OUT);


        //Read History Facts Button
       Button historyfacts=(Button)findViewById(R.id.readhistorynow);
       historyfacts.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intent);
           }
        });

        Button kannadafacts=(Button)findViewById(R.id.readkannada);
        kannadafacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivitykan.class);
                startActivity(intent);
            }
        });

        Button headlines = (Button)findViewById(R.id.headlines);
        headlines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.vadeworks.kannadafacts.Dindima.Category.Horizontal_Display_News.class);
                intent.putExtra("id", "PJ_HEADLINES");
                startActivity(intent);
            }
        });

        Button sports = (Button)findViewById(R.id.sports);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.vadeworks.kannadafacts.Dindima.Category.Horizontal_Display_News.class);
                intent.putExtra("id", "PJ_SPORTS");
                startActivity(intent);
            }
        });

        Button cinema = (Button)findViewById(R.id.cinema);
        cinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.vadeworks.kannadafacts.Dindima.Category.Horizontal_Display_News.class);
                intent.putExtra("id", "PJ_CINEMA");
                startActivity(intent);
            }
        });

        Button business = (Button)findViewById(R.id.business);
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.vadeworks.kannadafacts.Dindima.Category.Horizontal_Display_News.class);
                intent.putExtra("id", "PJ_BUSINESS");
                startActivity(intent);
            }
        });



        ImageButton share=(ImageButton)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "♥Spread The Glory Of Karnataka♥\n\nDownload it Now:\n" + str);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        final MaterialStyledDialog dialogHeader_3 = new MaterialStyledDialog.Builder(this)
                // .setHeaderDrawable(R.drawable.header)
                .setHeaderColor(R.color.alizarin)
                .setIcon(R.drawable.chat)
                .withDialogAnimation(true)
                .setTitle("Glad you liked\nKannada News & Facts♥")
                .setDescription("Your 5 ★★★★★ Rating will help us serve you better.\nKeep supporting us :)")
                .setPositive("Give us 5", new MaterialDialog.SingleButtonCallback() {
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
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"+",nikhilnagaraju96@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion about "+ getString(R.string.app_name));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .build();

        ImageButton fiveus= (ImageButton) findViewById(R.id.giveusfive);
        fiveus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_3.show();
            }
        });

        final MaterialStyledDialog dialogHeader_4 = new MaterialStyledDialog.Builder(this)
                // .setHeaderDrawable(R.drawable.header)
                .setHeaderColor(R.color.alizarin)
                .setIcon(R.drawable.logo1)
                .withDialogAnimation(true)
                .setTitle("Do you know Fascinating Facts about Kannada and Karnataka?")
                .setDescription("Share it with us & help us serve better!")
                .setPositive("Send Now", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ashwinchandlapur@gmail.com"+",nikhilnagaraju96@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                        startActivity(intent);
                    }
                })
                .setNegative("Not Now", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();

        ImageButton contribute=(ImageButton)findViewById(R.id.contribute);
        contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHeader_4.show();
            }
        });
    }

    private void setAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();
        findViewById(R.id.imagelogo).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        findViewById(R.id.imagelogo).startAnimation(anim);
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }



}