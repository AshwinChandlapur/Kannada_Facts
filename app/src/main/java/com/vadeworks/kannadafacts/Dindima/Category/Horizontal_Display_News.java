package com.vadeworks.kannadafacts.Dindima.Category;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.tooltip.Tooltip;
import com.udevel.widgetlab.TypingIndicatorView;
import com.vadeworks.kannadafacts.News;
import com.vadeworks.kannadafacts.R;

import java.util.ArrayList;


public class Horizontal_Display_News extends AppCompatActivity {

    ViewPager mViewPager;
    private ArrayList<News> news = new ArrayList<>();
    private int position;
    TypingIndicatorView typingIndicatorView;
    CustomPagerAdapters mCustomPagerAdapter;
    TextView loadingText;
    FirebaseFirestore firestoreNews;
    private ArrayList<News> newsList = new ArrayList<>();
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_display_news);
        android.support.v7.app.ActionBar AB = getSupportActionBar();
        AB.hide();
        typingIndicatorView = findViewById(R.id.loader);
        loadingText = findViewById(R.id.loadingText);

        final Tooltip tooltip = new Tooltip.Builder(loadingText)
                .setText("Swipe Left To Read News")
                .show();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("id");
            Log.d("id is",value);
            //The key argument here must match that used in the other activity
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                    Log.d("60News","60News");
                    Parsers parsers = new Parsers();
                    news = parsers.MainPage(value);
            }
        }).start();


        firestoreNews = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firestoreNews.setFirestoreSettings(settings);

        firestoreNews.collection(value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Log.d("AllContent", "all" + documentSnapshot.get("content"));
//                              News news = documentSnapshot.toObject(News.class);

                                News news1 = new News();
                                news1.head = (documentSnapshot.get("head") != null) ? documentSnapshot.get("head").toString() : "";
                                news1.link = (documentSnapshot.get("link") != null) ? documentSnapshot.get("link").toString() : "";
                                news1.content = (documentSnapshot.get("content") != null) ? documentSnapshot.get("content").toString() : "";
                                news1.content = news1.content.substring(0,news1.content.indexOf("\n"));
                                news1.thumburl = (documentSnapshot.get("thumburl") != null) ? documentSnapshot.get("thumburl").toString() : "";
                                news1.imgurl = (documentSnapshot.get("imgurl") != null) ? documentSnapshot.get("imgurl").toString() : "";
                                news1.tag = (documentSnapshot.get("tag") != null) ? documentSnapshot.get("tag").toString() : "";
                                news1.subtag = (documentSnapshot.get("subtag") != null) ? documentSnapshot.get("subtag").toString() : "";
                                news1.showNews();
                                if (!(news1.isEmpty()))
                                    newsList.add(news1);

                            }
                            Log.d("Starting Fetch", "Finishing Fetch");
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops, Something went wrong. Could'nt Fetch News :( ", Toast.LENGTH_SHORT).show();
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }

                        if(news.size()!=0){
                            news.addAll(newsList);
                            mCustomPagerAdapter = new CustomPagerAdapters(getApplicationContext(), news, 0);
                        }else{
                            mCustomPagerAdapter = new CustomPagerAdapters(getApplicationContext(), newsList, 0);
                        }

                        mViewPager = findViewById(R.id.pager);
                        mViewPager.setAdapter(mCustomPagerAdapter);
//                        mViewPager.setCurrentItem(3);
                        mViewPager.setOffscreenPageLimit(3);
                        typingIndicatorView.setVisibility(View.GONE);
                        loadingText.setVisibility(View.GONE);
                        tooltip.dismiss();
                    }
                });
    }


}
