package com.vadeworks.kannadafacts;

/**
 * Created by ${Ashwin_Chandlapur} on 11/24/2016.
 */
public class backgrounds {
    Integer colors[]={
            R.color.alizarin,
            R.color.amethyst,
            R.color.asbestos,
            R.color.belizehole,
            R.color.emerald,
            R.color.peterruver,
            R.color.orange,
            R.color.colorPrimary,
            R.color.sunflower,
            R.color.turquoise,
            R.color.wetasphalt,
            R.color.colorAccent,
            R.color.casablanca,
            R.color.casablanca1,
            R.color.casablanca2,
            R.color.casablanca3,
            R.color.casablanca4,
            R.color.casablanca5,
            R.color.casablanca6,
            R.color.casablanca7,
            R.color.casablanca8,
            R.color.casablanca9,
            R.color.casablanca10,
            R.color.casablanca11,
            R.color.casablanca12,
            R.color.casablanca13
    };

    Integer i=-1;

    public int getBackground(){
        i++;
        try{
            return colors[i];
        }
        catch(Exception e)
        {
            i=0;
            return colors[i];
        }
    }
}
