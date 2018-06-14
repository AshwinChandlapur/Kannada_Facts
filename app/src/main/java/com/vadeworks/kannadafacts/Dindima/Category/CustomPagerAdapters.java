package com.vadeworks.kannadafacts.Dindima.Category;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;
import com.vadeworks.kannadafacts.News;
import com.vadeworks.kannadafacts.R;

import java.util.ArrayList;


/**
 * Created by ashwinchandlapur on 27/02/18.
 */

class CustomPagerAdapters extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> mNews = new ArrayList<>();
    News fullnews;
    int mPos;
    String fileName;
    TextView content_textview;
    TextView link_textview;
    ImageView imageView;

    TextView headlines_textview;

    private InterstitialAd mInterstitialAd;



    public CustomPagerAdapters(Context context, ArrayList<News> news, int position) {
        mContext = context;
        mNews = news;
        mPos = position;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-1924436259631090/8770872369");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.horizontal_pager_item, container, false);
        mPos = position;
        fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl, mNews.get(mPos).content);
        display_news(fullnews, itemView, mPos);
        container.addView(itemView);


        if(mPos==10){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CoordinatorLayout) object);
    }


    private void display_news(News fullnews, final View itemView, final int mPos) {

        headlines_textview = itemView.findViewById(R.id.headline);
        content_textview = itemView.findViewById(R.id.content);
        AdView mAdView = (AdView) itemView.findViewById(R.id.adView);
        imageView = itemView.findViewById(R.id.imageView);
        headlines_textview.setText(fullnews.head);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (!fullnews.content.isEmpty()) {
            content_textview.setText(fullnews.content);
        }
        if (!fullnews.imgurl.isEmpty()) {
            Picasso.with(mContext)
                    .load(fullnews.imgurl)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }
    }


}