package com.vadeworks.kannadafacts.Dindima.Category;

import android.util.Log;
import android.widget.Toast;

import com.vadeworks.kannadafacts.MainActivity;
import com.vadeworks.kannadafacts.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Parsers {

    String link1 = "http://www.prajavani.net/news/category/27272.html";
    String link2 = "http://www.prajavani.net/news/category/11784.html";
    String link3 = "http://www.prajavani.net/news/category/98.html";
    String link4 = "http://www.prajavani.net/news/category/8690.html";
    String link5 = "http://www.prajavani.net/news/category/155.html";
    String link6 = "http://www.prajavani.net/news/category/81.html";
    String link7 = "http://www.prajavani.net/news/category/71.html";
    String link8 = "http://www.prajavani.net/news/category/80.html";
    String link9 = "http://www.prajavani.net/news/category/77.html";
    String link10 = "http://www.prajavani.net/news/category/84.html";
    String link11 = "http://www.prajavani.net/news/category/23647.html";
    String link12 = "http://www.prajavani.net/news/category/62.html";
    String link13 = "http://www.prajavani.net/news/category/22496.html";
    String link14 = "http://www.prajavani.net/news/category/70.html";
    String link15 = "http://www.prajavani.net/news/category/78.html";
    String link16 = "http://www.prajavani.net/news/category/40522.html";
    String link17 = "http://www.prajavani.net/news/category/33.html";
    String link18 = "http://www.prajavani.net/news/category/22498.html";
    String link19 = "http://www.prajavani.net/news/category/35056.html";
    String[] allLinks = {link1,link2,link3,link4,link5,link6,link7,link8,link9,link10,link11,link12,link13,link14,link15,link16,link17,link18,link19};


    String link21 = "http://www.prajavani.net/news/category/31.html";
    String link22 = "http://www.prajavani.net/news/category/50.html";//koppal
    String link23 = "http://www.prajavani.net/news/category/58.html";//Gadag
    String link24 = "http://www.prajavani.net/news/category/40.html";//Kalburgi
    String link25 = "http://www.prajavani.net/news/category/43.html";//CHamrajnagar
    String link26 = "http://www.prajavani.net/news/category/49.html";//CHikMagalooru
    String link27 = "http://www.prajavani.net/news/category/35.html";//Chikballapura
    String link28 = "http://www.prajavani.net/news/category/36.html";//CHitradurga
    String link29 = "http://www.prajavani.net/news/category/34.html";//TUmkur
    String link30 = "http://www.prajavani.net/news/category/53.html";//DK
    String link31 = "http://www.prajavani.net/news/category/38.html";//Davangere
    String link32 = "http://www.prajavani.net/news/category/54.html";//Dharwad
    String link33 = "http://www.prajavani.net/news/category/60.html";//Bellary
    String link34 = "http://www.prajavani.net/news/category/55.html";//Belagavi
    String link35 = "http://www.prajavani.net/news/category/46.html";//Mysore
    String link36 = "http://www.prajavani.net/news/category/63.html";//Mandya
    String link37 = "http://www.prajavani.net/news/category/37.html";//Shimogga
    String link38 = "http://www.prajavani.net/news/category/47.html";//Udupi
    String link39 = "http://www.prajavani.net/news/category/44.html";//Kodagu
    String link40 = "http://www.prajavani.net/news/category/56.html";//UK
    String link41 = "http://www.prajavani.net/news/category/30.html";//Rajya

    String[] allLinks2 = {link21,link22,link23,link24,link25,link26,link27,link28,link29,link30,link31,link32,link33,link34,link35,link36,link37,link38,link39,link40,link41};



    private String baseurl = "http://www.prajavani.net";
    private String suddi = "http://www.prajavani.net/news/";


    private String news_60_headlines="https://www.60secondsnow.com/kn/";
    private String news_60_sports="https://www.60secondsnow.com/kn/sports/";
    private String news_60_business ="https://www.60secondsnow.com/kn/business/";
    private String news_60_cinema="https://www.60secondsnow.com/kn/entertainment/";

    private ArrayList<News> headlinesList = new ArrayList<>();
    int i;

    public ArrayList<News> MainPage(String category){

        String inspecturl = new String();

        switch (category){
            case "PJ_HEADLINES":
                inspecturl= news_60_headlines;
                break;
            case "PJ_CINEMA":
                inspecturl= news_60_cinema;
                break;
            case "PJ_SPORTS":
                inspecturl= news_60_sports;
                break;
            case "PJ_BUSINESS":
                inspecturl= news_60_business;
                break;
        }

        try {
            Document d= Jsoup.connect(inspecturl).get();

            Elements topstories = d.getElementsByClass("listingpage").select("article");
            Log.d("Main", "TopStories: "+topstories.toString());
            String head;
            String imgurl;
            String link;
            String content;

            for (Element ele : topstories) {
                head= ele.select("div").select("div:eq(1)").select("h2").text();
                imgurl= ele.select("div:eq(0)").select("img").attr("src");
                imgurl = imgurl.replace("400x99/","");
                link = baseurl+ele.select("a").attr("href");
                content = ele.select("div").select("div:eq(1)").select("div.article-desc").text();
                if (!head.isEmpty())
                    headlinesList.add(new News(head, link, imgurl,content));
                Log.d("Main", "MainPage: "+imgurl);
            }

        }catch (Exception e){
            Log.e("Exception in pv head", e.toString());
        }


//            try {
//                String inspecturl= suddi;
//
//                Document d= Jsoup.connect(inspecturl).get();
//
//                Elements topstories = d.getElementsByClass(" col-lg-8 col-md-8    story_block big_node nopaddingleft nopaddingright paddingbottom15");
//                Log.d("Main", "TopStories: "+topstories.toString());
//                String head;
//                String imgurl;
//                String link;
//                String content;
//
//                for (Element ele : topstories) {
//                    head= ele.select("div:eq(1)").select("h1").text();
//                    imgurl= ele.select("div:eq(0)").select("img").attr("src");
//                    imgurl = imgurl.replace("styles/new_hp_main_big/public/","");
//                    if(imgurl.isEmpty()){
//                        imgurl= ele.select("div:eq(0)").select("img").attr("data-original");
//                        imgurl = imgurl.replace("styles/new_hp_main_big/public/","");
////                        styles/new_hp_main_big/public/
//                    }
//                    link = baseurl+ele.select("a").attr("href");
//                    content = ele.select("div:eq(1)").select("div.summary").select("p").text();
//                    if (!head.isEmpty())
//                        headlinesList.add(new News(head, link, imgurl,content));
//                    Log.d("Main", "MainPage: "+imgurl);
//                }
//
//            }catch (Exception e){
//                Log.e("Exception in pv head", e.toString());
//            }
//            Log.d("Fetch","Finish Fetch");

        return headlinesList;
    }


    public ArrayList<News> parseHeadline(){
        for(i=0;i<allLinks2.length;i++){
            try {
                String inspecturl= allLinks2[i];

                Document d= Jsoup.connect(inspecturl).get();

                Elements topstories = d.getElementsByClass(" col-lg-12 col-md-12    story_block big_node nopaddingleft nopaddingright paddingbottom15");

                String head;
                String imgurl;
                String link;
                String content;

                for (Element ele : topstories) {
                    head= ele.select("div:eq(1)").select("h1").text();
                    imgurl= ele.select("div:eq(0)").select("img").attr("src");
                    link = baseurl+ele.select("a").attr("href");
                    content = ele.select("div:eq(1)").select("div.summary").select("p").text();
                    if (!head.isEmpty())
                        headlinesList.add(new News(head, link, imgurl,content));
                    Log.d("NewsList",headlinesList.get(i).head+"\n"+headlinesList.get(i).content+"\n"+headlinesList.get(i).imgurl);
                }

            }catch (Exception e){
                Log.e("Exception in pv head", e.toString());
            }
            Log.d("Fetch","Finish Fetch");
        }

        return headlinesList;
    }


}
